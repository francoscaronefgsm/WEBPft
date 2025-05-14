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
public class EstadoReclamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descripcion;

    private boolean activo;

    @OneToMany(mappedBy = "estadoReclamo")
    private List<Reclamo> reclamos;
}
