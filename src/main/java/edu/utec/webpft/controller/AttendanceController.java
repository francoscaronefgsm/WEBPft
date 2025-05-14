package edu.utec.webpft.controller;

import edu.utec.webpft.dtos.*;
import edu.utec.webpft.service.AttendanceService;
import edu.utec.webpft.service.AuxiliaresService;
import edu.utec.webpft.service.EventService;
import edu.utec.webpft.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/attendances")
@RequiredArgsConstructor
public class AttendanceController {

    private final EventService eventService;
    private final UserService userService;
    private final AttendanceService attendanceService;
    private final Constantes constantes = new Constantes();
    private final AuxiliaresService auxiliaresService;

    @GetMapping("/attendancesAnalist")
    public String listEvents(Model model) {
        List<AsistenciaDto> attendances = attendanceService.findAll();
        List<AsistenciaDto> attendancesFiltradas = attendances.stream()
                .filter(attendance -> attendance.getEstado().isEmpty()) // Filtrar por status vacío
                .collect(Collectors.toList());

        List<String> attendanceStatus = constantes.getAttendanceStatus();

        for (AsistenciaDto attendance : attendancesFiltradas) {
            UsuarioDto user = userService.findUsuarioDtoById(attendance.getEstudiante());
            attendance.setNombreEvento(eventService.findById(attendance.getEvento()).getTitulo());
            attendance.setNombreEstudiante(user.getPrimerNombre()+" "+user.getPrimerApellido()+" "+user.getSegundoApellido());
        }
        // Pasar la lista de reclamos a la vista
        model.addAttribute("attendances", attendancesFiltradas);
        model.addAttribute("attendanceStatus", attendanceStatus);

        return "attendances/attendanceRegisterAnalist";
    }

    @GetMapping("/attendancesTutor")
    public String teacherListEvents(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UsuarioDto userDto = userService.findUsuarioDtoByUsername(username);
        Long teacherId = userDto.getId(); // Suponiendo que el ID del teacher está en el UsuarioDto

        // Obtener todas las asistencias y filtrar por status vacío y eventos donde el teacher esté en la lista
        List<AsistenciaDto> attendances = attendanceService.findAll();
        List<AsistenciaDto> attendancesFiltradas = attendances.stream()
                .filter(attendance -> attendance.getEstado().isEmpty()) // Filtrar por status vacío
                .filter(attendance -> eventService.findById(attendance.getEvento()).getDocentes().stream()
                        .anyMatch(teacher -> teacher.equals(teacherId))) // Filtrar si el teacher está en la lista
                .collect(Collectors.toList());

        List<String> attendanceStatus = constantes.getAttendanceStatus();
        for (AsistenciaDto attendance : attendancesFiltradas) {
            UsuarioDto user = userService.findUsuarioDtoById(attendance.getEstudiante());
            attendance.setNombreEvento(eventService.findById(attendance.getEvento()).getTitulo());
            attendance.setNombreEstudiante(user.getPrimerNombre()+" "+user.getPrimerApellido()+" "+user.getSegundoApellido());
        }
        // Pasar la lista de asistencias filtradas a la vista
        model.addAttribute("attendances", attendancesFiltradas);
        model.addAttribute("attendanceStatus", attendanceStatus);

        return "attendances/attendanceRegisterTutor";
    }




    @GetMapping("/calls")
    public String listEventsForCalls(Model model) {
        AsistenciaDto attendanceDto = new AsistenciaDto();
        List<EventoDto> events = eventService.findAll();
        List<UsuarioDto> students = userService.getAllStudents();

        // Filtrar estudiantes que tengan al menos un evento pendiente de asistencia
        List<UsuarioDto> filteredStudents = students.stream()
                .filter(student -> events.stream()
                        .anyMatch(event -> !attendanceService.hasAttendance(student.getId(), event.getId())))

                .collect(Collectors.toList());
        // Pasar la lista de reclamos a la vista
        model.addAttribute("events", events);
        model.addAttribute("students", filteredStudents);
        model.addAttribute("attendance", attendanceDto);

        return "callsStudentForEvent/callsStudentForEvent";
    }

    @PostMapping("/registerCall")
    public String registerCallAttendance(@ModelAttribute AsistenciaDto attendanceDto) {
        // Lógica para guardar la asistencia
        attendanceService.saveCall(attendanceDto);
        return "redirect:/attendances/calls";
    }

    @PostMapping("/register")
    public String registerAttendance(@ModelAttribute AsistenciaDto attendanceDto) {
        // Lógica para guardar la asistencia
        AsistenciaDto attendanceOriginal = attendanceService.findById(attendanceDto.getId());
        attendanceDto.setEstudiante(attendanceOriginal.getEstudiante());
        attendanceDto.setEvento(attendanceOriginal.getEvento());
        attendanceService.save(attendanceDto);
        return "redirect:/attendances/attendancesAnalist";
    }

    @PostMapping("/registerByTutor")
    public String registerAttendanceByTutor(@ModelAttribute AsistenciaDto attendanceDto) {
        // Lógica para guardar la asistencia
        AsistenciaDto attendanceOriginal = attendanceService.findById(attendanceDto.getId());
        attendanceDto.setEstudiante(attendanceOriginal.getEstudiante());
        attendanceDto.setEvento(attendanceOriginal.getEvento());
        attendanceService.save(attendanceDto);
        return "redirect:/attendances/attendancesTutor";
    }


}
