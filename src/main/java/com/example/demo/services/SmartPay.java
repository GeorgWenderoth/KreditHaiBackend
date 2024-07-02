package com.example.demo.services;

import com.example.demo.Elements.TransactionElement;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.ToDoubleFunction;

@Service
public class SmartPay  {


    TransactionService transactionService;


    public SmartPay(TransactionService transactionService) {
        this.transactionService  = transactionService;
    }

    public List<TransactionElement> smartPayAlgorytmus(int days){
       List<TransactionElement> transactionElementList =transactionService.getElements();

        Collections.sort(transactionElementList,
                Comparator.comparingDouble(TransactionElement::getFutureInterest));

        return transactionElementList;
    }

}



/* class TransactionComparator implements java.util.Comparator<TransactionElement> {
    @Override
    public int compare(TransactionElement o1,  TransactionElement o2) {
        return Double.compare (o2.getFutureInterest() - o1.getFutureInterest());
    }

    @Override
     public static<T> Comparator<T> comparingDouble(ToDoubleFunction<? super T> keyExtractor) {
         Objects.requireNonNull(keyExtractor);
         return (Comparator<T> & Serializable)
                 (c1, c2) -> Double.compare(keyExtractor.applyAsDouble(c1), keyExtractor.applyAsDouble(c2));
     }
} */
