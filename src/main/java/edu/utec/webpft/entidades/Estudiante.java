package edu.utec.webpft.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Estudiante extends Usuario {

    private int generacion;

    @OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL)
    private List<Certificado> certificados;

    @OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL)
    private List<Justificacion> justificaciones;

    @OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL)
    private List<Reclamo> reclamos;

    private Boolean anulado;


}