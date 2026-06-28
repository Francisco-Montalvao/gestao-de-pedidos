package com.francisco_montalvao.gestao_de_pedidos.exception;


import org.springframework.http.HttpStatus;


public class RegraNegocioException extends RuntimeException{

    private final HttpStatus status;

    public RegraNegocioException(String message, HttpStatus httpStatus){
        super(message);
        this.status = httpStatus;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
