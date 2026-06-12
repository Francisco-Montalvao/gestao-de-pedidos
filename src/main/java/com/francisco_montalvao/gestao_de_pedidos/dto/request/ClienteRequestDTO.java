package com.francisco_montalvao.gestao_de_pedidos.dto.request;

import com.francisco_montalvao.gestao_de_pedidos.model.Cliente;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClienteRequestDTO(

        @NotBlank
        @Size(min = 3, message = "Nome deve conter mais de duas letras")
        String nome,

        @NotBlank
        @jakarta.validation.constraints.Email
        String email,

        @NotBlank
        @Size(min = 10, max = 11, message = "Telefone invalido")
        String telefone
) {
        public static Cliente toEntity(ClienteRequestDTO dto){
                return new Cliente(
                        dto.nome,
                        dto.email,
                        dto.telefone
                );
        }

}
