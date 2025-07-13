package edu.utec.webpft.service.imp;

import edu.utec.webpft.dtos.JustificacionDto;
import edu.utec.webpft.entidades.Justificacion;
import edu.utec.webpft.repository.*;
import edu.utec.webpft.service.JustificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JustificationServiceImpl implements JustificationService {

    private final EventRepository eventRepository;
    private final StudentRepository studentRepository;
    private final EstadoJustificacionRepository estadoJustificacionRepository;
    private final JustificationRepository justificationRepository;

    private Justificacion mapToEntity (JustificacionDto justificacionDto){
        Justificacion justificacion = new Justificacion();
        justificacion.setEstadoJustificacion(estadoJustificacionRepository.findById(justificacionDto.getEstado()).orElse(null));
        justificacion.setEstudiante(studentRepository.getById(justificacionDto.getEstudiante()));
        justificacion.setFecha(justificacionDto.getFecha());
        justificacion.setInformacion(justificacionDto.getInformacion());
        justificacion.setEvento(eventRepository.getById(justificacionDto.getEvento()));

        return justificacion;
    }

    private JustificacionDto mapToDto(Justificacion justificacion){
        JustificacionDto justificacionDto = new JustificacionDto();
        justificacionDto.setId(justificacion.getId());
        justificacionDto.setDescripcionEstado(justificacion.getEstadoJustificacion().getDescripcion());
        justificacionDto.setFecha(justificacion.getFecha());
        justificacionDto.setInformacion(justificacion.getInformacion());
        justificacionDto.setEstudiante(justificacion.getEstudiante().getId());
        justificacionDto.setEvento(justificacion.getEvento().getId());
        justificacionDto.setDescripcionEvento(justificacion.getEvento().getTitulo());
        justificacionDto.setNombreEstudiante(justificacion.getEstudiante().getPrimerNombre()+" "+justificacion.getEstudiante().getPrimerApellido());

        return justificacionDto;
    }

    @Override
    public JustificacionDto save(JustificacionDto justificacionDto) {
        Justificacion justificacion = justificationRepository.save(mapToEntity(justificacionDto));
        return mapToDto(justificacion);
    }

    @Override
    public JustificacionDto update(Long id, JustificacionDto justificacionDto) {
        Justificacion justificacion = justificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Justificacion no encontrada"));
        justificacion.setId(justificacionDto.getId());
        justificacion.setInformacion(justificacionDto.getInformacion());
        justificacion.setFecha(justificacionDto.getFecha());
        justificacion.setEvento(eventRepository.findById(justificacionDto.getEvento())
                .orElseThrow(() -> new RuntimeException("Evento no encontrado")));

        return mapToDto(justificationRepository.save(justificacion));
    }

    @Override
    public void delete(Long id) {
        Justificacion justificacion = justificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Justificacion no encontrada"));
        justificacion.setAnulado(true);
        justificationRepository.save(justificacion);
    }

    @Override
    public JustificacionDto findById(Long id) {
        Justificacion justificacion = justificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Justificacion no encontrada"));
        return mapToDto(justificacion);
    }

    @Override
    public List<JustificacionDto> findAll() {
        return justificationRepository.findByAnuladoFalseOrAnuladoIsNull()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public List<JustificacionDto> findAllByStudentId(Long studentId) {
        return justificationRepository.findByEstudianteIdAndAnuladoFalseOrAnuladoIsNull(studentId).stream()
                .map(this::mapToDto)
                .toList();
    }
}
