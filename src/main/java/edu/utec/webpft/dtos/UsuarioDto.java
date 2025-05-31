package edu.utec.webpft.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class UsuarioDto {

    private Long id;

    @NotEmpty(message = "El nombre de usuario no puede estar vacío")
    private String nombreUsuario;

    @NotEmpty(message = "La contraseña no puede estar vacía")
    @Size(min = 3, max = 20, message = "La contraseña no cumple los requisitos de tamaño, mínimo 3, máximo 20")
    // @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{3,}$", message = "La contraseña debe contener al menos una letra y un número")
    private String contrasena;

    @NotEmpty(message = "El nombre no puede estar vacío")
    @Size(min = 3, max = 20, message = "El nombre no cumple los requisitos de tamaño, mínimo 3, máximo 20")
    private String primerNombre;

    private String segundoNombre;

    @Size(min = 3, max = 20, message = "El apellido no cumple los requisitos de tamaño, mínimo 3, máximo 20")
    @NotEmpty(message = "El primer apellido no puede estar vacío")
    private String primerApellido;

    @Size(min = 3, max = 20, message = "El apellido no cumple los requisitos de tamaño, mínimo 3, máximo 20")
    @NotEmpty(message = "El segundo apellido no puede estar vacío")
    private String segundoApellido;

    @NotNull(message = "El documento no puede ser nulo")
    private Integer documento;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaNacimiento;

    @NotEmpty(message = "El correo personal no puede estar vacío")
    private String correoPersonal;

    @NotNull(message = "El teléfono no puede ser nulo")
    private Integer telefono;

    @NotNull(message = "El departamento no puede ser nulo")
    private Long departamento;

    @NotNull(message = "La localidad no puede ser nula")
    private Long localidad;

    @NotEmpty(message = "El correo institucional no puede estar vacío")
    private String correoInstitucional;

    @NotNull(message = "El ITR no puede ser nulo")
    private Long itr;

    @NotEmpty(message = "El género no puede estar vacío")
    private String genero;

    // Si el usuario es estudiante
    private Integer generacion;

    // Si el usuario es docente
    private String area;

    private String rolDocente;

    private boolean activo;

    private Integer edad;


    public UsuarioDto(Long id, String nombreUsuario, String contrasena, String primerNombre, String segundoNombre, String primerApellido, String segundoApellido, int documento, LocalDate fechaNacimiento, String correoPersonal, int telefono, Long departamento, Long localidad, String correoInstitucional, Long itr, boolean activo) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.documento = documento;
        this.fechaNacimiento = fechaNacimiento;
        this.correoPersonal = correoPersonal;
        this.telefono = telefono;
        this.departamento = departamento;
        this.localidad = localidad;
        this.correoInstitucional = correoInstitucional;
        this.itr = itr;
        this.activo = activo;
    }
}

