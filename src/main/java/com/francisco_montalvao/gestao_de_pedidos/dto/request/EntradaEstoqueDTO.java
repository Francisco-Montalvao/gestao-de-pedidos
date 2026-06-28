package com.francisco_montalvao.gestao_de_pedidos.dto.request;

import jakarta.validation.constraints.Positive;

public record EntradaEstoqueDTO(

        @Positive(message = "Quantidade deve ser postiva")
        Integer quantidadeEntrada
) {
}
