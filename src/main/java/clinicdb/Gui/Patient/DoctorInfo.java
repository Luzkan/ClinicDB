package clinicdb.Gui.Patient;

public class DoctorInfo {
    private String day, beginning, end;

    public DoctorInfo(String day, String beginning, String end) {
        this.day = day;
        this.beginning = beginning;
        this.end = end;
    }

    public String getDay() {
        return day;
    }

    public String getBeginning() {
        return beginning;
    }

    public String getEnd() {
        return end;
    }
}
