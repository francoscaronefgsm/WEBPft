package edu.utec.webpft.service;

import edu.utec.webpft.dtos.*;

import java.util.List;

public interface AuxiliaresService {

    List<TipoEventoDto> obtenerTiposEvento();
    List<ModalidadEventoDto> obtenerModalidadesEvento();
    List<EstadoEventoDto> obtenerEstadosEvento();

    List<TipoConstanciaDto> obtenerTiposConstancia();
    List<EstadoConstanciaDto> obtenerEstadosConstancia();

    List<EstadoReclamoDto> obtenerEstadosReclamos();
    List<TipoReclamoDto> obtenerTiposReclamo();

    List<EstadoJustificacionDto> obtenerEstadosJustificacion();

}
