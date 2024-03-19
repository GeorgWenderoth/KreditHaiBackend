package com.adorsys.demo.rest;

public class ElementNichtVorhanden extends RuntimeException{
    public ElementNichtVorhanden(String message){
        super(message);
    }
}
