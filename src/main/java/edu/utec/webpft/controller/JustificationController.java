package edu.utec.webpft.controller;

import edu.utec.webpft.dtos.EstadoJustificacionDto;
import edu.utec.webpft.dtos.EventoDto;
import edu.utec.webpft.dtos.JustificacionDto;
import edu.utec.webpft.dtos.UsuarioDto;
import edu.utec.webpft.service.AuxiliaresService;
import edu.utec.webpft.service.EventService;
import edu.utec.webpft.service.JustificationService;
import edu.utec.webpft.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes; // Import RedirectAttributes

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects; // Import Objects for null-safe equals


@Controller
@RequestMapping("/justification")
@RequiredArgsConstructor
public class JustificationController {

    private final JustificationService justificationService;
    private final UserService userService;
    private final EventService eventService;
    private final AuxiliaresService auxiliaresService;

    // Método para mostrar la página de creación de justificaciones
    @GetMapping("/new")
    public String showNewJustificationForm(Model model) {
        JustificacionDto justificationDto = new JustificacionDto();
        List<EventoDto> events = eventService.findAll(); // Obtener todos los eventos
        // No se necesitan estados de justificación en la creación inicial por parte del estudiante

        model.addAttribute("justification", justificationDto);
        model.addAttribute("events", events);
        return "justifications/newJustification";
    }

    // Método para crear una nueva justificación
    @PostMapping("/create")
    public String createJustification(@ModelAttribute("justification") JustificacionDto justificationDto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UsuarioDto userDto = userService.findUsuarioDtoByUsername(username);
        justificationDto.setEstudiante(userDto.getId());
        justificationDto.setEstado(1L); // Asumiendo que 1L es el ID para "Pendiente" o estado inicial
        justificationDto.setFecha(LocalDateTime.now());
        justificationService.save(justificationDto);
        return "redirect:/justification/list/student"; // Redirige a la lista del estudiante
    }

    // Método para listar justificaciones (para administrador)
    @GetMapping("/list/admin")
    public String listJustificationsAdmin(Model model) {
        List<JustificacionDto> justifications = justificationService.findAll();
        // Para el admin, no necesitamos pasar 'students', 'events', 'justificationStatus' para acciones de edición/eliminación.
        // Solo para mostrar la información completa si es necesario en la vista.
        model.addAttribute("justifications", justifications);
        return "justifications/listJustificationsAdmin";
    }

    // Método para actualizar una justificación (solo si el usuario logueado es el estudiante que la creó)
    @PostMapping("/update")
    public String updateJustification(@ModelAttribute("justification") JustificacionDto justificationDto, RedirectAttributes redirectAttributes) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UsuarioDto currentUser = userService.findUsuarioDtoByUsername(username);

        JustificacionDto existingJustification = justificationService.findById(justificationDto.getId());

        if (existingJustification != null && Objects.equals(existingJustification.getEstudiante(), currentUser.getId())) {
            // Permite actualizar solo 'evento', 'fecha' e 'informacion' por parte del estudiante.
            // El estado no debería ser modificable por el estudiante en esta vista.
            existingJustification.setEvento(justificationDto.getEvento());
            existingJustification.setFecha(LocalDateTime.now());
            existingJustification.setInformacion(justificationDto.getInformacion());
            justificationService.update(existingJustification.getId(), existingJustification);
            redirectAttributes.addFlashAttribute("successMessage", "Justificación actualizada exitosamente.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "No tienes permiso para actualizar esta justificación.");
        }
        return "redirect:/justification/list/student";
    }

    // Método para eliminar una justificación (solo si el usuario logueado es el estudiante que la creó)
    @PostMapping("/delete")
    public String deleteJustification(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UsuarioDto currentUser = userService.findUsuarioDtoByUsername(username);

        JustificacionDto existingJustification = justificationService.findById(id);

        if (existingJustification != null && Objects.equals(existingJustification.getEstudiante(), currentUser.getId())) {
            justificationService.delete(id);
            redirectAttributes.addFlashAttribute("successMessage", "Justificación eliminada exitosamente.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "No tienes permiso para eliminar esta justificación.");
        }
        return "redirect:/justification/list/student";
    }

    // Método para listar las justificaciones de un estudiante (asumiendo que el usuario logueado es un estudiante)
    @GetMapping("/list/student")
    public String listJustificationsStudent(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UsuarioDto userDto = userService.findUsuarioDtoByUsername(username);
        List<JustificacionDto> justifications = justificationService.findAllByStudentId(userDto.getId());
        List<EventoDto> events = eventService.findAll(); // Necesario para el formulario de edición
        List<EstadoJustificacionDto> justificationStatus = auxiliaresService.obtenerEstadosJustificacion(); // Necesario para el formulario de edición

        model.addAttribute("justifications", justifications);
        model.addAttribute("events", events); // Add events to the model for student's edit modal
        model.addAttribute("justificationStatus", justificationStatus); // Add justificationStatus to the model for student's edit modal
        return "justifications/listJustificationStudent";
    }
}