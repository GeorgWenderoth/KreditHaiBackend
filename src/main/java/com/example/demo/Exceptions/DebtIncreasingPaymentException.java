package com.example.demo.Exceptions;

public class DebtIncreasingPaymentException extends RuntimeException{

    public DebtIncreasingPaymentException(String message){
        super(message);
    }
}
