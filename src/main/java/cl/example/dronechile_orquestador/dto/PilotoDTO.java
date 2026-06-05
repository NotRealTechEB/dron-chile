package cl.example.dronechile_orquestador.dto;

public record PilotoDTO(
    int idPiloto,
    String rutPiloto,
    String nombreEmpresa,
    String pNombrePiloto,
    String sNombrePiloto,
    String apPaternoPiloto,
    String apMaternoPiloto
) {}