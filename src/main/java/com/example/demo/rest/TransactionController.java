package com.example.demo.rest;


import com.example.demo.Elements.TransactionElement;
import com.example.demo.services.DebitorService;
import com.example.demo.services.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
/**
 * Controller
 * APi-Schnittstellen
 */
public class TransactionController {

    public TransactionService service;
    //private final TransactionService transactionService;
    private final DebitorService debitorService;


    public TransactionController(TransactionService transactionService, DebitorService debitorService ){
        this.service = transactionService;
        this.debitorService = debitorService;
    }



    @CrossOrigin
    @PostMapping("/neueTransaktion")
    public ResponseEntity<TransactionElement> newTransaction(@RequestBody TransactionElement element){
        final TransactionElement transactionElement = service.createElement(element);
        debitorService.calculateDebtsForDebitor(transactionElement.getDebitorId(), transactionElement.getOriginalAmount());
        return  new ResponseEntity<>(transactionElement, HttpStatus.CREATED); // Warum nochmal returnen

    }


    @CrossOrigin
    @GetMapping ("/alleTransaktionen")
    public ResponseEntity<List<TransactionElement>> getTransactions(){
        return new ResponseEntity<>(service.getElements(), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping ("/Transaktionen")
    public ResponseEntity<List<TransactionElement>> getTransactionsByDebitorId(@RequestParam int debitorId){
        return new ResponseEntity<>(service.getTransactionElementsByDebitorId(debitorId), HttpStatus.OK);
    }

}
