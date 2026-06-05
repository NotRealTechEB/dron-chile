package cl.example.dronechile_orquestador.dto;

import java.time.LocalDateTime;

public record RegistroVueloDTO(
    String codigoVuelo,
    LocalDateTime fechaPV,
    int rutPiloto,
    String rutEmpMandante,
    int tiempoTotal
) {}
