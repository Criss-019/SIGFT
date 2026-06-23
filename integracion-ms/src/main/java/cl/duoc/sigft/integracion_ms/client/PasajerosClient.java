package cl.duoc.sigft.integracion_ms.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import java.util.Map;

@FeignClient(name = "pasajeros-ms", path = "/api/v1/pasajeros")
public interface PasajerosClient {

    @GetMapping
    List<Object> obtenerTodosLosPasajeros();
    
    @GetMapping("/{rut}")
    Map<String, Object> obtenerPasajeroPorRut(@PathVariable String rut);
}
