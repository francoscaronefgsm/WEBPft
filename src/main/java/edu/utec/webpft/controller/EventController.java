package edu.utec.webpft.controller;

import edu.utec.webpft.dtos.*;
import edu.utec.webpft.entidades.Evento;
import edu.utec.webpft.service.AuxiliaresService;
import edu.utec.webpft.service.EventService;
import edu.utec.webpft.service.ItrService;
import edu.utec.webpft.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/event")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final UserService userService;
    private final ItrService itrService;
    private final AuxiliaresService auxiliaresService;

    // Método para mostrar la página de creación de eventos
    @GetMapping("/new")
    public String showNewEventForm(Model model) {
        Evento eventDto = new Evento();
        List<UsuarioDto> teachers = userService.getAllTutores();
        List<EstadoEventoDto> eventStatus = auxiliaresService.obtenerEstadosEvento();
        List<ModalidadEventoDto> eventModes = auxiliaresService.obtenerModalidadesEvento();
        List<TipoEventoDto> eventTypes = auxiliaresService.obtenerTiposEvento();
        List<ItrDto> itrs = itrService.findAll();
        model.addAttribute("teachers", teachers);
        model.addAttribute("eventStatus", eventStatus);
        model.addAttribute("eventModes", eventModes);
        model.addAttribute("eventTypes", eventTypes);
        model.addAttribute("itrs", itrs);
        model.addAttribute("event", eventDto);
        return "events/newEvent";
    }


    // Método para crear un nuevo evento
    @PostMapping("/create")
    public String createEvent(@ModelAttribute("event") EventoDto eventDto, Model model) {
        eventService.save(eventDto);
        return "redirect:/event/list";
    }

    @GetMapping("/list")
    public String listEvents(Model model) {
        List<EventoDto> events = eventService.findAll();
        List<UsuarioDto> teachers = userService.getAllTutores();
        List<EstadoEventoDto> eventStatus = auxiliaresService.obtenerEstadosEvento();
        List<ModalidadEventoDto> eventModes = auxiliaresService.obtenerModalidadesEvento();
        List<TipoEventoDto> eventTypes = auxiliaresService.obtenerTiposEvento();
        List<ItrDto> itrs = itrService.findAll();


        model.addAttribute("teachers", teachers);
        model.addAttribute("eventStatus", eventStatus);
        model.addAttribute("eventModes", eventModes);
        model.addAttribute("eventTypes", eventTypes);
        model.addAttribute("itrs", itrs);
        // Pasar la lista de reclamos a la vista
        model.addAttribute("events", events);

        return "events/eventsList";
    }


    // Método para actualizar un reclamo
    @PostMapping("/update")
    public String updateEvent(@ModelAttribute("event") EventoDto eventDto) {
        eventService.update(eventDto.getId(),eventDto);
        return "redirect:/event/list";
    }

    // Método para eliminar un reclamo
    @PostMapping("/delete")
    public String deleteEvent(@RequestParam("id") Long id) {
        eventService.delete(id);
        return "redirect:/event/list";
    }

    @GetMapping("/mylist")
    public String teacherListEvents(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UsuarioDto userDto = userService.findUsuarioDtoByUsername(username);
        List<EventoDto> events = eventService.findAllByUserId(userDto.getId());

        // Pasar la lista de reclamos a la vista
        model.addAttribute("events", events);

        return "events/teacherEventsList";
    }
}
