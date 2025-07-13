package edu.utec.webpft.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ConstanciaDto {

    private Long id;

    private Long tipoConstancia;

    @NotEmpty(message = "El evento no puede estar vacío")
    @NotNull(message = "El evento no puede ser nulo")
    private Long evento;

    private String informacion;

    private LocalDateTime fecha;

    private Long estudiante;

    private Long estadoConstancia;

    private String tituloEvento;

    private String nombreEstudiante;

    private String descripcionTipoConstancia;

    private String descripcionEstadoConstancia;

}

