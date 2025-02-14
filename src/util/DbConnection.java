package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

    public static void main(String[] args) {
        try (Connection conn = DbConnection.getDb()) {
            if (conn != null) {
                System.out.println("Connection successful!");
            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (SQLException e) {
            System.out.println("Error: Unable to close the connection");
            e.printStackTrace();
        }
    }
    
    public static Connection getDb() {
        String url = "jdbc:mysql://localhost:3306/Deliveryandstock?useUnicode=true&characterEncoding=UTF-8";
        String user = "root";
        String password = "12345678";
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("no connection");
            e.printStackTrace();
        }

        return conn;
    }
}
