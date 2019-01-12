package clinicdb.Controllers;


import clinicdb.Core.ConnectionClass;
import clinicdb.Gui.Receptionist.PatientInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class MainReceptionistController {

    private java.sql.Connection con = ConnectionClass.getConnectionRef();

    private Button delete, update, show;
    private int visitID = 2;
    private PatientInfo patientInfo;
    private PatientInfo row;

    @FXML
    private TableView<PatientInfo> table;

    @FXML
    private TableColumn<PatientInfo, String> id = new TableColumn<PatientInfo, String>("ID");

    @FXML
    private TableColumn<PatientInfo, String> pesel = new TableColumn<PatientInfo, String>("Pesel");

    @FXML
    private TableColumn<PatientInfo, String> date = new TableColumn<PatientInfo, String>("Date");

    @FXML
    private TableColumn<PatientInfo, String> hour = new TableColumn<PatientInfo, String>("Hour");

    @FXML
    private TableColumn<PatientInfo, String> conf = new TableColumn<PatientInfo, String>("Confirmation");

    @FXML
    void deleteVisit(ActionEvent event) {

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

    @FXML
    void showVisits() {

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

    @FXML
    void updateVisits(ActionEvent event) {

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
