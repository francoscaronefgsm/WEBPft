package edu.utec.webpft.dtos;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class EventoDto {

    private Long id;

    private String titulo;

    private Long tipoEvento;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime fechaInicio;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime fechaFin;

    private Long modalidad;

    private Long itr;

    private String ubicacion;

    private List<Long> docentes;

    private Long estado;

    private String nombresDocentes;

    private String nombreItr;
}

