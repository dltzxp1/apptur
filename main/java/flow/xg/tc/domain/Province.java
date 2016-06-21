package flow.xg.tc.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Province.
 */
@Entity
@Table(name = "province")
public class Province implements Serializable {

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
    @Size(min = 2, max = 50)
    @Column(name = "capital", length = 50, nullable = false)
    private String capital;
    
    @NotNull
    @Min(value = 1)
    @Max(value = 10000)
    @Column(name = "poblacion", nullable = false)
    private Integer poblacion;
    
    @NotNull
    @Size(min = 2, max = 50)
    @Column(name = "region", length = 50, nullable = false)
    private String region;
    
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

    public String getCapital() {
        return capital;
    }
    
    public void setCapital(String capital) {
        this.capital = capital;
    }

    public Integer getPoblacion() {
        return poblacion;
    }
    
    public void setPoblacion(Integer poblacion) {
        this.poblacion = poblacion;
    }

    public String getRegion() {
        return region;
    }
    
    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Province province = (Province) o;
        if(province.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, province.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Province{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            ", descripcion='" + descripcion + "'" +
            ", capital='" + capital + "'" +
            ", poblacion='" + poblacion + "'" +
            ", region='" + region + "'" +
            '}';
    }
}
