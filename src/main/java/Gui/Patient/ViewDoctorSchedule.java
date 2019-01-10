package Gui.Patient;

import Core.ConnectionClass;
import com.sun.rowset.CachedRowSetImpl;
import com.sun.rowset.JoinRowSetImpl;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.JoinRowSet;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ViewDoctorSchedule extends Application {
    public static Stage window = new Stage();
    private Scene scene;
    private Connection con;
    private String docName, docSurname;
    private String docID;
    private TableView<DoctorInfo> table;
    private TableColumn<DoctorInfo, String> dayTable = new TableColumn<DoctorInfo, String>("Day");
    private TableColumn<DoctorInfo, String> startHourTable = new TableColumn<DoctorInfo, String>("Start hours");
    private TableColumn<DoctorInfo, String> finishHourTable = new TableColumn<DoctorInfo, String>("Finish hours");
    private DoctorInfo doctorInfo;
    public static void main(String[] args) {
        launch(args);
    }

    public ViewDoctorSchedule(java.sql.Connection con, String docName, String docSurname) {
        this.con = con; this.docName = docName ; this.docSurname = docSurname;
        try {
            getID();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws SQLException {
        window = primaryStage;

        dayTable.setMinWidth(50);
        dayTable.setCellValueFactory(new PropertyValueFactory<>("day"));

        startHourTable.setMinWidth(50);
        startHourTable.setCellValueFactory(new PropertyValueFactory<>("startHour"));

        finishHourTable.setMinWidth(50);
        finishHourTable.setCellValueFactory(new PropertyValueFactory<>("finishHour"));

        table = new TableView<>();
        table.getColumns().addAll(dayTable, startHourTable, finishHourTable);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.getChildren().add(table);
        scene = new Scene(layout, 500, 500);
        window.setScene(scene);
        viewSchedule();
        window.show();

    }

    private void getID() throws SQLException{
        Statement stmt = null;
        String query =
                "select PWZ " +
                        "from " + "clinicdb" + ".doctors " +
                        "WHERE " + "clinicdb.doctors.name = '" + docName + "'" + " AND " + "clinicdb.doctors.surname = '" + docSurname + "'";
        stmt = con.createStatement();
        try {
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next())
                docID = rs.getString("PWZ");
        }
        catch (SQLException e) {
            System.out.println("błąd przy ściąganiu ID lekarza");
        }

    }

    public void viewSchedule() throws SQLException {

        Statement stmt = null;
        try {
            JoinRowSet jrs = new JoinRowSetImpl();
            CachedRowSet hours = new CachedRowSetImpl();
            hours.setCommand("SELECT * FROM clinicdb.office_hours");
            hours.setUsername(new ConnectionClass("asdsa").getConName());
            hours.setPassword(new ConnectionClass("asdasd").getPwd());
            hours.setUrl(new ConnectionClass("asd").getUrl());
            hours.execute();
            CachedRowSet doctors = new CachedRowSetImpl();
            doctors.setCommand("SELECT * FROM clinicdb.doctors");
            doctors.setUsername(new ConnectionClass("asdsa").getConName());
            doctors.setPassword(new ConnectionClass("asdasd").getPwd());
            doctors.setUrl(new ConnectionClass("asd").getUrl());
            doctors.execute();
            jrs.addRowSet(hours, "doctor");
            jrs.addRowSet(doctors, "PWZ");
            //stmt = con.createStatement();
            //ResultSet rs = stmt.executeQuery(query);
            while (jrs.next()) {
                String day = jrs.getString("day");
                String begTime = jrs.getString("beginning");
                String endTime = jrs.getString("end");
                doctorInfo = new DoctorInfo(day, begTime, endTime);
                table.getItems().add(doctorInfo);
                System.out.println(day + " " + begTime + " " + endTime + "\t");
            }
        } catch (SQLException e ) {
            System.out.println("nie masz uprawnień???");

        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }

}
