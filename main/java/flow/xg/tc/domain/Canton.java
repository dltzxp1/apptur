package flow.xg.tc.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Canton.
 */
@Entity
@Table(name = "canton")
public class Canton implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 2, max = 50)
    @Column(name = "nombre", length = 50, nullable = false)
    private String nombre;
    
    @NotNull
    @Size(min = 2, max = 512)
    @Column(name = "descripcion", length = 512, nullable = false)
    private String descripcion;
    
    @NotNull
    @Column(name = "poblacion", nullable = false)
    private Integer poblacion;
    
    @ManyToOne
    @JoinColumn(name = "provinces_id")
    private Province provinces;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getPoblacion() {
        return poblacion;
    }
    
    public void setPoblacion(Integer poblacion) {
        this.poblacion = poblacion;
    }

    public Province getProvinces() {
        return provinces;
    }

    public void setProvinces(Province province) {
        this.provinces = province;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Canton canton = (Canton) o;
        if(canton.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, canton.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Canton{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            ", descripcion='" + descripcion + "'" +
            ", poblacion='" + poblacion + "'" +
            '}';
    }
}
