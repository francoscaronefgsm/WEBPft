package edu.utec.webpft.service.imp;

import edu.utec.webpft.dtos.ConstanciaDto;
import edu.utec.webpft.entidades.Constancia;
import edu.utec.webpft.repository.*;
import edu.utec.webpft.service.ConstancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConstancyServiceImpl implements ConstancyService {

    private final EventRepository eventRepository;
    private final ConstancyRepository constancyRepository;
    private final StudentRepository studentRepository;
    private final EstadoConstanciaRepository estadoConstanciaRepository;
    private final TipoConstanciaRepository tipoConstanciaRepository;


    private Constancia mapToEntity(ConstanciaDto constancyDto) {
        Constancia constancy = new Constancia();
        constancy.setId(constancyDto.getId());
        constancy.setTipoConstancia(tipoConstanciaRepository.getById(constancyDto.getTipoConstancia()));
        constancy.setInformacion(constancyDto.getInformacion());
        constancy.setEvento(eventRepository.findById(constancyDto.getEvento())
                .orElseThrow(() -> new RuntimeException("Evento no encontrado")));
        constancy.setEstudiante(studentRepository.findById(constancyDto.getEstudiante())
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado")));
        constancy.setFecha(constancyDto.getFecha());
        constancy.setEstadoConstancia(estadoConstanciaRepository.getById(constancyDto.getEstadoConstancia()));


        return constancy;
    }

    private ConstanciaDto mapToDto(Constancia constancy) {
        ConstanciaDto constancyDto = new ConstanciaDto();
        constancyDto.setId(constancy.getId());
        constancyDto.setTipoConstancia(constancy.getTipoConstancia().getId());
        constancyDto.setTituloEvento(constancy.getEvento().getTitulo());
        constancyDto.setInformacion(constancy.getInformacion());
        constancyDto.setEvento(constancy.getEvento().getId());
        constancyDto.setEstudiante(constancy.getEstudiante().getId());
        constancyDto.setFecha(constancy.getFecha());
        constancyDto.setEstadoConstancia(constancy.getEstadoConstancia().getId());
        return constancyDto;
    }


    @Override
    public ConstanciaDto save(ConstanciaDto constancyDto) {
        Constancia constancy = constancyRepository.save(mapToEntity(constancyDto));
        return mapToDto(constancy);
    }

    @Override
    public ConstanciaDto update(Long id, ConstanciaDto constancyDto) {
        Constancia constancy = constancyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Constancia not found"));
        constancy.setId(constancyDto.getId());
        constancy.setTipoConstancia(tipoConstanciaRepository.getById(constancyDto.getTipoConstancia()));
        constancy.setFecha(constancyDto.getFecha());
        constancy.setInformacion(constancyDto.getInformacion());
        constancy.setEvento(eventRepository.findById(constancyDto.getEvento())
                .orElseThrow(() -> new RuntimeException("Evento no encontrado")));

        return mapToDto(constancyRepository.save(constancy));
    }

    @Override
    public void delete(Long id) {
        Constancia constancy = constancyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Constancia not found"));
        constancyRepository.delete(constancy);
    }

    @Override
    public ConstanciaDto findById(Long id) {
        Constancia constancy = constancyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Constancia not found"));
        return mapToDto(constancy);
    }

    @Override
    public List<ConstanciaDto> findAll() {
        return constancyRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public List<ConstanciaDto> findConstancyByStudent_Id(Long userId) {
        return constancyRepository.findConstancyByEstudianteId(userId).stream()
                .map(this::mapToDto)
                .toList();
    }
}
