package com.francisco_montalvao.gestao_de_pedidos.service;


import com.francisco_montalvao.gestao_de_pedidos.dto.response.*;
import com.francisco_montalvao.gestao_de_pedidos.model.enums.StatusPedido;
import com.francisco_montalvao.gestao_de_pedidos.repository.RelatorioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Locale;

@Service
@Transactional(readOnly = true)
public class RelatorioService {

    private final RelatorioRepository repository;

    public RelatorioService(RelatorioRepository repository) {
        this.repository = repository;
    }

    public RelatorioResumoResponseDTO resumoPorPeriodo(LocalDate inicio, LocalDate fim) {

        var linhas = repository.buscarResumoPorStatus(datas.inicio(inicio), datas.fim(fim));

        var resumo = new ArrayList<ResumoStatusItemDTO>(linhas.size());
        int totalPedidos = 0;
        long pedidosCancelados = 0;
        BigDecimal receitaTotal = BigDecimal.ZERO;

        for (var linha : linhas) {
            boolean cancelado = StatusPedido.CANCELADO.name().equalsIgnoreCase(linha.getStatus());

            resumo.add(new ResumoStatusItemDTO(linha.getStatus(), linha.getQuantidade(), linha.getReceita()));
            totalPedidos += linha.getQuantidade();

            if (cancelado) {
                pedidosCancelados += linha.getQuantidade();
            } else {
                receitaTotal = receitaTotal.add(linha.getReceita());
            }
        }

        String taxaCancelamento = "0,00%";

        if (totalPedidos > 0) {
            double porcentagem = ((double) pedidosCancelados / totalPedidos) * 100;

            taxaCancelamento = String.format(new Locale("pt", "BR"), "%.2f%%", porcentagem);
        }

        return new RelatorioResumoResponseDTO(
                new PeriodoResponseDTO(inicio, fim),
                resumo,
                totalPedidos,
                receitaTotal,
                taxaCancelamento
        );
    }

    public RelatorioResumoPorDiaDTO resumoPorDiaDTO(LocalDate inicio, LocalDate fim) {

        var linhas = repository.buscarResumoPorDia(datas.inicio(inicio), datas.fim(fim));

        var dias = new ArrayList<DiasResponseDTO>(linhas.size());

        var totalPedidos = 0;

        BigDecimal receitaTotal = BigDecimal.ZERO;

        for (var linha : linhas) {

            dias.add(
                    new DiasResponseDTO(
                            linha.getData(),
                            Math.toIntExact(linha.getQuantidadeDePedidos()),
                            linha.getReceita()
                    )
            );

            totalPedidos += linha.getQuantidadeDePedidos();

            receitaTotal = receitaTotal.add(linha.getReceita());
        }
        return new RelatorioResumoPorDiaDTO(
                new PeriodoResponseDTO(inicio, fim),
                dias,
                totalPedidos,
                receitaTotal
        );
    }

    public ProdutosRelatorioResponseDTO resumoProdutosMaisVendidos(LocalDate inicio, LocalDate fim) {
        var linhas = repository.produtosMaisVendidos(datas.inicio(inicio), datas.fim(fim));

        var produtos = linhas
                .stream()
                .map(linha ->
                        new ProdutosResponseDTO(
                                Math.toIntExact(linha.getPosicao()),
                                linha.getId(),
                                linha.getNome(),
                                linha.getCategoria(),
                                Math.toIntExact(linha.getQuantidadeVendida()),
                                linha.getReceita()
                        ))
                .toList();

        return new ProdutosRelatorioResponseDTO(
                new PeriodoResponseDTO(inicio, fim),
                produtos
        );
    }


    public RelatorioPorCategoriaDTO receitaPorCategoria(LocalDate inicio, LocalDate fim) {
        var linhas = repository.produtosMaisVendidos(datas.inicio(inicio), datas.fim(fim));



        var categorias = new ArrayList<RelatorioCategoriaReceita>(linhas.size());
        var receita = BigDecimal.ZERO;

        for (var linha : linhas) {
            categorias.add(
                    new RelatorioCategoriaReceita(
                            linha.getPosicao(),
                            linha.getId(),
                            linha.getNome(),
                            Math.toIntExact(linha.getQuantidadeVendida()),
                            linha.getReceita()
                    )
            );

            receita = receita.add(linha.getReceita());
        }

        return new RelatorioPorCategoriaDTO(
                new PeriodoResponseDTO(inicio, fim),
                categorias,
                receita
        );
    }


    private static class datas{

        private static LocalDateTime inicio(LocalDate inicio){
            return inicio.atTime(LocalTime.MIN);
        }

        private static LocalDateTime fim(LocalDate fim){
            return fim.atTime(LocalTime.MAX);
        }
    }

}
