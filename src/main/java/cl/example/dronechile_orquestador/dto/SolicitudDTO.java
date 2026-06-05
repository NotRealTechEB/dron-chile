package cl.example.dronechile_orquestador.dto;

public record SolicitudDTO(
    int idSolicitud,
    String codigoVuelo,
    int tipo // Este es el ID del Tipo de Trabajo
) {}