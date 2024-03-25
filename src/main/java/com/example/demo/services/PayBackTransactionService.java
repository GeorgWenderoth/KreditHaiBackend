package com.example.demo.services;

import com.example.demo.Elements.PayBackTransactionElement;
import com.example.demo.repositorys.PayBackTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PayBackTransactionService {

    @Autowired
    PayBackTransactionRepository repository;

    public PayBackTransactionService(){

    }

    public List<PayBackTransactionElement> getElements(){
        return repository.findAll();
    }

    public PayBackTransactionElement createElement(PayBackTransactionElement element){
        return repository.save(element);
    }

    public List<PayBackTransactionElement> getElementsByTransactionId(int transactionId){
        return repository.findAllByTransactionId(transactionId);
    }


}
