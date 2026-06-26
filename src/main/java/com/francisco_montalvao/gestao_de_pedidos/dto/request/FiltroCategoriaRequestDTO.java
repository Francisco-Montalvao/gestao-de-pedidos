package com.francisco_montalvao.gestao_de_pedidos.dto.request;

import org.springframework.web.bind.annotation.BindParam;

public record FiltroCategoriaRequestDTO(
        @BindParam("categoria")
        String nome
) {
}
