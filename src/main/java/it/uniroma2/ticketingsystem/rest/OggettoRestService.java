package it.uniroma2.ticketingsystem.rest;

import it.uniroma2.ticketingsystem.controller.OggettoController;
import it.uniroma2.ticketingsystem.entity.Oggetto;
import it.uniroma2.ticketingsystem.exception.EntitaNonTrovataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "oggetto")
public class OggettoRestService {


    @Autowired
    private OggettoController oggettoController;

    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<Oggetto> creaOggetto(@RequestBody Oggetto oggetto) {
        Oggetto oggettoCreato = oggettoController.creaOggetto(oggetto);
        return new ResponseEntity<>(oggettoCreato, HttpStatus.CREATED);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> eliminaOggetto(@PathVariable Integer id) {
        boolean oggettoEliminato = oggettoController.eliminaOggetto(id);
        return new ResponseEntity<>(oggettoEliminato, oggettoEliminato ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Oggetto> cercaOggetto(@PathVariable Integer id) {
        System.out.print("\n Sono in responseEntity");
        Oggetto oggettoTrovato = oggettoController.cercaOggettoById(id);
        return new ResponseEntity<>(oggettoTrovato, oggettoTrovato == null ? HttpStatus.NOT_FOUND : HttpStatus.CREATED);
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseEntity<List<Oggetto>> prelevaOggetti() {
        List<Oggetto> oggetto = oggettoController.prelevaOggetti();
        return new ResponseEntity<>(oggetto, HttpStatus.OK);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<Oggetto> aggiornaOggetto(@PathVariable Integer id, @RequestBody Oggetto oggetto) {
        Oggetto oggettoAggiornato;
        try {
            oggettoAggiornato = oggettoController.aggiornaOggetto(id, oggetto);
        } catch (EntitaNonTrovataException e) {
            return new ResponseEntity<>(oggetto, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(oggettoAggiornato, HttpStatus.OK);
    }

}