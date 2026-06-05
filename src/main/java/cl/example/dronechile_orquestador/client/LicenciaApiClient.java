package cl.example.dronechile_orquestador.client;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import cl.example.dronechile_orquestador.dto.LicenciaDTO;

@Component
public class LicenciaApiClient {
    
    private final WebClient webClient;

    public LicenciaApiClient(@Qualifier("licenciaWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public LicenciaDTO obtenerPorRut(String rutPiloto) {
        return webClient.get()
                .uri("/rut/" + rutPiloto)
                .retrieve()
                .bodyToMono(LicenciaDTO.class)
                .timeout(Duration.ofSeconds(3))
                .block();
    }
}