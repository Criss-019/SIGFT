package cl.duoc.sigft.integracion_ms.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "registros_integracion")
public class RegistroIntegracion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_tramite_ref", nullable = false, length = 50)
    private String idTramiteRef;

    @Column(name = "rut_pasajero", nullable = false, length = 12)
    private String rutPasajero;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_operacion", nullable = false, length = 30)
    private TipoOperacion tipoOperacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 20)
    private EstadoIntegracion estado;

    @Column(name = "fecha_envio")
    private LocalDateTime fechaEnvio;

    @Column(name = "fecha_respuesta")
    private LocalDateTime fechaRespuesta;

    @Column(name = "codigo_respuesta_argentina", length = 20)
    private String codigoRespuestaArgentina;

    @Column(name = "mensaje_respuesta", length = 500)
    private String mensajeRespuesta;

    @Column(name = "datos_enviados", columnDefinition = "TEXT")
    private String datosEnviados;

    @Column(name = "intentos_envio")
    private Integer intentosEnvio;

    // Lógica de dominio: verifica si se puede reintentar el envío
    public boolean puedeReintentar() {
        return (this.estado == EstadoIntegracion.ERROR || this.estado == EstadoIntegracion.RECHAZADO)
                && (this.intentosEnvio == null || this.intentosEnvio < 3);
    }
}
