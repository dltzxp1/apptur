package flow.xg.tc.web.rest;

import com.codahale.metrics.annotation.Timed;
import flow.xg.tc.domain.Sitios;
import flow.xg.tc.repository.SitiosRepository;
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
 * REST controller for managing Sitios.
 */
@RestController
@RequestMapping("/api")
public class SitiosResource {

    private final Logger log = LoggerFactory.getLogger(SitiosResource.class);
        
    @Inject
    private SitiosRepository sitiosRepository;
    
    /**
     * POST  /sitioss -> Create a new sitios.
     */
    @RequestMapping(value = "/sitioss",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Sitios> createSitios(@Valid @RequestBody Sitios sitios) throws URISyntaxException {
        log.debug("REST request to save Sitios : {}", sitios);
        if (sitios.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("sitios", "idexists", "A new sitios cannot already have an ID")).body(null);
        }
        Sitios result = sitiosRepository.save(sitios);
        return ResponseEntity.created(new URI("/api/sitioss/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("sitios", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sitioss -> Updates an existing sitios.
     */
    @RequestMapping(value = "/sitioss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Sitios> updateSitios(@Valid @RequestBody Sitios sitios) throws URISyntaxException {
        log.debug("REST request to update Sitios : {}", sitios);
        if (sitios.getId() == null) {
            return createSitios(sitios);
        }
        Sitios result = sitiosRepository.save(sitios);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("sitios", sitios.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sitioss -> get all the sitioss.
     */
    @RequestMapping(value = "/sitioss",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Sitios>> getAllSitioss(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Sitioss");
        Page<Sitios> page = sitiosRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sitioss");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /sitioss/:id -> get the "id" sitios.
     */
    @RequestMapping(value = "/sitioss/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Sitios> getSitios(@PathVariable Long id) {
        log.debug("REST request to get Sitios : {}", id);
        Sitios sitios = sitiosRepository.findOne(id);
        return Optional.ofNullable(sitios)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /sitioss/:id -> delete the "id" sitios.
     */
    @RequestMapping(value = "/sitioss/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSitios(@PathVariable Long id) {
        log.debug("REST request to delete Sitios : {}", id);
        sitiosRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("sitios", id.toString())).build();
    }
}
