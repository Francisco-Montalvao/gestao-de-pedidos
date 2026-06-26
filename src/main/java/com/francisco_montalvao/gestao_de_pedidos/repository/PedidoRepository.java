package com.francisco_montalvao.gestao_de_pedidos.repository;

import com.francisco_montalvao.gestao_de_pedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>, JpaSpecificationExecutor<Pedido> {



    @Query("""
            SELECT  DISTINCT p FROM Pedido p 
            JOIN FETCH p.cliente 
            JOIN FETCH p.itensPedido itens
            JOIN FETCH itens.produto
            """)
    List<Pedido> buscarTodosComJpql();


    @Query("""
            SELECT DISTINCT p FROM Pedido p
            JOIN FETCH p.cliente
            JOIN FETCH p.itensPedido itens
            JOIN FETCH itens.produto
            WHERE p.id = :id
            """)
    Optional<Pedido> buscarPorIdComJpql(@Param("id") Long id);


}
