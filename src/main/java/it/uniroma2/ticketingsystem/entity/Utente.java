package it.uniroma2.ticketingsystem.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Utente {

    @Id
    @GeneratedValue // Autoincrement
    private Integer id;
    private String nome;
    private String cognome;
    private String username;
    private String password;
    private String email;
    private int tipo;
    @OneToMany(mappedBy = "creatore", cascade = CascadeType.ALL)
    @JsonManagedReference(value="creatore")   // to avoid infinite recursion in serialization
    private Set<Ticket> ticketAperti;
    @OneToMany(mappedBy = "teamMember", cascade = CascadeType.ALL)
    @JsonManagedReference(value="team_member")   // to avoid infinite recursion in serialization
    private Set<Ticket> ticketAsseggnati;

    public Utente(@NotNull String nome, @NotNull String cognome, @NotNull String username, @NotNull String password, @NotNull String email, @NotNull int tipo) {
        this.nome = nome;
        this.cognome = cognome;
        this.username = username;
        this.password = password;
        this.email = email;
        this.tipo = tipo;
    }

    public void aggiorna(@NotNull Utente datiAggiornati) {
        this.nome = datiAggiornati.nome;
        this.cognome = datiAggiornati.cognome;
        this.username = datiAggiornati.username;
        this.password = datiAggiornati.password;
        this.email = datiAggiornati.email;
        this.tipo = datiAggiornati.tipo;
    }
}
