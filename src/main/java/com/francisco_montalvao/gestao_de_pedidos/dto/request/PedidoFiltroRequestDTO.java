package com.francisco_montalvao.gestao_de_pedidos.dto.request;

import org.springframework.web.bind.annotation.BindParam;

public record PedidoFiltroRequestDTO(
        String status,

        @BindParam("cliente_id")
        Long clienteId

) {
}
