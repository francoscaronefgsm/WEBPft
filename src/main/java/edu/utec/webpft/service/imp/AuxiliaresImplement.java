package edu.utec.webpft.service.imp;

import edu.utec.webpft.dtos.*;
import edu.utec.webpft.entidades.*;
import edu.utec.webpft.repository.*;
import edu.utec.webpft.service.AuxiliaresService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuxiliaresImplement implements AuxiliaresService {

    private final TipoEventoRepository tipoEventoRepository;
    private final ModalidadEventoRepository modalidadEventoRepository;
    private final EstadoEventoRepository estadoEventoRepository;
    private final TipoConstanciaRepository tipoConstanciaRepository;
    private final EstadoConstanciaRepository estadoConstanciaRepository;
    private final EstadoReclamoRepository estadoReclamoRepository;
    private final TipoReclamoRepository tipoReclamoRepository;
    private final EstadoJustificacionRepository estadoJustificacionRepository;

    @Override
    public List<TipoEventoDto> obtenerTiposEvento() {
        return tipoEventosToDto(tipoEventoRepository.findAll());
    }

    @Override
    public List<ModalidadEventoDto> obtenerModalidadesEvento() {
        return modalidadEventosToDto(modalidadEventoRepository.findAll());
    }

    @Override
    public List<EstadoEventoDto> obtenerEstadosEvento() {
        return estadoEventosToDto(estadoEventoRepository.findAll());
    }

    @Override
    public List<TipoConstanciaDto> obtenerTiposConstancia() {
        return tipoConstanciasToDto(tipoConstanciaRepository.findAll());
    }

    @Override
    public List<EstadoConstanciaDto> obtenerEstadosConstancia() {
        return estadoConstanciasToDto(estadoConstanciaRepository.findAll());
    }

    @Override
    public List<EstadoReclamoDto> obtenerEstadosReclamos() {
        return estadoReclamosToDto(estadoReclamoRepository.findAll());
    }

    @Override
    public List<TipoReclamoDto> obtenerTiposReclamo() {
        return tipoReclamosToDto(tipoReclamoRepository.findAll());
    }

    @Override
    public List<EstadoJustificacionDto> obtenerEstadosJustificacion() {
        return estadoJustificacionToDto(estadoJustificacionRepository.findAll());
    }


    private List<TipoEventoDto> tipoEventosToDto(List<TipoEvento> tipoEventos) {
        return tipoEventos.stream().map(tipoEvento -> {
            TipoEventoDto dto = new TipoEventoDto();
            dto.setId(tipoEvento.getId());
            dto.setDescripcion(tipoEvento.getDescripcion());
            return dto;
        }).collect(Collectors.toList());
    }

    private List<TipoConstanciaDto> tipoConstanciasToDto(List<TipoConstancia> tipoConstancias) {
        return tipoConstancias.stream().map(tipoConstancia -> {
            TipoConstanciaDto dto = new TipoConstanciaDto();
            dto.setId(tipoConstancia.getId());
            dto.setDescripcion(tipoConstancia.getDescripcion());
            return dto;
        }).collect(Collectors.toList());
    }

    private List<TipoReclamoDto> tipoReclamosToDto(List<TipoReclamo> tipoReclamos) {
        return tipoReclamos.stream().map(tipoReclamo -> {
            TipoReclamoDto dto = new TipoReclamoDto();
            dto.setId(tipoReclamo.getId());
            dto.setDescripcion(tipoReclamo.getDescripcion());
            return dto;
        }).collect(Collectors.toList());
    }

    private List<EstadoEventoDto> estadoEventosToDto(List<EstadoEvento> estadoEventos) {
        return estadoEventos.stream().map(estadoEvento -> {
            EstadoEventoDto dto = new EstadoEventoDto();
            dto.setId(estadoEvento.getId());
            dto.setDescripcion(estadoEvento.getDescripcion());
            return dto;
        }).collect(Collectors.toList());
    }

    private List<EstadoReclamoDto> estadoReclamosToDto(List<EstadoReclamo> estadoReclamos) {
        return estadoReclamos.stream().map(estadoReclamo -> {
            EstadoReclamoDto dto = new EstadoReclamoDto();
            dto.setId(estadoReclamo.getId());
            dto.setDescripcion(estadoReclamo.getDescripcion());
            return dto;
        }).collect(Collectors.toList());
    }

    private List<EstadoJustificacionDto> estadoJustificacionToDto(List<EstadoJustificacion> estadosJustificacion) {
        return estadosJustificacion.stream().map(estadoJustificacion -> {
            EstadoJustificacionDto dto = new EstadoJustificacionDto();
            dto.setId(estadoJustificacion.getId());
            dto.setDescripcion(estadoJustificacion.getDescripcion());
            return dto;
        }).collect(Collectors.toList());
    }

        private List<EstadoConstanciaDto> estadoConstanciasToDto(List<EstadoConstancia> estadoConstancias) {
        return estadoConstancias.stream().map(estadoConstancia -> {
            EstadoConstanciaDto dto = new EstadoConstanciaDto();
            dto.setId(estadoConstancia.getId());
            dto.setDescripcion(estadoConstancia.getDescripcion());
            return dto;
        }).collect(Collectors.toList());
    }

    private List<ModalidadEventoDto> modalidadEventosToDto(List<ModalidadEvento> modalidadEventos) {
        return modalidadEventos.stream().map(modalidadEvento -> {
            ModalidadEventoDto dto = new ModalidadEventoDto();
            dto.setId(modalidadEvento.getId());
            dto.setDescripcion(modalidadEvento.getDescripcion());
            return dto;
        }).collect(Collectors.toList());
    }


}
