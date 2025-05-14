package edu.utec.webpft.repository;

import edu.utec.webpft.entidades.Reclamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClaimRepository extends JpaRepository<Reclamo, Long> {

    List<Reclamo> findAllByEstudianteId(Long id);
    Reclamo findReclamoById(Long id);
}
