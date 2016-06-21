package flow.xg.tc.web.rest;

import com.codahale.metrics.annotation.Timed;
import flow.xg.tc.domain.Actividad;
import flow.xg.tc.repository.ActividadRepository;
import flow.xg.tc.repository.ResponsableRepository;
import flow.xg.tc.web.rest.util.HeaderUtil;
import flow.xg.tc.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Actividad.
 */
@RestController
@RequestMapping("/api")
public class ActividadResource {

    private final Logger log = LoggerFactory.getLogger(ActividadResource.class);
        
    @Inject
    private ActividadRepository actividadRepository;
    
    @Inject
    private ResponsableRepository responsablesRepository;
    
    
    /**
     * POST  /actividads -> Create a new actividad.
     */
    @RequestMapping(value = "/actividads",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Actividad> createActividad(@Valid @RequestBody Actividad actividad) throws URISyntaxException {
        log.debug("REST request to save Actividad : {}", actividad);
        if (actividad.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("actividad", "idexists", "A new actividad cannot already have an ID")).body(null);
        }
        Actividad result = actividadRepository.save(actividad);
        return ResponseEntity.created(new URI("/api/actividads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("actividad", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /actividads -> Updates an existing actividad.
     */
    @RequestMapping(value = "/actividads",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Actividad> updateActividad(@Valid @RequestBody Actividad actividad) throws URISyntaxException {
        log.debug("REST request to update Actividad : {}", actividad);
        if (actividad.getId() == null) {
            return createActividad(actividad);
        }
        Actividad result = actividadRepository.save(actividad);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("actividad", actividad.getId().toString()))
            .body(result);
    }

    /**
     * GET  /actividads -> get all the actividads.
     */
    @RequestMapping(value = "/actividads",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Actividad>> getAllActividads(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Actividads");
        Page<Actividad> page = actividadRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/actividads");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /actividads/:id -> get the "id" actividad.
     */
    @RequestMapping(value = "/actividads/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Actividad> getActividad(@PathVariable Long id) {
        log.debug("REST request to get Actividad : {}", id);
        Actividad actividad = actividadRepository.findOne(id);
        return Optional.ofNullable(actividad)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /actividads/:id -> delete the "id" actividad.
     */
    @RequestMapping(value = "/actividads/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteActividad(@PathVariable Long id) {
        log.debug("REST request to delete Actividad : {}", id);
        actividadRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("actividad", id.toString())).build();
    }
    
    
    /**
     * DELETE  /responsabless/:id -> delete the "id" responsables.
     */
    @RequestMapping(value = "/responsablessdel/{id}",method = RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void deleteResponsables(@PathVariable Long id) {
    	try {
    		responsablesRepository.delete(id);
		} catch (Exception e) {
			// TODO: handle exception
		} 
        //return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("responsables", id.toString())).build();
    }
    
}
