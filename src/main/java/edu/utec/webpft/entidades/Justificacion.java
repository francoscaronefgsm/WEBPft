package edu.utec.webpft.entidades;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Justificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String evento;

    private String informacion;

    private LocalDateTime fecha;

    @ManyToOne
    @JoinColumn(name = "estado_id")
    private EstadoJustificacion estadoJustificacion;

    @ManyToOne
    @JoinColumn(name = "estudiante_id")
    private Estudiante estudiante;

    private Boolean anulado;
}

