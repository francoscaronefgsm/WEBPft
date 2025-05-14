package edu.utec.webpft.service.imp;

import edu.utec.webpft.dtos.EventoDto;
import edu.utec.webpft.entidades.*;
import edu.utec.webpft.repository.*;
import edu.utec.webpft.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImp implements EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;
    private final ItrRepository itrRepository;
    private final EventTeacherRepository eventTutorRepository;
    private final EstadoEventoRepository estadoEventoRepository;
    private final TipoEventoRepository tipoEventoRepository;
    private final ModalidadEventoRepository modalidadEventoRepository;

    private Evento mapToEntity(EventoDto eventDto) {
        Evento event = new Evento();
        event.setId(eventDto.getId());
        event.setTitulo(eventDto.getTitulo());
        event.setModalidad(modalidadEventoRepository.getById(eventDto.getModalidad()));
        event.setTipoEvento(tipoEventoRepository.getById(eventDto.getTipoEvento()));
        event.setEstadoEvento(estadoEventoRepository.getById(eventDto.getEstado()));
        event.setFechaInicio(eventDto.getFechaInicio());
        event.setFechaFin(eventDto.getFechaFin());
        event.setItr(itrRepository.getReferenceById(eventDto.getItr()));
        event.setUbicacion(eventDto.getUbicacion());
        List<Usuario> validTutores = eventDto.getDocentes().stream()
                .map(teacher -> teacherRepository.findById(teacher).orElse(null))
                .filter(Objects::nonNull) // Filtrar solo aquellos que existen en el repositorio
                .collect(Collectors.toList());
        event.setTutores(validTutores);
        return event;
    }

    private EventoDto mapToDto(Evento event) {
        EventoDto eventDto = new EventoDto();
        eventDto.setId(event.getId());
        eventDto.setTitulo(event.getTitulo());
        eventDto.setModalidad(event.getModalidad().getId());
        eventDto.setTipoEvento(event.getTipoEvento().getId());
        eventDto.setEstado(event.getEstadoEvento().getId());
        eventDto.setFechaInicio(event.getFechaInicio());
        eventDto.setFechaFin(event.getFechaFin());
        eventDto.setItr(event.getItr().getId());
        eventDto.setUbicacion(event.getUbicacion());
        // Convertimos los objetos Tutor en IDs de teachers
        List<Long> teacherIds = event.getTutores().stream()
                .map(Usuario::getId)
                .collect(Collectors.toList());
        eventDto.setDocentes(teacherIds);
        return eventDto;
    }

    @Override
    public EventoDto save(EventoDto eventDto) {
        Evento event = eventRepository.save(mapToEntity(eventDto));
        return mapToDto(event);
    }

    @Override
    public EventoDto update(Long id, EventoDto eventDto) {
        Evento event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento not found"));
        event.setId(eventDto.getId());
        event.setTitulo(eventDto.getTitulo());
        event.setModalidad(modalidadEventoRepository.getById(eventDto.getModalidad()));
        event.setTipoEvento(tipoEventoRepository.getById(eventDto.getTipoEvento()));
        event.setEstadoEvento(estadoEventoRepository.getById(eventDto.getEstado()));
        event.setFechaInicio(eventDto.getFechaInicio());
        event.setFechaFin(eventDto.getFechaFin());
        event.setItr(itrRepository.getReferenceById(eventDto.getItr())); // Asumiendo que el objeto Itr ya está en formato adecuado
        event.setUbicacion(eventDto.getUbicacion());
        List<Usuario> validTutores = eventDto.getDocentes().stream()
                .map(teacherId -> userRepository.findUserById(teacherId).orElse(null))
                .filter(Objects::nonNull) // Filtrar solo aquellos que existen en el repositorio
                .collect(Collectors.toList());
        event.setTutores(validTutores);// Asumiendo que la lista de Tutor ya está mapeada
        return mapToDto(eventRepository.save(event));
    }

    @Override
    public void delete(Long id) {
        Evento claim = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento not found"));
        eventRepository.delete(claim);
    }

    @Override
    public EventoDto findById(Long id) {
        Evento event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento not found"));
        return mapToDto(event);
    }

    @Override
    public List<EventoDto> findAll() {
        return eventRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public List<EventoDto> findAllByUserId(Long userId) {
        return eventTutorRepository.findByTutoresId(userId).stream()
                .map(this::mapToDto)
                .toList();
    }


}
