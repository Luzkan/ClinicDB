package Gui.Receptionist;

public class PatientInfo {
    private String pesel, date, hour, confirmation, id;

    public PatientInfo(String id, String pesel, String date, String hour, String confirmation) {
        this.id = id;
        this.pesel = pesel;
        this.date = date;
        this.hour = hour;
        this.confirmation = confirmation;
    }

    public String getPesel() {
        return pesel;
    }

    public String getConfirmation() {
        return confirmation;
    }

    public String getDate() {
        return date;
    }

    public String getHour() {
        return hour;
    }

    public String getId() {
        return id;
    }
}
