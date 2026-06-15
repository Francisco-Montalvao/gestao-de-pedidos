package com.francisco_montalvao.gestao_de_pedidos.controller;


import com.francisco_montalvao.gestao_de_pedidos.dto.request.CategoriaRequestDTO;
import com.francisco_montalvao.gestao_de_pedidos.dto.response.CategoriaResponseDTO;
import com.francisco_montalvao.gestao_de_pedidos.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {
    private final CategoriaService service;

    public CategoriaController(CategoriaService service) {
        this.service = service;
    }


    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> cadastrarCategoria(@RequestBody @Valid CategoriaRequestDTO dto, UriComponentsBuilder uriComponentsBuilder) {
        var response = service.cadastrarCategoria(dto);

        URI uri = uriComponentsBuilder
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(uri).body(response);

    }


    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> listarCategorias (){
        return ResponseEntity.ok().body(service.listarCategorias());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCategoria(@PathVariable Long id){
        service.deletarCategoria(id);

        return ResponseEntity.noContent().build();
    }

}
