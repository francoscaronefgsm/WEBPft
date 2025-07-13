package edu.utec.webpft.dtos;

import edu.utec.webpft.entidades.EstadoJustificacion;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class JustificacionDto {
    private Long id;
    private Long evento;
    private String informacion;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime fecha;
    private Long estado;
    private Long estudiante;

    private String nombreEstudiante;
    private String descripcionEstado;
    private String descripcionEvento;
}

