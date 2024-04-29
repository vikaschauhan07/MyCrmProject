package Crm.service;

import Crm.model.entity.MyEmployees;
import java.util.*;
public interface MyEmployeeService {
	public String addEmployee(MyEmployees myEmployee);
	public List<MyEmployees> getAllEmployess();
}
