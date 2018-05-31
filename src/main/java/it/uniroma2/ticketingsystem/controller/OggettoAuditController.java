package it.uniroma2.ticketingsystem.controller;

import it.uniroma2.ticketingsystem.aud.OggettoAudit;
import it.uniroma2.ticketingsystem.dao.OggettoAuditDao;
import it.uniroma2.ticketingsystem.entity.Oggetto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

@Service
public class OggettoAuditController {

    @Autowired
    private OggettoAuditDao oad;

    @Transactional
    public @NotNull OggettoAudit creaOggettoAudit(@NotNull OggettoAudit oggettoAudit){

        OggettoAudit oggettoAuditSalvato = oad.save(oggettoAudit);
        return oggettoAuditSalvato;

    }
    @Transactional
    public @NotNull OggettoAudit getMostRecentOggettoAudit(@NotNull Oggetto oggetto){
        int audit_id = oad.getIdOfMostRecentOggettoAuditByOggetto(oggetto.getId());
        OggettoAudit oggettoAuditMostRecent = oad.getOne(audit_id);
        //OggettoAudit oggettoAuditMostRecent = oad.getMostRecentOggettoAudit(oggetto.getId());
        return oggettoAuditMostRecent;

    }

    public boolean eliminaOggettoAudit(@NotNull Integer id){
        if(!oad.existsById(id)){
            return false;
        }

        oad.deleteById(id);
        return true;
    }
}
