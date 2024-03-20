package com.example.demo;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity //gibt bekannt das, dass Element eine Jpa entity ist -> Repository







public class TransactionElement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int debitorId;
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
        this.purpose = purpose;
        this.amount = amount;
        this.borrowDate = borrowDate;
        this.interestRate = interestRate;
        this.interestStartDate = interestStartDate;
        this.notes = notes;
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
}


