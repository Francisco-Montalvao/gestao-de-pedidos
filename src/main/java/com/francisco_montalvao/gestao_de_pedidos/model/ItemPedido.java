package com.francisco_montalvao.gestao_de_pedidos.model;


import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "itens_pedido")
public class ItenPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    private Produto produto;

    private Integer quantidade;

    private BigDecimal precoUnitario;



}
