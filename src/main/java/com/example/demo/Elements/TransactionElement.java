package com.example.demo.Elements;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity //gibt bekannt das, dass Element eine Jpa entity ist -> Repository


@Table(name = "transaction")
public class TransactionElement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "debitor_id")
    private int debitorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "debitor_id", referencedColumnName = "itId", insertable = false, updatable = false)
    @JsonIgnore // Ignoriere das verknüpfte Debitor-Objekt beim Serialisieren
    private DebitorElement debitorElement;
    private String purpose;
    private double amount;
    private Date borrowDate;
    private double interestRate;

    private int interestFrequency;
    private Date interestStartDate;
    private String notes;

    // Konstruktor
    public TransactionElement(int id, int debitorId, String purpose, double amount, Date borrowDate, double interestRate, Date interestStartDate, String notes) {
        this.id = id;
        this.debitorId = debitorId;
       // this.debitorElement = debitorElement;
        this.purpose = purpose;
        this.amount = amount;
        this.borrowDate = borrowDate;
        this.interestRate = interestRate;
        this.interestStartDate = interestStartDate;
        this.notes = notes;
    }

    public TransactionElement(){

    }

    // Getter und Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDebitorId() {
        return debitorId;
    }

    public void setDebitorId(int debitorId) {
        this.debitorId = debitorId;
    }



    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public int getInterestFrequency() {
        return interestFrequency;
    }

    public void setInterestFrequency(int interestFrequency) {
        this.interestFrequency = interestFrequency;
    }

    public Date getInterestStartDate() {
        return interestStartDate;
    }

    public void setInterestStartDate(Date interestStartDate) {
        this.interestStartDate = interestStartDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }


    public DebitorElement getDebitorElement() {
        return debitorElement;
    }

    public void setDebitorElement(DebitorElement debitorElement) {
        this.debitorElement = debitorElement;
    }
}


