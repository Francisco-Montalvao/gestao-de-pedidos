package com.francisco_montalvao.gestao_de_pedidos.service;

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
import com.francisco_montalvao.gestao_de_pedidos.model.enums.StatusPedido;
import com.francisco_montalvao.gestao_de_pedidos.repository.ClienteRepository;
import com.francisco_montalvao.gestao_de_pedidos.repository.PedidoRepository;
import com.francisco_montalvao.gestao_de_pedidos.repository.ProdutoRepository;
import com.francisco_montalvao.gestao_de_pedidos.specification.PedidoSpecification;
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
        Cliente cliente = clienteRepository.findById(dto.clienteId())
                .orElseThrow(() -> new RegraNegocioException(
                        "Cliente com id " + dto.clienteId() + " não encontrado",
                        HttpStatus.NOT_FOUND));

        Pedido pedido = new Pedido(cliente);

        for (var itemDto : dto.itens()) {
            var produto = buscarProdutoPorId(itemDto.produtoId());

            if (!produto.getAtivo()) {
                throw new RegraNegocioException("O produto '" + produto.getNome().nome() + "' está inativo e não pode ser adicionado ao pedido.", HttpStatus.BAD_REQUEST);
            }

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

    public List<PedidoResponseDTO> findAll(){
        return repository.findAll().stream()
                .map(this::toResponseDto)
                .toList();
    }


    public List<PedidoResponseDTO> listarTodos(String status, Long clienteId) {
        return repository.findAll(PedidoSpecification.comFiltros(status, clienteId)).stream()
                .map(this::toResponseDto)
                .toList();

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
    public void cancelarPedido(Long id) {
        var pedido = buscarPedidoPorId(id);

        if (!pedido.getStatus().podeTransicionarPara(StatusPedido.CANCELADO)) {
            throw new IllegalArgumentException(
                    "Transição inválida: pedido " + pedido.getStatus() + " não pode ser cancelado."
            );
        }

        pedido.avancarStatus("CANCELADO");
    }

    private Pedido buscarPedidoPorId(Long id) {
        return repository.buscarPorIdComJpql(id)
                .orElseThrow(
                        () -> new RegraNegocioException("Pedido com id " + id + " não encontrado.", HttpStatus.NOT_FOUND)
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
                () -> new RegraNegocioException("Produto com id " + id + " não encontrado.", HttpStatus.NOT_FOUND)
        );
    }
}
