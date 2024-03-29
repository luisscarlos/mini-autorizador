package com.miniautorizador.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CartaoInexistenteSaldoException extends RuntimeException {

    public CartaoInexistenteSaldoException() {
        super();
    }
}
