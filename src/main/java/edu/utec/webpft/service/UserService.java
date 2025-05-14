package edu.utec.webpft.service;

import edu.utec.webpft.dtos.UsuarioDto;
import edu.utec.webpft.entidades.Usuario;

import java.util.List;

public interface UserService {

    void register(UsuarioDto userDto);
    Usuario findByUsername(String username);
    Usuario findByPersonalEmail(String personalEmail);
    Usuario findByInstitutionalEmail(String institutionalEmail);
    Usuario findByDocument(int document);

    void updateUser(UsuarioDto userDto);

    List<UsuarioDto> findAll();

    void changeUserStatus(Long id);

    boolean existsByUsername(String username);
    boolean existsByPersonalEmail(String personalEmail);
    boolean existsByInstitutionalEmail(String institutionalEmail);
    boolean existsByDocument(int document);

    UsuarioDto findUsuarioDtoByUsername(String name);

    UsuarioDto findUsuarioDtoById(Long id);

    String getUsernameById(Long id);

    List<UsuarioDto> findStudentsWithClaims();

    List<UsuarioDto> getAllTutores();

    List<UsuarioDto> getAllStudents();
}
