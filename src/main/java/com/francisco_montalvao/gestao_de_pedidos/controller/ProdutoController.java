package com.francisco_montalvao.gestao_de_pedidos.controller;

import com.francisco_montalvao.gestao_de_pedidos.dto.request.ProdutoRequestDTO;
import com.francisco_montalvao.gestao_de_pedidos.dto.response.ProdutoResponseDTO;
import com.francisco_montalvao.gestao_de_pedidos.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> cadastrarProduto(@RequestBody @Valid ProdutoRequestDTO dto, UriComponentsBuilder uriComponentsBuilder){
        var response = service.cadastrarProduto(dto);

        URI uri = uriComponentsBuilder
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> listarTodosProdutos (){
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> listarPorId(@PathVariable Long id){
        var response = service.listarPorId(id);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> atualizarProduto(@PathVariable Long id, @RequestBody ProdutoRequestDTO dto){
        var response = service.atualizarProduto(id, dto);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto (@PathVariable Long id){
        service.inativarProduto(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/reativar")
    public ResponseEntity<Void> reativarProduto(@PathVariable Long id){
        service.reativarProduto(id);
        return ResponseEntity.noContent().build();
    }

}
