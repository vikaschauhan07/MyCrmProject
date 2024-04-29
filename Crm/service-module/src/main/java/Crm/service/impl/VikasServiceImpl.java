package Crm.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Crm.model.entity.Vikas;
import Crm.repository.VikasRepository;
import Crm.service.VikasService;

@Service
public class VikasServiceImpl implements VikasService{

	@Autowired
	private VikasRepository vikasRepository;
	
	
	@Override
	public List<Vikas> getVikas() {
		return vikasRepository.findAll();
	}

	@Override
	public String saveVikas(List<Vikas> vikas) {
		
		vikasRepository.saveAll(vikas);
		return "Sucess";
	}

}
