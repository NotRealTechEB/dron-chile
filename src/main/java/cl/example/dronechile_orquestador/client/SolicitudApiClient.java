package cl.example.dronechile_orquestador.client;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import cl.example.dronechile_orquestador.dto.SolicitudDTO;

@Component
public class SolicitudApiClient {
    
    private final WebClient webClient;

    public SolicitudApiClient(@Qualifier("solicitudWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public SolicitudDTO obtenerPorCodigo(String codigoVuelo) {
        return webClient.get()
                .uri("/codigo/" + codigoVuelo)
                .retrieve()
                .bodyToMono(SolicitudDTO.class)
                .timeout(Duration.ofSeconds(3))
                .block();
    }
}