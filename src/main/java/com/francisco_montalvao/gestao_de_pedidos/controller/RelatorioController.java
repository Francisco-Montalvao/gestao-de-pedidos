package com.francisco_montalvao.gestao_de_pedidos.controller;


import com.francisco_montalvao.gestao_de_pedidos.dto.response.ProdutosRelatorioResponseDTO;
import com.francisco_montalvao.gestao_de_pedidos.dto.response.RelatorioResumoPorDiaDTO;
import com.francisco_montalvao.gestao_de_pedidos.dto.response.RelatorioResumoResponseDTO;
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
        var response = service.resumoPorPeriodo(inicio, fim);

        return ResponseEntity.ok(response);
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
            @RequestParam LocalDate fim
    ) {

        return ResponseEntity.ok(service.resumoProdutosMaisVendidos(inicio, fim));
    }

}
