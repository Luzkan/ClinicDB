package clinicdb.Controllers;

import clinicdb.Core.ConnectionClass;
import clinicdb.Gui.Doctor.MainDoctor;
import clinicdb.Gui.Patient.MainPatient;
import javafx.fxml.FXML;
import clinicdb.Main;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import java.io.IOException;
import java.sql.SQLException;

public class MainMenuController {

    @FXML
    private TextField login;

    @FXML
    private TextField password;

    @FXML
    private ToggleGroup job;

    @FXML
    private RadioButton doctor;

    @FXML
    private RadioButton patient;

    @FXML
    private RadioButton receptionist;

    // Ghost Button to read value from buttons above
    private RadioButton selectedRadioButton;

    @FXML
    ToggleGroup typeOfUser;


    // Show equivalent menu based on who the user is

    @FXML
    private void getConnection() throws SQLException, IOException {

        selectedRadioButton = (RadioButton) typeOfUser.getSelectedToggle();
        String typeOfUserValue = selectedRadioButton.getText();
        System.out.println("User logged in as: '" + typeOfUserValue +"'");

        ConnectionClass connection = new ConnectionClass(typeOfUserValue);
        connection.connect();
        connection.viewTable(connection.getConnectionRef(), "clinicdb");

        switch (typeOfUserValue) {
            case "Lekarz":
                Main.showDoctor();
                break;
            case "Pacjent":
                Main.showPatient();
                break;
            case "Recepcjonista":
                Main.showReceptionist();
                break;
        }

    }

}
