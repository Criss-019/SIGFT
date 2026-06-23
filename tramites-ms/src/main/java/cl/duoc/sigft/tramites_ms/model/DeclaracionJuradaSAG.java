package cl.duoc.sigft.tramites_ms.model;

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
@Table(name = "declaraciones_sag")
public class DeclaracionJuradaSAG {
    @Id
    @Column(name = "id_declaracion", length = 50)
    private String idDeclaracion;

    @Column(name = "fecha_registro", nullable = false)
    private Date fechaRegistro;

    @Column(name = "trae_animales")
    private boolean traeProductosAnimales;

    @Column(name = "trae_vegetales")
    private boolean traeProductosVegetales;

    @Column(name = "posee_mascotas")
    private boolean poseeMascotas;

    @Column(name = "rut_pasajero", nullable = false, length = 15)
    private String rutPasajero; 

    @Column(name = "requiere_revision")
    private boolean requiereRevisionSAG;

    // Método exigido en el diagrama (void)
    public void validarDeclaracion() {
        this.requiereRevisionSAG = (traeProductosAnimales || traeProductosVegetales || poseeMascotas);
    }

}
