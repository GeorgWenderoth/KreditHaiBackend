package com.example.demo.services;

import com.example.demo.TransactionElement;
import com.example.demo.repositorys.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository repository;

    public TransactionService(){

    }

    public List<TransactionElement> getElements(){
        return repository.findAll();
    }

    public TransactionElement createElement(TransactionElement element){
        return repository.save(element);
    }

    public List<TransactionElement> getTransactionElementsByDebitorId(int debitorId){
        return repository.findAllByDebitorId(debitorId);
    }



}
