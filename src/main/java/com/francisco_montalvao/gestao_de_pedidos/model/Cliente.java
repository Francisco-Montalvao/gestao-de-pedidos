package com.francisco_montalvao.gestao_de_pedidos.model;


import com.francisco_montalvao.gestao_de_pedidos.model.valueobjects.Email;
import com.francisco_montalvao.gestao_de_pedidos.model.valueobjects.NomePessoa;
import com.francisco_montalvao.gestao_de_pedidos.model.valueobjects.Telefone;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)


@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private NomePessoa nomePessoa;

    @Embedded
    @Column(name = "email", unique = true)
    private Email email;

    @Embedded
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

    public void atualizarNome(String novoNome) {
        this.nomePessoa = new NomePessoa(novoNome);
    }

    public void atualizarEmail(String email) {
        this.email = new Email(email);
    }

    public void atualizarTelefone(String novoTelefone) {
        this.telefone = new Telefone(novoTelefone);
    }
}
