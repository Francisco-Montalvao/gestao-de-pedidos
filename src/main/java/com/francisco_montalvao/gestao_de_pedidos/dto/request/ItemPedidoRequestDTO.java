package com.francisco_montalvao.gestao_de_pedidos.dto.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ItemPedidoRequestDTO(

        @NotNull(message = "deve vir o codigo")
        @JsonAlias("produto_id")
        Long produtoId,


        @Positive(message = "Quandidade invalida")
        Integer quantidade
) {
}
