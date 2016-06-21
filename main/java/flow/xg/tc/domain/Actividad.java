package flow.xg.tc.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A Actividad.
 */
@Entity
@Table(name = "actividad")
public class Actividad implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@Size(min = 2, max = 64)
	@Column(name = "nombre", length = 64, nullable = false)
	private String nombre;

	@NotNull
	@Size(min = 2, max = 2014)
	@Column(name = "descripcion", length = 2014, nullable = false)
	private String descripcion;

	@NotNull
	@Size(min = 2, max = 128)
	@Column(name = "direccion", length = 128, nullable = false)
	private String direccion;

	@NotNull
	@Column(name = "fecha", nullable = false)
	private LocalDate fecha;

	@NotNull
	@Size(min = 100, max = 100000)
	@Lob
	@Column(name = "foto", nullable = false)
	private byte[] foto;

	@Column(name = "foto_content_type", nullable = false)
	private String fotoContentType;

	@ManyToOne
	@JoinColumn(name = "sitioss_id")
	private Sitios sitioss;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "activid", cascade = { CascadeType.ALL }, orphanRemoval = true)
	private Set<Responsable> responsabless = new HashSet<>();

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

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public String getFotoContentType() {
		return fotoContentType;
	}

	public void setFotoContentType(String fotoContentType) {
		this.fotoContentType = fotoContentType;
	}

	public Sitios getSitioss() {
		return sitioss;
	}

	public void setSitioss(Sitios sitios) {
		this.sitioss = sitios;
	}

	public Set<Responsable> getResponsabless() {
		return responsabless;
	}

	public void setResponsabless(Set<Responsable> responsabless) {
		responsabless.forEach(x -> x.setActivid(this));
		this.responsabless = responsabless;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Actividad actividad = (Actividad) o;
		if (actividad.id == null || id == null) {
			return false;
		}
		return Objects.equals(id, actividad.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	@Override
	public String toString() {
		return "Actividad{" + "id=" + id + ", nombre='" + nombre + "'" + ", descripcion='" + descripcion + "'"
				+ ", direccion='" + direccion + "'" + ", fecha='" + fecha + "'" + ", foto='" + foto + "'"
				+ ", fotoContentType='" + fotoContentType + "'" + '}';
	}
}
