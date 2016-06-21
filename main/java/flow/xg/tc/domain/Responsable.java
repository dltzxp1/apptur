package flow.xg.tc.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Responsable.
 */
@Entity
@Table(name = "responsable")
public class Responsable implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@Size(min = 2, max = 128)
	@Column(name = "nombre", length = 128, nullable = false)
	private String nombre;

	@NotNull
	@Size(min = 2, max = 64)
	@Column(name = "correo", length = 64, nullable = false)
	private String correo;

	@NotNull
	@Size(min = 1, max = 64)
	@Column(name = "telefono", length = 64, nullable = false)
	private String telefono;

	@NotNull
	@Size(min = 2, max = 128)
	@Column(name = "celular", length = 128, nullable = false)
	private String celular;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "activi_id")
	private Actividad activid;

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

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	@JsonIgnore
	public Actividad getActivid() {
		return activid;
	}

	@JsonProperty
	public void setActivid(Actividad activid) {
		this.activid = activid;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Responsable responsable = (Responsable) o;
		if (responsable.id == null || id == null) {
			return false;
		}
		return Objects.equals(id, responsable.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	@Override
	public String toString() {
		return "Responsable{" + "id=" + id + ", nombre='" + nombre + "'" + ", correo='" + correo + "'" + ", telefono='"
				+ telefono + "'" + ", celular='" + celular + "'" + '}';
	}
}
