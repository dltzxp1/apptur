package flow.xg.tc.repository;

import flow.xg.tc.domain.Sitios;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Sitios entity.
 */
public interface SitiosRepository extends JpaRepository<Sitios,Long> {

}
