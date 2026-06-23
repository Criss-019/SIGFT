package cl.duoc.sigft.pasajeros_ms.model;



import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "vehiculos")
@EqualsAndHashCode(exclude = "vehiculos")
@Entity
@Table(name = "pasajeros")
public class Pasajero {
    @Id
    @Column(name = "rut", unique = true, nullable = false, length = 12)
    private String rut;

    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    @Column(name = "edad", nullable = false)
    private int edad;

    @Column(name = "nacionalidad", nullable = false, length = 50)
    private String nacionalidad;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    
    @OneToMany(mappedBy = "pasajero", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vehiculo> vehiculos;

    // Método definido en el diagrama
    public boolean verificarEdad() {
        return this.edad >= 18;
    }
}
