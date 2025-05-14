package edu.utec.webpft.repository;

import edu.utec.webpft.entidades.Evento;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EventRepository extends JpaRepository<Evento, Long> {
}
