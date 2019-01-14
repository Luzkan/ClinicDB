package clinicdb.Controllers;

import clinicdb.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import java.io.IOException;

public class MainWindowController {

    @FXML
    final void aboutClick() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Project made for College.");
        alert.setContentText("Rewritten by Marcel Jerzyk based on Jakub Kmita Project");
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
