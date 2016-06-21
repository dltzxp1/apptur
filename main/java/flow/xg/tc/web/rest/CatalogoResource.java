package flow.xg.tc.web.rest;

import com.codahale.metrics.annotation.Timed;
import flow.xg.tc.domain.Catalogo;
import flow.xg.tc.repository.CatalogoRepository;
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
 * REST controller for managing Catalogo.
 */
@RestController
@RequestMapping("/api")
public class CatalogoResource {

    private final Logger log = LoggerFactory.getLogger(CatalogoResource.class);
        
    @Inject
    private CatalogoRepository catalogoRepository;
    
    /**
     * POST  /catalogos -> Create a new catalogo.
     */
    @RequestMapping(value = "/catalogos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Catalogo> createCatalogo(@Valid @RequestBody Catalogo catalogo) throws URISyntaxException {
        log.debug("REST request to save Catalogo : {}", catalogo);
        if (catalogo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("catalogo", "idexists", "A new catalogo cannot already have an ID")).body(null);
        }
        Catalogo result = catalogoRepository.save(catalogo);
        return ResponseEntity.created(new URI("/api/catalogos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("catalogo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /catalogos -> Updates an existing catalogo.
     */
    @RequestMapping(value = "/catalogos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Catalogo> updateCatalogo(@Valid @RequestBody Catalogo catalogo) throws URISyntaxException {
        log.debug("REST request to update Catalogo : {}", catalogo);
        if (catalogo.getId() == null) {
            return createCatalogo(catalogo);
        }
        Catalogo result = catalogoRepository.save(catalogo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("catalogo", catalogo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /catalogos -> get all the catalogos.
     */
    @RequestMapping(value = "/catalogos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Catalogo>> getAllCatalogos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Catalogos");
        Page<Catalogo> page = catalogoRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/catalogos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /catalogos/:id -> get the "id" catalogo.
     */
    @RequestMapping(value = "/catalogos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Catalogo> getCatalogo(@PathVariable Long id) {
        log.debug("REST request to get Catalogo : {}", id);
        Catalogo catalogo = catalogoRepository.findOne(id);
        return Optional.ofNullable(catalogo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /catalogos/:id -> delete the "id" catalogo.
     */
    @RequestMapping(value = "/catalogos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCatalogo(@PathVariable Long id) {
        log.debug("REST request to delete Catalogo : {}", id);
        catalogoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("catalogo", id.toString())).build();
    }
}
