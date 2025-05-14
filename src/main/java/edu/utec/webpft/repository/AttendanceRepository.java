package edu.utec.webpft.repository;



import edu.utec.webpft.entidades.Asistencia;
import edu.utec.webpft.entidades.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AttendanceRepository extends JpaRepository<Asistencia, Long> {
    Boolean existsByEstudianteIdAndEventoId(Long studentId, Long eventId);

    @Query("SELECT e FROM Evento e JOIN e.asistencias a WHERE a.estudiante.id = :studentId")
    List<Evento> findEventosByEstudianteId(@Param("studentId") Long studentId);
}
