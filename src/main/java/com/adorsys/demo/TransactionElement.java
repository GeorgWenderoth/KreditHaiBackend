package com.adorsys.demo;
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
    private String purpose;
    private double amount;
    private Date date;
    private double interestRate;

    private int interestFrequency;
    private Date repaymentDate;
    private String notes;

    // Konstruktor
    public TransactionElement(int id, String purpose, double amount, Date date, double interestRate, Date repaymentDate, String notes) {
        this.id = id;
        this.purpose = purpose;
        this.amount = amount;
        this.date = date;
        this.interestRate = interestRate;
        this.repaymentDate = repaymentDate;
        this.notes = notes;
    }

    // Getter und Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public Date getRepaymentDate() {
        return repaymentDate;
    }

    public void setRepaymentDate(Date repaymentDate) {
        this.repaymentDate = repaymentDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}


