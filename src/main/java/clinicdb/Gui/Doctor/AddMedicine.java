package clinicdb.Gui.Doctor;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
    private TextField text;
    private Connection con;

    public AddMedicine(Connection con) {
        this.con = con;
    }
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        Label label = new Label("Add Medicine");
        text = new TextField();
        Button button = new Button("Add");
        button.setOnAction(e -> addMedicine());

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.getChildren().addAll(label, text, button);
        Scene scene = new Scene(layout, 500, 135);
        window.setScene(scene);
        primaryStage.setTitle("Add Medicine to Database");
        window.show();

    }

    private void addMedicine() {
        try {
            String med = text.getText();
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO medicines (name) VALUES (?);");
            pstmt.setString(1, med);
            pstmt.execute();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("New Medicine");
            alert.setHeaderText("Successfully added new medicine!");
            alert.show();

            System.out.println("[New] Added Medicine");
        } catch (SQLException e) {e.printStackTrace();}
    }
}
