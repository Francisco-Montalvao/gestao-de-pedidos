package com.francisco_montalvao.gestao_de_pedidos.exception;


import com.francisco_montalvao.gestao_de_pedidos.dto.erro.ErroPadrao;
import com.francisco_montalvao.gestao_de_pedidos.dto.erro.Erros;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
public class HandlerGlobalException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroPadrao> errosDeValidacao(MethodArgumentNotValidException ex, HttpServletRequest http){
        ErroPadrao erroPadrao = new ErroPadrao(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Erro de validação em campos",
                null,
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
                null,
                null
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroPadrao);
    }

    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<ErroPadrao> regraDeNegocio(RegraNegocioException ex, HttpServletRequest http){
        ErroPadrao erroPadrao = new ErroPadrao(
                LocalDateTime.now(),
                ex.getStatus().value(),
                ex.getMessage(),
                null,
                null
        );

        return ResponseEntity.status(ex.getStatus()).body(erroPadrao);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErroPadrao> parametrosObrigatoriosRelatorio(MissingServletRequestParameterException ex) {
        String parametro = ex.getParameterName();
        String mensagem = "Required request parameter '" + parametro + "' is not present";

        if ("data_inicio".equals(parametro) || "data_fim".equals(parametro)) {
            mensagem = "Os parâmetros 'data_inicio' e 'data_fim' são obrigatórios.";
        }

        ErroPadrao erroPadrao = new ErroPadrao(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                mensagem,
                null,
                null
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroPadrao);
    }

}
