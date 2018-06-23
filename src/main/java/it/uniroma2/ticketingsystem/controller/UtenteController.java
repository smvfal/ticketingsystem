package it.uniroma2.ticketingsystem.controller;

import it.uniroma2.ticketingsystem.dao.UtenteDao;
import it.uniroma2.ticketingsystem.entity.Utente;
import it.uniroma2.ticketingsystem.logger.aspect.LogOperation;
import it.uniroma2.ticketingsystem.logger.utils.ObjSer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.uniroma2.ticketingsystem.exception.EntitaNonTrovataException;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;

// @Service identifica uno Spring Bean che nell'architettura MVC è un Controller
@Service
public class UtenteController {

    @Autowired
    private UtenteDao utenteDao;

    @LogOperation(returnObject = true)
    @Transactional
    public @NotNull Utente creaUtente(@NotNull Utente utente) {
       Utente utenteSalvato = utenteDao.save(utente);
       return utenteSalvato;
   }

   /*

   @LogOperation(inputArgs = "i")
   public void daCancellare(Integer i){
        System.out.print("stampa :"+i);
   }
   */


    @LogOperation(inputArgs = "datiAggiornati")
    @Transactional
    public @NotNull Utente aggiornaUtente(@NotNull Integer id, @NotNull Utente datiAggiornati) throws EntitaNonTrovataException {
        Utente utenteDaAggiornare = utenteDao.getOne(id);
        if (utenteDaAggiornare == null)
            throw new EntitaNonTrovataException();

        utenteDaAggiornare.aggiorna(datiAggiornati);

        Utente utenteAggiornato = utenteDao.save(utenteDaAggiornare);
        return utenteAggiornato;
    }

    public Utente cercaUtentePerId(@NotNull Integer id) {
        Utente utenteTrovato = utenteDao.getOne(id);
        return utenteTrovato;
    }

    @LogOperation(inputArgs = "id")
    public boolean eliminaUtente(@NotNull Integer id) {
        if (!utenteDao.existsById(id)) {
            return false;
        }

        utenteDao.deleteById(id);
        return true;
    }

    public List<Utente> prelevaUtenti() {
        return utenteDao.findAll();
    }

    /*
    public Utente cercaUtentePerEmail(@NotNull String email) {
        return utenteDao.findByEmailAddress(email);
    }
    */
}
