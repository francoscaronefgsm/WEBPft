package edu.utec.webpft.service;

import edu.utec.webpft.dtos.ConstanciaDto;
import edu.utec.webpft.dtos.EventoDto;
import edu.utec.webpft.dtos.JustificacionDto;

import java.util.List;

public interface ReportService {
    List<JustificacionDto> findJustificationsByStudentId(Long studentId);
    List<ConstanciaDto> findConstanciesByStudentId(Long studentId);
    List<EventoDto> findEventsByStudentId(Long studentId);
}
