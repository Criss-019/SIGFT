package cl.duoc.sigft.tramites_ms.model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Table(name = "autorizaciones_notariales")
public class AutorizacionNotarial {
    @Id
    @Column(name = "id_autorizacion", length = 50)
    private String idAutorizacion;

    @Column(name = "fecha_emision", nullable = false)
    private Date fechaEmision;

    @Column(name = "notaria_origen", nullable = false, length = 100)
    private String notariaOrigen;

    @Column(name = "adjunto_pdf", length = 255)
    private String adjuntoPDF;

    @Column(name = "rut_pasajero", nullable = false, length = 15)
    private String rutPasajero;

    // Método exigido en el diagrama (boolean)
    public boolean validarVigencia() {
        LocalDate emision = this.fechaEmision.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        long dias = ChronoUnit.DAYS.between(emision, LocalDate.now());
        return dias <= 30; // Vigente solo si tiene 30 días o menos
    }

}
