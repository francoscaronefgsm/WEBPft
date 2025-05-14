package edu.utec.webpft.service;


import edu.utec.webpft.dtos.EventoDto;

import java.util.List;

public interface EventService {

    EventoDto save(EventoDto eventDto);

    EventoDto update(Long id, EventoDto eventDto);

    void delete(Long id);

    EventoDto findById(Long id);

    List<EventoDto> findAll();

    List<EventoDto> findAllByUserId(Long userId);
}
