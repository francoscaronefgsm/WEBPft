package edu.utec.webpft.dtos;

import lombok.Data;

@Data
public class AccionRealizadaDto {
    private Long id;
    private Long reclamo;
    private Long administrador;
    private String estado;
    private String accionTomada;
}
