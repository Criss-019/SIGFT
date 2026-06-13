package cl.duoc.sigft.tramites_ms.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "pasajeros-ms")
public interface PasajeroClient {
    @GetMapping("/api/v1/pasajeros/{rut}")
    Object obtenerPasajeroPorRut(@PathVariable("rut") String rut);

    @GetMapping("/api/v1/vehiculos/{patente}")
    Object obtenerVehiculoPorPatente(@PathVariable("patente") String patente);

}
