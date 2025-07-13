package edu.utec.webpft.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ReclamoDto {

    private Long id;

    @NotEmpty(message = "El título no puede estar vacío")
    @JsonProperty("title") // Mapea "title" del JSON a este atributo
    private String titulo;

    @NotEmpty(message = "La descripción no puede estar vacía")
    @JsonProperty("description") // Mapea "description" del JSON a este atributo
    private String descripcion;

    @JsonProperty("created")
    private LocalDateTime creado;

    @JsonProperty("updated")
    private LocalDateTime actualizado;

    @JsonProperty("tipoReclamo") // Mapea "tipoReclamo" del JSON
    private Long tipoReclamoDto;

    @JsonProperty("semester") // Mapea "semester" del JSON
    private int semestre;

    @JsonProperty("date") // Mapea "date" del JSON
    private LocalDate fecha;

    @JsonProperty("teacher") // Mapea "teacher" del JSON
    private String docente;

    @JsonProperty("credits") // Mapea "credits" del JSON
    private int creditos;

    @JsonProperty("status") // Mapea "status" del JSON
    private Long estadoReclamoDto;

    // Estos campos no tienen @JsonProperty si no vienen en el JSON de entrada
    private String descripcionEstadoReclamo;
    private String descripcionTipoReclamo;

    @JsonProperty("userId") // Mapea "userId" del JSON (o "user" si usas "user" en el cliente)
    private Long usuario;
}
