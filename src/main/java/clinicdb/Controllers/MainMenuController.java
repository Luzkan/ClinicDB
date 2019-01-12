package clinicdb.Controllers;

import clinicdb.Core.ConnectionClass;
import javafx.fxml.FXML;
import clinicdb.Main;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    @FXML
    ToggleGroup typeOfUser;


    // Show equivalent menu based on who the user is

    @FXML
    private void getConnection() throws SQLException, IOException {

        // Ghost Button to read value from buttons above
        RadioButton selectedRadioButton = (RadioButton) typeOfUser.getSelectedToggle();

        String typeOfUserValue = selectedRadioButton.getText();
        System.out.println("[Info] User logged in as: '" + typeOfUserValue +"'");

        ConnectionClass connection = new ConnectionClass(typeOfUserValue);
        connection.connect();
        connection.viewTable(connection.getConnectionRef(), "clinicdb");

        switch (typeOfUserValue) {
            case "Lekarz":
                if(checkLogin(login.getText(), password.getText(), typeOfUserValue))
                    Main.showDoctor();
                break;
            case "Pacjent":
                if(checkLogin(login.getText(), password.getText(), typeOfUserValue))
                    Main.showPatient();
                break;
            case "Recepcjonista":
                if(checkLogin(login.getText(), password.getText(), typeOfUserValue))
                    Main.showReceptionist();
                break;
        }

    }

    boolean checkLogin(String login, String password, String typeOfUser) throws SQLException {

        java.sql.Connection con = ConnectionClass.getConnectionRef();

        String passTable = null;
        if(typeOfUser.equals("Lekarz")){
            passTable = "doctorspass";
        }else if(typeOfUser.equals("Pacjent")){
            passTable = "patientspass";
        }else if(typeOfUser.equals("Recepcjonista")){
            passTable = "receptionistspass";
        }

        PreparedStatement pstmt = con.prepareStatement("SELECT Login FROM " + passTable + " WHERE Login = '" + login + "' AND Password = '" + password + "'");
        pstmt.execute();
        ResultSet rs = pstmt.executeQuery();

        String checkIfFound = "notfound";

        while (rs.next()) {
            checkIfFound = rs.getString("Login");
            System.out.println("[Found] " + checkIfFound + "\t");
        }

        if(checkIfFound.equals(login)) {
            return true;
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Wrong username / password.");
            alert.show();
        }

        return false;
    }

}
