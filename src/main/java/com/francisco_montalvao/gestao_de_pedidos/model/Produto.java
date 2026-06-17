package com.francisco_montalvao.gestao_de_pedidos.model;


import com.francisco_montalvao.gestao_de_pedidos.model.valueobjects.NomeComercial;
import com.francisco_montalvao.gestao_de_pedidos.model.valueobjects.NomePessoa;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;



@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)// construtor vazio para o reflection da jpa

@Entity
@Table(name = "produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private NomeComercial nome;

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

    public Produto(String nome, String descricao, BigDecimal preco, Integer estoque, Categoria categoria){
        validarPreco(preco);
        validarEstoque(estoque);

        this.nome = new NomeComercial(nome);
        this.descricao = descricao;
        this.preco = preco;
        this.estoque = estoque;
        this.categoria = categoria;

        criadoEm = LocalDateTime.now();
    }

    private void validarPreco(BigDecimal preco){
        if (preco == null || preco.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("o preco tem que ser maior que zero");
        }
    }

    private void validarEstoque(Integer estoque){
        if (estoque < 0){
            throw new IllegalArgumentException("Estoque tem que ser maior que zero");
        }
    }

    private void validarQuantidadeMovimentacao(Integer quantidade){
        if (quantidade <= 0){
            throw new IllegalArgumentException("Quantidade tem que ser maior que zero");
        }
    }

    public void atualizarPreco(BigDecimal novoPreco){
        validarPreco(novoPreco);
        this.preco = novoPreco;
    }

    public void entradaEstoque(Integer quantidadeAdicionada){
        validarQuantidadeMovimentacao(quantidadeAdicionada);
        this.estoque+=quantidadeAdicionada;
    }

    public void saidaEstoque(Integer quantidadeVendida){
        validarQuantidadeMovimentacao(quantidadeVendida);
        if (getEstoque() < quantidadeVendida){
            throw new IllegalArgumentException("Estoque insuficiente");
        }
        this.estoque-=quantidadeVendida;
    }



    public void atualizarInformacoes(String nome, String descricao, BigDecimal preco, Integer estoque, Categoria categoria) {
        this.nome = new NomeComercial(nome);
        this.descricao = descricao;
        this.preco = preco;
        this.estoque = estoque;
        this.categoria = categoria;
    }

    public void ativarProduto() {
        this.ativo = true;
    }

    public void inativarProduto(){
        this.ativo = false;
    }
}
