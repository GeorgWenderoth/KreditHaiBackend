package com.example.demo.rest;


import com.example.demo.Elements.DebitorElement;
import com.example.demo.services.DebitorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
/**
 * Controller
 * APi-Schnittstellen
 */
public class DebitorController {

    public DebitorService service;

    public DebitorController(DebitorService debitorService){
      this.service = debitorService;
    }

    @CrossOrigin
    @PostMapping("/neuerSchuldner")
    public ResponseEntity<DebitorElement> neuerSchuldner(@RequestBody DebitorElement element){
        System.out.println("Testelement");
        System.out.println( element);
        System.out.println("punkt:" + element.getDebitorName());
        System.out.println("id " + element.getItId());


        final DebitorElement debitorElement = service.createElement(element);
        return  new ResponseEntity<>(debitorElement, HttpStatus.CREATED); // Warum nochmal returnen

    }

    @CrossOrigin
    @GetMapping("/alleSchuldner")
    public ResponseEntity<List<DebitorElement>> getAllDebitors(){
        return new ResponseEntity<>(service.getElements(), HttpStatus.OK);

    }

    @CrossOrigin
    @GetMapping("/Schuldner")
    public ResponseEntity<DebitorElement> getDebitorById(@RequestParam int id){
        return new ResponseEntity<>(service.getElementById(id), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/SchuldnerName")
    public ResponseEntity<DebitorElement> getDebitorByName(@RequestParam String name){
        return new ResponseEntity<>(service.getElementsByName(name), HttpStatus.OK);
    }




}
