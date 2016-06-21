package flow.xg.tc.web.rest;

import flow.xg.tc.Application;
import flow.xg.tc.domain.Servicios;
import flow.xg.tc.repository.ServiciosRepository;

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
 * Test class for the ServiciosResource REST controller.
 *
 * @see ServiciosResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ServiciosResourceIntTest {

    private static final String DEFAULT_CODIGO = "AA";
    private static final String UPDATED_CODIGO = "BB";

    @Inject
    private ServiciosRepository serviciosRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restServiciosMockMvc;

    private Servicios servicios;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ServiciosResource serviciosResource = new ServiciosResource();
        ReflectionTestUtils.setField(serviciosResource, "serviciosRepository", serviciosRepository);
        this.restServiciosMockMvc = MockMvcBuilders.standaloneSetup(serviciosResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        servicios = new Servicios();
        servicios.setCodigo(DEFAULT_CODIGO);
    }

    @Test
    @Transactional
    public void createServicios() throws Exception {
        int databaseSizeBeforeCreate = serviciosRepository.findAll().size();

        // Create the Servicios

        restServiciosMockMvc.perform(post("/api/servicioss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(servicios)))
                .andExpect(status().isCreated());

        // Validate the Servicios in the database
        List<Servicios> servicioss = serviciosRepository.findAll();
        assertThat(servicioss).hasSize(databaseSizeBeforeCreate + 1);
        Servicios testServicios = servicioss.get(servicioss.size() - 1);
        assertThat(testServicios.getCodigo()).isEqualTo(DEFAULT_CODIGO);
    }

    @Test
    @Transactional
    public void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviciosRepository.findAll().size();
        // set the field null
        servicios.setCodigo(null);

        // Create the Servicios, which fails.

        restServiciosMockMvc.perform(post("/api/servicioss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(servicios)))
                .andExpect(status().isBadRequest());

        List<Servicios> servicioss = serviciosRepository.findAll();
        assertThat(servicioss).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllServicioss() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the servicioss
        restServiciosMockMvc.perform(get("/api/servicioss?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(servicios.getId().intValue())))
                .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO.toString())));
    }

    @Test
    @Transactional
    public void getServicios() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get the servicios
        restServiciosMockMvc.perform(get("/api/servicioss/{id}", servicios.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(servicios.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingServicios() throws Exception {
        // Get the servicios
        restServiciosMockMvc.perform(get("/api/servicioss/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServicios() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

		int databaseSizeBeforeUpdate = serviciosRepository.findAll().size();

        // Update the servicios
        servicios.setCodigo(UPDATED_CODIGO);

        restServiciosMockMvc.perform(put("/api/servicioss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(servicios)))
                .andExpect(status().isOk());

        // Validate the Servicios in the database
        List<Servicios> servicioss = serviciosRepository.findAll();
        assertThat(servicioss).hasSize(databaseSizeBeforeUpdate);
        Servicios testServicios = servicioss.get(servicioss.size() - 1);
        assertThat(testServicios.getCodigo()).isEqualTo(UPDATED_CODIGO);
    }

    @Test
    @Transactional
    public void deleteServicios() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

		int databaseSizeBeforeDelete = serviciosRepository.findAll().size();

        // Get the servicios
        restServiciosMockMvc.perform(delete("/api/servicioss/{id}", servicios.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Servicios> servicioss = serviciosRepository.findAll();
        assertThat(servicioss).hasSize(databaseSizeBeforeDelete - 1);
    }
}
