package edu.utec.webpft.service.imp;

import edu.utec.webpft.dtos.AsistenciaDto;
import edu.utec.webpft.entidades.Asistencia;
import edu.utec.webpft.repository.AttendanceRepository;
import edu.utec.webpft.repository.EventRepository;
import edu.utec.webpft.repository.StudentRepository;
import edu.utec.webpft.repository.UserRepository;
import edu.utec.webpft.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;


    // Método para convertir un AsistenciaDto a una entidad Attendance
    private Asistencia mapToEntity(AsistenciaDto attendanceDto) {
        Asistencia asistencia = new Asistencia();
        asistencia.setId(attendanceDto.getId());
        asistencia.setEstudiante(studentRepository.findById(attendanceDto.getEstudiante())
                .orElseThrow(() -> new RuntimeException("User not found"))); // Asegúrate de que el Dto contenga el objeto Student
        asistencia.setEvento(eventRepository.findById(attendanceDto.getEvento())
                .orElseThrow(() -> new RuntimeException("Evento not found")));
        asistencia.setEstado(attendanceDto.getEstado());
        asistencia.setCalificacion(attendanceDto.getCalificacion());
        return asistencia;
    }

    // Método para convertir una entidad Asistenciaa un AsistenciaDto
    private AsistenciaDto mapToDto(Asistencia asistencia) {
        AsistenciaDto attendanceDto = new AsistenciaDto();
        attendanceDto.setId(asistencia.getId());
        attendanceDto.setEstudiante(asistencia.getEstudiante().getId());
        attendanceDto.setEvento(asistencia.getEvento().getId());
        if (asistencia.getEstado() != null) {
            attendanceDto.setEstado(asistencia.getEstado());
        }else{
            attendanceDto.setEstado("");
        }

        attendanceDto.setCalificacion(asistencia.getCalificacion());
        return attendanceDto;
    }

    @Override
    public void save(AsistenciaDto attendanceDto) {
        // Mapea el AsistenciaDto a una entidad y guarda en la base de datos
        Asistencia attendance = mapToEntity(attendanceDto);
        attendanceRepository.save(attendance);
    }

    @Override
    public void saveCall(AsistenciaDto attendanceDto) {
        Asistencia attendance = new Asistencia();
        attendance.setId(attendanceDto.getId());
        attendance.setEstudiante(studentRepository.findById(attendanceDto.getEstudiante())
                .orElseThrow(() -> new RuntimeException("User not found"))); // Asegúrate de que el Dto contenga el objeto Student
        attendance.setEvento(eventRepository.findById(attendanceDto.getEvento())
                .orElseThrow(() -> new RuntimeException("Evento not found")));
        attendanceRepository.save(attendance);
    }

    @Override
    public List<AsistenciaDto> findAll() {
        return attendanceRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public AsistenciaDto findById(long id) {
        return attendanceRepository.findById(id).map(this::mapToDto).orElse(null);
    }

    @Override
    public boolean hasAttendance(Long studentId, Long eventId) {
        return attendanceRepository.existsByEstudianteIdAndEventoId(studentId , eventId);
    }
}
