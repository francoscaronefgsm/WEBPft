package edu.utec.webpft.service;

import edu.utec.webpft.dtos.LocalidadDto;

import java.util.List;

public interface LocalityService {

    LocalidadDto save(LocalidadDto localityDto);

    LocalidadDto update(Long id, LocalidadDto localityDto);

    void delete(Long id);

    LocalidadDto findById(Long id);

    List<LocalidadDto> findAll();

    List<LocalidadDto> findLocalityByDepartment(Long departmentId);
}
