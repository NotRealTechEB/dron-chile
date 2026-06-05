package cl.example.dronechile_orquestador.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    // Cada @Bean es como un "enchufe" listo para ser usado por el sistema

    @Bean(name = "pilotoWebClient")
    public WebClient pilotoWebClient(@Value("${api.pilotos.url:http://localhost:8086/api/pilotos}") String url) {
        return WebClient.builder().baseUrl(url).build();
    }

    @Bean(name = "licenciaWebClient")
    public WebClient licenciaWebClient(@Value("${api.licencias.url:http://localhost:8085/api/licencias}") String url) {
        return WebClient.builder().baseUrl(url).build();
    }

    @Bean(name = "vueloWebClient")
    public WebClient vueloWebClient(@Value("${api.registros.url:http://localhost:8089/api/registros}") String url) {
        return WebClient.builder().baseUrl(url).build();
    }

    @Bean(name = "solicitudWebClient")
    public WebClient solicitudWebClient(@Value("${api.solicitudes.url:http://localhost:8080/api/solicitudes}") String url) {
        return WebClient.builder().baseUrl(url).build();
    }

    @Bean(name = "tipoTrabajoWebClient")
    public WebClient tipoTrabajoWebClient(@Value("${api.tipostrabajo.url:http://localhost:8083/api/tipos-trabajo}") String url) {
        return WebClient.builder().baseUrl(url).build();
    }

    @Bean(name = "dronWebClient")
    public WebClient dronWebClient(@Value("${api.drones.url:http://localhost:8082/api/drones}") String url) {
        return WebClient.builder().baseUrl(url).build();
    }

    @Bean(name = "empresaWebClient")
    public WebClient empresaWebClient(@Value("${api.empresas.url:http://localhost:8081/api/empresas-proveedoras}") String url) {
        return WebClient.builder().baseUrl(url).build();
    }
}