package flow.xg.tc.web.rest;

import com.codahale.metrics.annotation.Timed;
import flow.xg.tc.domain.Servicios;
import flow.xg.tc.repository.ServiciosRepository;
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
 * REST controller for managing Servicios.
 */
@RestController
@RequestMapping("/api")
public class ServiciosResource {

    private final Logger log = LoggerFactory.getLogger(ServiciosResource.class);
        
    @Inject
    private ServiciosRepository serviciosRepository;
    
    /**
     * POST  /servicioss -> Create a new servicios.
     */
    @RequestMapping(value = "/servicioss",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Servicios> createServicios(@Valid @RequestBody Servicios servicios) throws URISyntaxException {
        log.debug("REST request to save Servicios : {}", servicios);
        if (servicios.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("servicios", "idexists", "A new servicios cannot already have an ID")).body(null);
        }
        Servicios result = serviciosRepository.save(servicios);
        return ResponseEntity.created(new URI("/api/servicioss/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("servicios", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /servicioss -> Updates an existing servicios.
     */
    @RequestMapping(value = "/servicioss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Servicios> updateServicios(@Valid @RequestBody Servicios servicios) throws URISyntaxException {
        log.debug("REST request to update Servicios : {}", servicios);
        if (servicios.getId() == null) {
            return createServicios(servicios);
        }
        Servicios result = serviciosRepository.save(servicios);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("servicios", servicios.getId().toString()))
            .body(result);
    }

    /**
     * GET  /servicioss -> get all the servicioss.
     */
    @RequestMapping(value = "/servicioss",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Servicios>> getAllServicioss(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Servicioss");
        Page<Servicios> page = serviciosRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/servicioss");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /servicioss/:id -> get the "id" servicios.
     */
    @RequestMapping(value = "/servicioss/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Servicios> getServicios(@PathVariable Long id) {
        log.debug("REST request to get Servicios : {}", id);
        Servicios servicios = serviciosRepository.findOneWithEagerRelationships(id);
        return Optional.ofNullable(servicios)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /servicioss/:id -> delete the "id" servicios.
     */
    @RequestMapping(value = "/servicioss/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteServicios(@PathVariable Long id) {
        log.debug("REST request to delete Servicios : {}", id);
        serviciosRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("servicios", id.toString())).build();
    }
}
