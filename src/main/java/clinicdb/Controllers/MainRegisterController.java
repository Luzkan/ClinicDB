package clinicdb.Controllers;

import clinicdb.Core.ConnectionClass;
import clinicdb.Main;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;


public class MainRegisterController {

    private java.sql.Connection con = ConnectionClass.getConnectionRef();

    @FXML
    private TextField pesel;
    @FXML
    private TextField name;
    @FXML
    private TextField surname;
    @FXML
    private DatePicker birthday;
    @FXML
    private TextField city;
    @FXML
    private TextField street;
    @FXML
    private TextField housenumber;
    @FXML
    private TextField flatnumber;
    @FXML
    private TextField postalcode;
    @FXML
    private TextField phone;
    @FXML
    private TextField login;
    @FXML
    private TextField password;

    // Database Connection
    public final void addToDB() throws IOException {

        try {
            Double.parseDouble(pesel.getText());
            Integer.parseInt(housenumber.getText());
            Integer.parseInt(flatnumber.getText());
            Integer.parseInt(phone.getText());

            try {
                String dateP = birthday.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                java.util.Date utilDate = null;
                utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateP);
                Date sqlDate = new Date(utilDate.getTime());

                try {
                    PreparedStatement pstmtPatient = con.prepareStatement("INSERT INTO patients (patients.PESEL, patients.name, patients.surname, patients.birthday, patients.city, patients.street, patients.`house number`, patients.`flat number`, patients.`postal code`, patients.phone) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                    pstmtPatient.setString(1, pesel.getText());
                    pstmtPatient.setString(2, name.getText());
                    pstmtPatient.setString(3, surname.getText());
                    pstmtPatient.setDate(4, sqlDate);
                    pstmtPatient.setString(5, city.getText());
                    pstmtPatient.setString(6, street.getText());
                    pstmtPatient.setString(7, housenumber.getText());
                    pstmtPatient.setString(8, flatnumber.getText());
                    pstmtPatient.setString(9, postalcode.getText());
                    pstmtPatient.setString(10, phone.getText());
                    pstmtPatient.execute();

                    PreparedStatement pstmtLogin = con.prepareStatement("INSERT INTO patientspass (patientspass.Login, patientspass.Password, patientspass.Patient) VALUES (?, ?, ?)");
                    pstmtLogin.setString(1, login.getText());
                    pstmtLogin.setString(2, password.getText());
                    pstmtLogin.setString(3, pesel.getText());
                    pstmtLogin.execute();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("New User");
                    alert.setHeaderText("Registration went successfully. You can now login to your account.");
                    alert.show();
                    Main.showMainMenu();

                } catch (SQLException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("There is a problem with database. Try again later.");
                    alert.show();
                }
            } catch (ParseException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Select a date.");
                alert.show();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Pesel / House Number / Flat Number / Phone fields must be a number.");
            alert.show();
        }
    }


    @FXML
    private void doCancel() throws IOException {
        Main.showMainMenu();

    }
}
