package edu.utec.webpft.dtos;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CertificadoDto {
    private Long id;
    private String tipoCertificado;
    private String evento;
    private String informacion;
    private LocalDateTime fecha;
    private String estado;
    private Long estudiante;
}

