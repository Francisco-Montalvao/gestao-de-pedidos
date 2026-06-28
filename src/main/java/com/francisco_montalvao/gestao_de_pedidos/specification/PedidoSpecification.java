package com.francisco_montalvao.gestao_de_pedidos.specification;

import com.francisco_montalvao.gestao_de_pedidos.model.Pedido;
import org.springframework.data.jpa.domain.Specification;

public class PedidoSpecification {

    public static Specification<Pedido> comFiltros(String status, Long clienteId) {
        return
                statusIgual(status)
                        .and(clienteIdIgual(clienteId));
    }

    private static Specification<Pedido> clienteIdIgual(Long clienteId) {
        return (root, query, cb) -> {
            // 1. A Regra do Ignorar: Se não mandou ID, devolve null e o Spring ignora este filtro
            if (clienteId == null) {
                return null;
            }

            // 2. A Navegação: root (Pedido) -> cliente -> id
            // 3. A Ferramenta: cb.equal (Exatamente igual ao ID fornecido)
            return cb.equal(root.get("cliente").get("id"), clienteId);
        };
    }

    private static Specification<Pedido> statusIgual(String status) {
        return (root, query, cb) -> {
            if (status == null || status.isBlank()) {
                return null;
            }

            // Aqui usamos equal em vez de like, pois Status é um valor exato.
            // O .as(String.class) garante que o banco compare o Enum como texto.
            return cb.equal(root.get("status").as(String.class), status.toUpperCase());
        };
    }
}