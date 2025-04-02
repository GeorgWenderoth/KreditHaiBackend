package com.example.demo.services;

import com.example.demo.Elements.PayBackTransactionElement;
import com.example.demo.repositorys.PayBackTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Null;
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

        if ( Double.isNaN(element.getAmount())) {
            throw new IllegalArgumentException("Ungültiger Betrag: " + element.getAmount());
        }



        return repository.save(element);
    }

    //des muss ich nor irgenwie besser machen.
    //Lieber originalAmount? um sichzr zu stellen das es keine folgefehler gibt? Brauche ich generell nen übergeordnenten service der erst alles checkt?
    public PayBackTransactionElement createValidPayBackTansaction(PayBackTransactionElement pbElement, double tAmount){
        double payBackAmount = pbElement.getAmount();
        double result = tAmount + payBackAmount;
        //warum <= 0 und net <0?
        //so? wir etwas lang?

        //des muss ich nor irgenwie besser machen. mit beseren unterschiedlichen fehlermeldungen je nach fall? und im frontend verhindern
        if(tAmount <= 0 && payBackAmount >= 0 && result <= 0 ||tAmount >= 0 && payBackAmount <= 0 && result >= 0){
            return createElement(pbElement);
        } else {
            throw new IllegalArgumentException("Die Rückzahlung übersteigt die ausstehende Schuld.");
        }

    };

    public List<PayBackTransactionElement> getElementsByTransactionId(int transactionId){
        return repository.findAllByTransactionId(transactionId);
    }


}
