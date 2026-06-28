package com.francisco_montalvao.gestao_de_pedidos.controller;


import com.francisco_montalvao.gestao_de_pedidos.dto.request.ClienteRequestDTO;
import com.francisco_montalvao.gestao_de_pedidos.dto.response.ClienteResponseDTO;
import com.francisco_montalvao.gestao_de_pedidos.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }


    @PostMapping
    public ResponseEntity<ClienteResponseDTO> cadastrarCliente(@RequestBody @Valid ClienteRequestDTO request, UriComponentsBuilder uriComponentsBuilder) {
        var response = service.cadastrarCliente(request);

        URI uri = uriComponentsBuilder
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listarClientes() {
        return ResponseEntity.ok().body(service.listarClientes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> buscarClientePorId(@PathVariable Long id) {

        return ResponseEntity.ok(service.listarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> atualizarCliente(@PathVariable Long id, @RequestBody @Valid ClienteRequestDTO dto) {
        var response = service.atualizarCliente(id, dto);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCliente(@PathVariable Long id) {
        service.deletarCliente(id);

        return ResponseEntity.noContent().build();
    }
}
