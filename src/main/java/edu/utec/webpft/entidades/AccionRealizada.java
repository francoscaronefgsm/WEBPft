package edu.utec.webpft.entidades;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class AccionRealizada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "reclamo_id")
    private Reclamo reclamo;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Usuario admin;

    private String estado;

    private String accionRealizada;
}