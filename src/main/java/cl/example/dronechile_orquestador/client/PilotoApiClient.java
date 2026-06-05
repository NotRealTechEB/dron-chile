package cl.example.dronechile_orquestador.client;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import cl.example.dronechile_orquestador.dto.PilotoDTO;

@Component
public class PilotoApiClient {
    
    private final WebClient webClient;

    public PilotoApiClient(@Qualifier("pilotoWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public PilotoDTO[] obtenerTodos() {
        return webClient.get()
                .uri("/")
                .retrieve()
                .bodyToMono(PilotoDTO[].class)
                .timeout(Duration.ofSeconds(3))
                .block();
    }

    public PilotoDTO[] obtenerPorEmpresa(String rutEmpresa) {
        return webClient.get()
                .uri("/empresa/" + rutEmpresa)
                .retrieve()
                .bodyToMono(PilotoDTO[].class)
                .timeout(Duration.ofSeconds(3))
                .block();
    }
}