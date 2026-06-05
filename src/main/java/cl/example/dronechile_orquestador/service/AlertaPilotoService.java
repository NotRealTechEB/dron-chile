package cl.example.dronechile_orquestador.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import cl.example.dronechile_orquestador.client.LicenciaApiClient;
import cl.example.dronechile_orquestador.client.PilotoApiClient;
import cl.example.dronechile_orquestador.dto.LicenciaDTO;
import cl.example.dronechile_orquestador.dto.PilotoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlertaPilotoService {

    private final PilotoApiClient pilotoClient;
    private final LicenciaApiClient licenciaClient;

    // -------------------------------------------------------------------
    // MÉTODO: Alerta de Licencias (Filtrado por Empresa)
    // -------------------------------------------------------------------
    public List<PilotoDTO> obtenerPilotosEnAlertaRoja(String rutEmpresa) {

        PilotoDTO[] pilotosDeLaEmpresa = pilotoClient.obtenerPorEmpresa(rutEmpresa);
        
        List<PilotoDTO> pilotosEnAlerta = new ArrayList<>();
        LocalDate hoy = LocalDate.now();

        if (pilotosDeLaEmpresa != null) {
            for (PilotoDTO piloto : pilotosDeLaEmpresa) {
                
                
                try {
                    LicenciaDTO licencia = licenciaClient.obtenerPorRut(piloto.rutPiloto());
                    
                    if (licencia != null && licencia.fechaVen() != null) {
                        long diasRestantes = ChronoUnit.DAYS.between(hoy, licencia.fechaVen());
                        if (diasRestantes <= 30) {
                            pilotosEnAlerta.add(piloto);
                        }
                    }
                } catch (WebClientResponseException.NotFound e) {
                    
                    log.warn("El piloto con RUT {} no tiene licencia registrada aún.", piloto.rutPiloto());
                } catch (Exception e) {
                    
                    log.error("Error crítico al consultar licencia del RUT {}: {}", piloto.rutPiloto(), e.getMessage());
                }
            }
        }
        return pilotosEnAlerta;
    }
}