package clinicdb.Gui.Doctor;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddDisease extends Application {
    public static Stage window = new Stage();
    private TextField textDisease;
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
        Label disease = new Label("Add Disease");
        textDisease = new TextField();
        Button button = new Button("Add");
        button.setOnAction(e -> addDisease());

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.getChildren().addAll(disease, textDisease, button);
        Scene scene = new Scene(layout, 500, 135);
        window.setScene(scene);
        primaryStage.setTitle("Add Disease to Database");
        window.show();
    }

    private void addDisease() {
        try {
            String dis = textDisease.getText();
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO diseases (name) VALUES (?);");
            pstmt.setString(1, dis);
            pstmt.execute();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("New Disease");
            alert.setHeaderText("Successfully added new disease!");
            alert.show();

            System.out.println("[New] Added Disease");
        } catch (SQLException e) {e.printStackTrace();}
    }
}
