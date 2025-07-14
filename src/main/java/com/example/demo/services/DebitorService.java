package com.example.demo.services;

import com.example.demo.Elements.DebitorElement;
import com.example.demo.repositorys.DebitorRepository;
import com.example.demo.rest.ElementNichtVorhanden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DebitorService {

    @Autowired
    DebitorRepository repository;

    public DebitorService(){

    }

    public List<DebitorElement> getElements(){
        return repository.findAll();
    }

    public DebitorElement createElement(DebitorElement element){
        return repository.save(element);
    }

    public DebitorElement getElementById(int itId){
        return this.repository.findByItId(itId).orElseThrow(()-> new ElementNichtVorhanden("Das Gesuchte Element ist nicht vorhanden"));
    }

    public DebitorElement getElementsByName(String name){
        return this.repository.findByDebitorName(name).orElseThrow(()-> new ElementNichtVorhanden("Das Gesuchte Element ist nicht vorhanden"));
    }

    public void calculateDebtsForDebitor(int id, double amount) {
        DebitorElement debitorElement;
        Optional<DebitorElement> optionalDebitor = repository.findByItId(id);
        if (optionalDebitor.isPresent()) {
            // Hier auf debitorElement zugreifen
            debitorElement = optionalDebitor.get();
            double newAmount = debitorElement.getAmount() + amount;
            debitorElement.setAmount(newAmount);
            repository.save(debitorElement);

        } else {
            // Hier den Fall behandeln, das kein Debitor-Element gefunden wurde
           // throw new Exception("DebitorElement wasnt found!");
            System.out.println("DebitorElement wasnt found!");
        }

    }

    public DebitorElement getDebitorByNameIgnoreCase(String name){
        return this.repository.findByDebitorNameIgnoreCase(name).orElse(null);
    }

    public boolean doesDebitorAllreadyExist(String name){

        Optional<DebitorElement> existing = Optional.ofNullable(this.repository.findByDebitorNameIgnoreCase(name).orElse(null));
        return existing.isPresent();

    }
}
