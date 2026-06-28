package com.francisco_montalvao.gestao_de_pedidos.model;


import com.francisco_montalvao.gestao_de_pedidos.model.enums.StatusPedido;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    protected Pedido(){};

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

            if ( itemPedido == null ){
                throw new IllegalArgumentException("Erro item invalido");
            }

            if (!itemPedido.getProduto().getAtivo()){
                throw new IllegalArgumentException("O produto "+ itemPedido.getProduto().getNome().nome() +" está inativo e não pode ser adicionado ao pedido.");
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
            if (this.status.obterProximosPassos().isEmpty()) {
                throw new IllegalArgumentException(
                        "Transição inválida: pedido " + this.status + " não pode mudar de status."
                );
            }

            String proximos = this.status.obterProximosPassos().stream()
                    .map(Enum::name)
                    .reduce((a, b) -> a + " ou " + b)
                    .orElse("");

            throw new IllegalArgumentException(
                    "Transição inválida: pedido " + this.status + " só pode ir para " + proximos + "."
            );
        }

        if (status.equals(StatusPedido.CANCELADO)){
            for (var item : itensPedido){
                item.getProduto().entradaEstoque(item.getQuantidade());
            }
        }
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public List<ItemPedido> getItensPedido() {
        return itensPedido;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public LocalDateTime getAtualizadoEm() {
        return atualizadoEm;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return Objects.equals(id, pedido.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
