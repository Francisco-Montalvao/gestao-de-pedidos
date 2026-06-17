package com.francisco_montalvao.gestao_de_pedidos.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProdutoRequestDTO(


        @NotBlank(message = "Nao pode estar vazio")
        @Size(min = 3, message = "NomePessoa nao pode ter menos de 3 letras")
        String nome,


        String descricao,

        @Positive(message = "Deve ser postivo")
        BigDecimal preco,


        @PositiveOrZero(message = "Deve ser positivo")
        Integer estoque,

        @NotNull(message = "Nao pode ser nulo")
        Long categoriaId
) {

}
