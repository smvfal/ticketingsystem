package it.uniroma2.ticketingsystem.rest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.uniroma2.ticketingsystem.controller.UtenteController;
import it.uniroma2.ticketingsystem.entity.Utente;
import it.uniroma2.ticketingsystem.event.UtenteEvent;
import it.uniroma2.ticketingsystem.exception.EntitaNonTrovataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


// @RestController e @Controller identificano uno Spring Bean che nell'architettura MVC è l'anello di congiunzione tra
    // la View e il Controller (vedere l'annotazione @Service della classe PersonaController).
// La differenzatra @Controller e @RestController è che @RestController (che estende @Controller) preconfigura tutti i
// metodi per accettare in input e restituire in output delle richieste HTTP il cui payload è in formato JSON.
// Per ottenere lo stesso comportamento del @RestController, si possono utilizzare l'annotazione @Controller e
// l'annotazione @ResponseBody; quest'ultima serve appunto a denotare che un metodo (o tutti i metodi di una classe)
// restituiscono dati in formati JSON. Gli attributi "produces" e "consumes" di @RequestMapping permettono di definire
// il MimeType dei dati restituiti e ricevuti, rispettivamente. Quando input e output sono in formato JSON, l'annotazione
// @RestController è un metodo sintetico per dichiararlo e fornire a Spring la configurazione necessaria per serialzizare
// e deserializzare il JSON.

    @RestController
    @RequestMapping(path = "utente")
    public class UtenteRestService {

        @Autowired
        private UtenteController utenteController;

        @Autowired
        private ApplicationEventPublisher applicationEventPublisher;

        @JsonIgnore
        @JsonProperty(value = "utente")
        @RequestMapping(path = "", method = RequestMethod.POST)
        public ResponseEntity<Integer> creaUtente(@RequestBody Utente utente) {
            /*
            * 0 = inserimento avvenuto con successo
            * 1 = username già presente
            * 2 = email già presente
            * */

            if (utenteController.cercaPerUsername(utente.getUsername())!= null){
                System.out.println("TROVATO USERNAME DUPLICATO");
                return new ResponseEntity<>(1, HttpStatus.BAD_REQUEST);
            }

            if (utenteController.cercaPerEmail(utente.getEmail()) != null){
                System.out.println("TROVATA EMAIL DUPLICATA");
                return new ResponseEntity<>(2, HttpStatus.BAD_REQUEST);
            }

            Utente utenteCreato = utenteController.creaUtente(utente);

            /*
            UtenteEvent utenteEvent =  new UtenteEvent(this,utente,0);
            applicationEventPublisher.publishEvent(utenteEvent);
*/
            return new ResponseEntity<>(0, HttpStatus.OK);


        }

        @RequestMapping(path = "{id}", method = RequestMethod.PUT)
        public ResponseEntity<Integer> aggiornaUtente(@PathVariable Integer id, @RequestBody Utente utente) {
            /*
            * -1 = errore generico
            * 0 = modifica avvenuta con successo
            * 1 = username già presente
            * 2 = email già presente
            * */
            Utente utenteAggiornato;
            try {
                Utente usr = utenteController.cercaPerUsername(utente.getUsername());

                if (usr != null &&  usr.getId()!=id.intValue()){
                    System.out.println("*********************** TROVATO USERNAME DUPLICATO *****************************");
                    return new ResponseEntity<>(1, HttpStatus.BAD_REQUEST);
                }
                usr = utenteController.cercaPerEmail(utente.getEmail());
                if (usr != null && usr.getId()!=id.intValue()){
                    System.out.println("*********************** TROVATA EMAIL DUPLICATA *****************************");
                    return new ResponseEntity<>(2, HttpStatus.BAD_REQUEST);
                }

                utenteAggiornato = utenteController.aggiornaUtente(id, utente);

                UtenteEvent utenteEvent = new UtenteEvent(this,utente,1);
                applicationEventPublisher.publishEvent(utenteEvent);

            } catch (EntitaNonTrovataException e) {
                return new ResponseEntity<>(-1, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(0, HttpStatus.OK);
        }

        @RequestMapping(path = "dettagliAccount/{id}", method = RequestMethod.PUT)
        public ResponseEntity<Integer> aggiornaAccount(@PathVariable Integer id, @RequestBody Utente utente) {
            /*
             * -1 = errore generico
             * 0 = modifica avvenuta con successo
             * 1 = username già presente
             * 2 = email già presente
             * */
            Utente utenteAggiornato;
            try {
                Utente usr = utenteController.cercaPerUsername(utente.getUsername());

                if (usr != null &&  usr.getId()!=id.intValue()){
                    System.out.println("*********************** TROVATO USERNAME DUPLICATO *****************************");
                    return new ResponseEntity<>(1, HttpStatus.BAD_REQUEST);
                }
                usr = utenteController.cercaPerEmail(utente.getEmail());
                if (usr != null && usr.getId()!=id.intValue()){
                    System.out.println("*********************** TROVATA EMAIL DUPLICATA *****************************");
                    return new ResponseEntity<>(2, HttpStatus.BAD_REQUEST);
                }

                utenteAggiornato = utenteController.aggiornaAccount(id, utente);

                UtenteEvent utenteEvent = new UtenteEvent(this,utente,1);
                applicationEventPublisher.publishEvent(utenteEvent);

            } catch (EntitaNonTrovataException e) {
                return new ResponseEntity<>(-1, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(0, HttpStatus.OK);
        }

        @RequestMapping(path = "{id}", method = RequestMethod.GET)
        public ResponseEntity<Utente> cercaUtente(@PathVariable Integer id) {
            Utente utenteTrovato = utenteController.cercaUtentePerId(id);
            return new ResponseEntity<>(utenteTrovato, utenteTrovato == null ? HttpStatus.NOT_FOUND : HttpStatus.CREATED);
        }

        @RequestMapping(path="dettagli-utente")
        public ResponseEntity<Utente> cercaUtente() {
            ModelAndView modelAndView = new ModelAndView();
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Utente utente = utenteController.cercaPerUsername(auth.getName());
            return new ResponseEntity<>(utente, utente == null ? HttpStatus.NOT_FOUND : HttpStatus.CREATED);
        }

        @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
        public ResponseEntity<Boolean> eliminaUtente(@PathVariable Integer id) {

            UtenteEvent utenteEvent = new UtenteEvent(this, utenteController.cercaUtentePerId(id), 2);

            applicationEventPublisher.publishEvent(utenteEvent);

         //   utenteController.daCancellare(10);

            boolean eliminata = utenteController.eliminaUtente(id);
            return new ResponseEntity<>(eliminata, eliminata ? HttpStatus.OK : HttpStatus.NOT_FOUND);
        }

        @RequestMapping(path = "", method = RequestMethod.GET)
        public ResponseEntity<List<Utente>> prelevaUtenti() {
            List<Utente> utenti = utenteController.prelevaUtenti();
            return new ResponseEntity<>(utenti, HttpStatus.OK);
        }

        @RequestMapping(path = "isAdmin", method = RequestMethod.GET)
        public ResponseEntity<Boolean> isAdmin() {
            Boolean isAdmin = false;
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Utente user = utenteController.cercaPerUsername(auth.getName());
            if (user.getRuolo().getName().equals("ADMIN"))
                isAdmin = true;
            return new ResponseEntity<>(isAdmin, HttpStatus.OK);
        }

        @RequestMapping(path = "getRole", method = RequestMethod.GET)
        public ResponseEntity<Integer> getRole() {
            Integer role = -1;
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Utente user = utenteController.cercaPerUsername(auth.getName());
            if (user.getRuolo().getName().equals("ADMIN"))
                role = 0;
            if (user.getRuolo().getName().equals("OPERATOR"))
                role = 1;
            if (user.getRuolo().getName().equals("USER"))
                role = 2;
            return new ResponseEntity<>(role, HttpStatus.OK);
        }

        @RequestMapping(path = "logged", method = RequestMethod.GET)
        public ResponseEntity<Utente> getLogged() {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Utente user = utenteController.cercaPerUsername(auth.getName());
            return new ResponseEntity<>(user, HttpStatus.OK);
        }

        /*
        @RequestMapping(method = RequestMethod.GET)
        public ResponseEntity<Utente> cercaUtentePerEmail(@RequestParam(value = "email") String email) {
            Utente utenteTrovato = utenteController.cercaUtentePerEmail(email);
            return new ResponseEntity<>(utenteTrovato, utenteTrovato == null ? HttpStatus.NOT_FOUND : HttpStatus.CREATED);
        }
        */
    }