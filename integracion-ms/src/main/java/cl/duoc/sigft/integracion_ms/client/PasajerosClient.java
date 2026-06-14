package cl.duoc.sigft.integracion_ms.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "pasajeros-ms", path = "/api/v1/pasajeros-ms")
public interface PasajerosClient {

    @GetMapping("/pasajeros/{rut}")
    Map<String, Object> obtenerPasajeroPorRut(@PathVariable String rut);
}
