package clinicdb.Gui.Patient;

import clinicdb.Controllers.MainMenuController;
import com.sun.org.apache.bcel.internal.generic.DCMPG;
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

public class ShowDoctors extends Application {
    public static Stage window = new Stage();
    private Connection con;
    private TextField DoctorsListToCopy;

    public static void main(String[] args) {
        launch(args);
    }

    public ShowDoctors(Connection con) {
        this.con = con;
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;

        Button showDoctors = new Button("Ok");
        showDoctors.setOnAction(e -> {
            Stage stage = (Stage) showDoctors.getScene().getWindow();
            stage.close();
        });


        DoctorsListToCopy = new TextField();
        DoctorsListToCopy.setPrefHeight(150);
        Label lDoctorsListToCopy = new Label("Copy your doctors name from this field below.");

        String DoctorFull = "";
        String query = "SELECT name, surname FROM doctors";

        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                DoctorFull = DoctorFull.concat(name + " " + surname + ", ");

            }
        } catch (SQLException e) {
            System.out.println("[Info] You do not have permission (ConnectionClass)!");

        }

        System.out.println(DoctorFull);
        DoctorsListToCopy.setText(DoctorFull);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.getChildren().addAll(lDoctorsListToCopy, DoctorsListToCopy, showDoctors);
        Scene scene = new Scene(layout, 500, 200);
        window.setScene(scene);
        primaryStage.setTitle("Make Appointment");
        window.show();

    }
}
