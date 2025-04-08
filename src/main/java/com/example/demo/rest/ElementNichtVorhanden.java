package com.example.demo.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/*@ResponseStatus(value = HttpStatus.BAD_REQUEST,
        reason = "ELEMENT_DOES_NOT_EXIST") */ //no longer needed? 08.04.25
public class ElementNichtVorhanden extends RuntimeException{
    public ElementNichtVorhanden(String message){
        super(message);
    }
}
