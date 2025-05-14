package edu.utec.webpft.service;

import edu.utec.webpft.dtos.ItrDto;

import java.util.List;

public interface ItrService {

    ItrDto save(ItrDto itrDto);

    ItrDto update(Long id, ItrDto itrDto);

    void delete(Long id);

    ItrDto findById(Long id);

    List<ItrDto> findAll();
}
