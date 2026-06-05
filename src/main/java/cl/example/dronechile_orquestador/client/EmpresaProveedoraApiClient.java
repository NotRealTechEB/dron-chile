package cl.example.dronechile_orquestador.client;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import cl.example.dronechile_orquestador.dto.EmpresaProveedoraDTO;

@Component
public class EmpresaProveedoraApiClient {
    
    private final WebClient webClient;

    public EmpresaProveedoraApiClient(@Qualifier("empresaWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public EmpresaProveedoraDTO obtenerPorRut(String rut) {
        return webClient.get()
                .uri("/rut/" + rut)
                .retrieve()
                .bodyToMono(EmpresaProveedoraDTO.class)
                .timeout(Duration.ofSeconds(3))
                .block();
    }
}