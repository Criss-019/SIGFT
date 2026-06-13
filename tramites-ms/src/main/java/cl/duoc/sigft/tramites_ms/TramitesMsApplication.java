package cl.duoc.sigft.tramites_ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TramitesMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TramitesMsApplication.class, args);
	}

}
