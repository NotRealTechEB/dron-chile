package cl.example.dronechile_orquestador.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cl.example.dronechile_orquestador.dto.PilotoDTO;
import cl.example.dronechile_orquestador.dto.ReporteOperacionDTO;
import cl.example.dronechile_orquestador.service.AlertaPilotoService;
import cl.example.dronechile_orquestador.service.ReporteOperacionService;
import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping("/api/orquestador") 
@RequiredArgsConstructor 
public class OrquestadorController {


    private final AlertaPilotoService alertaPilotoService;
    private final ReporteOperacionService reporteOperacionService;

  
    // ENDPOINT 1: Alerta de Licencias (Vencimiento en 30 días o menos)

    @GetMapping("/alertas-pilotos")
    public ResponseEntity<List<PilotoDTO>> obtenerAlertasPilotos(@RequestParam String rutEmpresa) {
        
        // Pasamos el rutEmpresa al servicio
        List<PilotoDTO> alertas = alertaPilotoService.obtenerPilotosEnAlertaRoja(rutEmpresa);
        
        if (alertas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(alertas);
    }


    // ENDPOINT 2: Generación de Reporte Mensual de Operaciones

    @GetMapping("/reportes-mensuales")
    public ResponseEntity<List<ReporteOperacionDTO>> generarReporteMensual(
            @RequestParam int mes, 
            @RequestParam int anio) {
        
        // Delega la ejecución a la capa de servicio y retorna la lista 
        List<ReporteOperacionDTO> reporte = reporteOperacionService.generarReporteMensual(mes, anio);
        
        if (reporte.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reporte);
    }
}