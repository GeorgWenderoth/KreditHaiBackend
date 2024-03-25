package com.example.demo.services;

import com.example.demo.Elements.DebitorElement;
import com.example.demo.repositorys.DebitorRepository;
import com.example.demo.rest.ElementNichtVorhanden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

}
