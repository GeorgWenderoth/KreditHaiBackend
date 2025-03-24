package com.example.demo.rest;

public class ExcessPaymentException extends RuntimeException {
    public ExcessPaymentException(String message) {
        super(message);
    }
}
