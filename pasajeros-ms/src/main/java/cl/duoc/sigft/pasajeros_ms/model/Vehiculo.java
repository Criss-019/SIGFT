package cl.duoc.sigft.pasajeros_ms.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vehiculos")
public class Vehiculo {
    @Id
    @Column(name = "patente", unique = true, nullable = false, length = 10)
    private String patente;

    @Column(name = "tipo_vehiculo", nullable = false, length = 50)
    private String tipoVehiculo;

    @Column(name = "es_diplomatico", nullable = false)
    private boolean esDiplomatico;

    @Column(name = "plazo_maximo_dias", nullable = false)
    private int plazoMaximoDias;

    // Relación Muchos a 1 con Pasajero
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rut_pasajero", nullable = false)
    private Pasajero pasajero;

    // Método definido en el diagrama
    public int calcularPlazo() {
        return this.esDiplomatico ? 90 : this.plazoMaximoDias; // Ejemplo de lógica
    }

}
