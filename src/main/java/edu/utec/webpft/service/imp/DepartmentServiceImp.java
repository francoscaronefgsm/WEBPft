package edu.utec.webpft.service.imp;

import edu.utec.webpft.dtos.DepartamentoDto;
import edu.utec.webpft.entidades.Departamento;
import edu.utec.webpft.repository.DepartmentRepository;
import edu.utec.webpft.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImp implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    private DepartamentoDto mapToDto(Departamento department) {
        return DepartamentoDto.builder()
                .id(department.getId())
                .nombre(department.getNombre())
                .build();
    }

    private Departamento mapToEntity(DepartamentoDto departmentDto) {
        return Departamento.builder()
                .id(departmentDto.getId())
                .nombre(departmentDto.getNombre())
                .build();
    }

    @Override
    public DepartamentoDto save(DepartamentoDto departmentDto) {
        Departamento department = departmentRepository.save(mapToEntity(departmentDto));
        return mapToDto(department);
    }

    @Override
    public DepartamentoDto update(Long id, DepartamentoDto departmentDto) {
        Departamento department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));
        department.setNombre(departmentDto.getNombre());
        return mapToDto(departmentRepository.save(department));
    }

    @Override
    public void delete(Long id) {
        Departamento department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));
        departmentRepository.delete(department);
    }

    @Override
    public DepartamentoDto findById(Long id) {
        Departamento department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));
        return mapToDto(department);
    }

    @Override
    public List<DepartamentoDto> findAll() {
        return departmentRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }
}
