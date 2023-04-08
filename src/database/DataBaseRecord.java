package database;

public class DataBaseRecord {

    private String ID;
    private String firstName;
    private String secondName;

    public DataBaseRecord(String ID, String firstName, String secondName) {
        this.ID = ID;
        this.firstName = firstName;
        this.secondName = secondName;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    @Override
    public String toString() {
        return ID + " " + firstName + " " + secondName;
    }
}
