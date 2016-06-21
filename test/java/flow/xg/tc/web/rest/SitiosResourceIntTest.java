package flow.xg.tc.web.rest;

import flow.xg.tc.Application;
import flow.xg.tc.domain.Sitios;
import flow.xg.tc.repository.SitiosRepository;

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
 * Test class for the SitiosResource REST controller.
 *
 * @see SitiosResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class SitiosResourceIntTest {

    private static final String DEFAULT_NOMBRE = "A";
    private static final String UPDATED_NOMBRE = "B";
    private static final String DEFAULT_DESCRIPCION = "A";
    private static final String UPDATED_DESCRIPCION = "B";
    private static final String DEFAULT_PAGINAWEB = "A";
    private static final String UPDATED_PAGINAWEB = "B";
    private static final String DEFAULT_MAIL = "A";
    private static final String UPDATED_MAIL = "B";
    private static final String DEFAULT_FACEBOOK = "A";
    private static final String UPDATED_FACEBOOK = "B";
    private static final String DEFAULT_TWITTER = "A";
    private static final String UPDATED_TWITTER = "B";
    private static final String DEFAULT_DIRECCION = "A";
    private static final String UPDATED_DIRECCION = "B";
    private static final String DEFAULT_TELEFONO = "AA";
    private static final String UPDATED_TELEFONO = "BB";
    private static final String DEFAULT_LATITUD = "A";
    private static final String UPDATED_LATITUD = "B";
    private static final String DEFAULT_LONGITUD = "A";
    private static final String UPDATED_LONGITUD = "B";

    @Inject
    private SitiosRepository sitiosRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSitiosMockMvc;

    private Sitios sitios;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SitiosResource sitiosResource = new SitiosResource();
        ReflectionTestUtils.setField(sitiosResource, "sitiosRepository", sitiosRepository);
        this.restSitiosMockMvc = MockMvcBuilders.standaloneSetup(sitiosResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        sitios = new Sitios();
        sitios.setNombre(DEFAULT_NOMBRE);
        sitios.setDescripcion(DEFAULT_DESCRIPCION);
        sitios.setPaginaweb(DEFAULT_PAGINAWEB);
        sitios.setMail(DEFAULT_MAIL);
        sitios.setFacebook(DEFAULT_FACEBOOK);
        sitios.setTwitter(DEFAULT_TWITTER);
        sitios.setDireccion(DEFAULT_DIRECCION);
        sitios.setTelefono(DEFAULT_TELEFONO);
        sitios.setLatitud(DEFAULT_LATITUD);
        sitios.setLongitud(DEFAULT_LONGITUD);
    }

    @Test
    @Transactional
    public void createSitios() throws Exception {
        int databaseSizeBeforeCreate = sitiosRepository.findAll().size();

        // Create the Sitios

        restSitiosMockMvc.perform(post("/api/sitioss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sitios)))
                .andExpect(status().isCreated());

        // Validate the Sitios in the database
        List<Sitios> sitioss = sitiosRepository.findAll();
        assertThat(sitioss).hasSize(databaseSizeBeforeCreate + 1);
        Sitios testSitios = sitioss.get(sitioss.size() - 1);
        assertThat(testSitios.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testSitios.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testSitios.getPaginaweb()).isEqualTo(DEFAULT_PAGINAWEB);
        assertThat(testSitios.getMail()).isEqualTo(DEFAULT_MAIL);
        assertThat(testSitios.getFacebook()).isEqualTo(DEFAULT_FACEBOOK);
        assertThat(testSitios.getTwitter()).isEqualTo(DEFAULT_TWITTER);
        assertThat(testSitios.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
        assertThat(testSitios.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testSitios.getLatitud()).isEqualTo(DEFAULT_LATITUD);
        assertThat(testSitios.getLongitud()).isEqualTo(DEFAULT_LONGITUD);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = sitiosRepository.findAll().size();
        // set the field null
        sitios.setNombre(null);

        // Create the Sitios, which fails.

        restSitiosMockMvc.perform(post("/api/sitioss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sitios)))
                .andExpect(status().isBadRequest());

        List<Sitios> sitioss = sitiosRepository.findAll();
        assertThat(sitioss).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = sitiosRepository.findAll().size();
        // set the field null
        sitios.setDescripcion(null);

        // Create the Sitios, which fails.

        restSitiosMockMvc.perform(post("/api/sitioss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sitios)))
                .andExpect(status().isBadRequest());

        List<Sitios> sitioss = sitiosRepository.findAll();
        assertThat(sitioss).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPaginawebIsRequired() throws Exception {
        int databaseSizeBeforeTest = sitiosRepository.findAll().size();
        // set the field null
        sitios.setPaginaweb(null);

        // Create the Sitios, which fails.

        restSitiosMockMvc.perform(post("/api/sitioss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sitios)))
                .andExpect(status().isBadRequest());

        List<Sitios> sitioss = sitiosRepository.findAll();
        assertThat(sitioss).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMailIsRequired() throws Exception {
        int databaseSizeBeforeTest = sitiosRepository.findAll().size();
        // set the field null
        sitios.setMail(null);

        // Create the Sitios, which fails.

        restSitiosMockMvc.perform(post("/api/sitioss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sitios)))
                .andExpect(status().isBadRequest());

        List<Sitios> sitioss = sitiosRepository.findAll();
        assertThat(sitioss).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFacebookIsRequired() throws Exception {
        int databaseSizeBeforeTest = sitiosRepository.findAll().size();
        // set the field null
        sitios.setFacebook(null);

        // Create the Sitios, which fails.

        restSitiosMockMvc.perform(post("/api/sitioss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sitios)))
                .andExpect(status().isBadRequest());

        List<Sitios> sitioss = sitiosRepository.findAll();
        assertThat(sitioss).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTwitterIsRequired() throws Exception {
        int databaseSizeBeforeTest = sitiosRepository.findAll().size();
        // set the field null
        sitios.setTwitter(null);

        // Create the Sitios, which fails.

        restSitiosMockMvc.perform(post("/api/sitioss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sitios)))
                .andExpect(status().isBadRequest());

        List<Sitios> sitioss = sitiosRepository.findAll();
        assertThat(sitioss).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDireccionIsRequired() throws Exception {
        int databaseSizeBeforeTest = sitiosRepository.findAll().size();
        // set the field null
        sitios.setDireccion(null);

        // Create the Sitios, which fails.

        restSitiosMockMvc.perform(post("/api/sitioss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sitios)))
                .andExpect(status().isBadRequest());

        List<Sitios> sitioss = sitiosRepository.findAll();
        assertThat(sitioss).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTelefonoIsRequired() throws Exception {
        int databaseSizeBeforeTest = sitiosRepository.findAll().size();
        // set the field null
        sitios.setTelefono(null);

        // Create the Sitios, which fails.

        restSitiosMockMvc.perform(post("/api/sitioss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sitios)))
                .andExpect(status().isBadRequest());

        List<Sitios> sitioss = sitiosRepository.findAll();
        assertThat(sitioss).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLatitudIsRequired() throws Exception {
        int databaseSizeBeforeTest = sitiosRepository.findAll().size();
        // set the field null
        sitios.setLatitud(null);

        // Create the Sitios, which fails.

        restSitiosMockMvc.perform(post("/api/sitioss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sitios)))
                .andExpect(status().isBadRequest());

        List<Sitios> sitioss = sitiosRepository.findAll();
        assertThat(sitioss).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLongitudIsRequired() throws Exception {
        int databaseSizeBeforeTest = sitiosRepository.findAll().size();
        // set the field null
        sitios.setLongitud(null);

        // Create the Sitios, which fails.

        restSitiosMockMvc.perform(post("/api/sitioss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sitios)))
                .andExpect(status().isBadRequest());

        List<Sitios> sitioss = sitiosRepository.findAll();
        assertThat(sitioss).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSitioss() throws Exception {
        // Initialize the database
        sitiosRepository.saveAndFlush(sitios);

        // Get all the sitioss
        restSitiosMockMvc.perform(get("/api/sitioss?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(sitios.getId().intValue())))
                .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
                .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
                .andExpect(jsonPath("$.[*].paginaweb").value(hasItem(DEFAULT_PAGINAWEB.toString())))
                .andExpect(jsonPath("$.[*].mail").value(hasItem(DEFAULT_MAIL.toString())))
                .andExpect(jsonPath("$.[*].facebook").value(hasItem(DEFAULT_FACEBOOK.toString())))
                .andExpect(jsonPath("$.[*].twitter").value(hasItem(DEFAULT_TWITTER.toString())))
                .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION.toString())))
                .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO.toString())))
                .andExpect(jsonPath("$.[*].latitud").value(hasItem(DEFAULT_LATITUD.toString())))
                .andExpect(jsonPath("$.[*].longitud").value(hasItem(DEFAULT_LONGITUD.toString())));
    }

    @Test
    @Transactional
    public void getSitios() throws Exception {
        // Initialize the database
        sitiosRepository.saveAndFlush(sitios);

        // Get the sitios
        restSitiosMockMvc.perform(get("/api/sitioss/{id}", sitios.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(sitios.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.paginaweb").value(DEFAULT_PAGINAWEB.toString()))
            .andExpect(jsonPath("$.mail").value(DEFAULT_MAIL.toString()))
            .andExpect(jsonPath("$.facebook").value(DEFAULT_FACEBOOK.toString()))
            .andExpect(jsonPath("$.twitter").value(DEFAULT_TWITTER.toString()))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION.toString()))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO.toString()))
            .andExpect(jsonPath("$.latitud").value(DEFAULT_LATITUD.toString()))
            .andExpect(jsonPath("$.longitud").value(DEFAULT_LONGITUD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSitios() throws Exception {
        // Get the sitios
        restSitiosMockMvc.perform(get("/api/sitioss/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSitios() throws Exception {
        // Initialize the database
        sitiosRepository.saveAndFlush(sitios);

		int databaseSizeBeforeUpdate = sitiosRepository.findAll().size();

        // Update the sitios
        sitios.setNombre(UPDATED_NOMBRE);
        sitios.setDescripcion(UPDATED_DESCRIPCION);
        sitios.setPaginaweb(UPDATED_PAGINAWEB);
        sitios.setMail(UPDATED_MAIL);
        sitios.setFacebook(UPDATED_FACEBOOK);
        sitios.setTwitter(UPDATED_TWITTER);
        sitios.setDireccion(UPDATED_DIRECCION);
        sitios.setTelefono(UPDATED_TELEFONO);
        sitios.setLatitud(UPDATED_LATITUD);
        sitios.setLongitud(UPDATED_LONGITUD);

        restSitiosMockMvc.perform(put("/api/sitioss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sitios)))
                .andExpect(status().isOk());

        // Validate the Sitios in the database
        List<Sitios> sitioss = sitiosRepository.findAll();
        assertThat(sitioss).hasSize(databaseSizeBeforeUpdate);
        Sitios testSitios = sitioss.get(sitioss.size() - 1);
        assertThat(testSitios.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testSitios.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testSitios.getPaginaweb()).isEqualTo(UPDATED_PAGINAWEB);
        assertThat(testSitios.getMail()).isEqualTo(UPDATED_MAIL);
        assertThat(testSitios.getFacebook()).isEqualTo(UPDATED_FACEBOOK);
        assertThat(testSitios.getTwitter()).isEqualTo(UPDATED_TWITTER);
        assertThat(testSitios.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testSitios.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testSitios.getLatitud()).isEqualTo(UPDATED_LATITUD);
        assertThat(testSitios.getLongitud()).isEqualTo(UPDATED_LONGITUD);
    }

    @Test
    @Transactional
    public void deleteSitios() throws Exception {
        // Initialize the database
        sitiosRepository.saveAndFlush(sitios);

		int databaseSizeBeforeDelete = sitiosRepository.findAll().size();

        // Get the sitios
        restSitiosMockMvc.perform(delete("/api/sitioss/{id}", sitios.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Sitios> sitioss = sitiosRepository.findAll();
        assertThat(sitioss).hasSize(databaseSizeBeforeDelete - 1);
    }
}
