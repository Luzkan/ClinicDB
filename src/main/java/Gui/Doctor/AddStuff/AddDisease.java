package Gui.Doctor.AddStuff;

import Gui.Patient.DoctorInfo;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddDisease extends Application {
    public static Stage window = new Stage();
    private Scene scene;
    private Label disease;
    private TextField textDisease;
    private Button button;
    private String dis;
    private Connection con;
    public static void main(String[] args) {
        launch(args);
    }

    public AddDisease(Connection con) {
        this.con = con;
    }
    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        disease = new Label("Add disease");
        textDisease = new TextField();
        button = new Button("Add");
        button.setOnAction(e -> addDisease());

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.getChildren().addAll(disease, textDisease, button);
        scene = new Scene(layout, 500, 500);
        window.setScene(scene);

        window.show();

    }

    private void addDisease() {
        try {
            dis = textDisease.getText();
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO diseases (name) VALUES (?);");
            pstmt.setString(1, dis);
            pstmt.execute();
            System.out.println("dodano chorobę");
        } catch (SQLException e) {e.printStackTrace();}
    }
}
