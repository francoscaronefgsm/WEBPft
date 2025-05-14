package edu.utec.webpft.service.imp;

import edu.utec.webpft.dtos.UsuarioDto;
import edu.utec.webpft.entidades.*;
import edu.utec.webpft.repository.*;

import edu.utec.webpft.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final DepartmentRepository departmentRepository;

    private final LocalityRepository localityRepository;

    private final ItrRepository itrRepository;

    @Override
    public void register(UsuarioDto registration){

        Rol rolEstudiante = roleRepository.findByNombre("ROLE_STUDENT")
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        Rol rolTutor = roleRepository.findByNombre("ROLE_TEACHER")
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        Departamento departamento = departmentRepository.findById(registration.getDepartamento())
                .orElseThrow(() -> new RuntimeException("Departamento no encontrado"));

        Localidad localidad = localityRepository.findById(registration.getLocalidad())
                .orElseThrow(() -> new RuntimeException("Localidad no encontrada"));

        Itr itr = itrRepository.findById(registration.getItr())
                .orElseThrow(() -> new RuntimeException("Itr no encontrado"));

        Usuario usuario;

        if (registration.getGeneracion() != 0) {
            usuario = Estudiante.builder()
                    .nombreUsuario(registration.getNombreUsuario())
                    .contrasena(passwordEncoder.encode(registration.getContrasena()))
                    .primerNombre(registration.getPrimerNombre())
                    .segundoNombre(registration.getSegundoNombre())
                    .primerApellido(registration.getPrimerApellido())
                    .segundoApellido(registration.getSegundoApellido())
                    .documento(registration.getDocumento())
                    .fechaNacimiento(registration.getFechaNacimiento())
                    .correoPersonal(registration.getCorreoPersonal())
                    .telefono(registration.getTelefono())
                    .departamento(departamento)
                    .localidad(localidad)
                    .correoInstitucional(registration.getCorreoInstitucional())
                    .itr(itr)
                    .generacion(registration.getGeneracion())
                    .genero(registration.getGenero())
                    .activo(false)
                    .roles(Collections.singletonList(rolEstudiante))
                    .build();
        } else {
            usuario = Tutor.builder()
                    .nombreUsuario(registration.getNombreUsuario())
                    .contrasena(passwordEncoder.encode(registration.getContrasena()))
                    .primerNombre(registration.getPrimerNombre())
                    .segundoNombre(registration.getSegundoNombre())
                    .primerApellido(registration.getPrimerApellido())
                    .segundoApellido(registration.getSegundoApellido())
                    .documento(registration.getDocumento())
                    .fechaNacimiento(registration.getFechaNacimiento())
                    .correoPersonal(registration.getCorreoPersonal())
                    .telefono(registration.getTelefono())
                    .departamento(departamento)
                    .localidad(localidad)
                    .genero(registration.getGenero())
                    .correoInstitucional(registration.getCorreoInstitucional())
                    .itr(itr)
                    .area(registration.getArea())
                    .rolDocente(registration.getRolDocente())
                    .activo(false)
                    .roles(Collections.singletonList(rolTutor))
                    .build();
        }
        userRepository.save(usuario);
    }

    @Override
    public Usuario findByUsername(String username) {
        return userRepository.findByNombreUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    }

    @Override
    public Usuario findByPersonalEmail(String personalEmail) {
        return userRepository.findByCorreoPersonal(personalEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Email no encontrado"));
    }

    @Override
    public Usuario findByInstitutionalEmail(String institutionalEmail) {
        return userRepository.findByCorreoInstitucional(institutionalEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Email no encontrado"));
    }

    @Override
    public Usuario findByDocument(int document) {
        return userRepository.findByDocumento(document)
                .orElseThrow(() -> new UsernameNotFoundException("Documento no encontrado"));
    }

    @Override
    public UsuarioDto findUsuarioDtoById(Long id) {
        return mapToDto(userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado")));
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByNombreUsuario(username);
    }

    @Override
    public boolean existsByPersonalEmail(String personalEmail) {
        return userRepository.existsByCorreoPersonal(personalEmail);
    }

    @Override
    public boolean existsByInstitutionalEmail(String institutionalEmail) {
        return userRepository.existsByCorreoInstitucional(institutionalEmail);
    }

    @Override
    public boolean existsByDocument(int document) {
        return userRepository.existsByDocumento(document);
    }

    public void changeUserStatus(Long id) {
        Usuario user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        user.setActivo(!user.isActivo());
        userRepository.save(user);
    }

    @Override
    public void updateUser(UsuarioDto usuarioDto) {

        Usuario usuario = userRepository.findById(usuarioDto.getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setContrasena(passwordEncoder.encode(usuarioDto.getContrasena()));
        usuario.setPrimerNombre(usuarioDto.getPrimerNombre());
        usuario.setSegundoNombre(usuarioDto.getSegundoNombre());
        usuario.setPrimerApellido(usuarioDto.getPrimerApellido());
        usuario.setSegundoApellido(usuarioDto.getSegundoApellido());
        usuario.setDocumento(usuarioDto.getDocumento());
        usuario.setFechaNacimiento(usuarioDto.getFechaNacimiento());
        usuario.setCorreoPersonal(usuarioDto.getCorreoPersonal());
        usuario.setTelefono(usuarioDto.getTelefono());

        usuario.setDepartamento(departmentRepository.findById(usuarioDto.getDepartamento())
                .orElseThrow(() -> new RuntimeException("Departamento no encontrado")));

        usuario.setLocalidad(localityRepository.findById(usuarioDto.getLocalidad())
                .orElseThrow(() -> new RuntimeException("Localidad no encontrada")));

        usuario.setCorreoInstitucional(usuarioDto.getCorreoInstitucional());

        usuario.setItr(itrRepository.findById(usuarioDto.getItr())
                .orElseThrow(() -> new RuntimeException("ITR no encontrado")));

        if (usuario instanceof Estudiante estudiante) {
            estudiante.setGeneracion(usuarioDto.getGeneracion());
        } else if (usuario instanceof Tutor tutor) {
            tutor.setArea(usuarioDto.getArea());
            tutor.setRolDocente(usuarioDto.getRolDocente());
        }

        usuario.setActivo(usuarioDto.isActivo());

        userRepository.save(usuario);
    }


    @Override
    public List<UsuarioDto> findAll() {
        return userRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioDto findUsuarioDtoByUsername(String username) {
        Usuario user = userRepository.findByNombreUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return mapToDto(user);
    }

    public UsuarioDto mapToDto(Usuario usuario) {
        UsuarioDto usuarioDto = UsuarioDto.builder()
                .id(usuario.getId())
                .nombreUsuario(usuario.getNombreUsuario())
                .contrasena(usuario.getContrasena())
                .primerNombre(usuario.getPrimerNombre())
                .segundoNombre(usuario.getSegundoNombre())
                .primerApellido(usuario.getPrimerApellido())
                .segundoApellido(usuario.getSegundoApellido())
                .documento(usuario.getDocumento())
                .fechaNacimiento(usuario.getFechaNacimiento())
                .correoPersonal(usuario.getCorreoPersonal())
                .telefono(usuario.getTelefono())
                .departamento(usuario.getDepartamento().getId())
                .localidad(usuario.getLocalidad().getId())
                .correoInstitucional(usuario.getCorreoInstitucional())
                .itr(usuario.getItr().getId())
                .activo(usuario.isActivo())
                .edad(usuario.getEdad())
                .build();

        if (usuario instanceof Estudiante estudiante) {
            usuarioDto.setGeneracion(estudiante.getGeneracion());
        } else if (usuario instanceof Tutor tutor) {
            usuarioDto.setArea(tutor.getArea());
            usuarioDto.setRolDocente(tutor.getRolDocente());
        }

        return usuarioDto;
    }


    public Usuario mapToEntity(UsuarioDto usuarioDto) {

        Rol rolEstudiante = roleRepository.findByNombre("ROLE_STUDENT")
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        Rol rolDocente = roleRepository.findByNombre("ROLE_TEACHER")
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        Usuario usuario;

        if (usuarioDto.getGeneracion() != 0) {
            usuario = Estudiante.builder()
                    .id(usuarioDto.getId())
                    .nombreUsuario(usuarioDto.getNombreUsuario())
                    .contrasena(usuarioDto.getContrasena())
                    .primerNombre(usuarioDto.getPrimerNombre())
                    .segundoNombre(usuarioDto.getSegundoNombre())
                    .primerApellido(usuarioDto.getPrimerApellido())
                    .segundoApellido(usuarioDto.getSegundoApellido())
                    .documento(usuarioDto.getDocumento())
                    .fechaNacimiento(usuarioDto.getFechaNacimiento())
                    .correoPersonal(usuarioDto.getCorreoPersonal())
                    .telefono(usuarioDto.getTelefono())
                    .departamento(departmentRepository.findById(usuarioDto.getDepartamento())
                            .orElseThrow(() -> new RuntimeException("Departamento no encontrado")))
                    .localidad(localityRepository.findById(usuarioDto.getLocalidad())
                            .orElseThrow(() -> new RuntimeException("Localidad no encontrada")))
                    .correoInstitucional(usuarioDto.getCorreoInstitucional())
                    .itr(itrRepository.findById(usuarioDto.getItr())
                            .orElseThrow(() -> new RuntimeException("ITR no encontrado")))
                    .generacion(usuarioDto.getGeneracion())
                    .activo(usuarioDto.isActivo())
                    .roles(Collections.singletonList(rolEstudiante))
                    .build();
        } else {
            usuario = Tutor.builder()
                    .id(usuarioDto.getId())
                    .nombreUsuario(usuarioDto.getNombreUsuario())
                    .contrasena(usuarioDto.getContrasena())
                    .primerNombre(usuarioDto.getPrimerNombre())
                    .segundoNombre(usuarioDto.getSegundoNombre())
                    .primerApellido(usuarioDto.getPrimerApellido())
                    .segundoApellido(usuarioDto.getSegundoApellido())
                    .documento(usuarioDto.getDocumento())
                    .fechaNacimiento(usuarioDto.getFechaNacimiento())
                    .correoPersonal(usuarioDto.getCorreoPersonal())
                    .telefono(usuarioDto.getTelefono())
                    .departamento(departmentRepository.findById(usuarioDto.getDepartamento())
                            .orElseThrow(() -> new RuntimeException("Departamento no encontrado")))
                    .localidad(localityRepository.findById(usuarioDto.getLocalidad())
                            .orElseThrow(() -> new RuntimeException("Localidad no encontrada")))
                    .correoInstitucional(usuarioDto.getCorreoInstitucional())
                    .itr(itrRepository.findById(usuarioDto.getItr())
                            .orElseThrow(() -> new RuntimeException("ITR no encontrado")))
                    .area(usuarioDto.getArea())
                    .rolDocente(usuarioDto.getRolDocente())
                    .activo(usuarioDto.isActivo())
                    .roles(Collections.singletonList(rolDocente))
                    .build();
        }

        return usuario;
    }

    @Override
    public String getUsernameById(Long id) {
        return userRepository
                .findById(id)
                .map(Usuario::getNombreUsuario)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public List<UsuarioDto> findStudentsWithClaims() {
        return studentRepository.findAll().stream()
                .filter(user -> !user.getReclamos().isEmpty())
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UsuarioDto> getAllTutores() {
        // Usa el ID del rol de Tutor (en este caso, 2)
        return userRepository.findUsersByRoleId(2L).stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<UsuarioDto> getAllStudents() {
        return userRepository.findUsersByRoleId(3L).stream().map(this::mapToDto).collect(Collectors.toList());
    }
}
