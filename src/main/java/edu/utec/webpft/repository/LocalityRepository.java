package edu.utec.webpft.repository;

import edu.utec.webpft.entidades.Localidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocalityRepository extends JpaRepository<Localidad, Long> {
    List<Localidad> findByDepartamentoId(Long departmentId);
}
