package com.francisco_montalvao.gestao_de_pedidos.service;


import com.francisco_montalvao.gestao_de_pedidos.dto.request.FiltroCategoriaRequestDTO;
import com.francisco_montalvao.gestao_de_pedidos.dto.request.ProdutoRequestDTO;
import com.francisco_montalvao.gestao_de_pedidos.dto.response.ProdutoResponseDTO;
import com.francisco_montalvao.gestao_de_pedidos.exception.RegraNegocioException;
import com.francisco_montalvao.gestao_de_pedidos.model.Categoria;
import com.francisco_montalvao.gestao_de_pedidos.model.Produto;
import com.francisco_montalvao.gestao_de_pedidos.model.valueobjects.NomeComercial;
import com.francisco_montalvao.gestao_de_pedidos.repository.CategoriaRespository;
import com.francisco_montalvao.gestao_de_pedidos.repository.ProdutoRepository;
import com.francisco_montalvao.gestao_de_pedidos.specification.ProdutoSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
            throw new RegraNegocioException("Categoria com id " + dto.categoriaId() + " Nao existe", HttpStatus.BAD_REQUEST);
        }

        if (repository.existsByNome(new NomeComercial(dto.nome()))) {
            throw new RegraNegocioException("Ja existe um produto com esse nome " + dto.nome(), HttpStatus.CONFLICT);
        }
        Categoria categoria = categoriaRepository.getReferenceById(dto.categoriaId());
        var produto = toEntity(dto, categoria);
        var produtoSalvo = repository.save(produto);
        return toDTO(produtoSalvo);
    }


    public ProdutoResponseDTO listarPorId(Long id) {
        return toDTO(buscarPorId(id));
    }

    public Page<ProdutoResponseDTO> listarTodos(
            FiltroCategoriaRequestDTO filtro,
            Pageable pageable) {

        Page<Produto> paginaDeProdutos = repository.findAll(
                ProdutoSpecification.categoriaNomeContem(filtro.nome()),
                pageable
        );
        return paginaDeProdutos.map(this::toDTO);
    }


    @Transactional
    public ProdutoResponseDTO atualizarProduto(Long id, ProdutoRequestDTO dto) {
        var produto = buscarPorId(id);

        if (!categoriaRepository.existsById(dto.categoriaId())) {
            throw new RegraNegocioException("Categoria com id " + dto.categoriaId() + " não existe", HttpStatus.BAD_REQUEST);
        }

        if (repository.existsByNomeAndIdNot(new NomeComercial(dto.nome()), id)) {
            throw new RegraNegocioException("Já existe outro produto utilizando o nome " + dto.nome(), HttpStatus.CONFLICT);
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
                p.getCategoria().getId(),
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
