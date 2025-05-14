package edu.utec.webpft.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Reclamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descripcion;
    private LocalDateTime creado;
    private LocalDateTime actualizado;
    private int semestre;
    private LocalDate fecha;
    private String docente;
    private int creditos;

    @ManyToOne
    @JoinColumn(name = "estado_id")
    private EstadoReclamo estadoReclamo;

    @ManyToOne
    @JoinColumn(name = "tipo_id")
    private TipoReclamo tipoReclamo;

    @ManyToOne
    @JoinColumn(name = "estudiante_id")
    private Estudiante estudiante;

    @OneToMany(mappedBy = "reclamo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccionRealizada> accionesTomadas;

    private Boolean anulado;
}