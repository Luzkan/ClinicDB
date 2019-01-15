package clinicdb.Core;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;

public class ConnectionClass {
    private static Connection con;

    public ConnectionClass() {
    }

    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/clinicdb", "root", "");
            System.out.println("[Info] Connected established successfully.");
            ConnectionClass.con = con;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static java.sql.Connection getConnectionRef() {
        return con;
    }
}
