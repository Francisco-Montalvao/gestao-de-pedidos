package com.francisco_montalvao.gestao_de_pedidos.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoriaRequestDTO(

        @NotBlank
        @Size(min = 3, message = "Categoria deve ter mais de 3 letras")
        String nome
) {


}
