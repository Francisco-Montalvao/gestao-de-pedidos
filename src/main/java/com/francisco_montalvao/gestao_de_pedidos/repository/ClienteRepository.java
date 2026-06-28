package com.francisco_montalvao.gestao_de_pedidos.repository;


import com.francisco_montalvao.gestao_de_pedidos.model.Cliente;
import com.francisco_montalvao.gestao_de_pedidos.model.valueobjects.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByEmail_Email(String email);

    boolean existsByEmail(Email email);
}
