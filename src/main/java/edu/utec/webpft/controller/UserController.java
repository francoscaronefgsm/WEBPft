package edu.utec.webpft.controller;

import edu.utec.webpft.dtos.ReclamoDto;
import edu.utec.webpft.dtos.UsuarioDto;
import edu.utec.webpft.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final ClaimService claimService;

    private final DepartmentService departmentService;

    private final LocalityService localityService;

    private final ItrService itrService;

    @PostMapping("/dropout")
    public String dropout(@RequestParam Long id) {
        userService.changeUserStatus(id);
        return "redirect:/login?logout";
    }

    @GetMapping("/update")
    public String update(Model model) {

        UsuarioDto userDto = userService.findUsuarioDtoByUsername
                (SecurityContextHolder.getContext().getAuthentication().getName());

        model.addAttribute("user", userDto);
        model.addAttribute("departments", departmentService.findAll());
        model.addAttribute("localities", localityService.findAll());
        model.addAttribute("itrs", itrService.findAll());
        return "user-update";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("user") UsuarioDto userDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            model.addAttribute("departments", departmentService.findAll());
            model.addAttribute("localities", localityService.findAll());
            model.addAttribute("itrs", itrService.findAll());
            return "user-update";
        } else {
            userService.updateUser(userDto);
            return "redirect:/user/update?success";
        }
    }

    // Método para mostrar la página de creación de reclamos
    @GetMapping("/claim/new")
    public String showNewClaimForm(Model model) {
        ReclamoDto claimDto = new ReclamoDto();
        model.addAttribute("claim", claimDto);
        return "claims/newClaim";  // Especificar la ruta correcta de la plantilla
    }


    // Método para crear un nuevo reclamo
    @PostMapping("/claim/create")
    public String createClaim(@ModelAttribute("claim") ReclamoDto claimDto, Model model) {
        // Obtener el UsuarioDto correspondiente al usuario autenticado
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UsuarioDto userDto = userService.findUsuarioDtoByUsername(username);
        claimDto.setUsuario(userDto.getId());
        claimDto.setCreado(LocalDateTime.now());
        claimDto.setActualizado(LocalDateTime.now());
        claimService.save(claimDto);
        return "redirect:/user/claim/list";
    }

    @GetMapping("/claim/list")
    public String listStudentClaims(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UsuarioDto userDto = userService.findUsuarioDtoByUsername(username);
        List<ReclamoDto> claims = claimService.findAllByUserId(userDto.getId());

        // Pasar la lista de reclamos a la vista
        model.addAttribute("claims", claims);

        return "claims/listClaimStudent"; // La vista donde se mostrarán los reclamos
    }


    // Método para actualizar un reclamo
    @PostMapping("/claim/update")
    public String updateClaim(@ModelAttribute("claim") ReclamoDto claimDto) {
        claimDto.setActualizado(LocalDateTime.now());
        claimDto.setEstadoReclamoDto(0l);
        claimService.update(claimDto.getId(),claimDto);
        return "redirect:/user/claim/list";
    }

    // Método para eliminar un reclamo
    @PostMapping("/claim/delete")
    public String deleteClaim(@RequestParam("id") Long id) {
        claimService.delete(id);
        return "redirect:/user/claim/list";
    }

}
