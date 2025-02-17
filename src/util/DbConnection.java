package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class DbConnection {
    private static String password = null;

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
        Connection conn = null;

        // 如果密碼還沒有被設置，則要求使用者輸入
        if (password == null) {
            password = JOptionPane.showInputDialog(null, 
                "請輸入資料庫密碼：", 
                "資料庫連線", 
                JOptionPane.QUESTION_MESSAGE);
            
            // 如果使用者取消輸入，返回 null
            if (password == null) {
                return null;
            }
        }

        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("no connection");
            e.printStackTrace();
            
            // 如果連線失敗，清除密碼並讓使用者重新輸入
            password = null;
            JOptionPane.showMessageDialog(null, 
                "連線失敗，請確認密碼是否正確", 
                "錯誤", 
                JOptionPane.ERROR_MESSAGE);
        }

        return conn;
    }

    // 清除已保存的密碼
    public static void clearPassword() {
        password = null;
    }

    public static Connection getConnection() {
        return getDb();
    }
}