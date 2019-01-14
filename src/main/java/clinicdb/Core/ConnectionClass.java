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
            System.out.println("[Info] Connected Successfully.");
            ConnectionClass.con = con;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void viewTable(java.sql.Connection con, String dbName){

        String query = "SELECT name, surname FROM " + dbName + ".doctors";

        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String name = rs.getString("name");
                String surname = rs.getString("surname");

                System.out.println(name + " " + surname + "\t");
            }
        } catch (SQLException e) {
            System.out.println("[Info] You do not have permission (ShowDoctors)!");
        }
    }

    public static java.sql.Connection getConnectionRef() {
        return con;
    }
}
