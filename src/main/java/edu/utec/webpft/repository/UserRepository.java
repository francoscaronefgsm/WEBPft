package edu.utec.webpft.repository;

import edu.utec.webpft.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByNombreUsuario(String username);
    Optional<Usuario> findByCorreoPersonal(String personalEmail);
    Optional<Usuario> findByCorreoInstitucional(String institutionalEmail);
    Optional<Usuario> findByDocumento(int document);
    Optional<Usuario> findUserById(Long id);

    boolean existsByNombreUsuario(String username);
    boolean existsByCorreoPersonal(String personalEmail);
    boolean existsByCorreoInstitucional(String institutionalEmail);
    boolean existsByDocumento(int document);

    @Query("SELECT u FROM Usuario u JOIN u.roles r WHERE r.id = :roleId")
    List<Usuario> findUsersByRoleId(@Param("roleId") Long roleId);
}
