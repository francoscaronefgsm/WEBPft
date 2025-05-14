package edu.utec.webpft.service.imp;

import edu.utec.webpft.dtos.ReclamoDto;
import edu.utec.webpft.entidades.Reclamo;
import edu.utec.webpft.repository.*;
import edu.utec.webpft.service.ClaimService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClaimServiceImp implements ClaimService {

    private final ClaimRepository claimRepository;
    private final StudentRepository studentRepository;
    private final EstadoReclamoRepository estadoReclamoRepository;
    private final TipoReclamoRepository tipoReclamoRepository;

    private ReclamoDto mapToDto(Reclamo claim) {
        ReclamoDto claimDto = new ReclamoDto();
        claimDto.setId(claim.getId());
        claimDto.setTitulo(claim.getTitulo());
        claimDto.setDescripcion(claim.getDescripcion());
        claimDto.setCreado(claim.getCreado());
        claimDto.setActualizado(claim.getActualizado());
        claimDto.setTipoReclamoDto(claim.getTipoReclamo().getId());
        claimDto.setSemestre(claim.getSemestre());
        claimDto.setFecha(claim.getFecha());
        claimDto.setDocente(claim.getDocente());
        claimDto.setCreditos(claim.getCreditos());
        claimDto.setEstadoReclamoDto(claim.getEstadoReclamo().getId());

        claimDto.setUsuario(claim.getEstudiante().getId());
        return claimDto;
    }

    private Reclamo mapToEntity(ReclamoDto claimDto) {
        Reclamo claim = new Reclamo();
        claim.setId(claimDto.getId());
        claim.setTitulo(claimDto.getTitulo());
        claim.setDescripcion(claimDto.getDescripcion());
        claim.setCreado(claimDto.getCreado());
        claim.setActualizado(claimDto.getActualizado());
        claim.setTipoReclamo(tipoReclamoRepository.getById(claimDto.getTipoReclamoDto()));

        claim.setSemestre(claimDto.getSemestre());
        claim.setFecha(claimDto.getFecha());
        claim.setDocente(claimDto.getDocente());
        claim.setCreditos(claimDto.getCreditos());
        claim.setEstadoReclamo(estadoReclamoRepository.getById(claimDto.getEstadoReclamoDto()));

        claim.setEstudiante(studentRepository.findById(claimDto.getUsuario())
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado")));
        return claim;
    }

    @Override
    public ReclamoDto save(ReclamoDto claimDto) {
        claimDto.setEstadoReclamoDto(1l);
        claimDto.setCreado(LocalDateTime.now());
        Reclamo claim = claimRepository.save(mapToEntity(claimDto));
        return mapToDto(claim);
    }

    @Override
    public ReclamoDto update(Long id, ReclamoDto claimDto) {
        Reclamo claim = claimRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reclamo not found"));
        claim.setTitulo(claimDto.getTitulo());
        claim.setDescripcion(claimDto.getDescripcion());
        claim.setActualizado(LocalDateTime.now());
        claim.setTipoReclamo(tipoReclamoRepository.getById(claimDto.getTipoReclamoDto()));
        claim.setSemestre(claimDto.getSemestre());
        claim.setFecha(claimDto.getFecha());
        claim.setDocente(claimDto.getDocente());
        claim.setCreditos(claimDto.getCreditos());
        claim.setEstadoReclamo(estadoReclamoRepository.getById(claimDto.getEstadoReclamoDto()));
        return mapToDto(claimRepository.save(claim));
    }

    @Override
    public void delete(Long id) {
        Reclamo claim = claimRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reclamo not found"));
        claimRepository.delete(claim);
    }

    @Override
    public ReclamoDto findById(Long id) {
        Reclamo claim = claimRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reclamo not found"));
        return mapToDto(claim);
    }

    @Override
    public List<ReclamoDto> findAll() {
        return claimRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public List<ReclamoDto> findAllByUserId(Long id) {
        return claimRepository.findAllByEstudianteId(id)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public void changeStatus(Long id, Long status) {
        Reclamo claim = claimRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reclamo not found"));
        claim.setEstadoReclamo(estadoReclamoRepository.getById(status));
        claim.setActualizado(LocalDateTime.now());
        mapToDto(claimRepository.save(claim));
    }
}
