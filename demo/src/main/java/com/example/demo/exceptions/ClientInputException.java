package com.example.demo.exceptions;

public class ClientInputException extends RuntimeException {

    public ClientInputException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

}
