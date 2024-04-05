package com.example.demo.Elements;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity //gibt bekannt das, dass Element eine Jpa entity ist -> Repository

public class DebitorElement {  // Hier sind die daten für das gesamte json objekt brauche ich keine getteruns setter

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   // private int id;
    private int itId;
    private String debitorName;
    private boolean strich;
    private double amount;

    private String notizen;


    public DebitorElement(int itId, String debitorName, boolean strich, int amount, String notizen){
        this.itId = itId;
        this.debitorName = debitorName;
        this.strich = strich;
        this.amount = amount;
        this.notizen = notizen;
    }

    public DebitorElement() {

    }




    public int getItId(){
        return itId;
    }

    public void setItId(int itId){
        this.itId = itId;
    }


    public String getDebitorName(){
        return debitorName;
    }


    public void setDebitorName(String debitorName) {
        this.debitorName = debitorName;
    }

    public Boolean getStrich(){
        return strich;
    }

    public void setStrich(Boolean strich){
        this.strich = strich;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }



    public String getNotizen() {
        return notizen;
    }

    public void setNotizen(String notizen) {
        this.notizen = notizen;
    }
    /*   public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    } */


}



