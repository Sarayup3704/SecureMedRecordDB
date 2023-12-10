import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import com.mysql.cj.jdbc.DatabaseMetaData;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class JavaConnection {
	

    public static String hashString(String inputString) {
        try {
            java.security.MessageDigest digest = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(inputString.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void DBdesign() {
        Connection con1 = null;
        Connection con = null;
        String dbname = "security";
        try {
            // Create a connection
            con1 = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/", "manager", "Chandu@30");
            
            if (!databaseExists(con1, dbname)) {
                createDatabase(con1, dbname);
                con1.close();
                
                
                try {
                	con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/"+dbname, "manager", "Chandu@30");
                } catch(Exception e) {
                	System.out.println(e);
                }
                
                
                // Create Customer Table
                String createTableQuery = "CREATE TABLE IF NOT EXISTS healthcare ("
                        + "id INT AUTO_INCREMENT PRIMARY KEY,"
                        + "first_name VARCHAR(255) NOT NULL,"
                        + "last_name VARCHAR(255) NOT NULL,"
                        + "gender VARCHAR(30) NOT NULL,"
                        + "age INT NOT NULL,"
                        + "weight DOUBLE NOT NULL,"
                        + "height DOUBLE NOT NULL,"
                        + "health_history VARCHAR(255) NOT NULL"
                        + ")";
                try (PreparedStatement createTableStatement = con.prepareStatement(createTableQuery)) {
                    createTableStatement.executeUpdate();
                }
                
                String createusertable = "CREATE TABLE IF NOT EXISTS Userinfo ("
                        + "username VARCHAR(255) NOT NULL PRIMARY KEY,"
                        + "password VARCHAR(255) NOT NULL,"
                        + "groupname VARCHAR(255) NOT NULL"
                        + ")";
                try (PreparedStatement createuserTableStatement = con.prepareStatement(createusertable)) {
                    createuserTableStatement.executeUpdate();
                }
                
                String createHashTableQuery = "CREATE TABLE IF NOT EXISTS healthcare_hash ("
                        + "id INT AUTO_INCREMENT PRIMARY KEY,"
                        + "first_name VARCHAR(255) NOT NULL,"
                        + "last_name VARCHAR(255) NOT NULL,"
                        + "gender VARCHAR(255) NOT NULL,"
                        + "age VARCHAR(255) NOT NULL,"
                        + "weight VARCHAR(255) NOT NULL,"
                        + "height VARCHAR(255) NOT NULL,"
                        + "health_history VARCHAR(255) NOT NULL"
                        + ")";
                try (PreparedStatement createHashTableStatement = con.prepareStatement(createHashTableQuery)) {
                    createHashTableStatement.executeUpdate();
                }
                
                String insertQuery = "INSERT INTO healthcare (first_name, last_name, gender, age, weight, height, health_history) VALUES (?, ?, ?, ?, ?, ?, ?)";
                String insertHashQuery = "INSERT INTO healthcare_hash (first_name, last_name, gender, age, weight, height, health_history) VALUES (?, ?, ?, ?, ?, ?, ?)";

                Random random = new Random();
                
                String csvFile = "data/health_care_data.csv";  

                try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
                    String line;
                    if ((line = br.readLine()) != null) {
                        String[] headers = line.split(",");
                    }
                    
                    while ((line = br.readLine()) != null) {
                        String[] values = line.split(",");
                        String firstName = values[0].trim();
                        String lastName = values[1].trim();
                        String gender = values[2].trim();
                        int age = Integer.parseInt(values[3].trim());
                        double weight = Double.parseDouble(values[4].trim());
                        double height = Double.parseDouble(values[5].trim());
                        String healthHistory = values[6].trim();
                        
                        
                        String hashFirstName = hashString(firstName);
                        String hashLastName = hashString(lastName);
                        String hashGender = hashString(gender);
                        String hashAge = hashString(Integer.toString(age));
                        String hashWeight = hashString(Double.toString(weight));
                        String hashHeight = hashString(Double.toString(height));
                        String hashHealthHistory = hashString(healthHistory);
                        
                        try (PreparedStatement insertStatement = con.prepareStatement(insertQuery)) {
                            insertStatement.setString(1, firstName);
                            insertStatement.setString(2, lastName);
                            insertStatement.setString(3, gender);
                            insertStatement.setInt(4, age);
                            insertStatement.setDouble(5, weight);
                            insertStatement.setDouble(6, height);
                            insertStatement.setString(7, healthHistory);
                            insertStatement.executeUpdate();
                        }

                        try (PreparedStatement insertHashStatement = con.prepareStatement(insertHashQuery)) {
                            insertHashStatement.setString(1, hashFirstName);
                            insertHashStatement.setString(2, hashLastName);
                            insertHashStatement.setString(3, hashGender);
                            insertHashStatement.setString(4, hashAge);
                            insertHashStatement.setString(5, hashWeight);
                            insertHashStatement.setString(6, hashHeight);
                            insertHashStatement.setString(7, hashHealthHistory);
                            insertHashStatement.executeUpdate();
                        }
                        
                        
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                // Close the connection
                	if (con != null) {
                		try {
                			con.close();
                			System.out.println("Connection closed.");
                		} catch (SQLException e) {
                			e.printStackTrace();
                		}
                	}
                }
            } 

        } catch(Exception e) {
        	System.out.println(e);
        }
    }
    
    private static boolean databaseExists(Connection connection, String databaseName) throws SQLException {
        String query = "SHOW DATABASES LIKE ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, databaseName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }
    private static void createDatabase(Connection connection, String databaseName) throws SQLException {
        String query = "CREATE DATABASE " + databaseName;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
            System.out.println("Database created: " + databaseName);
        }
    }
   
}
