package com.francisco_montalvao.gestao_de_pedidos.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.francisco_montalvao.gestao_de_pedidos.model.Cliente;

import java.time.LocalDateTime;

public record ClienteResponseDTO(
        Long id,
        String nome,
        String email,
        String telefone,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String path,
        LocalDateTime criadoEm
) {
    public static ClienteResponseDTO toDTO(Cliente cliente){
        return new ClienteResponseDTO(
                cliente.getId(),
                cliente.getNome().getNome(),
                cliente.getEmail().getEmail(),
                cliente.getTelefone().getTelefone(),
                null,
                cliente.getCriadoEm()
        );
    }
}
