package com.francisco_montalvao.gestao_de_pedidos.service;

import com.francisco_montalvao.gestao_de_pedidos.dto.request.PedidoFiltroRequestDTO;
import com.francisco_montalvao.gestao_de_pedidos.dto.request.PedidoRequestDTO;
import com.francisco_montalvao.gestao_de_pedidos.dto.request.StatusRequestDTO;
import com.francisco_montalvao.gestao_de_pedidos.dto.response.ClienteSimplificadoDTO;
import com.francisco_montalvao.gestao_de_pedidos.dto.response.ItemPedidoResponseDTO;
import com.francisco_montalvao.gestao_de_pedidos.dto.response.PedidoResponseDTO;
import com.francisco_montalvao.gestao_de_pedidos.dto.response.StatusResponseDTO;
import com.francisco_montalvao.gestao_de_pedidos.exception.RegraNegocioException;
import com.francisco_montalvao.gestao_de_pedidos.model.Cliente;
import com.francisco_montalvao.gestao_de_pedidos.model.ItemPedido;
import com.francisco_montalvao.gestao_de_pedidos.model.Pedido;
import com.francisco_montalvao.gestao_de_pedidos.model.Produto;
import com.francisco_montalvao.gestao_de_pedidos.repository.ClienteRepository;
import com.francisco_montalvao.gestao_de_pedidos.repository.PedidoRepository;
import com.francisco_montalvao.gestao_de_pedidos.repository.ProdutoRepository;
import com.francisco_montalvao.gestao_de_pedidos.specification.PedidoSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PedidoService {
    private final PedidoRepository repository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    public PedidoService(PedidoRepository repository, ClienteRepository clienteRepository, ProdutoRepository produtoRepository) {
        this.repository = repository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
    }

    @Transactional
    public PedidoResponseDTO cadastrarPedido(PedidoRequestDTO dto) {
        if (dto == null) {
            throw new RegraNegocioException(
                    "Pedido inválido",
                    HttpStatus.BAD_REQUEST);
        }

        Cliente cliente = clienteRepository.findById(dto.clienteId())
                .orElseThrow(() -> new RegraNegocioException(
                        "Cliente com id " + dto.clienteId() + " não encontrado",
                        HttpStatus.NOT_FOUND));

        if (dto.itens() == null || dto.itens().isEmpty()) {
            throw new RegraNegocioException(
                    "O pedido deve possuir ao menos um item",
                    HttpStatus.BAD_REQUEST
            );
        }

        Pedido pedido = new Pedido(cliente);

        for (var itemDto : dto.itens()) {
            var produto = buscarProdutoPorId(itemDto.produtoId());

            produto.saidaEstoque(itemDto.quantidade());

            ItemPedido itemPedido = new ItemPedido(pedido, produto, itemDto.quantidade());

            pedido.adicionarItem(itemPedido);
        }

        Pedido pedidoSalvo = repository.save(pedido);

        return toResponseDto(pedidoSalvo);
    }

    public PedidoResponseDTO listarPorId(Long id) {
        var response = buscarPedidoPorId(id);

        return toResponseDto(response);

    }

    public Page<PedidoResponseDTO> listarTodos(PedidoFiltroRequestDTO filtro, Pageable pageable) {
        return repository
                .findAll(PedidoSpecification.comFiltros(filtro), pageable)
                .map(this::toResponseDto);

    }

    @Transactional
    public StatusResponseDTO avancarStatus (Long id, StatusRequestDTO dto){
        var pedido = buscarPedidoPorId(id);

        pedido.avancarStatus(dto.status());

        repository.flush();

        return new StatusResponseDTO(
                pedido.getId(),
                pedido.getStatus().toString(),
                pedido.getAtualizadoEm()
        );
    }


    @Transactional
    public void deletarPedido(Long id) {
        var pedido = buscarPedidoPorId(id);

        pedido.avancarStatus("CANCELADO");
    }

    private Pedido buscarPedidoPorId(Long id) {
        return repository.buscarPorIdComJpql(id)
                .orElseThrow(
                        () -> new RegraNegocioException("Pedido com " + id + " nao encontrado", HttpStatus.NOT_FOUND)
                );
    }

    private PedidoResponseDTO toResponseDto(Pedido pedido) {
        return new PedidoResponseDTO(
                pedido.getId(),
                new ClienteSimplificadoDTO(
                        pedido.getCliente().getId(),
                        pedido.getCliente().getNomePessoa().nome()
                ),
                pedido.getStatus().toString(),
                pedido.getValorTotal(),
                pedido.getItensPedido()
                        .stream()
                        .map(this::criarItemPedido)
                        .toList(),
                pedido.getCriadoEm(),
                pedido.getAtualizadoEm()

        );
    }

    private ItemPedidoResponseDTO criarItemPedido(ItemPedido itemPedido) {
        return new ItemPedidoResponseDTO(
                itemPedido.getProduto().getId(),
                itemPedido.getProduto().getNome().nome(),
                itemPedido.getQuantidade(),
                itemPedido.getPrecoUnitario(),
                itemPedido.getSubtotal()
        );
    }

    private Produto buscarProdutoPorId(Long id) {
        return produtoRepository.findById(id).orElseThrow(
                () -> new RegraNegocioException("Produto com id " + id + " Inexistente", HttpStatus.NOT_FOUND)
        );
    }
}
