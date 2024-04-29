package Crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Crm.model.entity.MyUser;

import java.util.Optional;

public interface UserRepository extends JpaRepository<MyUser, Long> {
	 Optional<MyUser> findByEmail(String email);
//	 MyUser findByName(String name);
}