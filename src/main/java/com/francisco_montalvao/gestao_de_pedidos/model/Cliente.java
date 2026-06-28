package com.francisco_montalvao.gestao_de_pedidos.model;


import com.francisco_montalvao.gestao_de_pedidos.model.valueobjects.Email;
import com.francisco_montalvao.gestao_de_pedidos.model.valueobjects.NomePessoa;
import com.francisco_montalvao.gestao_de_pedidos.model.valueobjects.Telefone;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @AttributeOverride(name = "nome", column = @Column(name = "nome", nullable = false))
    private NomePessoa nomePessoa;

    @Embedded
    @AttributeOverride(name = "email", column = @Column(name = "email", unique = true, nullable = false))
    private Email email;

    @Embedded
    @AttributeOverride(name = "telefone", column = @Column(name = "telefone", unique = true, nullable = false ))
    private Telefone telefone;

    @CreationTimestamp
    @Column(name = "criado_em", nullable = false)
    private LocalDateTime criadoEm;

    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos = new ArrayList<>();

    public Cliente(String nome, String email, String telefone) {
        this.nomePessoa = new NomePessoa(nome);
        this.email = new Email(email);
        this.telefone = new Telefone(telefone);
    }

    protected Cliente(){}

    public void atualizarNome(String novoNome) {
        this.nomePessoa = new NomePessoa(novoNome);
    }

    public void atualizarEmail(String email) {
        this.email = new Email(email);
    }

    public void atualizarTelefone(String novoTelefone) {
        this.telefone = new Telefone(novoTelefone);
    }

    public Long getId() {
        return id;
    }

    public NomePessoa getNomePessoa() {
        return nomePessoa;
    }

    public Email getEmail() {
        return email;
    }

    public Telefone getTelefone() {
        return telefone;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }
}
