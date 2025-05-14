package edu.utec.webpft.service;

import edu.utec.webpft.dtos.DepartamentoDto;

import java.util.List;

public interface DepartmentService {

    DepartamentoDto save(DepartamentoDto departmentDto);

    DepartamentoDto update(Long id, DepartamentoDto departmentDto);

    void delete(Long id);

    DepartamentoDto findById(Long id);

    List<DepartamentoDto> findAll();
}
