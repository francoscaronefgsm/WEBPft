package edu.utec.webpft.repository;

import edu.utec.webpft.entidades.TipoEvento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoEventoRepository extends JpaRepository<TipoEvento, Long> {
}