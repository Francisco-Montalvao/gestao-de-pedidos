package com.francisco_montalvao.gestao_de_pedidos.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProdutoRequestDTO(

        @NotBlank(message = "O nome é obrigatório.")
        @Size(min = 3, message = "O nome deve ter no mínimo 3 caracteres.")
        String nome,

        String descricao,

        @NotNull(message = "O preço é obrigatório.")
        @Positive(message = "O preço deve ser maior que zero.")
        BigDecimal preco,

        @NotNull(message = "O estoque é obrigatório.")
        @PositiveOrZero(message = "O estoque não pode ser negativo.")
        Integer estoque,

        @NotNull(message = "A categoria é obrigatória.")
        Long categoriaId
) {
}
