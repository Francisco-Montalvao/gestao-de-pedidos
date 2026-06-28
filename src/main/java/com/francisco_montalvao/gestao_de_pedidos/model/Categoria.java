package com.francisco_montalvao.gestao_de_pedidos.model;


import com.francisco_montalvao.gestao_de_pedidos.model.valueobjects.NomeProduto;
import jakarta.persistence.*;


@Entity
@Table(name = "categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @AttributeOverride(name = "nome", column = @Column(name = "nome", nullable = false, unique = true))
    private NomeProduto nome;

    protected Categoria (){};

    public Categoria(String nome){
        this.nome = new NomeProduto(nome);
    }

    public Long getId() {
        return id;
    }

    public NomeProduto getNome() {
        return nome;
    }
}
