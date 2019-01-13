package clinicdb.Controllers;

import clinicdb.Core.ConnectionClass;
import clinicdb.Gui.Doctor.AddDisease;
import clinicdb.Gui.Doctor.AddMedicine;
import clinicdb.Gui.Doctor.AddVisitHistory;
import clinicdb.Gui.Doctor.EditVisit;
import clinicdb.Gui.Receptionist.PatientInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;


public class MainDoctorController {

    private java.sql.Connection con = ConnectionClass.getConnectionRef();

    @FXML
    private TableView<PatientInfo> tableVisits;
    @FXML
    private TableColumn<PatientInfo, String> id;
    @FXML
    private TableColumn<PatientInfo, Integer> pesel;
    @FXML
    private TableColumn<PatientInfo, Date> date;
    @FXML
    private TableColumn<PatientInfo, Time> hour;
    @FXML
    private TableColumn<PatientInfo, Integer> confirmation;

    @FXML
    void addDisease() {
        AddDisease addDisease = new AddDisease(con);
        addDisease.start(AddDisease.window);
    }

    @FXML
    void addMedicine() {
        AddMedicine addMedicine = new AddMedicine(con);
        addMedicine.start(AddMedicine.window);
    }

    @FXML
    void addVisitHistory() {
        try {
            AddVisitHistory v = new AddVisitHistory(con, tableVisits.getSelectionModel().getSelectedItem().getPesel(), tableVisits.getSelectionModel().getSelectedItem().getId());
            v.start(AddVisitHistory.window);
        } catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Select visit which you want to archive first.");
            alert.show();
        }
    }


    @FXML
    void editVisit() {
        try {
            EditVisit visit = new EditVisit(con, tableVisits.getSelectionModel().getSelectedItem().getId(), tableVisits.getSelectionModel().getSelectedItem().getPesel(), tableVisits.getSelectionModel().getSelectedItem().getDate(), tableVisits.getSelectionModel().getSelectedItem().getHour());
            visit.start(EditVisit.window);
        }catch(NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("You have to select visit first.");
            alert.show();
        }
    }

    @FXML
    void visitHistory() {

        // This one is just an update button so after click the table next to it fills with data.
        // Works as SELECT * FROM VISITS

        try {
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM visits");
            pstmt.execute();
            ResultSet rs = pstmt.executeQuery();

            ObservableList<PatientInfo> data = FXCollections.observableArrayList();

            while (rs.next()) {
                String idS = rs.getString("ID");
                String patientS = rs.getString("patient");
                String dateS = rs.getString("date");
                String timeS = rs.getString("time");
                String confirmationS = rs.getString("confirmation");

                data.add(new PatientInfo(idS, patientS, dateS, timeS, confirmationS));
                System.out.println(idS + " " + patientS + " " + dateS + " " + timeS + " " + confirmationS + "\t");
            }

            id.setCellValueFactory(new PropertyValueFactory<>("id"));
            pesel.setCellValueFactory(new PropertyValueFactory<>("pesel"));
            date.setCellValueFactory(new PropertyValueFactory<>("date"));
            hour.setCellValueFactory(new PropertyValueFactory<>("hour"));
            confirmation.setCellValueFactory(new PropertyValueFactory<>("confirmation"));

            // Clears & Sets Data
            tableVisits.setItems(null);
            tableVisits.setItems(data);
            System.out.println("[Show] Printed visits for doctor.");
        } catch (SQLException e) {e.printStackTrace();}
    }
}

