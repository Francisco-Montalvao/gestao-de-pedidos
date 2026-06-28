package com.francisco_montalvao.gestao_de_pedidos.controller;


import com.francisco_montalvao.gestao_de_pedidos.dto.request.PedidoRequestDTO;
import com.francisco_montalvao.gestao_de_pedidos.dto.request.StatusRequestDTO;
import com.francisco_montalvao.gestao_de_pedidos.dto.response.PedidoResponseDTO;
import com.francisco_montalvao.gestao_de_pedidos.dto.response.StatusResponseDTO;
import com.francisco_montalvao.gestao_de_pedidos.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> cadastrarPedido(
            @RequestBody
            @Valid PedidoRequestDTO dto,
            UriComponentsBuilder uriComponentsBuilder){
        var response = service.cadastrarPedido(dto);

        URI uri = uriComponentsBuilder
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> listarTodos (
            @RequestParam(required = false) String status,
            @RequestParam(required = false, name = "cliente_id") Long clienteId){
        return ResponseEntity.ok(service.listarTodos(status, clienteId));
    }

    @GetMapping("/{id}")
    public  ResponseEntity<PedidoResponseDTO> listarPorId(@PathVariable Long id){
        return ResponseEntity.ok(service.listarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelarPedido (@PathVariable Long id){
        service.cancelarPedido(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<StatusResponseDTO> avancarStatus (@PathVariable Long id, @RequestBody @Valid StatusRequestDTO dto){
        var status = service.avancarStatus(id, dto);
        return ResponseEntity.ok(status);
    }
}
