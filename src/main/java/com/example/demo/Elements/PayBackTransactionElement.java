package com.example.demo.Elements;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "payBackTransaction")
public class PayBackTransactionElement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name= "transaction_id")
    private int transactionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnore // Ignoriere das verknüpfte Debitor-Objekt beim Serialisieren
    private TransactionElement transactionElement;

    @Column(name = "debitor_id")
    private int debitorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "debitor_id", referencedColumnName = "itId", insertable = false, updatable = false)
    @JsonIgnore // Ignoriere das verknüpfte Debitor-Objekt beim Serialisieren
    private DebitorElement debitorElement;

    private double amount;

    private LocalDate payBackDate;

    private String notes;

    public PayBackTransactionElement(int id, int transactionId, int debitorId, double amount, LocalDate payBackDate, String notes) {
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

    public LocalDate getPayBackDate() {
        return payBackDate;
    }

    public void setPayBackDate(LocalDate payBackDate) {
        this.payBackDate = payBackDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
