package Gui.Doctor.AddStuff;

import Gui.Patient.DoctorInfo;
import Gui.Receptionist.PatientInfo;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddVisitHistory extends Application {
    public static Stage window = new Stage();
    private Scene scene;
    private Connection con;
    private Label dis, pres, med;
    private Button add, addMedicine, addDisease;
    private TextField disease, prescription, medicine;
    private String pesel, id;
    private String porada, choroba, lek, lekID, chorobaID;
    private TableView<DiseaseInfo> tableD;
    private TableColumn<DiseaseInfo, String> diseases = new TableColumn<>("Diseases");
    private TableView<MedicineInfo> tableM;
    private TableColumn<MedicineInfo, String> medicines = new TableColumn<>("Medicines");
    private DiseaseInfo d;
    private MedicineInfo m;

    public AddVisitHistory(Connection con, String pesel, String id) {
        this.con = con;
        this.pesel = pesel;
        this.id = id;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;

        diseases.setMinWidth(50);
        diseases.setCellValueFactory(new PropertyValueFactory<>("disease"));

        medicines.setMinWidth(50);
        medicines.setCellValueFactory(new PropertyValueFactory<>("medicine"));

        tableD = new TableView<>();
        tableD.getColumns().addAll(diseases);
        tableM = new TableView<>();
        tableM.getColumns().addAll(medicines);

        pres = new Label("Enter prescription");
        prescription = new TextField();

        add = new Button("Add visit history");
        add.setOnAction(e -> addVisitHistory());
        addDisease = new Button("Add Disease to visit history");
        addDisease.setOnAction(e -> addDiseaseName());
        addMedicine = new Button("Add Medicine to visit history");
        addMedicine.setOnAction(e -> addMedicineName());

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));
        scene = new Scene(layout, 500, 500);
        layout.getChildren().addAll(pres, prescription, add, tableD, addDisease, tableM, addMedicine);
        
        showDiseases();
        showMedicines();
        window.setScene(scene);

        window.show();

    }

    private void addMedicineName() {
        m = tableM.getSelectionModel().getSelectedItem();
    }

    private void addDiseaseName() {
        d = tableD.getSelectionModel().getSelectedItem();
    }

    private void showDiseases() {
        try {
            tableD.getItems().clear();
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM medicines");
            stmt.execute();
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                String d, id;
                d = rs.getString("name");
                id = rs.getString("ID");
                DiseaseInfo diseaseInfo = new DiseaseInfo(d, id);
                tableD.getItems().add(diseaseInfo);
            }

            stmt.close();
        } catch (SQLException e) {e.printStackTrace();}
    }

    private void showMedicines() {
        PreparedStatement stmt = null;
        try {
            tableM.getItems().clear();
            stmt = con.prepareStatement("SELECT * FROM diseases");
            stmt.execute();
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                String m, id;
                m = rs.getString("name");
                id = rs.getString("ID");
                MedicineInfo medicineInfo = new MedicineInfo(m, id);
                tableM.getItems().add(medicineInfo);
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addVisitHistory() {
        try {
            porada = prescription.getText();
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO visit_history (visit_ID, advices) VALUES (?, ?);"); //dodanie porady
            pstmt.setString(1, id);
            pstmt.setString(2, porada);
            pstmt.execute();
            pstmt.close();
            pstmt = con.prepareStatement("INSERT INTO recognition (visit_ID, disease) VALUES (?, ?);"); //dodanie choroby pacjenta
            pstmt.setString(1, id);
            pstmt.setString(2, d.getId());
            pstmt.execute();
            pstmt.close();
            pstmt = con.prepareStatement("INSERT INTO prescription (visit_ID, medicine) VALUES (?, ?);"); //dodanie leku dla pacjenta
            pstmt.setString(1, id);
            pstmt.setString(2, m.getId());
            pstmt.execute();
            pstmt.close();
            System.out.println("dodano historie wizyty");
        } catch (SQLException e) {e.printStackTrace();}
    }
}
