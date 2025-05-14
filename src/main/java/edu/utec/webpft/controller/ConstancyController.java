package edu.utec.webpft.controller;

import edu.utec.webpft.dtos.ConstanciaDto;
import edu.utec.webpft.dtos.EventoDto;
import edu.utec.webpft.dtos.TipoConstanciaDto;
import edu.utec.webpft.dtos.UsuarioDto;
import edu.utec.webpft.entidades.Evento;
import edu.utec.webpft.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/constancy")
@RequiredArgsConstructor
public class ConstancyController {

    private final EventService eventService;
    private final UserService userService;
    private final ConstancyService constancyService;
    private final AttendanceService attendanceService;
    private final AuxiliaresService auxiliaresService;

    @GetMapping("/new")
    public String showNewConstancyForm(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UsuarioDto userDto = userService.findUsuarioDtoByUsername(username);

        ConstanciaDto constancyDto = new ConstanciaDto();
        List<TipoConstanciaDto> constancyTypes = auxiliaresService.obtenerTiposConstancia();
        List<EventoDto> allEvents = eventService.findAll();
        List<EventoDto> filteredEvents = allEvents.stream()
                .filter(event -> attendanceService.hasAttendance(userDto.getId(), event.getId()))
                .collect(Collectors.toList());
        model.addAttribute("constancyTypes", constancyTypes);
        model.addAttribute("events", filteredEvents);
        model.addAttribute("constancy", constancyDto);
        return "constancies/newConstancy";
    }


    @PostMapping("/create")
    public String createConstancy(@ModelAttribute("constancy") ConstanciaDto constancyDto, Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UsuarioDto userDto = userService.findUsuarioDtoByUsername(username);
        constancyDto.setEstudiante(userDto.getId());
        constancyDto.setEstadoConstancia(0l);
        constancyDto.setFecha(LocalDateTime.now());
        constancyService.save(constancyDto);
        return "redirect:/constancy/myList";
    }

    @GetMapping("/list")
    public String listConstancies(Model model) {
       List<ConstanciaDto> constancies = constancyService.findAll();
       List<EventoDto> allEvents = eventService.findAll();
       for (ConstanciaDto constancyDto : constancies) {
           UsuarioDto constancyStudent = userService.findUsuarioDtoByUsername(userService.getUsernameById(constancyDto.getEstudiante()));
           constancyDto.setNombreEstudiante(constancyStudent.getPrimerNombre()+" "+constancyStudent.getPrimerApellido()+" "+constancyStudent.getSegundoApellido());
           for(EventoDto event : allEvents){
               if(constancyDto.getEvento().equals(event.getId())){
                   constancyDto.setTituloEvento(event.getTitulo());
               }
           }

       }
       model.addAttribute("constancies", constancies);

       return "constancies/listConstancy";
    }


    @PostMapping("/update")
    public String updateConstancy(@ModelAttribute("constancy") ConstanciaDto constancyDto) {
        constancyDto.setFecha(LocalDateTime.now());
        constancyService.update(constancyDto.getId(), constancyDto);
        return "redirect:/constancy/myList";
    }

    // Método para eliminar un reclamo
    @PostMapping("/delete")
    public String deleteConstancy(@RequestParam("id") Long id) {
        constancyService.delete(id);
        return "redirect:/constancy/myList";
    }

    @GetMapping("/myList")
    public String listConstanciesStudent(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UsuarioDto userDto = userService.findUsuarioDtoByUsername(username);
        List<EventoDto> allEvents = eventService.findAll();
        List<EventoDto> filteredEvents = allEvents.stream()
                .filter(event -> attendanceService.hasAttendance(userDto.getId(), event.getId()))
                .collect(Collectors.toList());
        List<ConstanciaDto> constancies = constancyService.findConstancyByStudent_Id(userDto.getId());

        List<TipoConstanciaDto> constancyTypes = auxiliaresService.obtenerTiposConstancia();

        for (ConstanciaDto constancyDto : constancies) {
            for(EventoDto event : filteredEvents){
                if(constancyDto.getEvento().equals(event.getId())){
                    constancyDto.setTituloEvento(event.getTitulo());
                }
            }

        }

        model.addAttribute("constancies", constancies);
        model.addAttribute("constancyTypes", constancyTypes);
        model.addAttribute("events", filteredEvents);

        return "constancies/listConstancyStudent";
    }
}
