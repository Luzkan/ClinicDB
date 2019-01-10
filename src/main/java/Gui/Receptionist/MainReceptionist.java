package Gui.Receptionist;

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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainReceptionist extends Application {
    public static Stage window = new Stage();
    private Scene scene;
    private java.sql.Connection con;
    private Button delete, update, show;
    private int visitID = 2;
    private TableView<PatientInfo> table;
    private TableColumn<PatientInfo, String> id = new TableColumn<PatientInfo, String>("ID");
    private TableColumn<PatientInfo, String> pesel = new TableColumn<PatientInfo, String>("Pesel");
    private TableColumn<PatientInfo, String> date = new TableColumn<PatientInfo, String>("Date");
    private TableColumn<PatientInfo, String> hour = new TableColumn<PatientInfo, String>("Hour");
    private TableColumn<PatientInfo, String> conf = new TableColumn<PatientInfo, String>("Confirmation");
    private PatientInfo patientInfo;
    private PatientInfo row;

    public MainReceptionist(java.sql.Connection con) {
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

        show = new Button("Show visits");
        show.setOnAction(e -> showVisits());
        delete = new Button("Delete visit");
        delete.setOnAction(e -> deleteVisit());
        update = new Button("Update visit");
        update.setOnAction(e -> updateVisit());
        delete = new Button("Delete visit");
        delete.setOnAction(e -> deleteVisit());

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.getChildren().addAll(show, update, delete, table);
        scene = new Scene(layout, 500, 500);
        window.setScene(scene);

        window.show();

    }

    private void deleteVisit() {
        try {
            visitID = Integer.parseInt(row.getId());
            PreparedStatement pstmt = con.prepareStatement("DELETE FROM visits WHERE visits.ID = " + visitID);
            pstmt.execute();
            showVisits();
            System.out.println("Usunięto wizytę");
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
                String date = rs.getString("date");
                String time = rs.getString("time");
                String conf = rs.getString("confirmation");
                patientInfo = new PatientInfo(id, patient, date, time, conf);
                table.getItems().add(patientInfo);
                System.out.println(patient + " " + date + " " + time + " " + conf + "\t");
            }
            System.out.println("wyświetlono wizyty dla recepcjonisty");
        } catch (SQLException e) {e.printStackTrace();}
    }

    private void updateVisit() {
        //UPDATE `visits` SET `confirmation` = '1' WHERE `visits`.`ID` = 10;
        try {
            visitID = Integer.parseInt(row.getId());
            PreparedStatement pstmt = con.prepareStatement("UPDATE visits SET confirmation = 1 WHERE visits.ID = " + visitID);
            pstmt.execute();
            showVisits();
            System.out.println("Wizyta updejtowana");
        } catch (SQLException e) {e.printStackTrace();}
    }

}
