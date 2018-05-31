package it.uniroma2.ticketingsystem.listener;

import it.uniroma2.ticketingsystem.aud.OggettoAudit;
import it.uniroma2.ticketingsystem.controller.OggettoAuditController;
import it.uniroma2.ticketingsystem.controller.TicketAuditController;
import it.uniroma2.ticketingsystem.entity.Oggetto;
import it.uniroma2.ticketingsystem.entity.Ticket;
import it.uniroma2.ticketingsystem.aud.TicketAudit;
import it.uniroma2.ticketingsystem.event.TicketEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class TicketEventListener {

    @Autowired
    TicketAuditController ticketAuditController;
    @Autowired
    OggettoAuditController oggettoAuditController;

    @EventListener
    public void handleTicketEvent(TicketEvent ticketEvent){

        OggettoAudit oggettoAudit;
        switch (ticketEvent.getToken()){
            case 0:
                //registra inserimento
                //prendi ultima istanza di OggettoAudit legato a ticket.oggettoID
                oggettoAudit = getLastOggettoAudit(ticketEvent.getTicket().getOggetto());
                registraInserimentoTicket(new TicketAudit(
                                                        ticketEvent.getTicket(),
                                                        new Timestamp(System.currentTimeMillis()),
                                                        oggettoAudit,
                                                        0));
                break;
            case 1:
                //registra cancellazione
                oggettoAudit = getLastOggettoAudit(ticketEvent.getTicket().getOggetto());
                registraCancellazioneTicket(new TicketAudit(
                                                        ticketEvent.getTicket(),
                                                        new Timestamp(System.currentTimeMillis()),
                                                        oggettoAudit,
                                                        1));
                break;
            case 2:
                //registra modifica
                //prendi ultima istanza di OggettoAudit legato a ticket.oggettoID
                oggettoAudit = getLastOggettoAudit(ticketEvent.getTicket().getOggetto());
                registraModificaTicket(new TicketAudit(
                                                    ticketEvent.getTicket(),
                                                    new Timestamp(System.currentTimeMillis()),
                                                    oggettoAudit,
                                                    2));
                break;
        }
    }
    private OggettoAudit getLastOggettoAudit(Oggetto oggetto){
        OggettoAudit oggettoAudit = oggettoAuditController.getMostRecentOggettoAudit(oggetto);
        return oggettoAudit;
    }
    private void registraInserimentoTicket(TicketAudit ticketAudit){
        ticketAuditController.registraTicketInsert(ticketAudit);
    }

    private void registraCancellazioneTicket(TicketAudit ticketAudit){
        ticketAuditController.registraTicketDelete(ticketAudit);
    }
    private void registraModificaTicket(TicketAudit ticketAudit){
        ticketAuditController.registraTicketEdit(ticketAudit);
    }

}
