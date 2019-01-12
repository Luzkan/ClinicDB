package clinicdb.Core;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;

public class ConnectionClass {
    private static Connection con;

    public ConnectionClass(String actualUser) {
    }

    public java.sql.Connection connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/clinicdb", "root", "");
            //java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", actualUser, actualUser+"pwd");
            System.out.println("[Info] Connected Successfully.");
            ConnectionClass.con = con;
            return con;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void viewTable(java.sql.Connection con, String dbName) throws SQLException {

        String query = "SELECT name, surname FROM " + dbName + ".doctors";

        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String name = rs.getString("name");
                String surname = rs.getString("surname");

                System.out.println(name + " " + surname + "\t");
            }
        } catch (SQLException e) {
            System.out.println("[Info] Nie masz uprawnień (ConnectionClass)!");

        }
    }

    public static java.sql.Connection getConnectionRef() {
        return con;
    }

    public String getConName() {
        String conName = "root";
        return conName;
    }

    public String getPwd() {
        String pwd = "";
        return pwd;
    }

    public String getUrl() {
        String url = "jdbc:mysql://localhost:3306";
        return url;
    }
}
