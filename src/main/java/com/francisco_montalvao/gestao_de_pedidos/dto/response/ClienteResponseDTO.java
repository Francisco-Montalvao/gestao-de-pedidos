package com.francisco_montalvao.gestao_de_pedidos.dto.response;

import com.francisco_montalvao.gestao_de_pedidos.model.Cliente;

import java.time.LocalDateTime;

public record ClienteResponseDTO(
        Long id,
        String nome,
        String email,
        String telefone,
        LocalDateTime criadoEm
) {
    public static ClienteResponseDTO toDTO(Cliente cliente){
        return new ClienteResponseDTO(
                cliente.getId(),
                cliente.getNomePessoa().nome(),
                cliente.getEmail().email(),
                cliente.getTelefone().telefone(),
                cliente.getCriadoEm()
        );
    }
}
