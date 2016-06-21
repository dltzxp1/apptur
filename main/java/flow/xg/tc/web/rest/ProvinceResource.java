package flow.xg.tc.web.rest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import flow.xg.tc.domain.Province;
import flow.xg.tc.repository.ProvinceRepository;
import flow.xg.tc.web.rest.util.HeaderUtil;
import flow.xg.tc.web.rest.util.PaginationUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * REST controller for managing Province.
 */
@RestController
@RequestMapping("/api")
public class ProvinceResource {

	private final Logger log = LoggerFactory.getLogger(ProvinceResource.class);

	@Inject
	private ProvinceRepository provinceRepository;
	
	@Inject
	private ReportService reportService;

	/**
	 * POST /provinces -> Create a new province.
	 */
	@RequestMapping(value = "/provinces", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Province> createProvince(@Valid @RequestBody Province province) throws URISyntaxException {
		log.debug("REST request to save Province : {}", province);
		if (province.getId() != null) {
			return ResponseEntity.badRequest().headers(
					HeaderUtil.createFailureAlert("province", "idexists", "A new province cannot already have an ID"))
					.body(null);
		}
		Province result = provinceRepository.save(province);
		return ResponseEntity.created(new URI("/api/provinces/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert("province", result.getId().toString())).body(result);
	}

	/**
	 * PUT /provinces -> Updates an existing province.
	 */
	@RequestMapping(value = "/provinces", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Province> updateProvince(@Valid @RequestBody Province province) throws URISyntaxException {
		log.debug("REST request to update Province : {}", province);
		if (province.getId() == null) {
			return createProvince(province);
		}
		Province result = provinceRepository.save(province);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("province", province.getId().toString()))
				.body(result);
	}

	/**
	 * GET /provinces -> get all the provinces.
	 */
	@RequestMapping(value = "/provinces", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<List<Province>> getAllProvinces(Pageable pageable) throws URISyntaxException {
		log.debug("REST request to get a page of Provinces");
		Page<Province> page = provinceRepository.findAll(pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/provinces");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * GET /provinces/:id -> get the "id" province.
	 */
	@RequestMapping(value = "/provinces/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Province> getProvince(@PathVariable Long id) {
		log.debug("REST request to get Province : {}", id);
		Province province = provinceRepository.findOne(id);
		return Optional.ofNullable(province).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/**
	 * DELETE /provinces/:id -> delete the "id" province.
	 */
	@RequestMapping(value = "/provinces/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Void> deleteProvince(@PathVariable Long id) {
		log.debug("REST request to delete Province : {}", id);
		provinceRepository.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("province", id.toString())).build();
	}
	
	@RequestMapping(value="/provinces/caseLawyerReportI", method = RequestMethod.GET)
	@ResponseBody
	public void caseLawyerReportI(HttpServletResponse response) throws JRException, IOException {
		this.caseLawyerReport(response);
	}
	
	private void caseLawyerReport(HttpServletResponse response) throws JRException, IOException {
		InputStream jasperStream = this.getClass().getResourceAsStream("/reports/Blank_A4.jasper");
	    //Map<String,Object> params = new HashMap<>();
	    
	   // params.put("P_SUB_THIS", this.getClass().getClassLoader().getResource("reports/caseLawyer/sub_this.jasper").getPath());
	    
	    Map<String,Object> params = new HashMap<>();
	    //params.put("P_SUB_THIS", this.getClass().getClassLoader().getResource("reports/caseLawyer/sub_this.jasper").getPath());
	    
	    //params.put("P_IMG_SNI", this.getClass().getResourceAsStream("/reports/image/SNI.png"));
	    //params.put("P_IMG_CAITISA", this.getClass().getResourceAsStream("/reports/image/CAITISA.png"));
	    //params.put("P_IMG_SENPLADES", this.getClass().getResourceAsStream("/reports/image/SENPLADES.png"));
	    
	    
	    
	    JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream); 
	    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,params, new JRBeanCollectionDataSource(reportService.listProvincePDF()));
	    
	    //JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JRBeanCollectionDataSourc
	    response.setContentType("application/x-pdf");
	    response.setHeader("Content-disposition", "inline; filename=reporte_abogados_caso.pdf");
	    final OutputStream outStream = response.getOutputStream();
	    JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
	}
}
