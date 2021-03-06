package clinicdb.Controllers;

import clinicdb.Core.ConnectionClass;
import clinicdb.Gui.Patient.*;
import clinicdb.Gui.Universal.EditVisit;
import clinicdb.Gui.Universal.PatientInfo;
import clinicdb.Gui.Universal.ShowVisit;
import com.mysql.jdbc.log.NullLogger;
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
    private TableView<DoctorInfo> tableHours;
    @FXML
    private TableColumn<DoctorInfo, String> day;
    @FXML
    private TableColumn<DoctorInfo, String> beginning;
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
                    String day = rs.getString("day");
                    String beginning = rs.getString("beginning");
                    String end = rs.getString("end");

                    data.add(new DoctorInfo(day, beginning, end));
                    System.out.println("[Row] " + day + " " + beginning + " " + end + "\t");
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

        String name = "";
        String surname = "";

        String[] doctorsFullName = docName.getText().split(" ");
        try {
            name = doctorsFullName[0];
            surname = doctorsFullName[1];
        } catch (ArrayIndexOutOfBoundsException e){
            System.out.println("{Error] User didn't input name. Alert will be shown from another catch further in the code.");
        }

        Statement stmt;
        String query = "SELECT PWZ FROM " + "clinicdb" + ".doctors WHERE " + "clinicdb.doctors.name = '" + name + "'" + " AND " + "clinicdb.doctors.surname = '" + surname + "'";
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
    void showVisit() {
        try {
            ShowVisit showVisit = new ShowVisit(con, Integer.parseInt(tableVisits.getSelectionModel().getSelectedItem().getId()));
            showVisit.start(ShowVisit.window);
        } catch (NullPointerException e) {
            System.out.println("{Error] User tried to inspect a visit w/o selecting it.");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Select a visit you want to inspect.");
            alert.show();
        }
    }

    @FXML
    void showVisits() {

        String autopesel = "";
        try {
            PreparedStatement pstmt = con.prepareStatement("SELECT Patient FROM `patientspass` WHERE Login = '" + MainMenuController.loginGlobal + "'");
            pstmt.execute();
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                autopesel = rs.getString("Patient");
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
                System.out.println("[Row] " + idS + " " + patientS + " " + dateS + " " + timeS + " " + confirmationS + "\t");
            }

            id.setCellValueFactory(new PropertyValueFactory<>("id"));
            pesel.setCellValueFactory(new PropertyValueFactory<>("pesel"));
            date.setCellValueFactory(new PropertyValueFactory<>("date"));
            hour.setCellValueFactory(new PropertyValueFactory<>("hour"));
            confirmation.setCellValueFactory(new PropertyValueFactory<>("confirmation"));

            // Clears & Sets Data
            tableVisits.setItems(null);
            tableVisits.setItems(data);
            System.out.println("[Show] Printed visits for patient.");
        } catch (SQLException e) {e.printStackTrace();}
    }


    @FXML
    void editVisit() {
        try {
            if (tableVisits.getSelectionModel().getSelectedItem().getConfirmation().equals("0")) {
                EditVisit visit = new EditVisit(con, tableVisits.getSelectionModel().getSelectedItem().getId(), tableVisits.getSelectionModel().getSelectedItem().getPesel(), tableVisits.getSelectionModel().getSelectedItem().getDate(), tableVisits.getSelectionModel().getSelectedItem().getHour());
                visit.start(EditVisit.window);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("You can't edit visit that is confirmed.");
                alert.setContentText("Contact with administration & office if you really need to.");
                alert.show();
            }
        }catch(NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("You have to select visit first.");
            alert.show();
        }
    }


}

