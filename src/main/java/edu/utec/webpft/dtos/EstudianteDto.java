package edu.utec.webpft.dtos;

import lombok.Data;
import java.util.List;

@Data
public class EstudianteDto {
    private Long id;
    private String nombreUsuario;
    private String nombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private int documento;
    private String correoPersonal;
    private String correoInstitucional;
    private int telefono;
    private String genero;
    private int generacion;
    private Long localidad;
    private Long departamento;
    private Long itr;
    private boolean activo;
    private List<Long> certificados;
    private List<Long> justificaciones;
}

