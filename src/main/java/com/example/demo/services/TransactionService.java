package com.example.demo.services;

import com.example.demo.Elements.TransactionElement;
import com.example.demo.repositorys.TransactionRepository;
import com.example.demo.rest.ElementNichtVorhanden;
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
        System.out.println("getElements TransactionService");
        System.out.println("t " + repository.findAll());
        return repository.findAll();
    }

    public TransactionElement createElement(TransactionElement element){

            TransactionElement transactionElement = new TransactionElement(
                    element.getId(), element.getDebitorId(), element.getPurpose(), element.getAmount(), element.getBorrowDate(),
                    element.getInterestRate(), element.getInterestFrequency(), element.getInterestStartDate(),element.getNotes());

            return repository.save(transactionElement);
    }

    public TransactionElement searchElement(int id){
        return repository.findById(id).orElseThrow(() -> new ElementNichtVorhanden("Das Gesuchte Element ist nicht vorhanden"));
    }
    public TransactionElement updateElement(TransactionElement element){

        return repository.save(element);
    }

   public List<TransactionElement> getTransactionElementsByDebitorId(int debitorId){

        return repository.findAllByDebitorId(debitorId);
    }

    public void caluclateTransactionAmount(int transactionId, double amount ){
        TransactionElement element = searchElement(transactionId);
        element.setAmount(element.getAmount()+ amount);
        repository.save(element);
    }



}
