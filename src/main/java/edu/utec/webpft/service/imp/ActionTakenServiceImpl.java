package edu.utec.webpft.service.imp;

import edu.utec.webpft.dtos.AccionRealizadaDto;
import edu.utec.webpft.entidades.AccionRealizada;
import edu.utec.webpft.repository.ActionTakenRepository;
import edu.utec.webpft.repository.ClaimRepository;
import edu.utec.webpft.repository.UserRepository;
import edu.utec.webpft.service.ActionTakenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActionTakenServiceImpl implements ActionTakenService {

    private final ActionTakenRepository actionTakenRepository;
    private final ClaimRepository claimRepository;
    private final UserRepository userRepository;


    private AccionRealizada mapToEntity(AccionRealizadaDto actionTakenDto) {
        AccionRealizada actionTaken = new AccionRealizada();
        actionTaken.setId(actionTakenDto.getId());
        actionTaken.setAdmin(userRepository.findById(actionTakenDto.getAdministrador())
                .orElseThrow(() -> new RuntimeException("User not found"))); // Asegúrate de que el Dto contenga el objeto Student
        actionTaken.setReclamo(claimRepository.findReclamoById(actionTakenDto.getReclamo()));
        actionTaken.setEstado(actionTakenDto.getEstado());
        actionTaken.setAccionRealizada(actionTakenDto.getAccionTomada());
        return actionTaken;
    }

    private AccionRealizadaDto mapToDto(AccionRealizada actionTaken) {
        AccionRealizadaDto actionTakenDto = new AccionRealizadaDto();
        actionTakenDto.setId(actionTaken.getId());
        actionTakenDto.setAdministrador(actionTaken.getAdmin().getId());
        actionTakenDto.setReclamo(actionTaken.getReclamo().getId());
        actionTakenDto.setEstado(actionTaken.getEstado());
        actionTakenDto.setAccionTomada(actionTaken.getAccionRealizada());
        return actionTakenDto;
    }


    @Override
    public void save(AccionRealizadaDto actionTakenDto) {
        AccionRealizada actionTaken = mapToEntity(actionTakenDto);
        actionTakenRepository.save(actionTaken);
    }
}
