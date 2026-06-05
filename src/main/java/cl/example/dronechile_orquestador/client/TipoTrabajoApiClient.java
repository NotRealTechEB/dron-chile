package cl.example.dronechile_orquestador.client;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import cl.example.dronechile_orquestador.dto.TipoTrabajoDTO;

@Component
public class TipoTrabajoApiClient {
    
    private final WebClient webClient;

    public TipoTrabajoApiClient(@Qualifier("tipoTrabajoWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public TipoTrabajoDTO obtenerPorId(int idTipoTrabajo) {
        return webClient.get()
                .uri("/" + idTipoTrabajo)
                .retrieve()
                .bodyToMono(TipoTrabajoDTO.class)
                .timeout(Duration.ofSeconds(3))
                .block();
    }
}