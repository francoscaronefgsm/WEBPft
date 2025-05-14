package edu.utec.webpft.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Itr {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @OneToMany(mappedBy = "itr", cascade = CascadeType.ALL)
    private List<Usuario> usuarios;

    @OneToMany(mappedBy = "itr", cascade = CascadeType.ALL)
    private List<Evento> eventos;

    @ManyToOne
    @JoinColumn(name = "localidad_id")
    private Localidad localidad;

    private Boolean anulado;
}

