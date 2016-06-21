package flow.xg.tc.web.rest;

import flow.xg.tc.Application;
import flow.xg.tc.domain.Canton;
import flow.xg.tc.repository.CantonRepository;

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
 * Test class for the CantonResource REST controller.
 *
 * @see CantonResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CantonResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AA";
    private static final String UPDATED_NOMBRE = "BB";
    private static final String DEFAULT_DESCRIPCION = "AA";
    private static final String UPDATED_DESCRIPCION = "BB";

    private static final Integer DEFAULT_POBLACION = 1;
    private static final Integer UPDATED_POBLACION = 2;

    @Inject
    private CantonRepository cantonRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCantonMockMvc;

    private Canton canton;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CantonResource cantonResource = new CantonResource();
        ReflectionTestUtils.setField(cantonResource, "cantonRepository", cantonRepository);
        this.restCantonMockMvc = MockMvcBuilders.standaloneSetup(cantonResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        canton = new Canton();
        canton.setNombre(DEFAULT_NOMBRE);
        canton.setDescripcion(DEFAULT_DESCRIPCION);
        canton.setPoblacion(DEFAULT_POBLACION);
    }

    @Test
    @Transactional
    public void createCanton() throws Exception {
        int databaseSizeBeforeCreate = cantonRepository.findAll().size();

        // Create the Canton

        restCantonMockMvc.perform(post("/api/cantons")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(canton)))
                .andExpect(status().isCreated());

        // Validate the Canton in the database
        List<Canton> cantons = cantonRepository.findAll();
        assertThat(cantons).hasSize(databaseSizeBeforeCreate + 1);
        Canton testCanton = cantons.get(cantons.size() - 1);
        assertThat(testCanton.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testCanton.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testCanton.getPoblacion()).isEqualTo(DEFAULT_POBLACION);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = cantonRepository.findAll().size();
        // set the field null
        canton.setNombre(null);

        // Create the Canton, which fails.

        restCantonMockMvc.perform(post("/api/cantons")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(canton)))
                .andExpect(status().isBadRequest());

        List<Canton> cantons = cantonRepository.findAll();
        assertThat(cantons).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = cantonRepository.findAll().size();
        // set the field null
        canton.setDescripcion(null);

        // Create the Canton, which fails.

        restCantonMockMvc.perform(post("/api/cantons")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(canton)))
                .andExpect(status().isBadRequest());

        List<Canton> cantons = cantonRepository.findAll();
        assertThat(cantons).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPoblacionIsRequired() throws Exception {
        int databaseSizeBeforeTest = cantonRepository.findAll().size();
        // set the field null
        canton.setPoblacion(null);

        // Create the Canton, which fails.

        restCantonMockMvc.perform(post("/api/cantons")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(canton)))
                .andExpect(status().isBadRequest());

        List<Canton> cantons = cantonRepository.findAll();
        assertThat(cantons).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCantons() throws Exception {
        // Initialize the database
        cantonRepository.saveAndFlush(canton);

        // Get all the cantons
        restCantonMockMvc.perform(get("/api/cantons?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(canton.getId().intValue())))
                .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
                .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
                .andExpect(jsonPath("$.[*].poblacion").value(hasItem(DEFAULT_POBLACION)));
    }

    @Test
    @Transactional
    public void getCanton() throws Exception {
        // Initialize the database
        cantonRepository.saveAndFlush(canton);

        // Get the canton
        restCantonMockMvc.perform(get("/api/cantons/{id}", canton.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(canton.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.poblacion").value(DEFAULT_POBLACION));
    }

    @Test
    @Transactional
    public void getNonExistingCanton() throws Exception {
        // Get the canton
        restCantonMockMvc.perform(get("/api/cantons/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCanton() throws Exception {
        // Initialize the database
        cantonRepository.saveAndFlush(canton);

		int databaseSizeBeforeUpdate = cantonRepository.findAll().size();

        // Update the canton
        canton.setNombre(UPDATED_NOMBRE);
        canton.setDescripcion(UPDATED_DESCRIPCION);
        canton.setPoblacion(UPDATED_POBLACION);

        restCantonMockMvc.perform(put("/api/cantons")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(canton)))
                .andExpect(status().isOk());

        // Validate the Canton in the database
        List<Canton> cantons = cantonRepository.findAll();
        assertThat(cantons).hasSize(databaseSizeBeforeUpdate);
        Canton testCanton = cantons.get(cantons.size() - 1);
        assertThat(testCanton.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testCanton.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testCanton.getPoblacion()).isEqualTo(UPDATED_POBLACION);
    }

    @Test
    @Transactional
    public void deleteCanton() throws Exception {
        // Initialize the database
        cantonRepository.saveAndFlush(canton);

		int databaseSizeBeforeDelete = cantonRepository.findAll().size();

        // Get the canton
        restCantonMockMvc.perform(delete("/api/cantons/{id}", canton.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Canton> cantons = cantonRepository.findAll();
        assertThat(cantons).hasSize(databaseSizeBeforeDelete - 1);
    }
}
