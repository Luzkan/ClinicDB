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

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class EditVisit extends Application {
    public static Stage window = new Stage();
    private final String peselS, dateS, hourS, idS;
    private Connection con;
    private TextField pesel, time, date;

    public static void main(String[] args) {
        launch(args);
    }

    public EditVisit(Connection con, String idS, String peselS, String dateS, String hourS) {
        this.con = con;
        this.idS = idS;
        this.peselS = peselS;
        this.dateS = dateS;
        this.hourS = hourS;
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;

        Button editVisit = new Button("Add visit");
        editVisit.setOnAction(e -> {
            try {
                setVisit();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });

        pesel = new TextField();
        pesel.setText(peselS);
        time = new TextField();
        time.setText(hourS);
        date = new TextField();
        date.setText(dateS);


        Label lpesel = new Label("Correct Pesel");
        Label ltime = new Label("Correct the visit's hour in format hh:mm");
        Label ldate = new Label("Correct the visit's date in format YYYY-MM-DD");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.getChildren().addAll(lpesel, pesel, ltime, time, ldate, date, editVisit);
        Scene scene = new Scene(layout, 500, 250);
        window.setScene(scene);
        primaryStage.setTitle("Edit Appointment");
        window.show();

    }

    private void setVisit() throws SQLException {
        String spesel = pesel.getText();
        String[] split = time.getText().split(":");
        long stime = Long.parseLong(split[0]) * 3600000 + Long.parseLong(split[1]) * 60000 - 3600000;
        String sdate = date.getText();
        try {
            try {
                java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(sdate);
                Date sqlDate = new Date(utilDate.getTime());
                Time sqlTime = new Time(stime);
                System.out.println(sqlTime);
                PreparedStatement pstmt = con.prepareStatement("UPDATE visits SET patient = ?, time = ?, date = ? WHERE ID = " + idS + "\n");
                pstmt.setString(1, spesel);
                pstmt.setTime(2, sqlTime);
                pstmt.setDate(3, sqlDate);
                pstmt.execute();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Edition");
                alert.setHeaderText("Successfully changed a visit!");
                alert.show();

                System.out.println("[Edit] Visit nr " + idS + " has been changed by a doctor.");
            } catch (ParseException e) {
                e.printStackTrace();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
