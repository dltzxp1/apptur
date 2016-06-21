package flow.xg.tc.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Servicios.
 */
@Entity
@Table(name = "servicios")
public class Servicios implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 2, max = 10)
    @Column(name = "codigo", length = 10, nullable = false)
    private String codigo;
    
    @ManyToOne
    @JoinColumn(name = "siti_id")
    private Sitios siti;

    @ManyToMany
    @JoinTable(name = "servicios_catalogos",
               joinColumns = @JoinColumn(name="servicioss_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="catalogoss_id", referencedColumnName="ID"))
    private Set<Catalogo> catalogoss = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }
    
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Sitios getSiti() {
        return siti;
    }

    public void setSiti(Sitios sitios) {
        this.siti = sitios;
    }

    public Set<Catalogo> getCatalogoss() {
        return catalogoss;
    }

    public void setCatalogoss(Set<Catalogo> catalogos) {
        this.catalogoss = catalogos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Servicios servicios = (Servicios) o;
        if(servicios.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, servicios.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Servicios{" +
            "id=" + id +
            ", codigo='" + codigo + "'" +
            '}';
    }
}
