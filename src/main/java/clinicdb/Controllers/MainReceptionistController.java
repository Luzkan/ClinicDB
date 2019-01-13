package clinicdb.Controllers;

import clinicdb.Core.ConnectionClass;
import clinicdb.Gui.Receptionist.PatientInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class MainReceptionistController {

    private java.sql.Connection con = ConnectionClass.getConnectionRef();
    private int visitID;

    @FXML
    private TableView<PatientInfo> tableVisits;

    @FXML
    private TableColumn<PatientInfo, String> id;
    @FXML
    private TableColumn<PatientInfo, String> pesel;
    @FXML
    private TableColumn<PatientInfo, String> date;
    @FXML
    private TableColumn<PatientInfo, String> hour;
    @FXML
    private TableColumn<PatientInfo, String> confirmation;

    @FXML
    void deleteVisit() {
        try {
            visitID = Integer.parseInt(tableVisits.getSelectionModel().getSelectedItem().getId());
            PreparedStatement pstmt = con.prepareStatement("DELETE FROM visits WHERE visits.ID = " + visitID);
            pstmt.execute();
            showVisits();
            System.out.println("[Deletion] Visit " + visitID + " was deleted from Database.");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Select visit which you want to delete first.");
            alert.show();
        }
    }

    @FXML
    void showVisits() {
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
            System.out.println("[Show] Printed visits for receptionist.");
        } catch (SQLException e) {e.printStackTrace();}
    }

    @FXML
    void updateVisits() {
        //UPDATE `visits` SET `confirmation` = '1' WHERE `visits`.`ID` = 10;
        try {
            visitID = Integer.parseInt(tableVisits.getSelectionModel().getSelectedItem().getId());
            PreparedStatement pstmt = con.prepareStatement("UPDATE visits SET confirmation = 1 WHERE visits.ID = " + visitID);
            pstmt.execute();
            showVisits();
            System.out.println("[Update] Visit " + visitID + " has been updated.");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Select visit which you want to confirm first.");
            alert.show();
        }
    }

}
