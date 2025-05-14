package edu.utec.webpft.dtos;

import lombok.Data;

@Data
public class AsistenciaDto {

    private Long id;
    private Long evento;
    private Long estudiante;
    private String estado;
    private int calificacion;

    private String nombreEvento;
    private String nombreEstudiante;
}
