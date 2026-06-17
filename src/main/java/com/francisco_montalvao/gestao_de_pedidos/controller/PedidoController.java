package com.francisco_montalvao.gestao_de_pedidos.controller;


import com.francisco_montalvao.gestao_de_pedidos.dto.response.PedidoResponseDTO;
import com.francisco_montalvao.gestao_de_pedidos.service.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> listarTodos (){
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public  ResponseEntity<PedidoResponseDTO> listarPorId(@PathVariable Long id){
        return ResponseEntity.ok(service.listarPorId(id));
    }
}
