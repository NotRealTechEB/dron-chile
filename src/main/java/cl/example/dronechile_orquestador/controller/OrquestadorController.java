package cl.example.dronechile_orquestador.controller;




import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.example.dronechile_orquestador.dto.PilotoDTO;
import cl.example.dronechile_orquestador.service.OrquestadorService;


@RestController // Indica que esta clase expone endpoints REST y serializa las respuestas directamente a JSON
@RequestMapping("/api/orquestador") // Define la URI base para todas las peticiones HTTP de este controlador
public class OrquestadorController {

    // Dependencia hacia la capa de servicio, donde reside la lógica de negocio y orquestación
    private final OrquestadorService orquestadorService;

    // Inyección de dependencias mediante constructor (Inversion of Control)
    public OrquestadorController(OrquestadorService orquestadorService) {
        this.orquestadorService = orquestadorService;
    }

    // -------------------------------------------------------------------
    // ENDPOINT 1: Alerta de Licencias (Vencimiento en 30 días o menos)
    // -------------------------------------------------------------------
    @GetMapping("/alertas-pilotos")
    public List<PilotoDTO> obtenerAlertasPilotos() {
        // Delega la ejecución a la capa de servicio y retorna la lista resultante
        return orquestadorService.obtenerPilotosEnAlertaRoja();
    }

    
}