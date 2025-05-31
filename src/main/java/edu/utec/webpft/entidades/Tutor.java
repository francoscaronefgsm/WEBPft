package edu.utec.webpft.entidades;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Tutor extends Usuario {

    private String area;

    private String rolDocente;

    @ManyToMany(mappedBy = "tutores")
    private List<Evento> eventos;

}
