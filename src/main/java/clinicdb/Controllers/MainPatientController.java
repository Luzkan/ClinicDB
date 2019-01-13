package clinicdb.Controllers;

import clinicdb.Core.ConnectionClass;
import clinicdb.Gui.Patient.AddVisit;
import clinicdb.Gui.Patient.DoctorInfo;
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
                    String startHourS = rs.getString("beginning");
                    String finishHourS = rs.getString("end");

                    data.add(new DoctorInfo(dayS, startHourS, finishHourS));
                    System.out.println(dayS + " " + startHourS + " " + finishHourS + "\t");
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

}

