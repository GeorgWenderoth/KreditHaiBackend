package com.example.demo.Elements;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;

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
    private double originalAmount;

    private double amount;
    private LocalDate borrowDate;
    private double interestRate;

    private int interestFrequency;
    private LocalDate interestStartDate;
    private String notes;

    @JsonIgnore
    private LocalDate lastInterestDate;

    // Konstruktor
    public TransactionElement(int id, int debitorId, String purpose, double amount, LocalDate borrowDate,
                              double interestRate, int interestFrequency, LocalDate interestStartDate, String notes) {
        this.id = id;
        this.debitorId = debitorId;
       // this.debitorElement = debitorElement;
        this.purpose = purpose;
        this.originalAmount = amount;
        this.amount = amount;
        this.borrowDate = borrowDate;
        this.interestRate = interestRate;
        this.interestFrequency = interestFrequency;
        this.interestStartDate = interestStartDate;
        this.notes = notes;
        this.lastInterestDate = interestStartDate;
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

    public double getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(double originalAmount) {
        this.originalAmount = originalAmount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
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

    public LocalDate getInterestStartDate() {
        return interestStartDate;
    }

    public void setInterestStartDate(LocalDate interestStartDate) {
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

    public LocalDate getLastInterestDate() {
        return lastInterestDate;
    }

    public void setLastInterestDate(LocalDate lastInterestDate) {
        this.lastInterestDate = lastInterestDate;
    }
}


