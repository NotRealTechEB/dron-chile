package cl.example.dronechile_orquestador.mapper;

import org.springframework.stereotype.Component;

import cl.example.dronechile_orquestador.dto.RegistroVueloDTO;
import cl.example.dronechile_orquestador.dto.ReporteOperacionDTO;

@Component
public class ReporteMapper {

    public ReporteOperacionDTO ensamblarFactura(RegistroVueloDTO vuelo, String nombreTrabajo) {
        
       
        if (vuelo == null) {
            return null; 
        }

        // Lógica por defecto si no se encuentra el trabajo
        String trabajoFinal = (nombreTrabajo != null && !nombreTrabajo.isBlank()) 
                              ? nombreTrabajo 
                              : "Desconocido";

        // Uso directo del constructor del Record para instanciar el nuevo objeto
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