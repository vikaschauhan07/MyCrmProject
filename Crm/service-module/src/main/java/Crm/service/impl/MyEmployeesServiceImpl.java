package Crm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import Crm.model.entity.MyEmployees;
import Crm.model.entity.payload.request.RegisterRequest;
import Crm.repository.MyEmployeesRepository;
import Crm.service.MyEmployeeService;
import jakarta.transaction.Transactional;

@Service
public class MyEmployeesServiceImpl implements MyEmployeeService{

	@Autowired
	private MyEmployeesRepository myEmployeesRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	@Transactional
	public String addEmployee(MyEmployees myEmployee) {
	    if (myEmployee == null) {
	        return "Employee object is null";
	    }

	    try {
	    	
	    	MyEmployees savedEmployee = myEmployeesRepository.save(myEmployee);
	       
	    	if (savedEmployee != null) {
	            return "Employee Added Successfully";
	        } else {
	            return "Failed to add employee: Save operation returned null";
	        }
	    } catch (Exception e) {
	        e.printStackTrace(); 
	        return "Failed to add employee: " + e.getMessage();
	    }
	}
	@Override
	@Transactional
	public List<MyEmployees> getAllEmployess() {
		try {
			List<MyEmployees> li = myEmployeesRepository.findAll();
			return myEmployeesRepository.findAll();
		} catch (Exception e) {
			return null;
		}
	}

}
