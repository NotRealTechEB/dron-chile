package cl.example.dronechile_orquestador.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import cl.example.dronechile_orquestador.client.RegistroVueloApiClient;
import cl.example.dronechile_orquestador.client.SolicitudApiClient;
import cl.example.dronechile_orquestador.client.TipoTrabajoApiClient;
import cl.example.dronechile_orquestador.dto.RegistroVueloDTO;
import cl.example.dronechile_orquestador.dto.ReporteOperacionDTO;
import cl.example.dronechile_orquestador.dto.SolicitudDTO;
import cl.example.dronechile_orquestador.dto.TipoTrabajoDTO;
import cl.example.dronechile_orquestador.mapper.ReporteMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReporteOperacionService {

    private final RegistroVueloApiClient vueloClient;
    private final SolicitudApiClient solicitudClient;
    private final TipoTrabajoApiClient tipoTrabajoClient;
    private final ReporteMapper reporteMapper;

    public List<ReporteOperacionDTO> generarReporteMensual(int mes, int anio) {
        RegistroVueloDTO[] registros = vueloClient.obtenerTodos();
        List<ReporteOperacionDTO> reporteFinal = new ArrayList<>();

        if (registros != null) {
            for (RegistroVueloDTO vuelo : registros) {
                
                if (vuelo.fechaPV().getMonthValue() == mes && vuelo.fechaPV().getYear() == anio) {
                    
                    String nombreTrabajo = "Desconocido";

                    try {
                        SolicitudDTO solicitud = solicitudClient.obtenerPorCodigo(vuelo.codigoVuelo());

                        if (solicitud != null) {
                            TipoTrabajoDTO tipo = tipoTrabajoClient.obtenerPorId(solicitud.tipo());
                            if (tipo != null) {
                                nombreTrabajo = tipo.nombreTrabajo();
                            }
                        }
                    } catch (WebClientResponseException.NotFound e) {
                        log.warn("No se encontró solicitud o tipo de trabajo para el código de vuelo: {}", vuelo.codigoVuelo());
                    } catch (Exception e) {
                        log.error("Error al cruzar datos para el vuelo {}: {}", vuelo.codigoVuelo(), e.getMessage());
                    }

                    ReporteOperacionDTO filaReporte = reporteMapper.ensamblarFactura(vuelo, nombreTrabajo);
                    
                    if (filaReporte != null) {
                        reporteFinal.add(filaReporte);
                    }
                }
            }
        }
        return reporteFinal;
    }
}