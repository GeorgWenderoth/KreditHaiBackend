package com.example.demo.services;

import com.example.demo.Elements.TransactionElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@EnableScheduling
@Component
public class InterestScheduler {

    TransactionService transactionService;

    @Autowired  // eneables automatic dependecy injection by spring
    public InterestScheduler(TransactionService transactionService){
    this.transactionService  = transactionService;
    }

    private ScheduledExecutorService scheduler;

    public void startScheduler() {
        System.out.println("startScheduler");
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(this::updateInterest, 0, 1, TimeUnit.MINUTES); // DAYS// Beispiel: Alle 24 Stunden aktualisieren
    }

    @Scheduled(fixedRate = 60000) // Alle 60 Sekunden ausführen
    public void updateInterest() {
        System.out.print("updateInterest");
       List<TransactionElement> transactionElements;
       try {
           transactionElements = transactionService.getElements();
           System.out.println("transactionEleents: " + transactionElements);
       } catch (Exception e) {
           System.out.println("Error");
           System.out.println( e);
       }
        transactionElements = transactionService.getElements();
        System.out.println("transactionEleents: " + transactionElements);
        LocalDate currentDate =  LocalDate.now();
      //  System.out.println("transactionEleents: " + transactionElements);
        if(transactionElements.isEmpty()){
            System.out.println("empty");
        }else {

                System.out.println("not empty");
            for (TransactionElement t : transactionElements) {
                System.out.println("for");
                System.out.println("for" + t.getLastInterstDate());
                System.out.println("for" + t.getInterestStartDate());

                System.out.println("for" + t.getOriginalAmount());
                System.out.println("for" + t.getAmount());
                long passedDays = ChronoUnit.DAYS.between(t.getInterestStartDate(), currentDate);
                long payDays = passedDays / t.getInterestFrequency();

                // es müssen genausoviele tage vergangen sein wie interestFrequency
                if (passedDays >= t.getInterestFrequency() && t.getInterestFrequency() > 0) {
                    System.out.println("if");

                    double cAmount = t.getAmount();
                    double interest = cAmount * t.getInterestRate() / 100;
                    //double newAmount = cAmount + interest;
                    // t.setAmount(newAmount);
                    // zur Sicherheit, falls das Backend mal offline sein sollte,
                    for (int i = 1; i <= payDays; i++) {
                        cAmount = cAmount + interest;

                    }
                    t.setAmount(cAmount);
                    t.setLastInterstDate(currentDate);
                    transactionService.updateElement(t);
                }


            }
        }


    }


    public void stopScheduler() {
        scheduler.shutdown();
    }

}
