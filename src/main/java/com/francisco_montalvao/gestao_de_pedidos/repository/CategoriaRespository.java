package com.francisco_montalvao.gestao_de_pedidos.repository;

import com.francisco_montalvao.gestao_de_pedidos.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CategoriaRespository extends JpaRepository<Categoria, Long> {
}
