package com.francisco_montalvao.gestao_de_pedidos.service;


import com.francisco_montalvao.gestao_de_pedidos.dto.request.ProdutoRequestDTO;
import com.francisco_montalvao.gestao_de_pedidos.dto.response.ProdutoResponseDTO;
import com.francisco_montalvao.gestao_de_pedidos.exception.RegraNegocioException;
import com.francisco_montalvao.gestao_de_pedidos.model.Categoria;
import com.francisco_montalvao.gestao_de_pedidos.model.Produto;
import com.francisco_montalvao.gestao_de_pedidos.model.valueobjects.NomeProduto;
import com.francisco_montalvao.gestao_de_pedidos.repository.CategoriaRespository;
import com.francisco_montalvao.gestao_de_pedidos.repository.ProdutoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProdutoService {
    private final ProdutoRepository repository;
    private final CategoriaRespository categoriaRepository;

    public ProdutoService(ProdutoRepository repository, CategoriaRespository categoriaRepository1) {
        this.repository = repository;
        this.categoriaRepository = categoriaRepository1;

    }

    @Transactional
    public ProdutoResponseDTO cadastrarProduto(ProdutoRequestDTO dto) {
        if (!categoriaRepository.existsById(dto.categoriaId())) {
            throw new RegraNegocioException("A categoria informada (id: " + dto.categoriaId() + ") não existe.", HttpStatus.BAD_REQUEST);
        }

        if (repository.existsByNome(new NomeProduto(dto.nome()))) {
            throw new RegraNegocioException("Já existe um produto cadastrado com o nome '" + dto.nome() + "'.", HttpStatus.CONFLICT);
        }
        Categoria categoria = categoriaRepository.getReferenceById(dto.categoriaId());
        var produto = toEntity(dto, categoria);
        var produtoSalvo = repository.save(produto);
        return toDTO(produtoSalvo);
    }


    public ProdutoResponseDTO listarPorId(Long id) {
        return toDTO(buscarPorId(id));
    }

    public java.util.List<ProdutoResponseDTO> listarTodos(Long categoriaId) {
        if (categoriaId != null) {
            return repository.findByCategoriaIdAndAtivoTrue(categoriaId).stream()
                    .map(this::toDTO)
                    .toList();
        }
        return repository.findAllByAtivoTrue().stream()
                .map(this::toDTO)
                .toList();
    }


    @Transactional
    public ProdutoResponseDTO atualizarProduto(Long id, ProdutoRequestDTO dto) {
        var produto = buscarPorId(id);

        if (!categoriaRepository.existsById(dto.categoriaId())) {
            throw new RegraNegocioException("A categoria informada (id: " + dto.categoriaId() + ") não existe.", HttpStatus.BAD_REQUEST);
        }

        if (repository.existsByNomeAndIdNot(new NomeProduto(dto.nome()), id)) {
            throw new RegraNegocioException("Já existe um produto cadastrado com o nome '" + dto.nome() + "'.", HttpStatus.CONFLICT);
        }

        Categoria novaCategoria = categoriaRepository.getReferenceById(dto.categoriaId());

        produto.atualizarInformacoes(
                dto.nome(),
                dto.descricao(),
                dto.preco(),
                dto.estoque(),
                novaCategoria
        );


        return toDTO(produto);
    }


    @Transactional
    public void inativarProduto(Long id) {
        var produto = buscarPorId(id);

        produto.inativarProduto();
    }

    @Transactional
    public void reativarProduto(Long id) {
        var produto = buscarPorId(id);

        produto.ativarProduto();
    }


    @Transactional
    public void adicionarEstoque (Long id, Integer entradaEstoque){
        Produto produto = buscarPorId(id);

        produto.entradaEstoque(entradaEstoque);
    }

    private Produto buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new RegraNegocioException("Produto com id " + id + " Inexistente", HttpStatus.NOT_FOUND)
        );
    }


    private ProdutoResponseDTO toDTO(Produto p) {
        return new ProdutoResponseDTO(
                p.getId(),
                p.getNome().nome(),
                p.getDescricao(),
                p.getPreco(),
                p.getEstoque(),
                new com.francisco_montalvao.gestao_de_pedidos.dto.response.CategoriaSimplesDTO(
                        p.getCategoria().getId(),
                        p.getCategoria().getNome().nome()
                ),
                p.getAtivo()
        );
    }

    private Produto toEntity(ProdutoRequestDTO dto, Categoria categoria) {
        return new Produto(
                dto.nome(),
                dto.descricao(),
                dto.preco(),
                dto.estoque(),
                categoria
        );
    }
}
