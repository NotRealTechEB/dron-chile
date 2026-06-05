package cl.example.dronechile_orquestador.dto;

import java.time.LocalDate;

public record LicenciaDTO(
    int idLicencia,
    int rutPiloto,
    LocalDate fechaVen
) {}