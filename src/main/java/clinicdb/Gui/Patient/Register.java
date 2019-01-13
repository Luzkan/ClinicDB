package clinicdb.Gui.Patient;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
/*
public class Register extends Application {
    public static Stage window = new Stage();
    private Connection con;
    private TextField pesel, docName, docSurname, time, date;
    private String sdocName;
    private String sdocSurname;
    private String docID;

    public static void main(String[] args) {
        launch(args);
    }

    public Register(Connection con) {
        this.con = con;
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;

        Button addVisit = new Button("Add visit");
        addVisit.setOnAction(e -> {
            try {
                setVisit();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });

        pesel = new TextField();
        docName = new TextField();
        docSurname = new TextField();
        time = new TextField();
        date = new TextField();

        Label lpesel = new Label("Enter your pesel");
        Label lname = new Label("Enter your name");
        Label lsurname = new Label("Enter your surname");
        Label ldate = new Label("Enter your birthday in format YYYY-MM-DD");
        Label lstreet = new Label("Enter your street address");
        Label lhousenumber = new Label("Enter your house number");
        Label lflatnumber = new Label("Enter your flat number");
        Label lpostalcode = new Label("Enter your flat number");



        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.getChildren().addAll(lpesel, pesel, ldocName, docName, ldocSurname, docSurname, ltime, time, ldate, date, addVisit);
        Scene scene = new Scene(layout, 500, 375);
        window.setScene(scene);
        primaryStage.setTitle("Register new Patient");
        window.show();

    }

    private void setVisit() throws SQLException {
        String spesel = pesel.getText();
        sdocName = docName.getText();
        sdocSurname = docSurname.getText();
        String[] split = time.getText().split(":");
        long stime = Long.parseLong(split[0]) * 3600000 + Long.parseLong(split[1]) * 60000 - 3600000;
        String sdate = date.getText();
        docID = getDocId();
        try {
            try {
                java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(sdate);
                Date sqlDate = new Date(utilDate.getTime());
                Time sqlTime = new Time(stime);
                System.out.println(sqlTime);
                PreparedStatement pstmt = con.prepareStatement("INSERT INTO visits (visits.Doctor, visits.Patient, visits.time, visits.date) VALUES (?, ?, ?, ?)");
                pstmt.setString(1, sdocName);
                pstmt.setString(2, spesel);
                pstmt.setTime(3, sqlTime);
                pstmt.setDate(4, sqlDate);
                pstmt.execute();
                System.out.println("[New] Added Visit");
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private String getDocId() throws SQLException {
        Statement stmt;
        String query =
                "SELECT PWZ FROM " + "clinicdb" + ".doctors WHERE " + "clinicdb.doctors.name = '" + sdocName + "'" + " AND " + "clinicdb.doctors.surname = '" + sdocSurname + "'";
        stmt = con.createStatement();
        try {
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next())
                docID = rs.getString("PWZ");
                return docID;
        }
        catch (SQLException e) {
            System.out.println("[Error] Failed to get Doctors ID");
        }
        return null;
    }
}
*/