package cl.duoc.sigft.reportes_ms.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@FeignClient(name = "tramites-ms", path = "/api/v1/tramites-ms")
public interface TramitesClient {

    @GetMapping("/tramites")
    List<Map<String, Object>> obtenerTodosTramites();

    @GetMapping("/declaraciones")
    List<Map<String, Object>> obtenerDeclaraciones();
}
