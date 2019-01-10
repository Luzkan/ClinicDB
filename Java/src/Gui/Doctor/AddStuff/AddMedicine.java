package Gui.Doctor.AddStuff;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddMedicine extends Application {
    public static Stage window = new Stage();
    private Scene scene;
    private Label label;
    private TextField text;
    private Button button;
    private Connection con;
    private String med;

    public AddMedicine(Connection con) {
        this.con = con;
    }
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        label = new Label("Add Medicine");
        text = new TextField();
        button = new Button("Add");
        button.setOnAction(e -> addMedicine());

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.getChildren().addAll(label, text, button);
        scene = new Scene(layout, 500, 500);
        window.setScene(scene);

        window.show();

    }

    private void addMedicine() {
        try {
            med = text.getText();
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO medicines (name) VALUES (?);");
            pstmt.setString(1, med);
            pstmt.execute();
            System.out.println("dodano chorobę");
        } catch (SQLException e) {e.printStackTrace();}
    }
}
