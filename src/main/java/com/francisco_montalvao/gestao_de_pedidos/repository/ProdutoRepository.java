package com.francisco_montalvao.gestao_de_pedidos.repository;

import com.francisco_montalvao.gestao_de_pedidos.model.Produto;
import com.francisco_montalvao.gestao_de_pedidos.model.valueobjects.NomeProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>, JpaSpecificationExecutor<Produto> {

    boolean existsByCategoriaId(Long id);

    long countByCategoriaId(Long id);

    boolean existsByNome(NomeProduto nome);

    boolean existsByNomeAndIdNot(NomeProduto nome, Long id);

    List<Produto> findAllByAtivoTrue();

    List<Produto> findByCategoriaIdAndAtivoTrue(Long categoriaId);


}
