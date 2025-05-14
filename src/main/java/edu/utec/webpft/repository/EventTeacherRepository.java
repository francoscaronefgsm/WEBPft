package edu.utec.webpft.repository;

import edu.utec.webpft.entidades.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventTeacherRepository extends JpaRepository<Evento, Long> {
    List<Evento> findByTutoresId(Long teacherId);
}
