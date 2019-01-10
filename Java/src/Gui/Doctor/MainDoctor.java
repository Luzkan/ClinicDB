package Gui.Doctor;

import Gui.Doctor.AddStuff.AddDisease;
import Gui.Doctor.AddStuff.AddMedicine;
import Gui.Doctor.AddStuff.AddVisitHistory;
import Gui.Doctor.AddStuff.ViewVisitHistory;
import Gui.Patient.AddVisit;
import Gui.Patient.DoctorInfo;
import Gui.Receptionist.PatientInfo;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainDoctor extends Application {
    public static Stage window = new Stage();
    private Scene scene;
    private java.sql.Connection con;
    private Button addD, addM, addV, view;
    private TableView<PatientInfo> table;
    private TableColumn<PatientInfo, String> id = new TableColumn<PatientInfo, String>("ID");
    private TableColumn<PatientInfo, String> pesel = new TableColumn<PatientInfo, String>("Pesel");
    private TableColumn<PatientInfo, String> date = new TableColumn<PatientInfo, String>("Date");
    private TableColumn<PatientInfo, String> hour = new TableColumn<PatientInfo, String>("Hour");
    private TableColumn<PatientInfo, String> conf = new TableColumn<PatientInfo, String>("Confirmation");
    private PatientInfo patientInfo, row;

    public MainDoctor(java.sql.Connection con) {
        this.con = con;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;

        id.setMinWidth(50);
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        pesel.setMinWidth(50);
        pesel.setCellValueFactory(new PropertyValueFactory<>("pesel"));

        date.setMinWidth(50);
        date.setCellValueFactory(new PropertyValueFactory<>("date"));

        hour.setMinWidth(50);
        hour.setCellValueFactory(new PropertyValueFactory<>("hour"));

        conf.setMinWidth(20);
        conf.setCellValueFactory(new PropertyValueFactory<>("confirmation"));

        table = new TableView<>();
        table.getColumns().addAll(pesel, date, hour, conf);

        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                row = table.getSelectionModel().getSelectedItem();
            }
        });

        addD = new Button("Add Disease");
        addM = new Button("Add Medicine");
        addV = new Button("Add Visit History");
        view = new Button("View Visit History");
        addD.setOnAction(e -> addDisease());
        addM.setOnAction(e -> addMedicine());
        addV.setOnAction(e -> addVisit());
        view.setOnAction(e -> viewVisit());

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.getChildren().addAll(addD, addM, addV, view, table);
        scene = new Scene(layout, 500, 500);
        showVisits();
        window.setScene(scene);

        window.show();

    }

    private void viewVisit() {
        ViewVisitHistory v = new ViewVisitHistory(con, Integer.parseInt(row.getId()));
        v.start(ViewVisitHistory.window);
    }

    private void addVisit() {
        AddVisitHistory v = new AddVisitHistory(con, row.getPesel(), row.getId());
        v.start(AddVisitHistory.window);
    }

    private void addMedicine() {
        AddMedicine addMedicine = new AddMedicine(con);
        addMedicine.start(AddMedicine.window);
    }

    private void addDisease() {
        AddDisease addDisease = new AddDisease(con);
        addDisease.start(AddDisease.window);
    }

    private void showVisits() {
        // SELECT * FROM VISITS
        try {
            table.getItems().clear();
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM visits");
            pstmt.execute();
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String id = rs.getString("ID");
                String patient = rs.getString("Patient");
                String conf = rs.getString("confirmation");
                String date = rs.getString("date");
                String time = rs.getString("time");
                patientInfo = new PatientInfo(id, patient, date, time, conf);
                table.getItems().add(patientInfo);

                System.out.println(patient + " " + date + " " + time + " " + conf + "\t");
            }
            System.out.println("wyświetlono wizyty dla recepcjonisty");
        } catch (SQLException e) {e.printStackTrace();}
    }


}
