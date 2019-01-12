package clinicdb.Controllers;

import clinicdb.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import java.io.IOException;


public class MainWindowController {

    // (J) Top Menu
    @FXML
    final void aboutClick(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Project made for College.");
        alert.setContentText("Marcel Jerzyk");
        alert.show();
    }

    @FXML
    final void logoutClick() throws IOException {
        Main.showMainMenu();
    }

    @FXML
    final void closeClick() {
        Runtime.getRuntime().exit(0);
    }
}
