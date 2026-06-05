package cl.example.dronechile_orquestador.service;


import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import cl.example.dronechileorquestador.dto.LicenciaDTO;
import cl.example.dronechileorquestador.dto.PilotoDTO;
import cl.example.dronechileorquestador.dto.RegistroVueloDTO;
import cl.example.dronechileorquestador.dto.ReporteOperacionDTO;
import cl.example.dronechileorquestador.dto.SolicitudDTO;
import cl.example.dronechileorquestador.dto.TipoTrabajoDTO;

@Service
public class OrquestadorService {

    private final WebClient webClientLicencias;
    private final WebClient webClientPilotos;
    private final WebClient webClientRegistrosVuelo;
    private final WebClient webClientSolicitudes;
    private final WebClient webClientTiposTrabajo;

    public OrquestadorService(WebClient.Builder webClientBuilder) {
        this.webClientLicencias = webClientBuilder.baseUrl("http://localhost:8086/api/licencias").build();
        this.webClientPilotos = webClientBuilder.baseUrl("http://localhost:8084/api/pilotos").build();
        
        // Agregamos las conexiones a los demás microservicios (Ajusta los puertos según tu entorno local)
        this.webClientRegistrosVuelo = webClientBuilder.baseUrl("http://localhost:8081/api/registros").build();
        this.webClientSolicitudes = webClientBuilder.baseUrl("http://localhost:8082/api/solicitudes").build();
        this.webClientTiposTrabajo = webClientBuilder.baseUrl("http://localhost:8083/api/tipos-trabajo").build();
    }

    // -------------------------------------------------------------------
    // MÉTODO 1: Alerta de Licencias (Ya implementado)
    // -------------------------------------------------------------------
    public List<PilotoDTO> obtenerPilotosEnAlertaRoja() {
        PilotoDTO[] todosLosPilotos = this.webClientPilotos.get().uri("/").retrieve().bodyToMono(PilotoDTO[].class).block();
        List<PilotoDTO> pilotosEnAlerta = new ArrayList<>();
        LocalDate hoy = LocalDate.now();

        if (todosLosPilotos != null) {
            for (PilotoDTO piloto : todosLosPilotos) {
                try {
                    LicenciaDTO licencia = this.webClientLicencias.get().uri("/rut/" + piloto.rutPiloto()).retrieve().bodyToMono(LicenciaDTO.class).block();
                    if (licencia != null && licencia.fechaVen() != null) {
                        long diasRestantes = ChronoUnit.DAYS.between(hoy, licencia.fechaVen());
                        if (diasRestantes <= 30) {
                            pilotosEnAlerta.add(piloto);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("No se encontró licencia para el RUT: " + piloto.rutPiloto());
                }
            }
        }
        return pilotosEnAlerta;
    }

    // -------------------------------------------------------------------
    // MÉTODO 2: Generación de Reporte Mensual de Operaciones
    // -------------------------------------------------------------------
    public List<ReporteOperacionDTO> generarReporteMensual(int mes, int anio) {
        
        // 1. Obtener todos los registros de vuelo
        RegistroVueloDTO[] registros = this.webClientRegistrosVuelo.get().uri("/").retrieve().bodyToMono(RegistroVueloDTO[].class).block();
        List<ReporteOperacionDTO> reporteFinal = new ArrayList<>();

        if (registros != null) {
            for (RegistroVueloDTO vuelo : registros) {
                
                // 2. Filtrar solo los vuelos que correspondan al mes y año solicitados
                if (vuelo.fechaPV().getMonthValue() == mes && vuelo.fechaPV().getYear() == anio) {
                    
                    String nombreTrabajo = "Desconocido";

                    try {
                        // 3. Buscar la solicitud asociada para saber el Tipo de Trabajo (usando el código de vuelo o ID)
                        // Aquí asumimos que tienes un endpoint para buscar la solicitud por el código del vuelo
                        SolicitudDTO solicitud = this.webClientSolicitudes.get().uri("/codigo/" + vuelo.codigoVuelo()).retrieve().bodyToMono(SolicitudDTO.class).block();

                        if (solicitud != null) {
                            // 4. Con el ID del tipo de trabajo de la solicitud, buscamos su nombre real
                            TipoTrabajoDTO tipo = this.webClientTiposTrabajo.get().uri("/" + solicitud.tipo()).retrieve().bodyToMono(TipoTrabajoDTO.class).block();
                            if (tipo != null) {
                                nombreTrabajo = tipo.nombreTrabajo();
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("No se pudo cruzar la información para el vuelo: " + vuelo.codigoVuelo());
                    }

                    // 5. Ensamblar la línea del reporte consolidado
                    reporteFinal.add(new ReporteOperacionDTO(
                        vuelo.codigoVuelo(),
                        vuelo.fechaPV(),
                        vuelo.rutPiloto(),
                        vuelo.rutEmpMandante(),
                        nombreTrabajo,
                        vuelo.tiempoTotal()
                    ));
                }
            }
        }
        return reporteFinal;
    }
}