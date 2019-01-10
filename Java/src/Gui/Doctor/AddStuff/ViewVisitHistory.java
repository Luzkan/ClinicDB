package Gui.Doctor.AddStuff;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;

public class ViewVisitHistory extends Application {
    public static Stage window = new Stage();
    private Scene scene;
    private Connection con;
    private int id;

    public ViewVisitHistory(Connection con, int id) {
        this.con = con;
        this.id = id;
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));
        scene = new Scene(layout, 500, 500);
        window.setScene(scene);

        window.show();

    }
}
