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
        alert.setTitle("Rules");
        alert.setHeaderText("You can move any direction but only one space.\n" +
                "If there's another checker u can jump over it with yours continuously if the condition is still met.\n" +
                "Your goal is to get all of your checkers to opposite triangle.");
        alert.setContentText("This simple game is made for college project.\n" +
                "Hope you enjoy.\n" +
                "Credits: Luzkan & Gorsky");
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

    // (J) Developer Options just to move around right now
    // 23.12.2018 (J) they are no longer needed cause now its possible to move around from everywhere
    /*@FXML
    private Button quitBtn;

    @FXML
    private void goMainMenu() throws IOException {
        Main.showMainMenu();
    }

    @FXML
    private void goQuit(){
        Stage stage = (Stage) quitBtn.getScene().getWindow();
        stage.close();
    }
    */

}
