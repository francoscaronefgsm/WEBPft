package edu.utec.webpft.service;

import edu.utec.webpft.dtos.AsistenciaDto;

import java.util.List;

public interface AttendanceService {

     void save(AsistenciaDto attendanceDto);
     void saveCall(AsistenciaDto attendanceDto);
     List<AsistenciaDto> findAll();
     AsistenciaDto findById(long id);
     boolean hasAttendance(Long studentId, Long eventId);
}
