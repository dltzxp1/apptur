package flow.xg.tc.repository;

import flow.xg.tc.domain.Servicios;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Servicios entity.
 */
public interface ServiciosRepository extends JpaRepository<Servicios,Long> {

    @Query("select distinct servicios from Servicios servicios left join fetch servicios.catalogoss")
    List<Servicios> findAllWithEagerRelationships();

    @Query("select servicios from Servicios servicios left join fetch servicios.catalogoss where servicios.id =:id")
    Servicios findOneWithEagerRelationships(@Param("id") Long id);

}
