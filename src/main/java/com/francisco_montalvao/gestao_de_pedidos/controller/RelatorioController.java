package com.francisco_montalvao.gestao_de_pedidos.controller;


import com.francisco_montalvao.gestao_de_pedidos.dto.response.*;
import com.francisco_montalvao.gestao_de_pedidos.service.RelatorioService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

    private final RelatorioService service;

    public RelatorioController(RelatorioService service) {
        this.service = service;
    }


    @GetMapping("/pedidos/resumos")
    public ResponseEntity<RelatorioResumoResponseDTO> resumoPorStatus(
            @RequestParam LocalDate inicio,
            @RequestParam LocalDate fim) {

        return ResponseEntity.ok(service.resumoPorPeriodo(inicio, fim));
    }


    @GetMapping("/pedidos/por-dia")
    public ResponseEntity<RelatorioResumoPorDiaDTO> resumoPorDia(
            @RequestParam LocalDate inicio,
            @RequestParam LocalDate fim) {

        return ResponseEntity.ok(service.resumoPorDiaDTO(inicio, fim));
    }

    @GetMapping("/produtos/mais-vendidos")
    public ResponseEntity<ProdutosRelatorioResponseDTO> produtosMaisVendidos(
            @RequestParam LocalDate inicio,
            @RequestParam LocalDate fim) {

        return ResponseEntity.ok(service.resumoProdutosMaisVendidos(inicio, fim));
    }

    @GetMapping("/categorias/receita")
    public ResponseEntity<RelatorioPorCategoriaDTO> relatorioVendaCategoria(
            @RequestParam LocalDate inicio,
            @RequestParam LocalDate fim){

        return ResponseEntity.ok(service.receitaPorCategoria(inicio, fim));
    }

    @GetMapping("/clientes/ticket-medio")
    public ResponseEntity<RelatorioClienteTicketMedio> relatorioTicketMedio(
            @RequestParam LocalDate inicio,
            @RequestParam LocalDate fim){

        return ResponseEntity.ok(service.relatorioTicketMedioPorCliente(inicio, fim));
    }

}
