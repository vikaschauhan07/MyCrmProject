package Crm.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;

import Crm.model.entity.MyUser;
import Crm.model.entity.Token;
import Crm.model.entity.payload.request.AuthenticationRequest;
import Crm.model.entity.payload.request.RegisterRequest;
import Crm.model.entity.payload.response.AuthenticationResponse;
import Crm.model.enumsandconstants.TokenType;
import Crm.repository.TokenRepository;
import Crm.repository.UserRepository;
import Crm.service.AuthenticationService;
import Crm.service.JwtService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
	private final UserRepository repository;
	private final TokenRepository tokenRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	@PersistenceContext
	private EntityManager entityManager;
	private final JdbcTemplate jdbcTemplate;
	
	@Override
	public boolean checkIfExists(String email){
		if(repository.findByEmail(email).isPresent()) {
			return true;
		}
		return false;
	}

	@Override
	@Transactional
//	@Async
	public AuthenticationResponse register(RegisterRequest request) {
		try {
				var user = MyUser
						.builder()
						.firstname(request.getFirstname())
						.lastname(request.getLastname())
						.email(request.getEmail())
						.password(passwordEncoder.encode(request.getPassword()))
						.role(request.getRole())
						.build();
				registerUser(user.getFirstname(),user.getEmail());
				var savedUser = repository.save(user);
				var jwtToken = jwtService.generateToken(user);
				var refreshToken = jwtService.generateRefreshToken(user);
				saveUserToken(savedUser, jwtToken);
				return new AuthenticationResponse(jwtToken, refreshToken, "Sucessfully registered.");
		}catch(Exception e) {
			return new AuthenticationResponse(e.toString());
		}
		
	}

	@Override
	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		var user = repository.findByEmail(request.getEmail()).orElseThrow();
		var jwtToken = jwtService.generateToken(user);
		var refreshToken = jwtService.generateRefreshToken(user);
		revokeAllUserTokens(user);
		saveUserToken(user, jwtToken);
		
		return new AuthenticationResponse(jwtToken,refreshToken,"Sucessfully Loged In.");
	}

	private void saveUserToken(MyUser user, String jwtToken) {
		var token = Token.builder()
				.user(user)
				.token(jwtToken)
				.tokenType(TokenType.BEARER)
				.expired(false)
				.revoked(false)
				.build();
		tokenRepository.save(token);
	}

	private void revokeAllUserTokens(MyUser user) {
		var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
		if (validUserTokens.isEmpty())
			return;
		validUserTokens.forEach(token -> {
			token.setExpired(true);
			token.setRevoked(true);
		});
		tokenRepository.saveAll(validUserTokens);
	}

	@Override
	public AuthenticationResponse refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
		final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		final String refreshToken;
		final String userEmail;
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			return new AuthenticationResponse("Invalid token");
		}
		refreshToken = authHeader.substring(7);
		userEmail = jwtService.extractUsername(refreshToken);
		if (userEmail != null) {
			var user = this.repository.findByEmail(userEmail).orElseThrow();
			if (jwtService.isTokenValid(refreshToken, user)) {
				var accessToken = jwtService.generateToken(user);
				revokeAllUserTokens(user);
				saveUserToken(user, accessToken);
				var authResponse = AuthenticationResponse
									.builder()
									.accessToken(accessToken)
									.refreshToken(refreshToken)
									.message("New Tokens.")
									.build();
//				new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
				return authResponse;
			}
		}
		return new AuthenticationResponse("Inavalid User.");
	}
	
	
//	@Async
	 private void registerUser(String domain, String username) {
	        // Check if the domain or username is already taken

//	        if (repository.findByDomain(domain).isPresent() || userRepository.findByUsername(username).isPresent()) {
//	            throw new IllegalArgumentException("Domain or username already exists");
//	        }

	        // Create a new user
//	        User user = new User();
//	        user.setDomain(domain);
//	        user.setUsername(username);

	        // Save the user to the main database
//	        userRepository.save(user);

	        // Create a separate database for the user
	        createDatabaseForUser(domain);

	        // Migrate the user's database schema
//	        migrateDatabaseForUser(domain);

	        // Insert the user into the user table within the user's database
//	        insertUserIntoDatabase(user, domain);
	    }
//	 @Transactional
	    private void createDatabaseForUser(String domain) {
	        // Implement the logic to create a separate database for the user
	        // For example, if you are using MySQL, you might execute a SQL statement
	        // to create a new database with a name like 'domain_db'
	        String createDatabaseSql = "CREATE SCHEMA " + domain + "_db";
	        entityManager.createNativeQuery(createDatabaseSql).executeUpdate();
	        
//	        String createDatabaseSql = "CREATE DATABASE " + domain + "_db";
//
//	        // Execute the SQL statement without being wrapped in a transaction
//	        jdbcTemplate.execute(createDatabaseSql);
	    }

//	   private void migrateDatabaseForUser(String domain) {
//	    try {
//	        // Obtain the database connection from EntityManager
//	        Connection connection = entityManager.unwrap(Connection.class);
//	        DatabaseConnection databaseConnection = new JdbcConnection(connection);
//
//	        // Create a Liquibase instance
//	        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(databaseConnection);
//	        Liquibase liquibase = new Liquibase("classpath:db/changelog/db-changelog.xml", new ClassLoaderResourceAccessor(), database);
//
//	        // Set the Liquibase contexts and labels
//	        Contexts contexts = new Contexts("context1");
//	        Labels labels = new Labels("label1");
//
//	        // Perform the database migration
//	        liquibase.update(contexts, labels);
//
//	        System.out.println("Successfully migrated database for user: " + domain);
//	    } catch (Exception e) {
//	        // Handle exceptions
//	        e.printStackTrace();
//	    }
//	}

//	    private void insertUserIntoDatabase(User user, String domain) {
//	        // Insert the user into the user table within the user's database
//	        String insertUserSql = "INSERT INTO user (username, domain) VALUES (?, ?)";
//	        entityManager.createNativeQuery(insertUserSql)
//	                .setParameter(1, user.getUsername())
//	                .setParameter(2, domain)
//	                .executeUpdate();
//	    }

	
}