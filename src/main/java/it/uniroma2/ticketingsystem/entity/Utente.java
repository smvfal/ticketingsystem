package it.uniroma2.ticketingsystem.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import it.uniroma2.ticketingsystem.logger.aspect.LogClass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.Set;


@Entity
@NoArgsConstructor
@LogClass(logAttrs = {"nome", "cognome"}, idAttrs = {"id"})
@Getter
@Setter
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Utente {

    @Id
    @GeneratedValue // Autoincrement
    private Integer id;

    @NotNull
    private String nome;
    @NotNull
    private String cognome;
    @NotNull
    @Column(unique = true)
    private String username;
    @NotNull
    private String password;
    @NotNull
    @Column(unique = true)
    private String email;

    @ManyToOne
    private Ruolo ruolo;

    @JsonIgnore
    @OneToMany(mappedBy = "autore", cascade = CascadeType.ALL)
    private Set<Ticket> ticketAperti;

    @OneToMany(mappedBy = "teamMember", cascade = CascadeType.ALL)
    private Set<Ticket> ticketAssegnati;


    public Utente(@NotNull String nome, @NotNull String cognome, @NotNull String username, @NotNull String password,
                  @NotNull String email, @NotNull Ruolo ruolo, @NotNull Set<Ticket> ticketAperti,
                  @NotNull Set<Ticket> ticketAssegnati) {

        this.nome = nome;
        this.cognome = cognome;
        this.username = username;
        this.password = password;
        this.email = email;
        this.ruolo = ruolo;
        this.ticketAperti = ticketAperti;
        this.ticketAssegnati = ticketAssegnati;

    }

    public void aggiorna(@NotNull Utente datiAggiornati) {

        this.nome = datiAggiornati.nome;
        this.cognome = datiAggiornati.cognome;
        this.username = datiAggiornati.username;
        this.password = datiAggiornati.password;
        this.email = datiAggiornati.email;
        this.ruolo = datiAggiornati.ruolo;
        this.ticketAperti = datiAggiornati.ticketAperti;
        this.ticketAssegnati = datiAggiornati.ticketAssegnati;

    }

    @Override
    public String toString() {
        return "Utente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", tipo=" + ruolo +
                '}';
    }
}
