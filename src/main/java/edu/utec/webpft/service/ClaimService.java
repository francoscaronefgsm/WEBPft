package edu.utec.webpft.service;

import edu.utec.webpft.dtos.ReclamoDto;

import java.util.List;

public interface ClaimService {

    ReclamoDto save(ReclamoDto claimDto);

    ReclamoDto update(Long id, ReclamoDto claimDto);

    void delete(Long id);

    ReclamoDto findById(Long id);

    List<ReclamoDto> findAll();

    List<ReclamoDto> findAllByUserId(Long id);

    void changeStatus(Long id, Long status);

}
