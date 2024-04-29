package Crm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import Crm.model.entity.MyEmployees;


public interface MyEmployeesRepository extends JpaRepository<MyEmployees, Long> {
//	 Optional<MyEmployees> findByEmail(String email);/
}