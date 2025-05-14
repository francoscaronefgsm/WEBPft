package edu.utec.webpft.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ReclamoDto {

    private Long id;

    @NotEmpty(message = "El título no puede estar vacío")
    private String titulo;

    @NotEmpty(message = "La descripción no puede estar vacía")
    private String descripcion;

    private LocalDateTime creado;

    private LocalDateTime actualizado;

    private Long tipoReclamoDto;

    private int semestre;

    private LocalDate fecha;

    private String docente;

    private int creditos;

    private Long estadoReclamoDto;

    private Long usuario;
}
