package com.francisco_montalvao.gestao_de_pedidos.exception;


import com.francisco_montalvao.gestao_de_pedidos.dto.erro.ErroPadrao;
import com.francisco_montalvao.gestao_de_pedidos.dto.erro.Erros;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class HandlerGlobalException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroPadrao> errosDeValidacao(MethodArgumentNotValidException ex, HttpServletRequest http){
        ErroPadrao erroPadrao = new ErroPadrao(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Erro de validacao em campos",
                http.getRequestURI(),
                ex.getBindingResult().getFieldErrors()
                        .stream()
                        .map(
                                erro -> new Erros(
                                        erro.getField(),
                                        erro.getDefaultMessage()
                                )
                        ).toList()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroPadrao);
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErroPadrao> errosDeDominio (IllegalArgumentException ex, HttpServletRequest http){
        ErroPadrao erroPadrao = new ErroPadrao(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                http.getRequestURI(),
                null
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroPadrao);
    }

    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<?> regraDeNegocio(RegraNegocioException ex, HttpServletRequest http){
        ErroPadrao erroPadrao = new ErroPadrao(
                LocalDateTime.now(),
                ex.getStatus().value(),
                ex.getMessage(),
                http.getRequestURI(),
                null
        );

        return ResponseEntity.status(ex.getStatus()).body(erroPadrao);
    }


}
