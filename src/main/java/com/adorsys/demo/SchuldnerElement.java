package com.adorsys.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity //gibt bekannt das, dass Element eine Jpa entity ist -> Repository

public class SchuldnerElement {  // Hier sind die daten für das gesamte json objekt brauche ich keine getteruns setter

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   // private int id;
    private int itId;
    private String schuldnerName;
    private boolean strich;
    private int amount;

    private String notizen;


    public SchuldnerElement(int itId, String schuldnerName, boolean strich, int amount, String notizen){
        this.itId = itId;
        this.schuldnerName = schuldnerName;
        this.strich = strich;
        this.amount = amount;
        this.notizen = notizen;
    }

    public SchuldnerElement() {

    }




    public int getItId(){
        return itId;
    }

    public void setItId(int itId){
        this.itId = itId;
    }


    public String getSchuldnerName(){
        return schuldnerName;
    }


    public void setSchuldnerName(String schuldnerName) {
        this.schuldnerName = schuldnerName;
    }

    public Boolean getStrich(){
        return strich;
    }

    public void setStrich(Boolean strich){
        this.strich = strich;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
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



