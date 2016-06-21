package flow.xg.tc.web.rest;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import flow.xg.tc.domain.Province;
import flow.xg.tc.repository.ProvinceRepository;

@Service
public class ReportService {

	@Inject
	private ProvinceRepository provinceRepository;

	@Transactional
	public List<ProvinceDTO> listProvincePDF() {
		List<ProvinceDTO> listDTO = new ArrayList<>();
		for (Province listpro : provinceRepository.findAll()) {
			ProvinceDTO objDTO = new ProvinceDTO();
			objDTO.setNombre(listpro.getNombre());
			objDTO.setDescripcion(listpro.getDescripcion());
			listDTO.add(objDTO);
		}
		return listDTO;
	}
}
