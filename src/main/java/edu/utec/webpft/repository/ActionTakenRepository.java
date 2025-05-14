package edu.utec.webpft.repository;

import edu.utec.webpft.entidades.AccionRealizada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionTakenRepository extends JpaRepository<AccionRealizada, Long> {
}
