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
