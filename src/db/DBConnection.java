package db;

import java.sql.*;

public class DBConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "apartments";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "admin";

    private static DBConnection dbConnection;
    private static Connection connection;

    public DBConnection() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SHOW DATABASES LIKE 'apartments'");
            if (!result.next()) {
                createDBandTable(statement);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void createDBandTable(Statement statement) throws SQLException {
        statement.execute("CREATE DATABASE apartments");

        statement.execute("USE apartments");

        statement.execute("CREATE TABLE IF NOT EXISTS districts (" +
                "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(25) UNIQUE)");

        statement.execute("CREATE TABLE IF NOT EXISTS apartments (" +
                "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                "district_id INT," +
                "address VARCHAR(35)," +
                "square TINYINT NOT NULL ," +
                "flats_count TINYINT NOT NULL," +
                "price INT," +
                "FOREIGN KEY (district_id) REFERENCES districts(id))");
    }

    public static Connection getConnection() throws SQLException {
        if (dbConnection == null) {
            dbConnection = new DBConnection();
            connection = DriverManager.getConnection(DB_URL + DB_NAME, DB_USER, DB_PASSWORD);
            return connection;
        } else {
            connection = DriverManager.getConnection(DB_URL + DB_NAME, DB_USER, DB_PASSWORD);
            return connection;
        }
    }
}
