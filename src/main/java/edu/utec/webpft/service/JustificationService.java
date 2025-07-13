package edu.utec.webpft.service;

import edu.utec.webpft.dtos.JustificacionDto;

import java.util.List;

public interface JustificationService {

    JustificacionDto save(JustificacionDto justificacionDto);

    JustificacionDto update(Long id, JustificacionDto justificacionDto);

    void delete(Long id);

    JustificacionDto findById(Long id);

    List<JustificacionDto> findAll();

    List<JustificacionDto> findAllByStudentId(Long studentId);
}
