package clinicdb.Controllers;

import clinicdb.Core.ConnectionClass;
import clinicdb.Gui.Patient.AddVisit;
import clinicdb.Gui.Patient.DoctorInfo;
import clinicdb.Gui.Patient.ViewDoctorSchedule;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

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
    void showDocsVisitHours(ActionEvent event) throws SQLException {
        ViewDoctorSchedule view = new ViewDoctorSchedule(con, docName.getText(), docSurname.getText());
        view.start(ViewDoctorSchedule.window);
    }

}

