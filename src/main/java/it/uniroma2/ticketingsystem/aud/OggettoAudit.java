package it.uniroma2.ticketingsystem.aud;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import it.uniroma2.ticketingsystem.entity.Oggetto;
import it.uniroma2.ticketingsystem.entity.Ticket;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Set;


@Entity
@NoArgsConstructor
@Getter
@Setter
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class OggettoAudit {

    @Id
    @GeneratedValue
    private Integer id;

    private Integer idOggetto;
    private String nome;
    private String versione;
    @Column(name = "edit_time")
    private Timestamp time_stamp;
    private int operazione;

    @OneToMany(mappedBy = "oggetto", cascade = CascadeType.ALL)
    private Set<TicketAudit> tickets;

    public OggettoAudit(@NotNull Integer id, @NotNull Integer idOggetto, @NotNull String nome,
                        @NotNull String versione, @NotNull Timestamp time_stamp, @NotNull int operazione,
                        @NotNull Set<TicketAudit> tickets){

        this.id = id;
        this.idOggetto = idOggetto;
        this.nome = nome;
        this.versione = versione;
        this.time_stamp = time_stamp;
        this.operazione = operazione;
        this.tickets = tickets;
    }
    public OggettoAudit(Oggetto oggetto, Timestamp time_stamp, int operazione) {
        this.idOggetto = oggetto.getId();
        this.nome = oggetto.getNome();
        this.versione = oggetto.getVersione();
        this.time_stamp = time_stamp;
        this.operazione = operazione;
    }

    @Override
    public String toString() {
        return "OggettoAudit{" +
                "id=" + id +
                ", idOggetto=" + idOggetto +
                ", nome='" + nome + '\'' +
                ", versione='" + versione + '\'' +
                ", timestamp=" + time_stamp +
                ", operazione=" + operazione +
                ", tickets=" + tickets +
                '}';
    }
}
