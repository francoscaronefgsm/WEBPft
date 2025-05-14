package edu.utec.webpft.service;

import edu.utec.webpft.dtos.ConstanciaDto;

import java.util.List;

public interface ConstancyService {

    ConstanciaDto save(ConstanciaDto constancyDto);

    ConstanciaDto update(Long id, ConstanciaDto constancyDto);

    void delete(Long id);

    ConstanciaDto findById(Long id);

    List<ConstanciaDto> findAll();

    List<ConstanciaDto> findConstancyByStudent_Id(Long userId);


}
