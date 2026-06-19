package com.francisco_montalvao.gestao_de_pedidos.model;


import com.francisco_montalvao.gestao_de_pedidos.model.enums.StatusPedido;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @Enumerated(EnumType.STRING)
    private StatusPedido status =  StatusPedido.PENDENTE;

    @Column(name = "valor_total", nullable = false)
    private BigDecimal valorTotal;

    @OneToMany(mappedBy = "pedido", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ItemPedido> itensPedido = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "criado_em", nullable = false)
    private LocalDateTime criadoEm;

    @UpdateTimestamp
    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;

    public Pedido (Cliente clinete){
        validarCliente(clinete);
        this.cliente = clinete;
        this.valorTotal = BigDecimal.ZERO;
    }

    public BigDecimal calcularValorTotal() {
        return itensPedido.stream()
                .map(item -> item.getPrecoUnitario()
                        .multiply(BigDecimal.valueOf(item.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void validarCliente(Cliente cliente){
        if (cliente == null){
            throw new IllegalArgumentException("Cliente invalido");
        }
    }

    public void adicionarItem(ItemPedido itemPedido){

            if ( itemPedido == null){
                throw new IllegalArgumentException("Erro item invalido");
            }
            itensPedido.add(itemPedido);
            valorTotal = calcularValorTotal();
    }

    public void removerItem(ItemPedido itemPedido){
        if (itemPedido == null){
            throw new IllegalArgumentException("Erro item invalido");
        }
        if (! itensPedido.contains(itemPedido)){
            throw new IllegalArgumentException("Item nao existgente no pedido");
        }
        itensPedido.remove(itemPedido);
        valorTotal = calcularValorTotal();
    }

    public void avancarStatus(String novoStatus) {
        var status = StatusPedido.valueOf(novoStatus);

        if (!this.status.podeTransicionarPara(status)) {
            throw new IllegalArgumentException(
                    "Transição inválida: o pedido está " + this.status +
                            " e não pode mudar para " + status +
                            ". Os próximos passos permitidos são: " + this.status.obterProximosPassos()
            );
        }
        this.status = status;
    }
}
