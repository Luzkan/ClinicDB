package clinicdb.Controllers;

import clinicdb.Core.ConnectionClass;
import clinicdb.Gui.Doctor.AddStuff.AddDisease;
import clinicdb.Gui.Doctor.AddStuff.AddMedicine;
import clinicdb.Gui.Doctor.AddStuff.AddVisitHistory;
import clinicdb.Gui.Patient.AddVisit;
import clinicdb.Gui.Patient.DoctorInfo;
import clinicdb.Gui.Patient.ViewDoctorSchedule;
import clinicdb.Gui.Receptionist.PatientInfo;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import clinicdb.Main;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class MainPatientController {

    private java.sql.Connection con = ConnectionClass.getConnectionRef();

    @FXML
    private TableView<DoctorInfo> tableAppointment;

    @FXML
    private TextField docName;

    @FXML
    private TextField docSurname;

    @FXML
    private TableColumn<DoctorInfo, String> day = new TableColumn<DoctorInfo, String>("Day");

    @FXML
    private TableColumn<DoctorInfo, String> startHour = new TableColumn<DoctorInfo, String>("Start hours");

    @FXML
    private TableColumn<DoctorInfo, String> finishHour = new TableColumn<DoctorInfo, String>("Finish hours");

    @FXML
    void makeAppointment(ActionEvent event) {
        AddVisit visit = new AddVisit(con);
        visit.start(AddVisit.window);
    }

    @FXML
    void showDocsVisitHours(ActionEvent event) {
        ViewDoctorSchedule view = new ViewDoctorSchedule(con, docName.getText(), docSurname.getText());
        //view.start(ViewDoctorSchedule.window);
    }

}

