package Gui.Patient;

public class DoctorInfo {
    private String day, startHour, finishHour;

    public DoctorInfo(String day, String startHour, String finishHour) {
        this.day = day;
        this.startHour = startHour;
        this.finishHour = finishHour;
    }

    public String getDay() {
        return day;
    }

    public String getStartHour() {
        return startHour;
    }

    public String getFinishHour() {
        return finishHour;
    }
}
