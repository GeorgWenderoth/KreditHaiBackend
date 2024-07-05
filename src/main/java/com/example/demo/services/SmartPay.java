package com.example.demo.services;

import com.example.demo.Elements.TransactionElement;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class SmartPay  {

    TransactionService transactionService;

    public SmartPay(TransactionService transactionService) {
        this.transactionService  = transactionService;
    }

    public List<TransactionElement> smartPayAlgorytmus(int days, double payBackMoney, Integer debitorId){
        List<TransactionElement> sortedTransactions;
        if(debitorId != null){
            sortedTransactions = sortTransactionsByFutureInterest(days, transactionService.getTransactionElementsByDebitorId(debitorId));
        } else {
            sortedTransactions = sortTransactionsByFutureInterest(days, transactionService.getElements());
        }
       // List<TransactionElement> sortedTransactions = sortTransactionsByFutureInterest(days, transactionService.getElements());
        List<TransactionElement> updatedTransactions =payOfPrioritisedDepts(sortedTransactions, payBackMoney);

      return updatedTransactions;
    };

    public List<TransactionElement> getTransactionSortedByFutureInterest (int days){
       return sortTransactionsByFutureInterest(days, transactionService.getElements());
    }

    public List<TransactionElement> smartPayBackForDebitor(int days, double payBackMoney, int debitorId){

        List<TransactionElement> sortedTransactions = sortTransactionsByFutureInterest(days, transactionService.getTransactionElementsByDebitorId(debitorId));
        List<TransactionElement> updatedTransactions =payOfPrioritisedDepts(sortedTransactions, payBackMoney);

        return updatedTransactions;
    };


    //Sollen noch andere faktoren die Liste Bestimmen?
    public List<TransactionElement> sortTransactionsByFutureInterest(int days,  List<TransactionElement> transactionElementList ){

        Collections.sort(transactionElementList,
                Comparator.comparingDouble(transactionElement -> transactionElement.calculateFutureInterest(days)));

        return transactionElementList;
    }

    public List<TransactionElement> payOfPrioritisedDepts(List<TransactionElement> sortedTransactions, double payBackMoney){
        double cPayBackMoney = payBackMoney;
        // ich muss erstmal schauen ob die Gesammtschulden überhaupt so hoch sind wie payBackMoney! bz was mache ich dann?

        List<TransactionElement> updatedTransactionElements = new ArrayList<>();

            for( TransactionElement transactionElement: sortedTransactions) {
                if (cPayBackMoney != 0.00) {

                    // am anfang definieren?
                    double amount = transactionElement.getAmount();
                    double result = amount + cPayBackMoney;

                    if (result <= 0) {
                        transactionElement.setAmount(result);
                        transactionElement.setFutureInterest(transactionElement.calculateFutureInterest(7));


                        updatedTransactionElements.add( transactionElement);
                        // hier muss n break oder so, es dar ja net weiter iteriert werden sonst wird doppelt abgezogen!
                        //aber ist das die eleganteste Lösung? Clean code?, so besser?
                        // break;
                        cPayBackMoney = 0.00;
                    } else {
                        transactionElement.setAmount(0);
                        transactionElement.setFutureInterest(0);
                        updatedTransactionElements.add(transactionElement);
                        cPayBackMoney = result;
                    }

                } else {
                    break;
                }
            }

        for( TransactionElement updatedTransactionElement: updatedTransactionElements) {
            transactionService.updateElement( updatedTransactionElement);
        };
        return updatedTransactionElements;
    }

}