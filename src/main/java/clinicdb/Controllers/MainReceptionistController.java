package clinicdb.Controllers;


import clinicdb.Core.ConnectionClass;
import clinicdb.Gui.Receptionist.PatientInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class MainReceptionistController {

    private java.sql.Connection con = ConnectionClass.getConnectionRef();
    private ObservableList<PatientInfo> data;

    private int visitID = 2;
    private PatientInfo patientInfo;
    private PatientInfo row;

    @FXML
    private TableView<PatientInfo> tableVisits;

    @FXML
    private TableColumn<PatientInfo, String> ID;
    @FXML
    private TableColumn<PatientInfo, String> Patient;
    @FXML
    private TableColumn<PatientInfo, String> date;
    @FXML
    private TableColumn<PatientInfo, String> time;
    @FXML
    private TableColumn<PatientInfo, String> confirmation;

    @FXML
    void deleteVisit(ActionEvent event) {
        try {
            visitID = Integer.parseInt(row.getId());
            PreparedStatement pstmt = con.prepareStatement("DELETE FROM visits WHERE visits.ID = " + visitID);
            pstmt.execute();
            showVisits();
            System.out.println("[Deletion] Visit " + visitID + " was delted from Database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void showVisits() {
        try {
            //tableVisits.getItems().clear();
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM visits");
            pstmt.execute();
            ResultSet rs = pstmt.executeQuery();

            data = FXCollections.observableArrayList();

            while (rs.next()) {
                String idS = rs.getString("ID");
                String patientS = rs.getString("Patient");
                String dateS = rs.getString("date");
                String timeS = rs.getString("time");
                String confirmationS = rs.getString("confirmation");

                data.add(new PatientInfo(idS, patientS, dateS, timeS, confirmationS));
                System.out.println(idS + " " + patientS + " " + dateS + " " + timeS + " " + confirmationS + "\t");
            }

            ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
            Patient.setCellValueFactory(new PropertyValueFactory<>("Patient"));
            date.setCellValueFactory(new PropertyValueFactory<>("date"));
            time.setCellValueFactory(new PropertyValueFactory<>("time"));
            confirmation.setCellValueFactory(new PropertyValueFactory<>("confirmation"));

            // Clears & Sets Data
            tableVisits.setItems(null);
            tableVisits.setItems(data);
            System.out.println("[Show] Printed visits for doctor.");
        } catch (SQLException e) {e.printStackTrace();}
    }

    @FXML
    void updateVisits(ActionEvent event) {
        //UPDATE `visits` SET `confirmation` = '1' WHERE `visits`.`ID` = 10;
        try {
            visitID = Integer.parseInt(row.getId());
            PreparedStatement pstmt = con.prepareStatement("UPDATE visits SET confirmation = 1 WHERE visits.ID = " + visitID);
            pstmt.execute();
            showVisits();
            System.out.println("[Update] Visit " + visitID + " has been updated.");
        } catch (SQLException e) {e.printStackTrace();}
    }

}
