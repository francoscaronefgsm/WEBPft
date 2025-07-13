package edu.utec.webpft.repository;

import edu.utec.webpft.entidades.Justificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JustificationRepository extends JpaRepository<Justificacion, Long> {
    List<Justificacion> findByEstudianteIdAndAnuladoFalseOrAnuladoIsNull(Long id);
    List<Justificacion> findByAnuladoFalseOrAnuladoIsNull();
}
