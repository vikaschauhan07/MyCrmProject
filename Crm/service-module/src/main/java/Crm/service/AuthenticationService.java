package Crm.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;

import Crm.model.entity.MyUser;
import Crm.model.entity.payload.request.AuthenticationRequest;
import Crm.model.entity.payload.request.RegisterRequest;
import Crm.model.entity.payload.response.AuthenticationResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthenticationService {
	public AuthenticationResponse register(RegisterRequest request);

	public AuthenticationResponse authenticate(AuthenticationRequest request);

	public AuthenticationResponse refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
	
	public boolean checkIfExists(String email);
//	public String generateRefreshToken(UserDetails userDetails);
}