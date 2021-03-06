package clinicdb.Gui.Patient;

import clinicdb.Controllers.MainMenuController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AddVisit extends Application {
    public static Stage window = new Stage();
    private Connection con;
    private TextField pesel, docName, docSurname, time, date;
    private String sdocName;
    private String sdocSurname;
    private String docID;

    public static void main(String[] args) {
        launch(args);
    }

    public AddVisit(Connection con) {
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


        // Quality of Life Feature for Patient - autopesel
        String autopesel = "";
        System.out.println("Test #1: " + MainMenuController.loginGlobal);
        try {
            PreparedStatement pstmt = con.prepareStatement("SELECT Patient FROM `patientspass` WHERE Login = '" + MainMenuController.loginGlobal + "'");
            pstmt.execute();
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                autopesel = rs.getString("Patient");
                System.out.println("Test #2: " + autopesel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        pesel = new TextField();
        pesel.setText(autopesel);
        docName = new TextField();
        docSurname = new TextField();
        time = new TextField();
        date = new TextField();

        Label lpesel = new Label("Enter your pesel");
        Label ldocName = new Label("Enter doc's name");
        Label ldocSurname = new Label("Enter doc's surname");
        Label ltime = new Label("Enter the visit's hour in format hh:mm");
        Label ldate = new Label("Enter the visit's date in format YYYY-MM-DD");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.getChildren().addAll(lpesel, pesel, ldocName, docName, ldocSurname, docSurname, ltime, time, ldate, date, addVisit);
        Scene scene = new Scene(layout, 500, 375);
        window.setScene(scene);
        primaryStage.setTitle("Make Appointment");
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
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                java.sql.Time sqlTime = new java.sql.Time(stime);
                System.out.println(sqlTime);
                PreparedStatement pstmt = con.prepareStatement("INSERT INTO visits (visits.Doctor, visits.Patient, visits.time, visits.date) VALUES (?, ?, ?, ?)");
                pstmt.setString(1, docID);
                pstmt.setString(2, spesel);
                pstmt.setTime(3, sqlTime);
                pstmt.setDate(4, sqlDate);
                pstmt.execute();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("New Visit");
                alert.setHeaderText("Successfully added a visit!");
                alert.show();

                // Get scene from any object in that scene
                Stage stage = (Stage) pesel.getScene().getWindow();
                stage.close();

                System.out.println("[New] Added Visit");
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.err.println("[Error] Check constraint on Office Hours failed");

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Wrong Hours");
            alert.setHeaderText("Please input viable hours for this doctor!");
            alert.show();
        }
    }

    private String getDocId() throws SQLException {
        Statement stmt;
        String query = "SELECT PWZ FROM " + "clinicdb" + ".doctors WHERE " + "clinicdb.doctors.name = '" + sdocName + "'" + " AND " + "clinicdb.doctors.surname = '" + sdocSurname + "'";
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
