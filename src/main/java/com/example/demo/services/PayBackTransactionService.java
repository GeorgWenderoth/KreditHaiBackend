package com.example.demo.services;

import com.example.demo.Elements.PayBackTransactionElement;
import com.example.demo.Exceptions.DebtIncreasingPaymentException;
import com.example.demo.repositorys.PayBackTransactionRepository;
import com.example.demo.rest.ExcessPaymentException;
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

        if ( Double.isNaN(element.getAmount())) {
            throw new IllegalArgumentException("Ungültiger Betrag: " + element.getAmount());
        }

        return repository.save(element);
    }


    public PayBackTransactionElement createValidPayBackTansaction(PayBackTransactionElement pbElement, double tAmount){
        double payBackAmount = pbElement.getAmount();
        double result = tAmount + payBackAmount;

        try{
            validatePayBackTansaction(payBackAmount, tAmount, result);
            return createElement(pbElement);

        } catch (DebtIncreasingPaymentException e){
            throw new DebtIncreasingPaymentException("Die Rückzahlung ist Invalid, da sie die ausstehende Schuld erhöht, oder nichts zurück zahlt DebtIncreasingPaymentException:");

        } catch (ExcessPaymentException e){
            throw e;
        }
    };


    public boolean validatePayBackTansaction(double payBackAmount, double transactionAmount, double result){

        //mit nem case?
        //soll ich hier zwischen Schulden und Kredit unterscheiden, also ob mir geschuldet wird oder ob ich schulde?


        //auf Schuldenerrhöhende rückzahlungen checken.
        if( transactionAmount <=0 && payBackAmount <=0
         || transactionAmount >=0 && payBackAmount >=0 ){

            throw new DebtIncreasingPaymentException("Die Rückzahlung ist Invalid, da sie die ausstehende Schuld erhöht, oder nichts zurück zahlt DebtIncreasingPaymentException:");
        }

        // Schuld übersteigen checken
        // wenn die Schulden - sind & rückzahlungsMenge + ist, & ergebniss 0 oder - ist
        if((transactionAmount < 0.00  && payBackAmount > 0.00 && result <= 0)
        // wenn die Schulden + sind & rückzahlungsMenge - ist, & ergebniss 0 oder + ist
         || transactionAmount > 0.00  && payBackAmount < 0.00 && result >= 0){
            return true;
        } else {

            throw new ExcessPaymentException("Die Rückzahlung übersteigt die ausstehende Schuld. ExcessPaymentException:");
        }

    }


    public List<PayBackTransactionElement> getElementsByTransactionId(int transactionId){
        return repository.findAllByTransactionId(transactionId);
    }
}