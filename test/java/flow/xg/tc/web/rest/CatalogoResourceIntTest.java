package flow.xg.tc.web.rest;

import flow.xg.tc.Application;
import flow.xg.tc.domain.Catalogo;
import flow.xg.tc.repository.CatalogoRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the CatalogoResource REST controller.
 *
 * @see CatalogoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CatalogoResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AA";
    private static final String UPDATED_NOMBRE = "BB";
    private static final String DEFAULT_DESCRIPCION = "AA";
    private static final String UPDATED_DESCRIPCION = "BB";
    private static final String DEFAULT_TIPO = "AA";
    private static final String UPDATED_TIPO = "BB";

    @Inject
    private CatalogoRepository catalogoRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCatalogoMockMvc;

    private Catalogo catalogo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CatalogoResource catalogoResource = new CatalogoResource();
        ReflectionTestUtils.setField(catalogoResource, "catalogoRepository", catalogoRepository);
        this.restCatalogoMockMvc = MockMvcBuilders.standaloneSetup(catalogoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        catalogo = new Catalogo();
        catalogo.setNombre(DEFAULT_NOMBRE);
        catalogo.setDescripcion(DEFAULT_DESCRIPCION);
        catalogo.setTipo(DEFAULT_TIPO);
    }

    @Test
    @Transactional
    public void createCatalogo() throws Exception {
        int databaseSizeBeforeCreate = catalogoRepository.findAll().size();

        // Create the Catalogo

        restCatalogoMockMvc.perform(post("/api/catalogos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(catalogo)))
                .andExpect(status().isCreated());

        // Validate the Catalogo in the database
        List<Catalogo> catalogos = catalogoRepository.findAll();
        assertThat(catalogos).hasSize(databaseSizeBeforeCreate + 1);
        Catalogo testCatalogo = catalogos.get(catalogos.size() - 1);
        assertThat(testCatalogo.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testCatalogo.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testCatalogo.getTipo()).isEqualTo(DEFAULT_TIPO);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = catalogoRepository.findAll().size();
        // set the field null
        catalogo.setNombre(null);

        // Create the Catalogo, which fails.

        restCatalogoMockMvc.perform(post("/api/catalogos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(catalogo)))
                .andExpect(status().isBadRequest());

        List<Catalogo> catalogos = catalogoRepository.findAll();
        assertThat(catalogos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = catalogoRepository.findAll().size();
        // set the field null
        catalogo.setDescripcion(null);

        // Create the Catalogo, which fails.

        restCatalogoMockMvc.perform(post("/api/catalogos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(catalogo)))
                .andExpect(status().isBadRequest());

        List<Catalogo> catalogos = catalogoRepository.findAll();
        assertThat(catalogos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTipoIsRequired() throws Exception {
        int databaseSizeBeforeTest = catalogoRepository.findAll().size();
        // set the field null
        catalogo.setTipo(null);

        // Create the Catalogo, which fails.

        restCatalogoMockMvc.perform(post("/api/catalogos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(catalogo)))
                .andExpect(status().isBadRequest());

        List<Catalogo> catalogos = catalogoRepository.findAll();
        assertThat(catalogos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCatalogos() throws Exception {
        // Initialize the database
        catalogoRepository.saveAndFlush(catalogo);

        // Get all the catalogos
        restCatalogoMockMvc.perform(get("/api/catalogos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(catalogo.getId().intValue())))
                .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
                .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
                .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())));
    }

    @Test
    @Transactional
    public void getCatalogo() throws Exception {
        // Initialize the database
        catalogoRepository.saveAndFlush(catalogo);

        // Get the catalogo
        restCatalogoMockMvc.perform(get("/api/catalogos/{id}", catalogo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(catalogo.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCatalogo() throws Exception {
        // Get the catalogo
        restCatalogoMockMvc.perform(get("/api/catalogos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCatalogo() throws Exception {
        // Initialize the database
        catalogoRepository.saveAndFlush(catalogo);

		int databaseSizeBeforeUpdate = catalogoRepository.findAll().size();

        // Update the catalogo
        catalogo.setNombre(UPDATED_NOMBRE);
        catalogo.setDescripcion(UPDATED_DESCRIPCION);
        catalogo.setTipo(UPDATED_TIPO);

        restCatalogoMockMvc.perform(put("/api/catalogos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(catalogo)))
                .andExpect(status().isOk());

        // Validate the Catalogo in the database
        List<Catalogo> catalogos = catalogoRepository.findAll();
        assertThat(catalogos).hasSize(databaseSizeBeforeUpdate);
        Catalogo testCatalogo = catalogos.get(catalogos.size() - 1);
        assertThat(testCatalogo.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testCatalogo.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testCatalogo.getTipo()).isEqualTo(UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void deleteCatalogo() throws Exception {
        // Initialize the database
        catalogoRepository.saveAndFlush(catalogo);

		int databaseSizeBeforeDelete = catalogoRepository.findAll().size();

        // Get the catalogo
        restCatalogoMockMvc.perform(delete("/api/catalogos/{id}", catalogo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Catalogo> catalogos = catalogoRepository.findAll();
        assertThat(catalogos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
