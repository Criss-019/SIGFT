package cl.duoc.sigft.integracion_ms.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "tramites-ms", path = "/api/v1/tramites")
public interface TramitesClient {

    @GetMapping("/{id}")
    Map<String, Object> obtenerTramitePorId(@PathVariable String id);
}
