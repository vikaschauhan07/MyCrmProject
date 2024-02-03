# MyCrmProject
my custom gmail
gsdfgdfgfasdfas@gmail.com

Imp Links:-
https://stackoverflow.com/questions/975384/eclipse-error-failed-to-connect-to-remote-vm

(https://github.com/Arun-Sudhakaran/hh)https://github.com/Arun-Sudhakaran/hh

https://www.javatpoint.com/spring-boot-multi-module-project


	
//	 public void registerUser(String domain, String username) {
//	        // Check if the domain or username is already taken
//	        if (userRepository.findByDomain(domain).isPresent() || userRepository.findByUsername(username).isPresent()) {
//	            throw new IllegalArgumentException("Domain or username already exists");
//	        }
//
//	        // Create a new user
//	        User user = new User();
//	        user.setDomain(domain);
//	        user.setUsername(username);
//
//	        // Save the user to the main database
//	        userRepository.save(user);
//
//	        // Create a separate database for the user
//	        createDatabaseForUser(domain);
//
//	        // Migrate the user's database schema
//	        migrateDatabaseForUser(domain);
//
//	        // Insert the user into the user table within the user's database
//	        insertUserIntoDatabase(user, domain);
//	    }
//
//	    private void createDatabaseForUser(String domain) {
//	        // Implement the logic to create a separate database for the user
//	        // For example, if you are using MySQL, you might execute a SQL statement
//	        // to create a new database with a name like 'domain_db'
//	        String createDatabaseSql = "CREATE DATABASE IF NOT EXISTS " + domain + "_db";
//	        entityManager.createNativeQuery(createDatabaseSql).executeUpdate();
//	    }
//
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
//
//	    private void insertUserIntoDatabase(User user, String domain) {
//	        // Insert the user into the user table within the user's database
//	        String insertUserSql = "INSERT INTO user (username, domain) VALUES (?, ?)";
//	        entityManager.createNativeQuery(insertUserSql)
//	                .setParameter(1, user.getUsername())
//	                .setParameter(2, domain)
//	                .executeUpdate();
//	    }

---------------------------------------------------------------------------------------------------
User registers with a custom domain.
Application creates a separate database and stores user details.
During login, subdomain-based authentication ensures the user is authenticated based on the subdomain.
Controllers use subdomain information for dynamic content retrieval from the subdomain-specific database.
DNS configuration redirects subdomains to the application, completing the end-to-end process.


#spring.datasource.url=jdbc:mysql://localhost:3306/java
#spring.datasource.username=root
#spring.datasource.password=Mrdhara@123
#spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
#
#spring.jpa.hibernate.ddl-auto=update
#spring.jpa.show-sql=false
#spring.jpa.properties.hibernate.format_sql=true
#spring.jpa.database=mysql
#spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect

spring.datasource.url=jdbc:postgresql://localhost:5432/mycrm
spring.datasource.username=postgres
spring.datasource.password=Welcome@123
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.database=postgresql
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect


application.security.jwt.secret-key=404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970
application.security.jwt.expiration=86400000
application.security.jwt.refresh-token.expiration=604800000

--------------------------------------------------------------------------------------------------------
Shared Database and Separate Schema
In this approach, each tenant’s data is kept in a distinct schema on a shared database. This is sometimes called Schema per Tenant:separate schema



