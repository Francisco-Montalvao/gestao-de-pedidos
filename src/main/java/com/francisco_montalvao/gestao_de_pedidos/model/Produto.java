package com.francisco_montalvao.gestao_de_pedidos.model;

import com.francisco_montalvao.gestao_de_pedidos.model.valueobjects.NomeProduto;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @AttributeOverride(name = "nome", column = @Column(name = "nome", nullable = false, unique = true))
    private NomeProduto nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(nullable = false)
    private BigDecimal preco;

    @Column(nullable = false)
    private Integer estoque;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @Column(nullable = false)
    private Boolean ativo = true;

    @CreationTimestamp
    @Column(name = "criado_em")
    private LocalDateTime criadoEm;

    // Construtor protegido exigido pelo JPA
    protected Produto() {}

    public Produto(String nome, String descricao, BigDecimal preco, Integer estoque, Categoria categoria) {
        validarPreco(preco);
        validarEstoque(estoque);

        this.nome = new NomeProduto(nome);
        this.descricao = descricao;
        this.preco = preco;
        this.estoque = estoque;
        this.categoria = categoria;
    }

    private void validarPreco(BigDecimal preco) {
        if (preco == null || preco.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O preço tem que ser maior que zero");
        }
    }

    private void validarEstoque(Integer estoque) {
        if (estoque < 0) {
            throw new IllegalArgumentException("Estoque não pode ser negativo");
        }
    }

    private void validarQuantidadeMovimentacao(Integer quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }
    }

    public void atualizarPreco(BigDecimal novoPreco) {
        validarPreco(novoPreco);
        this.preco = novoPreco;
    }

    public void entradaEstoque(Integer quantidadeAdicionada) {
        validarQuantidadeMovimentacao(quantidadeAdicionada);
        this.estoque += quantidadeAdicionada;
    }

    public void saidaEstoque(Integer quantidadeVendida) {
        validarQuantidadeMovimentacao(quantidadeVendida);
        if (getEstoque() < quantidadeVendida) {
            throw new IllegalArgumentException(
                    "Estoque insuficiente para o produto '" + this.nome.nome() +
                            "'. Disponível: " + this.estoque + ", solicitado: " + quantidadeVendida + "."
            );
        }
        this.estoque -= quantidadeVendida;
    }

    public void atualizarInformacoes(String nome, String descricao, BigDecimal preco, Integer estoque, Categoria categoria) {
        this.nome = new NomeProduto(nome);
        this.descricao = descricao;
        this.preco = preco;
        this.estoque = estoque;
        this.categoria = categoria;
    }

    public void ativarProduto() {
        this.ativo = true;
    }

    public void inativarProduto() {
        this.ativo = false;
    }


    public Long getId() { return id; }
    public NomeProduto getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public BigDecimal getPreco() { return preco; }
    public Integer getEstoque() { return estoque; }
    public Categoria getCategoria() { return categoria; }
    public Boolean getAtivo() { return ativo; }
    public LocalDateTime getCriadoEm() { return criadoEm; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return Objects.equals(id, produto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}