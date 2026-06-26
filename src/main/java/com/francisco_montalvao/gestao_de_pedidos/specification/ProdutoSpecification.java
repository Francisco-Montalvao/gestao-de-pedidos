package com.francisco_montalvao.gestao_de_pedidos.specification;

import com.francisco_montalvao.gestao_de_pedidos.model.Produto;
import org.springframework.data.jpa.domain.Specification;

public class ProdutoSpecification {

    public static Specification<Produto> categoriaNomeContem(String nomeCategoria) {

        return (root, query, cb) -> {
            if (nomeCategoria == null || nomeCategoria.isBlank()) {
                return null;
            }

            // 1. Fazemos o JOIN da tabela de Produto com a tabela de Categoria
            var joinCategoria = root.join("categoria");

            // 2. Navegamos do JOIN para o VO, e do VO para a String
            return cb.like(
                    cb.lower(joinCategoria.get("nome").get("nome")),
                    "%" + nomeCategoria.toLowerCase() + "%"
            );
        };
    }
}
