package edu.utec.webpft.dtos;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class JustificacionDto {
    private Long id;
    private String evento;
    private String informacion;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime fecha;

    private String estado;
    private Long estudiante;
}

