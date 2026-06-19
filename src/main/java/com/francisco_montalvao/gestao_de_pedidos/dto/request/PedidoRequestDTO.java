package com.francisco_montalvao.gestao_de_pedidos.dto.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PedidoRequestDTO(
        @NotNull(message = "id e obrigatório")
        @JsonAlias("cliente_id")
        Long clienteId,

        @NotNull(message = "lista nao pode estar vazia")
        List<ItemPedidoRequestDTO> itens
) {
}
