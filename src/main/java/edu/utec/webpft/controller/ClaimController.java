package edu.utec.webpft.controller;

import edu.utec.webpft.dtos.ReclamoDto;
import edu.utec.webpft.dtos.UsuarioDto;
import edu.utec.webpft.service.ClaimService;
import edu.utec.webpft.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/claim")
@RequiredArgsConstructor
public class ClaimController {

    private final ClaimService claimService;
    private final UserService userService;

    @PostMapping("/")
    public ReclamoDto save(@RequestBody ReclamoDto claimDto) {
        return claimService.save(claimDto);
    }

    @PutMapping("/{id}")
    public ReclamoDto update(@PathVariable Long id, @RequestBody ReclamoDto claimDto) {
        return claimService.update(id, claimDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        claimService.delete(id);
    }

    @GetMapping("/{id}")
    public ReclamoDto findById(@PathVariable Long id) {
        return claimService.findById(id);
    }

    @GetMapping("/")
    public List<ReclamoDto> findAll() {
        return claimService.findAll();
    }

    @GetMapping("/user/{id}")
    public List<ReclamoDto> findAllByUserId(@PathVariable Long id) {
        return claimService.findAllByUserId(id);
    }

    @GetMapping("/studentsWithClaims")
    public List<UsuarioDto> buscarEstudiantesConReclamos() {
        return userService.findStudentsWithClaims();
    }


}
