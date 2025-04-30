package com.example.demo.rest;


import com.example.demo.Elements.PayBackTransactionElement;

import com.example.demo.Elements.TransactionElement;
import com.example.demo.services.DebitorService;
import com.example.demo.services.PayBackTransactionService;
import com.example.demo.services.SmartPay;
import com.example.demo.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class PayBackTransactionController {


    public PayBackTransactionService service;
    private final DebitorService debitorService;

    private final TransactionService transactionService;

    private SmartPay smartPay;

    public PayBackTransactionController(PayBackTransactionService payBackTransactionService, DebitorService debitorService, SmartPay smartPay, TransactionService transactionService){
        this.service = payBackTransactionService;
        this.debitorService = debitorService;
        this.smartPay = smartPay;
        this.transactionService = transactionService;
    }

    @CrossOrigin
    @PostMapping("/neuePayBackTransaktion")
    public ResponseEntity<PayBackTransactionElement> newPayBackTransaction(@RequestBody PayBackTransactionElement element){
        double tAmount = transactionService.getTransactionAmount(element.getTransactionId());
        final PayBackTransactionElement payBackTransactionElement = service.createValidPayBackTansaction(element, tAmount );
        debitorService.calculateDebtsForDebitor(payBackTransactionElement.getDebitorId(), payBackTransactionElement.getAmount());
        transactionService.caluclateTransactionAmount(payBackTransactionElement.getTransactionId(), payBackTransactionElement.getAmount());
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
         List<TransactionElement> transactionElementList =  smartPay.getTransactionSortedByFutureInterest(7);
        return ResponseEntity.ok(transactionElementList);
    }

    @CrossOrigin
    @PostMapping("/smartPayBack")
    public ResponseEntity<?> smartPayBack(@RequestParam(defaultValue = "7")  int days, @RequestParam double payBackMoney,
                                          @RequestParam(required = false) Integer debitorId, @RequestParam(required = false) String notes ) {
        //added try catch coz of exception 16.09
        try {
            List<TransactionElement> updatedTransactions = smartPay.smartPayAlgorytmus(days, payBackMoney, debitorId, notes);
            return ResponseEntity.ok(updatedTransactions);
        } catch (ExcessPaymentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @CrossOrigin
    @PostMapping("/smartPositivePayBack")
    public ResponseEntity<?> smartPayBackPositive(@RequestParam(defaultValue = "7")  int days, @RequestParam double payBackMoney, @RequestParam(required = false) Integer debitorId) {
        List<TransactionElement> updatedTransactions = smartPay.smartPayAlgorytmusPositiv(days, payBackMoney, debitorId);
        return ResponseEntity.ok(updatedTransactions);
    }

}
