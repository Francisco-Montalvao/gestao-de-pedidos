package com.francisco_montalvao.gestao_de_pedidos.repository;


import com.francisco_montalvao.gestao_de_pedidos.model.Pedido;
import com.francisco_montalvao.gestao_de_pedidos.projection.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;


@org.springframework.stereotype.Repository
public interface RelatorioRepository extends Repository<Pedido, Long> {
    @Query("""
                SELECT p.status AS status,
                       COUNT(p.id) AS quantidade, 
                       COALESCE(SUM(p.valorTotal), 0) AS receita
                FROM Pedido p 
                WHERE p.criadoEm >= :inicio AND p.criadoEm <= :fim 
                GROUP BY p.status
            """)
    List<ResumoStatusProjection> buscarResumoPorStatus(
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim
    );


    @Query("""
                SELECT CAST(p.criadoEm AS date) AS data, 
                       COUNT(p.id) AS quantidadeDePedidos, 
                       COALESCE(SUM(CASE WHEN p.status = 'CANCELADO' THEN 0 ELSE p.valorTotal END), 0) AS receita
                FROM Pedido p 
                WHERE p.criadoEm >= :inicio AND p.criadoEm <= :fim 
                GROUP BY CAST(p.criadoEm AS date)
                ORDER BY CAST(p.criadoEm AS date) ASC
            """)
    List<ResumoPorDiaProjection> buscarResumoPorDia(
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim
    );


    @Query("""
            SELECT p.id AS id, 
                   p.nome.nome AS nome, 
                   c.nome.nome AS categoria,
                   SUM(i.quantidade) AS quantidadeVendida,
                   SUM(i.quantidade * i.precoUnitario) AS receita,
                   ROW_NUMBER() OVER(ORDER BY SUM(i.quantidade) DESC) AS posicao
            FROM ItemPedido i
            JOIN i.produto p
            JOIN p.categoria c
            JOIN i.pedido ped
            WHERE ped.criadoEm >= :inicio 
              AND ped.criadoEm <= :fim
              AND ped.status != 'CANCELADO'
            GROUP BY p.id, p.nome.nome, c.nome.nome
            ORDER BY SUM(i.quantidade) DESC
            """)
    List<ProdutosMaisVendidosProjection> produtosMaisVendidos(
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim
    );


    @Query("""
            SELECT c.id AS id, 
                   c.nome.nome AS nome, 
                   SUM(i.quantidade) AS quantidadeVendida,
                   SUM(i.quantidade * i.precoUnitario) AS receita,
                   ROW_NUMBER() OVER(ORDER BY SUM(i.quantidade * i.precoUnitario) DESC) AS posicao
            FROM ItemPedido i
            JOIN i.produto p
            JOIN p.categoria c
            JOIN i.pedido ped
            WHERE ped.criadoEm >= :inicio 
              AND ped.criadoEm <= :fim
              AND ped.status != 'CANCELADO'
            GROUP BY c.id, c.nome.nome
            ORDER BY SUM(i.quantidade * i.precoUnitario) DESC
            """)
    List<RelatorioCategoriaReceitaProjection> vendasPorCategoria(
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim
    );

    @Query("""
            SELECT c.id AS id, 
                   c.nomePessoa.nome AS nome, 
                   COUNT(p.id) AS totalPedidos, 
                   SUM(p.valorTotal) AS receitaTotal, 
                   AVG(p.valorTotal) AS ticketMedio, 
                   ROW_NUMBER() OVER(ORDER BY SUM(p.valorTotal) DESC) AS posicao
            FROM Pedido p
            JOIN p.cliente c
            WHERE p.criadoEm >= :inicio 
              AND p.criadoEm <= :fim
              AND p.status != 'CANCELADO'
            GROUP BY c.id, c.nomePessoa.nome
            ORDER BY receitaTotal DESC
            """)
    List<RelatorioVendasTicketMedioClienteProjection> relatorioTicketMedioPorCliente(
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim
    );

}
