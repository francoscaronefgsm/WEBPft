package edu.utec.webpft.controller;

import java.util.ArrayList;
import java.util.List;

public class Constantes {

    final List<String> eventTypes = new ArrayList<>();
    final List<String> modes = new ArrayList<>();
    final List<String> eventStatus = new ArrayList<>();

    final List<String> attendanceStatus = new ArrayList<>();

    final List<String> sexo = new ArrayList<>();
    final List<String> constancyTypes = new ArrayList<>();
    final List<String> constancyStatus = new ArrayList<>();

    // Constructor que inicializa las listas solo una vez
    public Constantes() {
//        eventTypes.add("Jornada presencial");
//        eventTypes.add("Prueba final");
//        eventTypes.add("Examen");
//        eventTypes.add("Defensa de proyecto");
//        eventTypes.add("Convocatoria VME");
//        eventTypes.add("Convocatoria Optativa");
//        eventTypes.add("Otro");
//
//        modes.add("Virtual");
//        modes.add("Presencial");
//        modes.add("Semipresencial");
//
//        eventStatus.add("Corriente");
//        eventStatus.add("Finalizado");
//        eventStatus.add("Futuro");

        sexo.add("Masculino");
        sexo.add("Femenino");
        sexo.add("Otro");

//        constancyTypes.add("Constancia de Transporte");
//        constancyTypes.add("Constancias de Examen");
//        constancyTypes.add("Constancia de prueba parcial");
//        constancyTypes.add("Constancia de jornada externa");
//
//        constancyStatus.add("Ingresado");
//        constancyStatus.add("En proceso");
//        constancyStatus.add("Finalizado");
//
        attendanceStatus.add("Asistencia");
        attendanceStatus.add("Media Asistencia Matutina");
        attendanceStatus.add("Media Asistencia Vespertina");
        attendanceStatus.add("Ausencia");
        attendanceStatus.add("Ausencia Justificada");

        // Agrega los valores correspondientes para constancyTypes y constancyStatus si los necesitas
    }


    public List<String> getSexo() {
        return sexo;
    }

    public List<String> getAttendanceStatus() {
        return attendanceStatus;
    }
}

