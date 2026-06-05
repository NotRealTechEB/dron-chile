package cl.example.dronechile_orquestador.mapper;

import cl.example.dronechile_orquestador.dto.RegistroVueloDTO;
import cl.example.dronechile_orquestador.dto.ReporteOperacionDTO;
import org.springframework.stereotype.Component;

@Component
public class ReporteMapper {

    public ReporteOperacionDTO ensamblarFactura(RegistroVueloDTO vuelo, String nombreTrabajo) {
        
        // 1. Manejo de nulos preventivo (Buena práctica)
        if (vuelo == null) {
            return null; 
        }

        // 2. Lógica por defecto si no se encuentra el trabajo
        String trabajoFinal = (nombreTrabajo != null && !nombreTrabajo.isBlank()) 
                              ? nombreTrabajo 
                              : "Desconocido";

        // 3. Uso directo del constructor del Record para instanciar el nuevo objeto
        return new ReporteOperacionDTO(
            vuelo.codigoVuelo(),
            vuelo.fechaPV(),
            vuelo.rutPiloto(),
            vuelo.rutEmpMandante(),
            trabajoFinal,
            vuelo.tiempoTotal()
        );
    }
}