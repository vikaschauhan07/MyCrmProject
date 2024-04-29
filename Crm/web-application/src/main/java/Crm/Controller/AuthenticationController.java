package Crm.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Crm.model.entity.payload.request.AuthenticationRequest;
import Crm.model.entity.payload.request.RegisterRequest;
import Crm.model.entity.payload.response.AuthenticationResponse;
import Crm.service.AuthenticationService;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

	private final AuthenticationService service;

	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
		if (!service.checkIfExists(request.getEmail())) {
            
			try {
			
				AuthenticationResponse response = service.register(request);
				return ResponseEntity
						.status(HttpStatus.OK)
						.body(response);
			} catch(Exception e) {
				return ResponseEntity
						.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                	.body(new AuthenticationResponse("Error occurred during registration"));
			}
		}
		return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new AuthenticationResponse("User with this email already exists"));
    
	}

	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
		return ResponseEntity.ok(service.authenticate(request));
	}

	@PostMapping("/refresh-token")
	public ResponseEntity<AuthenticationResponse> refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.refreshToken(request, response));
	}

}
