package clinicdb.Gui.Doctor;
public class MedicineInfo {
    private String medicine, id;


    MedicineInfo(String medicine, String id) {
        this.medicine = medicine;
        this.id = id;
    }

    public String getMedicine() {
        return medicine;
    }

    public String getId() {
        return id;
    }
}
