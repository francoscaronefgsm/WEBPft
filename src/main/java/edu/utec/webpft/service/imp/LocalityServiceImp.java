package edu.utec.webpft.service.imp;

import edu.utec.webpft.dtos.DepartamentoDto;
import edu.utec.webpft.dtos.LocalidadDto;
import edu.utec.webpft.entidades.Departamento;
import edu.utec.webpft.entidades.Localidad;
import edu.utec.webpft.repository.LocalityRepository;
import edu.utec.webpft.service.DepartmentService;
import edu.utec.webpft.service.LocalityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocalityServiceImp implements LocalityService {

    private final LocalityRepository localityRepository;

    private LocalidadDto mapToDto(Localidad locality) {
        return LocalidadDto.builder()
                .id(locality.getId())
                .nombre(locality.getNombre())
                .departamento(locality.getDepartamento().getId())
                .build();
    }

    private Localidad mapToEntity(LocalidadDto localityDto) {
        return Localidad.builder()
                .id(localityDto.getId())
                .nombre(localityDto.getNombre())
                .build();
    }


    @Override
    public LocalidadDto save(LocalidadDto localityDto) {
        Localidad locality = localityRepository.save(mapToEntity(localityDto));
        return mapToDto(locality);
    }

    @Override
    public LocalidadDto update(Long id, LocalidadDto localityDto) {
        Localidad locality = localityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Localidad no encontrada"));
        locality.setNombre(localityDto.getNombre());
        return mapToDto(localityRepository.save(locality));
    }

    @Override
    public void delete(Long id) {
        Localidad locality = localityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Localidad no encontrada"));
        localityRepository.delete(locality);
    }

    @Override
    public LocalidadDto findById(Long id) {
        Localidad locality = localityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Localidad no encontrada"));
        return mapToDto(locality);
    }

    @Override
    public List<LocalidadDto> findAll() {
        return localityRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public List<LocalidadDto> findLocalityByDepartment(Long departmentId) {
        return localityRepository.findByDepartamentoId(departmentId)
                .stream()
                .map(this::mapToDto)
                .toList();
    }
}
