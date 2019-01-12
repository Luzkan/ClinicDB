package clinicdb.Controllers;

import clinicdb.Gui.Doctor.AddStuff.AddDisease;
import clinicdb.Gui.Doctor.AddStuff.AddMedicine;
import clinicdb.Gui.Doctor.AddStuff.AddVisitHistory;
import clinicdb.Gui.Receptionist.PatientInfo;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class MainDoctorController {


    // Buttons to move around if you are a doc

    @FXML
    void addDisease() throws IOException {
        //Main.addDisease();
        AddDisease addDisease = new AddDisease(con);
        addDisease.start(AddDisease.window);
    }

    @FXML
    void addMedicine() throws IOException {
        //Main.addMedicine();
        AddMedicine addMedicine = new AddMedicine(con);
        addMedicine.start(AddMedicine.window);
    }

    @FXML
    void addVisitHistory() throws IOException {
        //Main.addVisitHistory();
        AddVisitHistory v = new AddVisitHistory(con, row.getPesel(), row.getId());
        v.start(AddVisitHistory.window);
    }

    @FXML
    void visitHistory() throws IOException {

        // This one is just an update button so after click the table next to it fills with data.
        // Works as SELECT * FROM VISITS

        try {
            tableVisits.getItems().clear();
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
                tableVisits.getItems().add(patientInfo);

                System.out.println(patient + " " + date + " " + time + " " + conf + "\t");
            }
            System.out.println("wyświetlono wizyty dla recepcjonisty");
        } catch (SQLException e) {e.printStackTrace();}

    }

    private java.sql.Connection con;
    //public MainDoctor(java.sql.Connection con) {
    //        this.con = con;
    //}


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
    private TableColumn<PatientInfo, String> conf;

    private PatientInfo patientInfo, row;


    private void showVisits() {

    }


    @FXML
    private void goQuit(){
        //Stage stage = (Stage) quitBtn.getScene().getWindow();
        //stage.close();
    }
}

