package com.example.demo.services;

import com.example.demo.Elements.PayBackTransactionElement;
import com.example.demo.Exceptions.DebtIncreasingPaymentException;
import com.example.demo.repositorys.PayBackTransactionRepository;
import com.example.demo.rest.ExcessPaymentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

        //des muss ich noch irgenwie besser machen. mit beseren unterschiedlichen fehlermeldungen je nach fall? und im frontend verhindern
        if(tAmount <= 0 && payBackAmount >= 0 && result <= 0 ||
           tAmount >= 0 && payBackAmount <= 0 && result >= 0){
            return createElement(pbElement);
        } else {

            throw new ExcessPaymentException("Die Rückzahlung übersteigt die ausstehende Schuld.");
        }

    };




    public double validatePayBackTansaction(double payBackMoney, double tAmount){

        double result = tAmount + payBackMoney;
        //warum <= 0 und net <0?
        //so? wir etwas lang?boo


        //des muss ich noch irgenwie besser machen. mit beseren unterschiedlichen fehlermeldungen je nach fall? und im frontend verhindern
        if(tAmount <= 0 && payBackMoney >= 0 && result <= 0 ||
           tAmount >= 0 && payBackMoney <= 0 && result >= 0){
                return result;
        } else {

            throw new ExcessPaymentException("Die Rückzahlung übersteigt die ausstehende Schuld.");
        }
    }


    public PayBackTransactionElement createValidPayBackTansactionEXPERIMENT(PayBackTransactionElement pbElement, double tAmount){
        double payBackAmount = pbElement.getAmount();
        double result = tAmount + payBackAmount;
        //warum <= 0 und net <0?
        //so? wir etwas lang?

        //des muss ich noch irgenwie besser machen. mit beseren unterschiedlichen fehlermeldungen je nach fall? und im frontend verhindern
        try{
            validatePayBackTansactionEXPERIMENT(payBackAmount, tAmount, result);
            return createElement(pbElement);
        } catch (DebtIncreasingPaymentException e){
            throw new DebtIncreasingPaymentException("Die Rückzahlung ist Invalid, da sie die ausstehende Schuld erhöht, oder nichts zurück zahlt DebtIncreasingPaymentException:");

        } catch (ExcessPaymentException e){
            throw e;
            //throw new ExcessPaymentException("Die Rückzahlung übersteigt die ausstehende Schuld. ExcessPaymentException:");
        }

    };


    public boolean validatePayBackTansactionEXPERIMENT(double payBackAmount, double transactionAmount, double result){

        //mit nem case?

        //soll ich hier zwischen Schulden und Kredit unterscheiden, also ob mir geschuldet wird oder ob ich schulde?
        //für jeden fall eine seperate fehlermeldung? aber wenn ich es im frontend nicht anzeige? waste o time?
        //ziwschen schulderhöhneder und schuld übersteigender unterscheiden, da übersteigend ja teil der smar payback logig ist

        //ergebnis auch checken?
        //auf Schuldenerrhöhende rückzahlungen checken.

        if( transactionAmount <=0 && payBackAmount <=0
         || transactionAmount >=0 && payBackAmount >=0 ){

            throw new DebtIncreasingPaymentException("Die Rückzahlung ist Invalid, da sie die ausstehende Schuld erhöht, oder nichts zurück zahlt DebtIncreasingPaymentException:");

        }

        // Schuld übersteigen checken
        //des muss ich noch irgenwie besser machen. mit beseren unterschiedlichen fehlermeldungen je nach fall? und im frontend verhindern
        //brauche ich alle drei? ist es besser mit allen drein?
        // wenn die Schulden - sind & rückzahlungsMenge + ist, & ergebniss 0 oder - ist
        if((transactionAmount < 0.00  && payBackAmount > 0.00 && result <= 0)
        // wenn die Schulden + sind & rückzahlungsMenge - ist, & ergebniss 0 oder + ist
         || transactionAmount > 0.00  && payBackAmount < 0.00 && result >= 0){
            return true;
        } else {
            //return false;
            throw new ExcessPaymentException("Die Rückzahlung übersteigt die ausstehende Schuld. ExcessPaymentException:");
        }

    }




    public List<PayBackTransactionElement> getElementsByTransactionId(int transactionId){
        return repository.findAllByTransactionId(transactionId);
    }


}
