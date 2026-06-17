package com.francisco_montalvao.gestao_de_pedidos.dto.response;

import com.francisco_montalvao.gestao_de_pedidos.model.Categoria;

public record CategoriaResponseDTO(

        Long id,
        String nome
) {
        public static CategoriaResponseDTO toDTO(Categoria categoria){
                return new CategoriaResponseDTO(
                        categoria.getId(),
                        categoria.getNome().nome()
                );
        }
}
