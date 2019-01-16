package clinicdb.Gui.Doctor;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.w3c.dom.stylesheets.MediaList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AddVisitHistory extends Application {
    public static Stage window = new Stage();
    private Connection con;
    private TextField prescription;
    private String id;
    private TableView<DiseaseInfo> tableD;
    private TableColumn<DiseaseInfo, String> diseases = new TableColumn<>("Diseases");
    private TableView<MedicineInfo> tableM;
    private TableColumn<MedicineInfo, String> medicines = new TableColumn<>("Medicines");
    private DiseaseInfo d;
    private MedicineInfo m;
    private int i = 0;
    private int k = 0;
    private ArrayList<MedicineInfo> mediList = new ArrayList<>();
    private ArrayList<DiseaseInfo> disList = new ArrayList<>();

    public AddVisitHistory(Connection con, String pesel, String id) {
        this.con = con;
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

        Label pres = new Label("Enter prescription");
        prescription = new TextField();

        Button addDisease = new Button("Add Disease to visit history");
        addDisease.setOnAction(e -> addDiseaseName());
        Button addMedicine = new Button("Add Medicine to visit history");
        addMedicine.setOnAction(e -> addMedicineName());
        Button add = new Button("Add Visit History");
        add.setOnAction(e -> addVisitHistory());

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));
        Scene scene = new Scene(layout, 500, 500);
        layout.getChildren().addAll(pres, prescription, tableD, addDisease, tableM, addMedicine, add);
        
        showDiseases();
        showMedicines();
        window.setScene(scene);
        primaryStage.setTitle("Archive a Visit");
        window.show();

    }

    private void addMedicineName() {
        m = tableM.getSelectionModel().getSelectedItem();
        mediList.add(m);
        i++;
        System.out.println("i: " + i);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Added");
        alert.setHeaderText("Added Selected Medicine to Visit History.");
        alert.show();
        System.out.println("[VH] Added Medicine to Visit History");


    }

    private void addDiseaseName() {
        d = tableD.getSelectionModel().getSelectedItem();
        disList.add(d);
        k++;
        System.out.println("k: " + k);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Added");
        alert.setHeaderText("Added Selected Disease to Visit History.");
        alert.show();
        System.out.println("[VH] Added Disease to Visit History");

    }

    private void showDiseases() {
        try {
            tableD.getItems().clear();
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM diseases");
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
        PreparedStatement stmt;
        try {
            tableM.getItems().clear();
            stmt = con.prepareStatement("SELECT * FROM medicines");
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
            String porada = prescription.getText();
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO visit_history (visit_ID, advices) VALUES (?, ?);"); // Adds Advice
            pstmt.setString(1, id);
            pstmt.setString(2, porada);
            pstmt.execute();
            pstmt.close();

            PreparedStatement p = con.prepareStatement(" SELECT ID FROM visit_history WHERE visit_ID=?");
            p.setString(1, id);
            p.execute();
            ResultSet rs = p.executeQuery();
            rs.next();
            id = rs.getString("ID");
            p.close();

            try {
                for(DiseaseInfo d : disList) {
                    pstmt = con.prepareStatement("INSERT INTO recognition (visit_ID, disease) VALUES (?, ?);"); // Adds Disease
                    pstmt.setString(1, id);
                    pstmt.setString(2, d.getId());
                    pstmt.execute();
                    pstmt.close();
                }
            } catch (NullPointerException ignored){}

            try{
                for(MedicineInfo m : mediList) {
                    pstmt = con.prepareStatement("INSERT INTO prescription (visit_ID, medicine) VALUES (?, ?);"); // Adds Medicine
                    pstmt.setString(1, id);
                    pstmt.setString(2, m.getId());
                    pstmt.execute();
                    pstmt.close();
                }
            } catch (NullPointerException ignored){}

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Added Visit History to database!");
            alert.show();

            System.out.println("[New] Added History Visit");
        } catch (SQLException e) {e.printStackTrace();}
    }
}
