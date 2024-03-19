package com.adorsys.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
/**
 * Controller
 * APi-Schnittstellen
 */
public class GroceryController {


    public GroceryService service;
    public GroceryController(GroceryService groceryService){ //warum
        this.service = groceryService;
    }

    /**
     * Gibt alle nicht erledigten einkäufe zurück
     * @return
     */
    @CrossOrigin
    @GetMapping ("/einkaufsListeElementeNotDone")
    public  ResponseEntity<List<SchuldnerElement>> getEinkaufUndone(){
        return new  ResponseEntity<>(service.getEinkaufElementsByStrich(false),HttpStatus.OK);
    }

    /**
     * Gibt alle erledigten Einkäufe zurück
     * @return
     */
    @CrossOrigin
    @GetMapping ("/einkaufsListeElementeDone")
    public  ResponseEntity<List<SchuldnerElement>> getEinkaufDone(){
        return new  ResponseEntity<>(service.getEinkaufElementsByStrich(true),HttpStatus.OK);
    }
/*
    @CrossOrigin
    @GetMapping ("/einkaufsListeElemente")
    public ResponseEntity<List<EinkaufElement>> getEinkauf(){
        return new ResponseEntity<>(service.getEinkaufElements(), HttpStatus.OK);
    } */

    /**
     * Fügt einen neuen Einkauf hinzu
     * @param element
     * @return
     */
    @CrossOrigin
    @PostMapping("/einkaufsListe")
    public ResponseEntity<SchuldnerElement> neuerEinkauf(@RequestBody SchuldnerElement element){
        System.out.println("Testelement");
        System.out.println( element);
        System.out.println("punkt:" + element.getSchuldnerName());
        System.out.println("id " + element.getItId());
        System.out.println("strich" + element.getStrich());

        final SchuldnerElement schuldnerElement = service.erstelleElement(element);
        return  new ResponseEntity<>(schuldnerElement, HttpStatus.CREATED); // Warum nochmal returnen

    }

    /**
     * ändert den status auf durchgestrichen/ erledigt oder unerledigt / nicht durchgestrichen
     * @param element
     * @return
     */
    @CrossOrigin
    @PutMapping("/einkaufsListeDurchgestrichen")
    public ResponseEntity<SchuldnerElement> durchstreichen(@RequestBody SchuldnerElement element){
           return new ResponseEntity<>(this.service.streicheDurch(element.getItId(), element.getStrich()),HttpStatus.OK);
    }

    /**
     * Updated element
     * @param element
     * @return
     */
    @CrossOrigin
    @PutMapping("/einkaufsListeUpdateM")
    public ResponseEntity<SchuldnerElement> update(@RequestBody SchuldnerElement element){
        return new ResponseEntity<>(this.service.updateElementM(element.getItId(), element.getAmount(), element.getSchuldnerName(), element.getNotizen()),HttpStatus.OK);
    }
    /* Brauche ich das noch?
    @CrossOrigin
    @DeleteMapping("/einkaufsListeElementLoeschen")
    public ResponseEntity<Void> loescheElement(@RequestBody EinkaufElement element){
        service.loescheElement(element.getItId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

     */

    /**
     * Löscht alle erledigten elemente
     */
    @CrossOrigin
    @DeleteMapping("/einkaufssListeElementeDoneLoeschen")
    public void loescheAllDoneElements(){
        service.loescheElementeDone();
        return ;
    }


}
