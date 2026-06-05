package cl.example.dronechile_orquestador.client;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import cl.example.dronechile_orquestador.dto.RegistroVueloDTO;

@Component
public class RegistroVueloApiClient {
    
    private final WebClient webClient;

    public RegistroVueloApiClient(@Qualifier("vueloWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public RegistroVueloDTO[] obtenerTodos() {
        return webClient.get()
                .uri("/")
                .retrieve()
                .bodyToMono(RegistroVueloDTO[].class)
                .timeout(Duration.ofSeconds(3))
                .block();
    }
}