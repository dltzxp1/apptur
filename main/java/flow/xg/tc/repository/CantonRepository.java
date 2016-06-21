package flow.xg.tc.repository;

import flow.xg.tc.domain.Canton;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Canton entity.
 */
public interface CantonRepository extends JpaRepository<Canton,Long> {

}
