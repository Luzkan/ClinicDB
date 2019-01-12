package clinicdb;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {

    private static Stage primaryStage;
    private static BorderPane MainWindow;

    @Override
    public final void start(Stage primaryStage) throws IOException {
        Main.primaryStage = primaryStage;
        Main.primaryStage.setTitle("Clinic Database 2019");
        showWholeWindow();
        showMainMenu();
    }

    // Main Window in which everything will happen.
    // Like all screens (main menu, the game and options are in this this dude right here)
    private void showWholeWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/fxml/MainWindow.fxml"));
        MainWindow = loader.load();
        Scene scene = new Scene(MainWindow);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void showMainMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/fxml/MainMenu.fxml"));
        BorderPane MainMenu = loader.load();
        MainWindow.setCenter(MainMenu);
    }

    public static void showDoctor() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/fxml/MainDoctor.fxml"));
        BorderPane MainDoctor = loader.load();
        MainWindow.setCenter(MainDoctor);
    }

    public static void showPatient() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/fxml/MainPatient.fxml"));
        BorderPane Patient = loader.load();
        MainWindow.setCenter(Patient);
    }

    public static void showReceptionist() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/fxml/MainReceptionist.fxml"));
        BorderPane Receptionist = loader.load();
        MainWindow.setCenter(Receptionist);
    }


    public static void main(String[] args) {
        launch(args);
    }

}