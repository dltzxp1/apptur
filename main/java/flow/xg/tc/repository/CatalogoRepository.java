package flow.xg.tc.repository;

import flow.xg.tc.domain.Catalogo;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Catalogo entity.
 */
public interface CatalogoRepository extends JpaRepository<Catalogo,Long> {

}
