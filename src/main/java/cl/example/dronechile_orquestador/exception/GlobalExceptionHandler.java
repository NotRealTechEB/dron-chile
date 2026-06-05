package cl.example.dronechile_orquestador.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@RestControllerAdvice
public class GlobalExceptionHandler {

   
    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<Map<String, String>> manejarErroresWebClient(WebClientResponseException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Fallo de comunicación con microservicio externo");
        error.put("detalle", "El servicio respondió con estado: " + ex.getStatusCode().value());
        error.put("mensaje_original", ex.getMessage());
        
        return ResponseEntity.status(ex.getStatusCode()).body(error);
    }

  
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> manejarErrorGeneral(Exception ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Error interno del Orquestador");
        error.put("detalle", "Ocurrió un problema inesperado en el servidor.");
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}