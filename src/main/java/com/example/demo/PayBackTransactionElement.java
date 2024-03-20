package com.example.demo;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class PayBackTransactionElement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int transactionId;

    private int debitorId;

    private double amount;

    private Date payBackDate;

    private String notes;

    public PayBackTransactionElement(int id, int transactionId, int debitorId, double amount, Date payBackDate, String notes) {
        this.id = id;
        this.transactionId = transactionId;
        this.debitorId = debitorId;

        this.amount = amount;
        this.payBackDate = payBackDate;
        this.notes = notes;
    }

    public PayBackTransactionElement(){};

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getDebitorId() {
        return debitorId;
    }

    public void setDebitorId(int debitorId) {
        this.debitorId = debitorId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getPayBackDate() {
        return payBackDate;
    }

    public void setPayBackDate(Date payBackDate) {
        this.payBackDate = payBackDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
