package com.francisco_montalvao.gestao_de_pedidos.service;


import com.francisco_montalvao.gestao_de_pedidos.dto.request.CategoriaRequestDTO;
import com.francisco_montalvao.gestao_de_pedidos.dto.response.CategoriaResponseDTO;
import com.francisco_montalvao.gestao_de_pedidos.exception.RegraNegocioException;
import com.francisco_montalvao.gestao_de_pedidos.model.Categoria;
import com.francisco_montalvao.gestao_de_pedidos.repository.CategoriaRespository;
import com.francisco_montalvao.gestao_de_pedidos.repository.ProdutoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CategoriaService {
    private final CategoriaRespository repository;
    private final ProdutoRepository produtoRepository;

    public CategoriaService(CategoriaRespository repository, ProdutoRepository produtoRepository) {
        this.repository = repository;
        this.produtoRepository = produtoRepository;
    }

    @Transactional
    public CategoriaResponseDTO cadastrarCategoria(CategoriaRequestDTO requestDTO){
        if (repository.existsByNome(requestDTO.nome())){
            throw new RegraNegocioException("Já existe uma categoria cadastrada com esse nome", HttpStatus.CONFLICT);
        }
        var cat = new Categoria(requestDTO.nome());

        var categoriaSalva = repository.save(cat);

        return CategoriaResponseDTO.toDTO(categoriaSalva);
    }

    public List<CategoriaResponseDTO> listarCategorias (){
        return repository.findAll()
                .stream()
                .map(CategoriaResponseDTO::toDTO)
                .toList();
    }

    public CategoriaResponseDTO listarPorId(Long id){
        return CategoriaResponseDTO .toDTO(buscarPorId(id));
    }


    @Transactional
    public void deletarCategoria(Long id) {
        var cat = buscarPorId(id);
        if (produtoRepository.existsByCategoriaId(cat.getId())){
            throw  new RegraNegocioException("Categoria com id "+ id +" possui produtos vinculados", HttpStatus.BAD_REQUEST);
        }
        repository.delete(cat);
    }

    private Categoria buscarPorId(Long id){
        return repository.findById(id).orElseThrow(
                ()-> new RegraNegocioException("Categoria com id " + id + " nao cadastradada", HttpStatus.NOT_FOUND)
        );
    }
}
