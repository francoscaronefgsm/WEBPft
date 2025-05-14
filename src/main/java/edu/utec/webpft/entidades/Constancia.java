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
public class Constancia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "evento_id")
    private Evento evento;

    private String informacion;

    private LocalDateTime fecha;

    @ManyToOne
    @JoinColumn(name = "estudiante_id")
    private Estudiante estudiante;

    @ManyToOne
    @JoinColumn(name = "estado_id")
    private EstadoConstancia estadoConstancia;

    @ManyToOne
    @JoinColumn(name = "tipo_id")
    private TipoConstancia tipoConstancia;


    private Boolean anulado;
}

