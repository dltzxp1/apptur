package flow.xg.tc.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Catalogo.
 */
@Entity
@Table(name = "catalogo")
public class Catalogo implements Serializable {

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
    @Size(min = 2, max = 10)
    @Column(name = "tipo", length = 10, nullable = false)
    private String tipo;
    
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

    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Catalogo catalogo = (Catalogo) o;
        if(catalogo.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, catalogo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Catalogo{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            ", descripcion='" + descripcion + "'" +
            ", tipo='" + tipo + "'" +
            '}';
    }
}
