package com.example.demo.rest;


import com.example.demo.Elements.PayBackTransactionElement;

import com.example.demo.Elements.TransactionElement;
import com.example.demo.services.DebitorService;
import com.example.demo.services.PayBackTransactionService;
import com.example.demo.services.SmartPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PayBackTransactionController {


    public PayBackTransactionService service;
    private final DebitorService debitorService;

    private SmartPay smartPay;

    public PayBackTransactionController(PayBackTransactionService payBackTransactionService, DebitorService debitorService, SmartPay smartPay){
        this.service = payBackTransactionService;
        this.debitorService = debitorService;
        this.smartPay = smartPay;
    }

    @CrossOrigin
    @PostMapping("/neuePayBackTransaktion")
    public ResponseEntity<PayBackTransactionElement> newPayBackTransaction(@RequestBody PayBackTransactionElement element){
        final PayBackTransactionElement payBackTransactionElement = service.createElement(element);
        debitorService.calculateDebtsForDebitor(payBackTransactionElement.getDebitorId(), payBackTransactionElement.getAmount());
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

    @CrossOrigin
    @GetMapping("/smartpay")
    public ResponseEntity<?> calculateSmartPay() {
        // Implementiere hier den Smart-Pay-Algorithmus

         List<TransactionElement> transactionElementList =  smartPay.smartPayAlgorytmus(7);
        // Sortiere oder berechne die priorisierten Transaktionen


        // Gib die berechneten Transaktionen zurück
        return ResponseEntity.ok(transactionElementList);
    }
}
