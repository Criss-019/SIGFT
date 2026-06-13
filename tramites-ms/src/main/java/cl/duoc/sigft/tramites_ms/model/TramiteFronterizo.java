package cl.duoc.sigft.tramites_ms.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tramites_fronterizos")
public class TramiteFronterizo {
    @Id
    @Column(name = "id_tramite", length = 50)
    private String idTramite;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    @Column(name = "estado_tramite", nullable = false, length = 20)
    private String estadoTramite;

    @Column(name = "aduana_origen", nullable = false, length = 100)
    private String aduanaOrigen;

    @Column(name = "aduana_destino", nullable = false, length = 100)
    private String aduanaDestino;

    // Relación de agregación: Guarda los RUTs referenciales
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "tramite_pasajeros", joinColumns = @JoinColumn(name = "id_tramite"))
    @Column(name = "rut_pasajero")
    private List<String> rutsPasajeros;

    // Relación de agregación: Guarda las Patentes referenciales
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "tramite_vehiculos", joinColumns = @JoinColumn(name = "id_tramite"))
    @Column(name = "patente_vehiculo")
    private List<String> patentesVehiculos;

    // Método exigido en el diagrama (void)
    public void procesarCruce() {
        if ("PENDIENTE".equalsIgnoreCase(this.estadoTramite)) {
            this.estadoTramite = "PROCESADO";
        } else {
            throw new IllegalStateException("El trámite no está en estado PENDIENTE.");
        }
    }

}
