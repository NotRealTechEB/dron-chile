package cl.example.dronechile_orquestador.dto;

import java.time.LocalDateTime;

public record ReporteOperacionDTO(
    String codigoVuelo,
    LocalDateTime fechaVuelo,
    int rutPiloto,
    String rutEmpresaMandante,
    String nombreTrabajo,
    int tiempoTotalMinutos
) {}