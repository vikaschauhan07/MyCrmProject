package Crm.service;

import java.util.List;
import java.util.Optional;

import Crm.model.entity.Vikas;

public interface VikasService {

	public List<Vikas> getVikas();
	public String saveVikas(List<Vikas> vikas);
}
