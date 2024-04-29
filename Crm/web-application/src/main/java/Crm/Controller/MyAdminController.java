package Crm.Controller;

import java.util.Enumeration;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Crm.model.entity.MyEmployees;
import Crm.model.entity.MyUser;
import Crm.model.entity.payload.request.RegisterRequest;
import Crm.model.entity.payload.response.AuthenticationResponse;
import Crm.repository.UserRepository;
import Crm.service.AuthenticationService;
import Crm.service.MyEmployeeService;
import Crm.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import java.util.*;
@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
public class MyAdminController {

	@Autowired
	private MyEmployeeService myEmployeeService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	

	@PostMapping("/add-employee")
	@PreAuthorize("hasAuthority('admin:create')")
	public ResponseEntity<String> addEmployee(HttpServletRequest httpRequest,
			@RequestHeader(name = "Authorization") String token, @Valid @RequestBody RegisterRequest request) {
		if(request == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Request Can't  Be Null.");
		}
		
		
		if(!userRepository.findByEmail(request.getEmail()).isPresent()) {

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String username = authentication.getName();

			Optional<MyUser> userOptional = userRepository.findByEmail(username);
			Long createdByUserId = 0L;

			if (userOptional.isPresent()) {
				createdByUserId = userOptional.get().getId();
			}

			var myemployee = MyEmployees
								.builder()
								.firstname(request.getFirstname())
								.lastname(request.getLastname())
								.email(request.getEmail())
								.password(passwordEncoder.encode(request.getPassword()))
								.role(request.getRole()).createdBy(createdByUserId).build();

			try {
				 
				return ResponseEntity.ok(myEmployeeService.addEmployee(myemployee));
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body("Can't Add Server Error");
			}
		} 
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body("User with this email already exists");
		
	}
	
	@GetMapping("/get-employee")
	public ResponseEntity<List<MyEmployees>> getAllEmployees(){
		try {
			List<MyEmployees> allEmployees = myEmployeeService.getAllEmployess();
			if(!allEmployees.isEmpty()) {
				return ResponseEntity.status(HttpStatus.OK).body(allEmployees);
			} 
			return ResponseEntity.status(HttpStatus.OK).body(null);
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

}
