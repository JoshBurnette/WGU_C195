package model;

/**
 This class is used to construct FirstLevelDivisions objects.
 It has getters and setters.
 */
public class FirstLevelDivisions {
    private int divisionID;
    private String divisionName;

    /**
     This method is used to construct a FirstLevelDivisions object.
     */
    //constructor
    public FirstLevelDivisions(int divisionID, String divisionName) {
        this.divisionID = divisionID;
        this.divisionName = divisionName;
    }

    //setters and getters
    public int getDivisionID() {
        return divisionID;
    }

    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }


}
