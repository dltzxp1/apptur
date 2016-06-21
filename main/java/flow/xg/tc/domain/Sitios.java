package flow.xg.tc.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Sitios.
 */
@Entity
@Table(name = "sitios")
public class Sitios implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "nombre", length = 64, nullable = false)
    private String nombre;
    
    @NotNull
    @Size(min = 1, max = 1024)
    @Column(name = "descripcion", length = 1024, nullable = false)
    private String descripcion;
    
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "paginaweb", length = 128, nullable = false)
    private String paginaweb;
    
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "mail", length = 64, nullable = false)
    private String mail;
    
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "facebook", length = 128, nullable = false)
    private String facebook;
    
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "twitter", length = 128, nullable = false)
    private String twitter;
    
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "direccion", length = 256, nullable = false)
    private String direccion;
    
    @NotNull
    @Size(min = 2, max = 64)
    @Column(name = "telefono", length = 64, nullable = false)
    private String telefono;
    
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "latitud", length = 20, nullable = false)
    private String latitud;
    
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "longitud", length = 20, nullable = false)
    private String longitud;
    
    @ManyToOne
    @JoinColumn(name = "cantons_id")
    private Canton cantons;

    @ManyToOne
    @JoinColumn(name = "categories_id")
    private Category categories;

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

    public String getPaginaweb() {
        return paginaweb;
    }
    
    public void setPaginaweb(String paginaweb) {
        this.paginaweb = paginaweb;
    }

    public String getMail() {
        return mail;
    }
    
    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getFacebook() {
        return facebook;
    }
    
    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getTwitter() {
        return twitter;
    }
    
    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getDireccion() {
        return direccion;
    }
    
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getLatitud() {
        return latitud;
    }
    
    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }
    
    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public Canton getCantons() {
        return cantons;
    }

    public void setCantons(Canton canton) {
        this.cantons = canton;
    }

    public Category getCategories() {
        return categories;
    }

    public void setCategories(Category category) {
        this.categories = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Sitios sitios = (Sitios) o;
        if(sitios.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, sitios.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Sitios{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            ", descripcion='" + descripcion + "'" +
            ", paginaweb='" + paginaweb + "'" +
            ", mail='" + mail + "'" +
            ", facebook='" + facebook + "'" +
            ", twitter='" + twitter + "'" +
            ", direccion='" + direccion + "'" +
            ", telefono='" + telefono + "'" +
            ", latitud='" + latitud + "'" +
            ", longitud='" + longitud + "'" +
            '}';
    }
}
