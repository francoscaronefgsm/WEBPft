package edu.utec.webpft.repository;


import edu.utec.webpft.entidades.Constancia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConstancyRepository extends JpaRepository<Constancia, Long> {
    List<Constancia> findConstancyByEstudianteId(Long studentId);
    List<Constancia> findConstancyByEstudianteIdAndAnuladoFalseOrAnuladoNull(Long studentId);
    List<Constancia> findAllByEstudianteId(Long id);
    List<Constancia> findByAnuladoFalseOrAnuladoIsNull();
}
