package clinicdb.Gui.Universal;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ShowVisit extends Application {
    public static Stage window = new Stage();
    private Connection con;
    private int idVisit;

    public static void main(String[] args) {
        launch(args);
    }

    public ShowVisit(Connection con, int idVisit) {
        this.con = con;
        this.idVisit = idVisit;
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;

        Button showVisit = new Button("Ok");
        showVisit.setOnAction(e -> {
            Stage stage = (Stage) showVisit.getScene().getWindow();
            stage.close();
        });

        TextField descriptionToCopy = new TextField();
        descriptionToCopy.setPrefHeight(150);
        Label lDescriptionToCopy = new Label("Description");

        TextField medicinesToCopy = new TextField();
        medicinesToCopy.setPrefHeight(150);
        Label lMedicinesToCopy = new Label("Medicines prescribed");

        TextField diseasesToCopy = new TextField();
        diseasesToCopy.setPrefHeight(150);
        Label lDiseasesListToCopy = new Label("Diseased detected");

        String DescriptionFull = "";
        String MedicinesFull = "";
        String DiseasesFull = "";

        System.out.println("[Info] Printing for " + idVisit);

        // Description
        String queryForDescription = "SELECT advices FROM `visit_history` WHERE visit_ID = '" + idVisit + "'";

        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(queryForDescription);
            while (rs.next()) {
                DescriptionFull = rs.getString("advices");
            }
        } catch (SQLException e) {
            System.out.println("[Info] You do not have permission (ShowVisit)!");
        }

        System.out.println("[Info] Printing Description: " + DescriptionFull);
        descriptionToCopy.setText(DescriptionFull);


        // Medicine
        String queryForMedicine = "SELECT name FROM medicines INNER JOIN prescription p on medicines.ID = p.medicine INNER JOIN visit_history v on p.visit_ID = v.ID WHERE v.visit_ID=" + idVisit;

        boolean moreItemsThanOne = false;

        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(queryForMedicine);
            while (rs.next()) {
                if(moreItemsThanOne){
                    MedicinesFull = MedicinesFull.concat(", ");
                }else{
                    moreItemsThanOne = true;
                }
                String name = rs.getString("name");
                MedicinesFull = MedicinesFull.concat(name);
            }
        } catch (SQLException e) {
            System.out.println("[Info] You do not have permission (ShowVisit)!");
        }

        System.out.println("[Info] Printing Medicines: " + MedicinesFull);
        medicinesToCopy.setText(MedicinesFull);


        // Disease
        String queryForDisease = "SELECT name FROM diseases INNER JOIN recognition r on diseases.ID = r.disease INNER JOIN visit_history v on r.visit_ID = v.ID WHERE v.visit_ID=" + idVisit;

        moreItemsThanOne = false;

        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(queryForDisease);
            while (rs.next()) {
                if(moreItemsThanOne){
                    DiseasesFull = DiseasesFull.concat(", ");
                }else{
                    moreItemsThanOne = true;
                }
                String name = rs.getString("name");
                DiseasesFull = DiseasesFull.concat(name);
            }
        } catch (SQLException e) {
            System.out.println("[Info] You do not have permission (ShowVisit)!");
            System.out.println(e);
        }

        System.out.println("[Info] Printing Diseases: " + DiseasesFull);
        diseasesToCopy.setText(DiseasesFull);


        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.getChildren().addAll(lDescriptionToCopy, descriptionToCopy, lMedicinesToCopy, medicinesToCopy, lDiseasesListToCopy, diseasesToCopy,showVisit);
        Scene scene = new Scene(layout, 500, 260);
        window.setScene(scene);
        primaryStage.setTitle("Your Visits");
        window.show();

    }
}
