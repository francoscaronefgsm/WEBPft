package edu.utec.webpft.entidades;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private LocalDateTime fechaInicio;

    private LocalDateTime fechaFin;

    @ManyToOne
    @JoinColumn(name = "itr_id")
    private Itr itr;

    private String ubicacion;

    @ManyToMany
    @JoinTable(
            name = "evento_tutor",
            joinColumns = @JoinColumn(name = "evento_id"),
            inverseJoinColumns = @JoinColumn(name = "tutor_id")
    )
    private List<Usuario> tutores;

    @ManyToOne
    @JoinColumn(name = "estado_id")
    private EstadoEvento estadoEvento;

    @ManyToOne
    @JoinColumn(name = "modalidad_id")
    private ModalidadEvento modalidad;

    @ManyToOne
    @JoinColumn(name = "tipo_id")
    private TipoEvento tipoEvento;

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Asistencia> asistencias;

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Constancia> constancias;

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Justificacion> justificaciones;

    private Boolean anulado;
}
