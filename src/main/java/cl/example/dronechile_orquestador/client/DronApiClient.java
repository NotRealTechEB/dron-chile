package cl.example.dronechile_orquestador.client;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import cl.example.dronechile_orquestador.dto.DronDTO;

@Component
public class DronApiClient {
    
    private final WebClient webClient;

    public DronApiClient(@Qualifier("dronWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public DronDTO[] obtenerTodos() {
        return webClient.get()
                .uri("/")
                .retrieve()
                .bodyToMono(DronDTO[].class)
                .timeout(Duration.ofSeconds(3))
                .block();
    }
}