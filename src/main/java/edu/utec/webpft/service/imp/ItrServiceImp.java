package edu.utec.webpft.service.imp;

import edu.utec.webpft.dtos.ItrDto;
import edu.utec.webpft.entidades.Itr;
import edu.utec.webpft.repository.ItrRepository;
import edu.utec.webpft.service.ItrService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItrServiceImp implements ItrService {

    private final ItrRepository itrRepository;

    private ItrDto mapToDto(Itr itr) {
        return ItrDto.builder()
                .id(itr.getId())
                .nombre(itr.getNombre())
                .build();
    }

    private Itr mapToEntity(ItrDto itrDto) {
        return Itr.builder()
                .id(itrDto.getId())
                .nombre(itrDto.getNombre())
                .build();
    }

    @Override
    public ItrDto save(ItrDto itrDto) {
        Itr itr = itrRepository.save(mapToEntity(itrDto));
        return mapToDto(itr);
    }

    @Override
    public ItrDto update(Long id, ItrDto itrDto) {
        Itr itr = itrRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Itr not found"));
        itr.setNombre(itrDto.getNombre());
        return mapToDto(itrRepository.save(itr));
    }

    @Override
    public void delete(Long id) {
        Itr itr = itrRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Itr not found"));
        itrRepository.delete(itr);
    }

    @Override
    public ItrDto findById(Long id) {
        Itr itr = itrRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Itr not found"));
        return mapToDto(itr);
    }

    @Override
    public List<ItrDto> findAll() {
        return itrRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }
}
