package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {
    public static Connection getConn() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Replace with your JDBC driver class
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/filedb", "root", "pass");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Connection established!");
        return conn;
    }
    public  static void closeConn(){
        try {
            if (getConn()!=null)
            {
            getConn().close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Connection closed");
    }


}
