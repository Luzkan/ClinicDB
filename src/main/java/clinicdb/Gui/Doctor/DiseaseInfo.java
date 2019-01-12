package clinicdb.Gui.Doctor;
public class DiseaseInfo {
    private String disease, id;

    DiseaseInfo(String disease, String id) {
        this.disease = disease;
        this.id = id;
    }

    public String getDisease() {
        return disease;
    }

    public String getId() {
        return id;
    }
}
