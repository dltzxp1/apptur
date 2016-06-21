package flow.xg.tc.repository;

import flow.xg.tc.domain.Province;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Province entity.
 */
public interface ProvinceRepository extends JpaRepository<Province,Long> {

}
