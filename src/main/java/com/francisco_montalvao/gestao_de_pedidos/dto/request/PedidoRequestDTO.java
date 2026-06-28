package com.francisco_montalvao.gestao_de_pedidos.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PedidoRequestDTO(
        @NotNull(message = "cliente_id e obrigatório")
        Long clienteId,

        @NotNull(message = "a lista de itens nao pode ser nula")
        @NotEmpty(message = "O pedido deve ter pelo menos 1 item.")
        List<ItemPedidoRequestDTO> itens
) {
}
