package com.francisco_montalvao.gestao_de_pedidos.dto.erro;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;

public record ErroPadrao(
        @JsonProperty("timestamp")
        LocalDateTime timestamp,
        Integer status,
        String mensagem,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String path,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        List<Erros> erros
) {
}
