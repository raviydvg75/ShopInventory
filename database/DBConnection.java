package database;


import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/meropasal";
    private static final String USER = "meropasal";
    private static final String PASSWORD = ""; //try "YourPassword123!" for a demo

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {

        Connection conn = getConnection();

        if (conn != null) {
            System.out.println("Database Connected Successfully!");

            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}