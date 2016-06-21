package flow.xg.tc.repository;

import flow.xg.tc.domain.Responsable;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Responsable entity.
 */
public interface ResponsableRepository extends JpaRepository<Responsable,Long> {

}
