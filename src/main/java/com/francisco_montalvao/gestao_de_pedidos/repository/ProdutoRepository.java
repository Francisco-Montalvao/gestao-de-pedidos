package com.francisco_montalvao.gestao_de_pedidos.repository;

import com.francisco_montalvao.gestao_de_pedidos.model.Produto;
import com.francisco_montalvao.gestao_de_pedidos.model.valueobjects.NomeComercial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>, JpaSpecificationExecutor<Produto> {

    boolean existsByCategoriaId(Long id);

    boolean existsByNome(NomeComercial nome);

    boolean existsByNomeAndIdNot(NomeComercial nome, Long id);

    List<Produto> findByAtivoTrue();


}
