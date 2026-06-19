package com.francisco_montalvao.gestao_de_pedidos.model.enums;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public List<StatusPedido> obterProximosPassos() {
        return switch (this) {
            case PENDENTE -> List.of(CONFIRMADO, CANCELADO);
            case CONFIRMADO -> List.of(EM_PREPARO, CANCELADO);
            case EM_PREPARO -> List.of(ENVIADO);
            case ENVIADO -> List.of(ENTREGUE);
            case ENTREGUE, CANCELADO -> List.of(); // Estados finais retornam lista vazia
        };
    }
}