package flow.xg.tc.web.rest;

import flow.xg.tc.Application;
import flow.xg.tc.domain.Responsable;
import flow.xg.tc.repository.ResponsableRepository;

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
 * Test class for the ResponsableResource REST controller.
 *
 * @see ResponsableResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ResponsableResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AA";
    private static final String UPDATED_NOMBRE = "BB";
    private static final String DEFAULT_CORREO = "AA";
    private static final String UPDATED_CORREO = "BB";
    private static final String DEFAULT_TELEFONO = "A";
    private static final String UPDATED_TELEFONO = "B";
    private static final String DEFAULT_CELULAR = "AA";
    private static final String UPDATED_CELULAR = "BB";

    @Inject
    private ResponsableRepository responsableRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restResponsableMockMvc;

    private Responsable responsable;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ResponsableResource responsableResource = new ResponsableResource();
        ReflectionTestUtils.setField(responsableResource, "responsableRepository", responsableRepository);
        this.restResponsableMockMvc = MockMvcBuilders.standaloneSetup(responsableResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        responsable = new Responsable();
        responsable.setNombre(DEFAULT_NOMBRE);
        responsable.setCorreo(DEFAULT_CORREO);
        responsable.setTelefono(DEFAULT_TELEFONO);
        responsable.setCelular(DEFAULT_CELULAR);
    }

    @Test
    @Transactional
    public void createResponsable() throws Exception {
        int databaseSizeBeforeCreate = responsableRepository.findAll().size();

        // Create the Responsable

        restResponsableMockMvc.perform(post("/api/responsables")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(responsable)))
                .andExpect(status().isCreated());

        // Validate the Responsable in the database
        List<Responsable> responsables = responsableRepository.findAll();
        assertThat(responsables).hasSize(databaseSizeBeforeCreate + 1);
        Responsable testResponsable = responsables.get(responsables.size() - 1);
        assertThat(testResponsable.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testResponsable.getCorreo()).isEqualTo(DEFAULT_CORREO);
        assertThat(testResponsable.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testResponsable.getCelular()).isEqualTo(DEFAULT_CELULAR);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = responsableRepository.findAll().size();
        // set the field null
        responsable.setNombre(null);

        // Create the Responsable, which fails.

        restResponsableMockMvc.perform(post("/api/responsables")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(responsable)))
                .andExpect(status().isBadRequest());

        List<Responsable> responsables = responsableRepository.findAll();
        assertThat(responsables).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCorreoIsRequired() throws Exception {
        int databaseSizeBeforeTest = responsableRepository.findAll().size();
        // set the field null
        responsable.setCorreo(null);

        // Create the Responsable, which fails.

        restResponsableMockMvc.perform(post("/api/responsables")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(responsable)))
                .andExpect(status().isBadRequest());

        List<Responsable> responsables = responsableRepository.findAll();
        assertThat(responsables).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTelefonoIsRequired() throws Exception {
        int databaseSizeBeforeTest = responsableRepository.findAll().size();
        // set the field null
        responsable.setTelefono(null);

        // Create the Responsable, which fails.

        restResponsableMockMvc.perform(post("/api/responsables")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(responsable)))
                .andExpect(status().isBadRequest());

        List<Responsable> responsables = responsableRepository.findAll();
        assertThat(responsables).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCelularIsRequired() throws Exception {
        int databaseSizeBeforeTest = responsableRepository.findAll().size();
        // set the field null
        responsable.setCelular(null);

        // Create the Responsable, which fails.

        restResponsableMockMvc.perform(post("/api/responsables")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(responsable)))
                .andExpect(status().isBadRequest());

        List<Responsable> responsables = responsableRepository.findAll();
        assertThat(responsables).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllResponsables() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsables
        restResponsableMockMvc.perform(get("/api/responsables?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(responsable.getId().intValue())))
                .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
                .andExpect(jsonPath("$.[*].correo").value(hasItem(DEFAULT_CORREO.toString())))
                .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO.toString())))
                .andExpect(jsonPath("$.[*].celular").value(hasItem(DEFAULT_CELULAR.toString())));
    }

    @Test
    @Transactional
    public void getResponsable() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get the responsable
        restResponsableMockMvc.perform(get("/api/responsables/{id}", responsable.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(responsable.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.correo").value(DEFAULT_CORREO.toString()))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO.toString()))
            .andExpect(jsonPath("$.celular").value(DEFAULT_CELULAR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingResponsable() throws Exception {
        // Get the responsable
        restResponsableMockMvc.perform(get("/api/responsables/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResponsable() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

		int databaseSizeBeforeUpdate = responsableRepository.findAll().size();

        // Update the responsable
        responsable.setNombre(UPDATED_NOMBRE);
        responsable.setCorreo(UPDATED_CORREO);
        responsable.setTelefono(UPDATED_TELEFONO);
        responsable.setCelular(UPDATED_CELULAR);

        restResponsableMockMvc.perform(put("/api/responsables")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(responsable)))
                .andExpect(status().isOk());

        // Validate the Responsable in the database
        List<Responsable> responsables = responsableRepository.findAll();
        assertThat(responsables).hasSize(databaseSizeBeforeUpdate);
        Responsable testResponsable = responsables.get(responsables.size() - 1);
        assertThat(testResponsable.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testResponsable.getCorreo()).isEqualTo(UPDATED_CORREO);
        assertThat(testResponsable.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testResponsable.getCelular()).isEqualTo(UPDATED_CELULAR);
    }

    @Test
    @Transactional
    public void deleteResponsable() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

		int databaseSizeBeforeDelete = responsableRepository.findAll().size();

        // Get the responsable
        restResponsableMockMvc.perform(delete("/api/responsables/{id}", responsable.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Responsable> responsables = responsableRepository.findAll();
        assertThat(responsables).hasSize(databaseSizeBeforeDelete - 1);
    }
}
