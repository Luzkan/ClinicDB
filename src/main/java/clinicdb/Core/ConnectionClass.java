package clinicdb.Core;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;

public class ConnectionClass {
    private String actualUser;
    private static Connection con;
    private String conName = "root";
    private String pwd = "";
    private String url = "jdbc:mysql://localhost:3306";
    public ConnectionClass(String actualUser) {
        this.actualUser = actualUser;
    }

    public java.sql.Connection connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/clinicdb", "root", "");
            //java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", actualUser, actualUser+"pwd");
            System.out.println("[Info] Connected Successfully.");
            this.con = con;
            return con;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void viewTable(java.sql.Connection con, String dbName) throws SQLException {

        Statement stmt = null;
        String query =
                "select name, surname " +
                        "from " + dbName + ".doctors";

        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String name = rs.getString("name");
                String surname = rs.getString("surname");

                System.out.println(name + " " + surname + "\t");
            }
        } catch (SQLException e ) {
            System.out.println("[Info] Nie masz uprawnień (ConnectionClass)!");

        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }

    public static java.sql.Connection getConnectionRef() {
        return con;
    }

    public String getConName() {
        return conName;
    }

    public String getPwd() {
        return pwd;
    }

    public String getUrl() {
        return url;
    }
}
