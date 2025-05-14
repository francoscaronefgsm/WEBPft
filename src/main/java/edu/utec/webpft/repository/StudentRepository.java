package edu.utec.webpft.repository;

import edu.utec.webpft.entidades.Estudiante;
import edu.utec.webpft.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Estudiante, Long> {

}
