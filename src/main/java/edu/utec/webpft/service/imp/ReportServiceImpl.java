package edu.utec.webpft.service.imp;

import edu.utec.webpft.dtos.*;
import edu.utec.webpft.entidades.*;
import edu.utec.webpft.repository.*;
import edu.utec.webpft.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ConstancyRepository constancyRepository;
    private final JustificationRepository justificationRepository;
    private final AttendanceRepository attendanceRepository;

    private List<JustificacionDto> mapListJustificationToDto(List<Justificacion> justifications){
        List<JustificacionDto> justificationDtos = new ArrayList<>();
        for(Justificacion justification : justifications){
            JustificacionDto justificationDto = new JustificacionDto();
            justificationDto.setId(justification.getId());
            justificationDto.setFecha(justification.getFecha());
            justificationDto.setEstado(justification.getEstadoJustificacion().getId());
            justificationDto.setEvento(justification.getEvento().getId());
            justificationDto.setDescripcionEstado(justification.getEstadoJustificacion().getDescripcion());
            justificationDto.setInformacion(justification.getInformacion());
            justificationDto.setEstudiante(justification.getEstudiante().getId());
            justificationDtos.add(justificationDto);
        }
        return justificationDtos;
    }

    private List<ConstanciaDto> mapListConstanciesToDto(List<Constancia> constancies){
        List<ConstanciaDto> constancyDtos = new ArrayList<>();
        for(Constancia constancy : constancies){
            ConstanciaDto constancyDto = new ConstanciaDto();
            constancyDto.setId(constancy.getId());
            constancyDto.setFecha(constancy.getFecha());
            constancyDto.setEstadoConstancia(constancy.getEstadoConstancia().getId());
            constancyDto.setEvento(constancy.getEvento().getId());
            constancyDto.setTituloEvento(constancy.getEvento().getTitulo());
            constancyDto.setTipoConstancia(constancy.getTipoConstancia().getId());
            constancyDto.setDescripcionEstadoConstancia(constancy.getEstadoConstancia().getDescripcion());
            constancyDto.setDescripcionTipoConstancia(constancy.getTipoConstancia().getDescripcion());
            constancyDto.setInformacion(constancy.getInformacion());
            constancyDto.setEstudiante(constancy.getEstudiante().getId());
            constancyDtos.add(constancyDto);
        }
        return constancyDtos;
    }

    private List<EventoDto> mapListEventsToDto(List<Evento> events){
        List<EventoDto> eventDtos = new ArrayList<>();
        for(Evento event : events){
            EventoDto eventDto = new EventoDto();
            eventDto.setId(event.getId());
            eventDto.setUbicacion(event.getUbicacion());
            eventDto.setItr(event.getItr().getId());
            eventDto.setNombreItr(event.getItr().getNombre());
            eventDto.setModalidad(event.getModalidad().getId());
            eventDto.setTipoEvento(event.getTipoEvento().getId());
            eventDto.setEstado(event.getEstadoEvento().getId());
            eventDto.setTitulo(event.getTitulo());
            eventDto.setFechaInicio(event.getFechaInicio());
            eventDto.setFechaFin(event.getFechaFin());
            eventDto.setDocentes(event.getTutores().stream().map(Usuario::getId).collect(Collectors.toList()));
            eventDto.setNombresDocentes(event.getTutores()
                    .stream()
                    .map(teacher -> teacher.getPrimerNombre() + " " + teacher.getPrimerApellido())
                    .collect(Collectors.toList()).toString());
            eventDto.setDescripcionEstadoEvento(event.getEstadoEvento().getDescripcion());
            eventDto.setDescripcionTipoEvento(event.getTipoEvento().getDescripcion());
            eventDto.setDescripcionModalidad(event.getModalidad().getDescripcion());
            eventDtos.add(eventDto);
        }
        return eventDtos;
    }

    @Override
    public List<JustificacionDto> findJustificationsByStudentId(Long studentId) {
        return mapListJustificationToDto(justificationRepository.findByEstudianteIdAndAnuladoFalseOrAnuladoIsNull(studentId));
    }

    @Override
    public List<ConstanciaDto> findConstanciesByStudentId(Long studentId) {
        return mapListConstanciesToDto(constancyRepository.findAllByEstudianteId(studentId));

    }

    @Override
    public List<EventoDto> findEventsByStudentId(Long userId) {
        return mapListEventsToDto(attendanceRepository.findEventosByEstudianteId(userId));
    }

}
