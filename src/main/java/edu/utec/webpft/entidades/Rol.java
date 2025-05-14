package edu.utec.webpft.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
public class Rol {

    @Id @GeneratedValue(strategy =
            GenerationType.IDENTITY)
    @ToString.Exclude
    private Long id;

    private String nombre;

    @ManyToMany(mappedBy = "roles")
    @ToString.Exclude
    private List<Usuario> usuarios = new ArrayList<>();

}

