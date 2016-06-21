package flow.xg.tc.web.rest;

import flow.xg.tc.Application;
import flow.xg.tc.domain.Province;
import flow.xg.tc.repository.ProvinceRepository;

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
 * Test class for the ProvinceResource REST controller.
 *
 * @see ProvinceResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ProvinceResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AA";
    private static final String UPDATED_NOMBRE = "BB";
    private static final String DEFAULT_DESCRIPCION = "AA";
    private static final String UPDATED_DESCRIPCION = "BB";
    private static final String DEFAULT_CAPITAL = "AA";
    private static final String UPDATED_CAPITAL = "BB";

    private static final Integer DEFAULT_POBLACION = 1;
    private static final Integer UPDATED_POBLACION = 2;
    private static final String DEFAULT_REGION = "AA";
    private static final String UPDATED_REGION = "BB";

    @Inject
    private ProvinceRepository provinceRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restProvinceMockMvc;

    private Province province;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProvinceResource provinceResource = new ProvinceResource();
        ReflectionTestUtils.setField(provinceResource, "provinceRepository", provinceRepository);
        this.restProvinceMockMvc = MockMvcBuilders.standaloneSetup(provinceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        province = new Province();
        province.setNombre(DEFAULT_NOMBRE);
        province.setDescripcion(DEFAULT_DESCRIPCION);
        province.setCapital(DEFAULT_CAPITAL);
        province.setPoblacion(DEFAULT_POBLACION);
        province.setRegion(DEFAULT_REGION);
    }

    @Test
    @Transactional
    public void createProvince() throws Exception {
        int databaseSizeBeforeCreate = provinceRepository.findAll().size();

        // Create the Province

        restProvinceMockMvc.perform(post("/api/provinces")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(province)))
                .andExpect(status().isCreated());

        // Validate the Province in the database
        List<Province> provinces = provinceRepository.findAll();
        assertThat(provinces).hasSize(databaseSizeBeforeCreate + 1);
        Province testProvince = provinces.get(provinces.size() - 1);
        assertThat(testProvince.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testProvince.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testProvince.getCapital()).isEqualTo(DEFAULT_CAPITAL);
        assertThat(testProvince.getPoblacion()).isEqualTo(DEFAULT_POBLACION);
        assertThat(testProvince.getRegion()).isEqualTo(DEFAULT_REGION);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = provinceRepository.findAll().size();
        // set the field null
        province.setNombre(null);

        // Create the Province, which fails.

        restProvinceMockMvc.perform(post("/api/provinces")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(province)))
                .andExpect(status().isBadRequest());

        List<Province> provinces = provinceRepository.findAll();
        assertThat(provinces).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = provinceRepository.findAll().size();
        // set the field null
        province.setDescripcion(null);

        // Create the Province, which fails.

        restProvinceMockMvc.perform(post("/api/provinces")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(province)))
                .andExpect(status().isBadRequest());

        List<Province> provinces = provinceRepository.findAll();
        assertThat(provinces).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCapitalIsRequired() throws Exception {
        int databaseSizeBeforeTest = provinceRepository.findAll().size();
        // set the field null
        province.setCapital(null);

        // Create the Province, which fails.

        restProvinceMockMvc.perform(post("/api/provinces")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(province)))
                .andExpect(status().isBadRequest());

        List<Province> provinces = provinceRepository.findAll();
        assertThat(provinces).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPoblacionIsRequired() throws Exception {
        int databaseSizeBeforeTest = provinceRepository.findAll().size();
        // set the field null
        province.setPoblacion(null);

        // Create the Province, which fails.

        restProvinceMockMvc.perform(post("/api/provinces")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(province)))
                .andExpect(status().isBadRequest());

        List<Province> provinces = provinceRepository.findAll();
        assertThat(provinces).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRegionIsRequired() throws Exception {
        int databaseSizeBeforeTest = provinceRepository.findAll().size();
        // set the field null
        province.setRegion(null);

        // Create the Province, which fails.

        restProvinceMockMvc.perform(post("/api/provinces")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(province)))
                .andExpect(status().isBadRequest());

        List<Province> provinces = provinceRepository.findAll();
        assertThat(provinces).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProvinces() throws Exception {
        // Initialize the database
        provinceRepository.saveAndFlush(province);

        // Get all the provinces
        restProvinceMockMvc.perform(get("/api/provinces?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(province.getId().intValue())))
                .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
                .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
                .andExpect(jsonPath("$.[*].capital").value(hasItem(DEFAULT_CAPITAL.toString())))
                .andExpect(jsonPath("$.[*].poblacion").value(hasItem(DEFAULT_POBLACION)))
                .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION.toString())));
    }

    @Test
    @Transactional
    public void getProvince() throws Exception {
        // Initialize the database
        provinceRepository.saveAndFlush(province);

        // Get the province
        restProvinceMockMvc.perform(get("/api/provinces/{id}", province.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(province.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.capital").value(DEFAULT_CAPITAL.toString()))
            .andExpect(jsonPath("$.poblacion").value(DEFAULT_POBLACION))
            .andExpect(jsonPath("$.region").value(DEFAULT_REGION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProvince() throws Exception {
        // Get the province
        restProvinceMockMvc.perform(get("/api/provinces/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProvince() throws Exception {
        // Initialize the database
        provinceRepository.saveAndFlush(province);

		int databaseSizeBeforeUpdate = provinceRepository.findAll().size();

        // Update the province
        province.setNombre(UPDATED_NOMBRE);
        province.setDescripcion(UPDATED_DESCRIPCION);
        province.setCapital(UPDATED_CAPITAL);
        province.setPoblacion(UPDATED_POBLACION);
        province.setRegion(UPDATED_REGION);

        restProvinceMockMvc.perform(put("/api/provinces")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(province)))
                .andExpect(status().isOk());

        // Validate the Province in the database
        List<Province> provinces = provinceRepository.findAll();
        assertThat(provinces).hasSize(databaseSizeBeforeUpdate);
        Province testProvince = provinces.get(provinces.size() - 1);
        assertThat(testProvince.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testProvince.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testProvince.getCapital()).isEqualTo(UPDATED_CAPITAL);
        assertThat(testProvince.getPoblacion()).isEqualTo(UPDATED_POBLACION);
        assertThat(testProvince.getRegion()).isEqualTo(UPDATED_REGION);
    }

    @Test
    @Transactional
    public void deleteProvince() throws Exception {
        // Initialize the database
        provinceRepository.saveAndFlush(province);

		int databaseSizeBeforeDelete = provinceRepository.findAll().size();

        // Get the province
        restProvinceMockMvc.perform(delete("/api/provinces/{id}", province.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Province> provinces = provinceRepository.findAll();
        assertThat(provinces).hasSize(databaseSizeBeforeDelete - 1);
    }
}
