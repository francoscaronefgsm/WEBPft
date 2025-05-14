package edu.utec.webpft.controller;

import edu.utec.webpft.dtos.DepartamentoDto;
import edu.utec.webpft.dtos.ItrDto;
import edu.utec.webpft.dtos.LocalidadDto;
import edu.utec.webpft.dtos.UsuarioDto;
import edu.utec.webpft.service.DepartmentService;
import edu.utec.webpft.service.ItrService;
import edu.utec.webpft.service.LocalityService;
import edu.utec.webpft.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    private final DepartmentService departmentService;

    private final LocalityService localityService;

    private final ItrService itrService;

    private final Constantes constantes = new Constantes();

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        HttpServletRequest request,
                        Model model) {
        if (error != null) {
            Exception exception = (Exception) request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
            model.addAttribute("errorMessage", exception != null ? exception.getMessage() : "Unknown error");
        }
        return "login";
    }

    @GetMapping("/register")
    public String getRegisterForm(Model model){


        UsuarioDto user = new UsuarioDto();
        List<DepartamentoDto> departments = departmentService.findAll();
        List<LocalidadDto> localities = localityService.findAll();
        List<ItrDto> itrs = itrService.findAll();
        List<String> genders = constantes.getSexo();

        model.addAttribute("user", user);
        model.addAttribute("departments", departments);
        model.addAttribute("localities", localities);
        model.addAttribute("itrs", itrs);
        model.addAttribute("genders", genders);

        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") UsuarioDto user,
                           BindingResult result, Model model) {

        if(userService.existsByUsername(user.getNombreUsuario())) {
            result.rejectValue("username", "error.user", "Username already in use");
        }

        if(userService.existsByPersonalEmail(user.getCorreoPersonal())) {
            result.rejectValue("personalEmail", "error.user", "Personal email already in use");
        }

        if(userService.existsByInstitutionalEmail(user.getCorreoInstitucional())) {
            result.rejectValue("institutionalEmail", "error.user", "Institutional email already in use");
        }

        if(userService.existsByDocument(user.getDocumento())) {
            result.rejectValue("document", "error.user", "Document already in use");
        }

        if(result.hasErrors()) {
            model.addAttribute("user", user);
            model.addAttribute("departments", departmentService.findAll());
            model.addAttribute("localities", localityService.findAll());
            model.addAttribute("itrs", itrService.findAll());
            return "register";
        } else {
            userService.register(user);
            return "redirect:/login?success";
        }
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/localities/{departmentId}")
    @ResponseBody // Asegúrate de que este decorador esté presente
    public List<LocalidadDto> getLocalitiesByDepartment(@PathVariable Long departmentId) {
        return localityService.findLocalityByDepartment(departmentId); // Esto devolverá un JSON
    }
}
