package com.francisco_montalvao.gestao_de_pedidos.service;

import com.francisco_montalvao.gestao_de_pedidos.dto.response.ClienteSimplificadoDTO;
import com.francisco_montalvao.gestao_de_pedidos.dto.response.ItemPedidoResponseDTO;
import com.francisco_montalvao.gestao_de_pedidos.dto.response.PedidoResponseDTO;
import com.francisco_montalvao.gestao_de_pedidos.exception.RegraNegocioException;
import com.francisco_montalvao.gestao_de_pedidos.model.ItemPedido;
import com.francisco_montalvao.gestao_de_pedidos.model.Pedido;
import com.francisco_montalvao.gestao_de_pedidos.repository.PedidoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PedidoService {
    private final PedidoRepository repository;

    public PedidoService(PedidoRepository repository) {
        this.repository = repository;
    }


    public PedidoResponseDTO listarPorId(Long id) {
        var response = buscarPorId(id);

        return criarPedido(response);

    }


    public List<PedidoResponseDTO> listarTodos() {
        return repository
                .buscarTodosComJpql()
                .stream()
                .map(this::criarPedido)
                .toList();

    }


    private Pedido buscarPorId(Long id) {
        return repository.buscarPorIdComJpql(id)
                .orElseThrow(
                        () -> new RegraNegocioException("Pedido com " + id + " nao encontrado", HttpStatus.NOT_FOUND)
                );
    }







    private PedidoResponseDTO criarPedido(Pedido pedido) {
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
                itemPedido.getId(),
                itemPedido.getProduto().getNome().nome(),
                itemPedido.getQuantidade(),
                itemPedido.getPrecoUnitario(),
                itemPedido.getSubtotal()
        );
    }


}
