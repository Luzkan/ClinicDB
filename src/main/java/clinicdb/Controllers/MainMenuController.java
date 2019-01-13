package clinicdb.Controllers;

import clinicdb.Core.ConnectionClass;

import javafx.fxml.FXML;
import clinicdb.Main;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class MainMenuController {



    @FXML
    private TextField login;
    public static String loginGlobal = "Null";
    @FXML
    private TextField password;
    @FXML
    private CheckBox pass_toggle;
    @FXML
    private Button btn_start_stop;
    @FXML
    private PasswordField pass_hidden;

    @FXML
    public void togglevisiblePassword() {
        if (pass_toggle.isSelected()) {
            password.setText(pass_hidden.getText());
            password.setVisible(true);
            pass_hidden.setVisible(false);
            return;
        }
        pass_hidden.setText(password.getText());
        pass_hidden.setVisible(true);
        password.setVisible(false);
    }

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


    @FXML
    void initialize() {
        this.togglevisiblePassword();
    }

    // Show equivalent menu based on who the user is

    @FXML
    private void getConnection() throws SQLException, IOException {

        password.setText(pass_hidden.getText());
        // Ghost Button to read value from buttons above
        RadioButton selectedRadioButton = (RadioButton) typeOfUser.getSelectedToggle();
        String typeOfUserValue = "Nothing";

        try {
            typeOfUserValue = selectedRadioButton.getText();
            connectWithDB(typeOfUserValue);
        }catch(NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Select what type of user you are.");
            alert.show();
        }

        loginGlobal = login.getText();

        switch (typeOfUserValue) {
            case "Doctor":
                if(checkLogin(login.getText(), password.getText(), typeOfUserValue))
                    Main.showDoctor();
                break;
            case "Patient":
                if(checkLogin(login.getText(), password.getText(), typeOfUserValue))
                    Main.showPatient();
                break;
            case "Receptionist":
                if(checkLogin(login.getText(), password.getText(), typeOfUserValue))
                    Main.showReceptionist();
                break;
        }

    }

    private void connectWithDB(String typeOfUserValue) throws SQLException{

        System.out.println("[Info] User logged in as: '" + typeOfUserValue +"'");

        ConnectionClass connection = new ConnectionClass(typeOfUserValue);
        connection.connect();
        connection.viewTable(connection.getConnectionRef(), "clinicdb");
    }

    boolean checkLogin(String login, String password, String typeOfUser) throws SQLException {

        java.sql.Connection con = ConnectionClass.getConnectionRef();

        String passTable = null;
        if(typeOfUser.equals("Doctor")){
            passTable = "doctorspass";
        }else if(typeOfUser.equals("Patient")){
            passTable = "patientspass";
        }else if(typeOfUser.equals("Receptionist")){
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


    @FXML
    void makeRegister() throws SQLException, IOException {

        String typeOfUserValue = "New User Registration";
        connectWithDB(typeOfUserValue);
        Main.showRegister();

    }

}
