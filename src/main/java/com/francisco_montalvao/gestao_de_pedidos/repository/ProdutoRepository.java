package com.francisco_montalvao.gestao_de_pedidos.repository;

import com.francisco_montalvao.gestao_de_pedidos.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    boolean existsByCategoriaId(Long id);
}
