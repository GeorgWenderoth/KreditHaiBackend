package com.example.demo.rest;


import com.example.demo.Elements.PayBackTransactionElement;

import com.example.demo.services.PayBackTransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PayBackTransactionController {


    public PayBackTransactionService service;

    public PayBackTransactionController(PayBackTransactionService payBackTransactionService){
        this.service = payBackTransactionService;
    }

    @CrossOrigin
    @PostMapping("/neuePayBackTransaktion")
    public ResponseEntity<PayBackTransactionElement> newPayBackTransaction(@RequestBody PayBackTransactionElement element){
        final PayBackTransactionElement payBackTransactionElement = service.createElement(element);
        return  new ResponseEntity<>(payBackTransactionElement, HttpStatus.CREATED); // Warum nochmal returnen
    }

    @CrossOrigin
    @GetMapping("/allePayBackTransaktionen")
    public ResponseEntity<List<PayBackTransactionElement>> getPayBackTransactions(){
        return new ResponseEntity<>(service.getElements(), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/payBackTransaktionen")
    public ResponseEntity<List<PayBackTransactionElement>> getPayBackTransactionsByTransactionId(@RequestParam int transactionId){
        return new ResponseEntity<>(service.getElementsByTransactionId(transactionId), HttpStatus.OK);
    }
}
