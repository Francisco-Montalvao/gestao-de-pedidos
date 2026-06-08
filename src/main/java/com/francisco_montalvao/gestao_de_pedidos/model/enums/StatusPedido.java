package com.francisco_montalvao.gestao_de_pedidos.model.enums;

public enum StatusPedido {
    PENDENTE, CONFIRMADO, EM_PREPARO, ENVIADO, ENTREGUE, CANCELADO;

    public boolean podeTransicionarPara(StatusPedido novo) {
        return switch (this) {
            case PENDENTE -> novo == CONFIRMADO || novo == CANCELADO;
            case CONFIRMADO -> novo == EM_PREPARO || novo == CANCELADO;
            case EM_PREPARO -> novo == ENVIADO;
            case ENVIADO -> novo == ENTREGUE;
            case ENTREGUE, CANCELADO -> false; // estados finais
        };
    }
}