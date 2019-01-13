package clinicdb.Controllers;

import clinicdb.Core.ConnectionClass;
import clinicdb.Gui.Patient.AddVisit;
import clinicdb.Gui.Patient.DoctorInfo;
import clinicdb.Gui.Patient.ShowDoctors;
import clinicdb.Gui.Receptionist.PatientInfo;
import clinicdb.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;


public class MainPatientController {

    private java.sql.Connection con = ConnectionClass.getConnectionRef();

    @FXML
    private TextField docName;

    @FXML
    private TextField docSurname;

    @FXML
    private TableView<DoctorInfo> tableHours;

    @FXML
    private TableColumn<DoctorInfo, String> day;

    @FXML
    private TableColumn<DoctorInfo, Time> beginning;

    @FXML
    private TableColumn<DoctorInfo, Time> end;

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
    void makeAppointment() {
        AddVisit visit = new AddVisit(con);
        visit.start(AddVisit.window);
    }

    @FXML
    void showDocsVisitHours() throws SQLException {

        String PWZ = getPWZ();

        if(PWZ.equals("Not Found")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please input doctor's credentials to look up his available hours.");
            alert.setContentText("If you did - check spelling");
            alert.show();
        } else {
            try {
                PreparedStatement pstmt = con.prepareStatement("SELECT * FROM `office hours` WHERE doctor = " + PWZ);
                pstmt.execute();
                ResultSet rs = pstmt.executeQuery();

                ObservableList<DoctorInfo> data = FXCollections.observableArrayList();

                while (rs.next()) {
                    String dayS = rs.getString("day");
                    String beginningS = rs.getString("beginning");
                    String endS = rs.getString("end");

                    data.add(new DoctorInfo(dayS, beginningS, endS));
                    System.out.println(dayS + " " + beginningS + " " + endS + "\t");
                }

                day.setCellValueFactory(new PropertyValueFactory<>("day"));
                beginning.setCellValueFactory(new PropertyValueFactory<>("beginning"));
                end.setCellValueFactory(new PropertyValueFactory<>("end"));

                // Clears & Sets Data
                tableHours.setItems(null);
                tableHours.setItems(data);
                System.out.println("[Show] Printed available appointment time for Doctor with PWZ number: " + PWZ);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private String getPWZ() throws SQLException{
        Statement stmt;
        String query = "SELECT PWZ FROM " + "clinicdb" + ".doctors WHERE " + "clinicdb.doctors.name = '" + docName.getText() + "'" + " AND " + "clinicdb.doctors.surname = '" + docSurname.getText() + "'";
        stmt = con.createStatement();

        String docID = "Not Found";
        ResultSet rs = stmt.executeQuery(query);
        if (rs.next())
            docID = rs.getString("PWZ");
        System.out.println("[Info] PWZ is: " + docID);
        return docID;
    }

    @FXML
    void showDoctorsList() {
        ShowDoctors showDoctors = new ShowDoctors(con);
        showDoctors.start(ShowDoctors.window);

    }

    @FXML
    void showVisits() {


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






        try {
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM visits WHERE Patient = '" + autopesel +"'");
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


}

