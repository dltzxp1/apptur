package flow.xg.tc.repository;

import flow.xg.tc.domain.Actividad;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Actividad entity.
 */
public interface ActividadRepository extends JpaRepository<Actividad,Long> {

}
