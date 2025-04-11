package com.example.demo.services;

import com.example.demo.Elements.PayBackTransactionElement;
import com.example.demo.Elements.TransactionElement;
import com.example.demo.Exceptions.DebtIncreasingPaymentException;
import com.example.demo.rest.ElementNichtVorhanden;
import com.example.demo.rest.ExcessPaymentException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class SmartPay {

    TransactionService transactionService;
    PayBackTransactionService payBackTransactionService;

    // 25.03.25
    DebitorService debitorService;

    public SmartPay(TransactionService transactionService, PayBackTransactionService payBackTransactionService, DebitorService debitorService) {
        this.transactionService = transactionService;
        this.payBackTransactionService = payBackTransactionService;
        this.debitorService = debitorService;
    }


    public List<TransactionElement> smartPayAlgorytmus(int days, double payBackMoney, Integer debitorId, String notes) {
        List<TransactionElement> sortedTransactions;

        if (payBackMoney < 0 || payBackMoney > 0) {
            if (debitorId != null) {
                sortedTransactions = sortTransactionsByFutureInterest(days, transactionService.getTransactionElementsByDebitorId(debitorId));
                if (payBackMoney < 0) {
                    Collections.reverse(sortedTransactions);
                }

            } else {
                sortedTransactions = sortTransactionsByFutureInterest(days, transactionService.getElements());
            }
            //test, damit ich  wegen der exeption die ich im valle einer überzahltung werfe keine probleme kriege? 16.09, ist aber egal glaube ich
            /*List<TransactionElement> updatedTransactions = payOfPrioritisedDepts(sortedTransactions, payBackMoney, notes);

            return updatedTransactions; */
            return  payOfPrioritisedDeptsExperiment(sortedTransactions, payBackMoney, notes, debitorId);
        } else {
            //wenn payBackMoney = 0, dann was machen?, oder soll das vorher ausgeschlossen sein?
            return null;
        }
    }


    public List<TransactionElement> smartPayAlgorytmusPositiv(int days, double payBackMoney, Integer debitorId) {
        List<TransactionElement> sortedTransactions;
        if (debitorId != null) {
            sortedTransactions = sortTransactionsByFutureInterest(days, transactionService.getTransactionElementsByDebitorId(debitorId));
        } else {
            sortedTransactions = sortTransactionsByFutureInterest(days, transactionService.getElements());
        }
        Collections.reverse(sortedTransactions);
        List<TransactionElement> updatedTransactions = payOfPrioritisedPositiveDepts(sortedTransactions, payBackMoney);

        return updatedTransactions;
    }

    public List<TransactionElement> getTransactionSortedByFutureInterest(int days) {
        return sortTransactionsByFutureInterest(days, transactionService.getElements());
    }


    //Sollen noch andere faktoren die Liste Bestimmen?
    public List<TransactionElement> sortTransactionsByFutureInterest(int days, List<TransactionElement> transactionElementList) {

        Collections.sort(transactionElementList, Comparator.comparingDouble(transactionElement -> transactionElement.calculateFutureInterest(days)));

        return transactionElementList;
    }


    //für positive und negative Rückzahlungen
    public List<TransactionElement> payOfPrioritisedDepts(List<TransactionElement> sortedTransactions, double payBackMoney, String notes, Integer debitorId) {
        double remainingPayBackMoney = payBackMoney;

        List<TransactionElement> updatedTransactionElements = new ArrayList<>();
        List<PayBackTransactionElement> payBackTransactions = new ArrayList<>();
        int payBackCounter = 0;

        for (TransactionElement transactionElement : sortedTransactions) {

            if (remainingPayBackMoney == 0.00) {
                break;
            }
            // am anfang definieren?
            double amount = transactionElement.getAmount();
            double result = amount + remainingPayBackMoney;
            LocalDate today = LocalDate.now();

            //wenn Ergebniss kleiner gleich 0 ist und payBackMoney positive ist oder wenn Ergebniss größer gleich 0 ist und payBackMoney negative ist
            // also wenn gerade genug Geld da ist um diese transaction zum Teil oder ganz abzuzahlen, aber micht mehr
            //  NegativeTransaction=positivePayBack || PositiveTransaction=negativePayBack
            if (result <= 0 && payBackMoney > 0.00 || result >= 0 && payBackMoney < 0.00) {

                payBackCounter++;
                PayBackTransactionElement payBackTransactionElement = new PayBackTransactionElement(0, transactionElement.getId(), transactionElement.getDebitorId(),
                        //cPayBack weil das ja der betrag war der zurückgezahlt wurde.
                        remainingPayBackMoney, today, notes + " " + payBackCounter);

                transactionElement.setAmount(result);
                transactionElement.setFutureInterest(transactionElement.calculateFutureInterest(7));

                payBackTransactions.add(payBackTransactionElement);
                updatedTransactionElements.add(transactionElement);

                remainingPayBackMoney = 0.00;
                break;
            } else {
                payBackCounter++;
                PayBackTransactionElement payBackTransactionElement = new PayBackTransactionElement(0, transactionElement.getId(), transactionElement.getDebitorId(), transactionElement.getAmount() * -1, today, notes + " " + payBackCounter);

                transactionElement.setAmount(0);
                transactionElement.setFutureInterest(0);

                payBackTransactions.add(payBackTransactionElement);
                updatedTransactionElements.add(transactionElement);
                remainingPayBackMoney = result;
            }


        }

        // Speichert die Änderungen einzeln ab (gäbe es hier noch en effizenteren weg? das direkt rein speicher? aber hätte halt viel fehler potenzial?
        //NegativeTransaction=positivePayBack || PositiveTransaction=negativePayBack
        if (remainingPayBackMoney <= 0.00 && payBackMoney > 0.00 || remainingPayBackMoney >= 0.00 && payBackMoney < 0.00) {
            for (TransactionElement updatedTransactionElement : updatedTransactionElements) {
                transactionService.updateElement(updatedTransactionElement);

            }

            for (PayBackTransactionElement payBackTransactionElement : payBackTransactions) {
                payBackTransactionService.createElement(payBackTransactionElement);
                /// debitorService.calculateDebtsForDebitor(...) muss noch aufgerufen werden irgendwie /
                // debitorDept muss geupdated werden, generell neustrukturieren???

            }

            debitorService.calculateDebtsForDebitor(debitorId, payBackMoney);

            return updatedTransactionElements;
        } else {
            // was tuhen,
            // anfrage zurück?
            //oder einfach Fehler?
            // was soll es returnen, wenn die if fehlschlägt, der Betrag zu groß negativ / positive ist und es zum overflow kommt?
            //Commented out below to try out trow new ElementNichtVorhanden
           // List<TransactionElement> emptyList = new ArrayList<>();
           // return emptyList;
            throw new ExcessPaymentException("Rückzahltung zu viel");
        }
    }




    public List<TransactionElement> payOfPrioritisedDeptsExperiment(List<TransactionElement> sortedTransactions, double payBackMoney, String notes, Integer debitorId) {
        double remainingPayBackMoney = payBackMoney;

        List<TransactionElement> updatedTransactionElements = new ArrayList<>();
        List<PayBackTransactionElement> payBackTransactions = new ArrayList<>();
        int payBackCounter = 0;

        for (TransactionElement transactionElement : sortedTransactions) {

            if (remainingPayBackMoney == 0.00) {
                break;
            }
            // am anfang definieren?
            double schulden = transactionElement.getAmount();
            double result = schulden + remainingPayBackMoney;
            LocalDate today = LocalDate.now();

            //wenn Ergebniss kleiner gleich 0 ist und payBackMoney positive ist oder wenn Ergebniss größer gleich 0 ist und payBackMoney negative ist
            // also wenn gerade genug Geld da ist um diese transaction zum Teil oder ganz abzuzahlen, aber micht mehr
            //  NegativeTransaction=positivePayBack || PositiveTransaction=negativePayBack
            //if (result <= 0 && payBackMoney > 0.00 || result >= 0 && payBackMoney < 0.00) {
            //wie sinnig ist das mit ry catch, wenn ich das hier eh nur als if  ersatz nehme und die unten nochmal werfe?
            try {
                // ne warte mal so geht das nicht, validate wird ja auch werfen wenn negativ bezahlt wurde
                 payBackTransactionService.validatePayBackTansactionEXPERIMENT(remainingPayBackMoney, schulden, result);


                payBackCounter++;

                PayBackTransactionElement payBackTransactionElement = new PayBackTransactionElement(0, transactionElement.getId(), transactionElement.getDebitorId(),
                        //cPayBack weil das ja der betrag war der zurückgezahlt wurde.
                        remainingPayBackMoney, today, notes + " " + payBackCounter);

                transactionElement.setAmount(result);
                transactionElement.setFutureInterest(transactionElement.calculateFutureInterest(7));

                payBackTransactions.add(payBackTransactionElement);
                updatedTransactionElements.add(transactionElement);

                remainingPayBackMoney = 0.00;
                break;
                // } else {w
            }catch(ExcessPaymentException e) {
                payBackCounter++;
                PayBackTransactionElement payBackTransactionElement = new PayBackTransactionElement(0, transactionElement.getId(), transactionElement.getDebitorId(), transactionElement.getAmount() * -1, today, notes + " " + payBackCounter);

                transactionElement.setAmount(0);
                transactionElement.setFutureInterest(0);

                payBackTransactions.add(payBackTransactionElement);
                updatedTransactionElements.add(transactionElement);
                remainingPayBackMoney = result;
            }catch(DebtIncreasingPaymentException err) {
                System.out.println("Die Rückzahlung ist Invalid, da sie die ausstehende Schuld erhöht, oder nichts zurück zahlt");
                throw new DebtIncreasingPaymentException("Die Rückzahlung ist Invalid, da sie die ausstehende Schuld erhöht, oder nichts zurück zahlt DebtIncreasingPaymentException");
            };
            //muss ich hier die DebtIncreasingPaymentException catchen? ne eigendlich net oder, also da ist ja dann ein tatsächlicher fehler.was machen?


        }
        /*
        // positive schulden ->
        if(payBackMoney < 0 && remainingPayBackMoney < 0  ||
        // negative schulden ->
           payBackMoney > 0 && remainingPayBackMoney > 0){
            throw new ExcessPaymentException("Rückzahltung zu viel");
        }
        */

        // Speichert die Änderungen einzeln ab (gäbe es hier noch en effizenteren weg? das direkt rein speicher? aber hätte halt viel fehler potenzial?
        //NegativeTransaction=positivePayBack || PositiveTransaction=negativePayBack
        //mach das hier überhaupt sinn?
        //wenn das ürig gebliebene RückzahlungsGeld 0 oder - ist, && die organiale rückzahlungsMenge + ist
        if (remainingPayBackMoney <= 0.00 && payBackMoney > 0.00
        //wenn das ürig gebliebene RückzahlungsGeld 0 oder + ist, && die organiale rückzahlungsMenge - ist
         || remainingPayBackMoney >= 0.00 && payBackMoney < 0.00) {
            for (TransactionElement updatedTransactionElement : updatedTransactionElements) {
                transactionService.updateElement(updatedTransactionElement);

            }

            for (PayBackTransactionElement payBackTransactionElement : payBackTransactions) {
                payBackTransactionService.createElement(payBackTransactionElement);
                /// debitorService.calculateDebtsForDebitor(...) muss noch aufgerufen werden irgendwie /
                // debitorDept muss geupdated werden, generell neustrukturieren???

            }

            debitorService.calculateDebtsForDebitor(debitorId, payBackMoney);

            return updatedTransactionElements;
        } else {
            // was tuhen,
            // anfrage zurück?
            //oder einfach Fehler?
            // was soll es returnen, wenn die if fehlschlägt, der Betrag zu groß negativ / positive ist und es zum overflow kommt?
            //Commented out below to try out trow new ElementNichtVorhanden
            // List<TransactionElement> emptyList = new ArrayList<>();
            // return emptyList;
            throw new ExcessPaymentException("Rückzahltung zu viel");

        }
    }






    public List<TransactionElement> payOfPrioritisedNegativeDepts(List<TransactionElement> sortedTransactions, double payBackMoney) {
        double cPayBackMoney = payBackMoney;
        // ich muss erstmal schauen ob die Gesammtschulden überhaupt so hoch sind wie payBackMoney! bz was mache ich dann?

        List<TransactionElement> updatedTransactionElements = new ArrayList<>();

        for (TransactionElement transactionElement : sortedTransactions) {

            if (cPayBackMoney == 0.00) {
                break;
            }

            // am anfang definieren?
            double amount = transactionElement.getAmount();
            double result = amount + cPayBackMoney;

            //wenn ergebniss kleiner gleich null ist, wenn also nicht mehr genug Geld, oder gerade genug geld da ist um diese transaction vollständig zurückzuzahlen
            if (result <= 0) {
                transactionElement.setAmount(result);
                transactionElement.setFutureInterest(transactionElement.calculateFutureInterest(7));
                updatedTransactionElements.add(transactionElement);
                cPayBackMoney = 0.00;
                break;
            } else {
                //wenn das ergebniss größer als 0 ist also Geld da ist um auch noch was von der nächsten transaction abzuzahlen
                transactionElement.setAmount(0);
                transactionElement.setFutureInterest(0);
                updatedTransactionElements.add(transactionElement);
                cPayBackMoney = result;
            }
        }

        //Kleiner gleich damit nicht zu viel geld bezahlt werden kann, bz geld das über schulden hinaus geht. Und verhindert wird das positive transactionen, aufaddiert werden.
        //wobei man sich den overflow auch zu nutze machen könte, in dem eben neue transactionen angelegt werden, aber dafür währe else zuständig
        //Sollte sortedTransaction vielleicht abgeschnitten werden? bz eine Überprüfung ob die transaction auch negativ sind?
        //schutz gegen overflow,
        if (cPayBackMoney <= 0.00) {
            for (TransactionElement updatedTransactionElement : updatedTransactionElements) {
                transactionService.updateElement(updatedTransactionElement);
            }

            return updatedTransactionElements;
        } else {
            // was tuhen,
            // anfrage zurück?
            //oder einfach Fehler?
        }


        return updatedTransactionElements;
    }


    public List<TransactionElement> payOfPrioritisedPositiveDepts(List<TransactionElement> sortedTransactions, double payBackMoney) {
        double cPayBackMoney = payBackMoney;
        // ich muss erstmal schauen ob die Gesammtschulden überhaupt so hoch sind wie payBackMoney! bz was mache ich dann?

        List<TransactionElement> updatedTransactionElements = new ArrayList<>();

        for (TransactionElement transactionElement : sortedTransactions) {
            if (cPayBackMoney == 0.00) {
                break;
            }

            // am anfang definieren?
            double amount = transactionElement.getAmount();
            double result = amount + cPayBackMoney;

            if (result >= 0) {
                transactionElement.setAmount(result);
                transactionElement.setFutureInterest(transactionElement.calculateFutureInterest(7));
                updatedTransactionElements.add(transactionElement);

                cPayBackMoney = 0.00;
                break;
            } else {
                transactionElement.setAmount(0);
                transactionElement.setFutureInterest(0);
                updatedTransactionElements.add(transactionElement);
                cPayBackMoney = result;
            }


        }

        for (TransactionElement updatedTransactionElement : updatedTransactionElements) {
            transactionService.updateElement(updatedTransactionElement);

        }

        return updatedTransactionElements;
    }


    public List<TransactionElement> smartPayBackForDebitor(int days, double payBackMoney, int debitorId) {

        List<TransactionElement> sortedTransactions = sortTransactionsByFutureInterest(days, transactionService.getTransactionElementsByDebitorId(debitorId));
        List<TransactionElement> updatedTransactions = payOfPrioritisedNegativeDepts(sortedTransactions, payBackMoney);

        return updatedTransactions;
    }

}