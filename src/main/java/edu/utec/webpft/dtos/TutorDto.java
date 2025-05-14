package edu.utec.webpft.dtos;

import lombok.Data;
import java.util.List;

@Data
public class TutorDto {
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
    private String area;
    private String rolDocente;
    private Long localidad;
    private Long departamento;
    private Long itr;
    private boolean activo;
    private List<Long> eventos;
}
