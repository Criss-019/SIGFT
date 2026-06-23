package cl.duoc.sigft.seguridad_ms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
public class SecurityConfig {
    /**
     * Expone BCryptPasswordEncoder como bean para ser inyectado en el servicio.
     * Strength 12 = ~300ms por hash en hardware moderno, balance adecuado
     * entre seguridad y rendimiento para un sistema de autenticación de back-office.
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

}
