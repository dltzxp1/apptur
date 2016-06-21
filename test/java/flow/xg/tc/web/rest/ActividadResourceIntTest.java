package flow.xg.tc.web.rest;

import flow.xg.tc.Application;
import flow.xg.tc.domain.Actividad;
import flow.xg.tc.repository.ActividadRepository;

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
import org.springframework.util.Base64Utils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the ActividadResource REST controller.
 *
 * @see ActividadResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ActividadResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AA";
    private static final String UPDATED_NOMBRE = "BB";
    private static final String DEFAULT_DESCRIPCION = "AA";
    private static final String UPDATED_DESCRIPCION = "BB";
    private static final String DEFAULT_DIRECCION = "AA";
    private static final String UPDATED_DIRECCION = "BB";

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final byte[] DEFAULT_FOTO = TestUtil.createByteArray(100, "0");
    private static final byte[] UPDATED_FOTO = TestUtil.createByteArray(100000, "1");
    private static final String DEFAULT_FOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FOTO_CONTENT_TYPE = "image/png";

    @Inject
    private ActividadRepository actividadRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restActividadMockMvc;

    private Actividad actividad;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ActividadResource actividadResource = new ActividadResource();
        ReflectionTestUtils.setField(actividadResource, "actividadRepository", actividadRepository);
        this.restActividadMockMvc = MockMvcBuilders.standaloneSetup(actividadResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        actividad = new Actividad();
        actividad.setNombre(DEFAULT_NOMBRE);
        actividad.setDescripcion(DEFAULT_DESCRIPCION);
        actividad.setDireccion(DEFAULT_DIRECCION);
        actividad.setFecha(DEFAULT_FECHA);
        actividad.setFoto(DEFAULT_FOTO);
        actividad.setFotoContentType(DEFAULT_FOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createActividad() throws Exception {
        int databaseSizeBeforeCreate = actividadRepository.findAll().size();

        // Create the Actividad

        restActividadMockMvc.perform(post("/api/actividads")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(actividad)))
                .andExpect(status().isCreated());

        // Validate the Actividad in the database
        List<Actividad> actividads = actividadRepository.findAll();
        assertThat(actividads).hasSize(databaseSizeBeforeCreate + 1);
        Actividad testActividad = actividads.get(actividads.size() - 1);
        assertThat(testActividad.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testActividad.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testActividad.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
        assertThat(testActividad.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testActividad.getFoto()).isEqualTo(DEFAULT_FOTO);
        assertThat(testActividad.getFotoContentType()).isEqualTo(DEFAULT_FOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = actividadRepository.findAll().size();
        // set the field null
        actividad.setNombre(null);

        // Create the Actividad, which fails.

        restActividadMockMvc.perform(post("/api/actividads")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(actividad)))
                .andExpect(status().isBadRequest());

        List<Actividad> actividads = actividadRepository.findAll();
        assertThat(actividads).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = actividadRepository.findAll().size();
        // set the field null
        actividad.setDescripcion(null);

        // Create the Actividad, which fails.

        restActividadMockMvc.perform(post("/api/actividads")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(actividad)))
                .andExpect(status().isBadRequest());

        List<Actividad> actividads = actividadRepository.findAll();
        assertThat(actividads).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDireccionIsRequired() throws Exception {
        int databaseSizeBeforeTest = actividadRepository.findAll().size();
        // set the field null
        actividad.setDireccion(null);

        // Create the Actividad, which fails.

        restActividadMockMvc.perform(post("/api/actividads")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(actividad)))
                .andExpect(status().isBadRequest());

        List<Actividad> actividads = actividadRepository.findAll();
        assertThat(actividads).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = actividadRepository.findAll().size();
        // set the field null
        actividad.setFecha(null);

        // Create the Actividad, which fails.

        restActividadMockMvc.perform(post("/api/actividads")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(actividad)))
                .andExpect(status().isBadRequest());

        List<Actividad> actividads = actividadRepository.findAll();
        assertThat(actividads).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFotoIsRequired() throws Exception {
        int databaseSizeBeforeTest = actividadRepository.findAll().size();
        // set the field null
        actividad.setFoto(null);

        // Create the Actividad, which fails.

        restActividadMockMvc.perform(post("/api/actividads")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(actividad)))
                .andExpect(status().isBadRequest());

        List<Actividad> actividads = actividadRepository.findAll();
        assertThat(actividads).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllActividads() throws Exception {
        // Initialize the database
        actividadRepository.saveAndFlush(actividad);

        // Get all the actividads
        restActividadMockMvc.perform(get("/api/actividads?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(actividad.getId().intValue())))
                .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
                .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
                .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION.toString())))
                .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
                .andExpect(jsonPath("$.[*].fotoContentType").value(hasItem(DEFAULT_FOTO_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].foto").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTO))));
    }

    @Test
    @Transactional
    public void getActividad() throws Exception {
        // Initialize the database
        actividadRepository.saveAndFlush(actividad);

        // Get the actividad
        restActividadMockMvc.perform(get("/api/actividads/{id}", actividad.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(actividad.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION.toString()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.fotoContentType").value(DEFAULT_FOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.foto").value(Base64Utils.encodeToString(DEFAULT_FOTO)));
    }

    @Test
    @Transactional
    public void getNonExistingActividad() throws Exception {
        // Get the actividad
        restActividadMockMvc.perform(get("/api/actividads/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateActividad() throws Exception {
        // Initialize the database
        actividadRepository.saveAndFlush(actividad);

		int databaseSizeBeforeUpdate = actividadRepository.findAll().size();

        // Update the actividad
        actividad.setNombre(UPDATED_NOMBRE);
        actividad.setDescripcion(UPDATED_DESCRIPCION);
        actividad.setDireccion(UPDATED_DIRECCION);
        actividad.setFecha(UPDATED_FECHA);
        actividad.setFoto(UPDATED_FOTO);
        actividad.setFotoContentType(UPDATED_FOTO_CONTENT_TYPE);

        restActividadMockMvc.perform(put("/api/actividads")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(actividad)))
                .andExpect(status().isOk());

        // Validate the Actividad in the database
        List<Actividad> actividads = actividadRepository.findAll();
        assertThat(actividads).hasSize(databaseSizeBeforeUpdate);
        Actividad testActividad = actividads.get(actividads.size() - 1);
        assertThat(testActividad.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testActividad.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testActividad.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testActividad.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testActividad.getFoto()).isEqualTo(UPDATED_FOTO);
        assertThat(testActividad.getFotoContentType()).isEqualTo(UPDATED_FOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void deleteActividad() throws Exception {
        // Initialize the database
        actividadRepository.saveAndFlush(actividad);

		int databaseSizeBeforeDelete = actividadRepository.findAll().size();

        // Get the actividad
        restActividadMockMvc.perform(delete("/api/actividads/{id}", actividad.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Actividad> actividads = actividadRepository.findAll();
        assertThat(actividads).hasSize(databaseSizeBeforeDelete - 1);
    }
}
