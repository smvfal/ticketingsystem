package it.uniroma2.ticketingsystem.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import it.uniroma2.ticketingsystem.logger.aspect.LogClass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@Getter
@LogClass (logAttrs = {"stato"}, idAttrs = {"id"})
@Setter
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Ticket {
    @Id
    @GeneratedValue
    private Integer id;
    @NotNull
    private String titolo;
    @NotNull
    private String categoria;
    private String descrizione;
    @NotNull
    private String stato;
    @NotNull
    private Timestamp timestamp;
    private Integer prioritaAutore;
    private Integer prioritaTeam;

    @ManyToOne
    private Utente autore;
    @ManyToOne
    private Utente teamMember;
    @ManyToOne
    private Oggetto oggetto;

    //private Integer teamid;  //attributo opzionale

    public Ticket(@NotNull String categoria, @NotNull String descrizione, @NotNull Integer prioritaAutore,
                  @NotNull Integer prioritaTeam, @NotNull String titolo, @NotNull String stato,
                  @NotNull Timestamp timestamp, @NotNull Utente autore, @NotNull Utente teamMember,
                  @NotNull Oggetto oggetto) {

        this.categoria = categoria;
        this.descrizione = descrizione;
        this.prioritaAutore = prioritaAutore;
        this.prioritaTeam = prioritaTeam;

        this.titolo = titolo;
        this.stato = stato;
        this.timestamp = timestamp;
        this.autore = autore;
        this.teamMember = teamMember;
        this.oggetto = oggetto;

    }

    public void aggiorna(@NotNull Ticket nuovoTicket) {

        this.titolo = nuovoTicket.titolo;
        this.categoria = nuovoTicket.categoria;
        this.descrizione= nuovoTicket.descrizione;
        this.stato = nuovoTicket.stato;
        this.prioritaAutore = nuovoTicket.prioritaAutore;
        this.prioritaTeam = nuovoTicket.prioritaTeam;
        this.timestamp = nuovoTicket.timestamp;
        this.autore = nuovoTicket.autore;
        this.teamMember = nuovoTicket.teamMember;
        this.oggetto = nuovoTicket.oggetto;
        //this.teamid = nuovoTicket.teamid;

    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", titolo='" + titolo + '\'' +
                ", categoria='" + categoria + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", stato='" + stato + '\'' +
                ", timestamp=" + timestamp +
                ", prioritaAutore=" + prioritaAutore +
                ", prioritaTeam=" + prioritaTeam +
                ", autore=" + autore +
                ", teamMember=" + teamMember +
                ", oggetto=" + oggetto +
                '}';
    }

}
