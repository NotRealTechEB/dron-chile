package cl.example.dronechile_orquestador.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    // Cada @Bean es como un "enchufe" listo para ser usado por el sistema

    @Bean(name = "pilotoWebClient")
    public WebClient pilotoWebClient(@Value("${api.pilotos.url:https://piloto-dfcf.onrender.com}") String url) {
        return WebClient.builder().baseUrl(url).build();
    }

    @Bean(name = "licenciaWebClient")
    public WebClient licenciaWebClient(@Value("${api.licencias.url:https://licencia-bvee.onrender.com}") String url) {
        return WebClient.builder().baseUrl(url).build();
    }

    @Bean(name = "vueloWebClient")
    public WebClient vueloWebClient(@Value("${api.registros.url:https://registrovuelo.onrender.com}") String url) {
        return WebClient.builder().baseUrl(url).build();
    }

    @Bean(name = "solicitudWebClient")
    public WebClient solicitudWebClient(@Value("${api.solicitudes.url:https://solicitudes-e4e1.onrender.com}") String url) {
        return WebClient.builder().baseUrl(url).build();
    }

    @Bean(name = "tipoTrabajoWebClient")
    public WebClient tipoTrabajoWebClient(@Value("${api.tipostrabajo.url:https://tipotrabajo.onrender.com}") String url) {
        return WebClient.builder().baseUrl(url).build();
    }

    @Bean(name = "dronWebClient")
    public WebClient dronWebClient(@Value("${api.drones.url:https://drones1-1.onrender.com}") String url) {
        return WebClient.builder().baseUrl(url).build();
    }

    @Bean(name = "empresaWebClient")
    public WebClient empresaWebClient(@Value("${api.empresas.url:https://empresaproveedora1.onrender.com}") String url) {
        return WebClient.builder().baseUrl(url).build();
    }
}