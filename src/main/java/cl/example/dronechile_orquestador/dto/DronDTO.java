package cl.example.dronechile_orquestador.dto;

import java.time.LocalDate;

public record DronDTO(
    int idDrone,
    String numeroRegistro,
    double peso,
    int idTipo,
    LocalDate fechaVencimientoSeguro
) {}
