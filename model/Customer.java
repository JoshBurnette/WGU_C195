package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 This class is used to construct Customer objects.
 It has getters and setters.
 */
public class Customer {

    private int customerID;
    private String customerName;
    private String customerAddress;
    private String customerPostalCode;
    private String customerPhoneNumber;
    private String customerCreateDate;
    private String customerCreatedBy;
    private Timestamp customerLastUpdate;
    private String customerLastUpdatedBy;
    private int countryID;
    private int divisionID;

    /**
     This method is used to construct a Customer object.
     */
    //constructor
    public Customer(int customerID, String customerName, String customerAddress, String customerPostalCode, String customerPhoneNumber, String customerCreateDate, String customerCreatedBy, Timestamp customerLastUpdate, String customerLastUpdatedBy, int divisionID) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;
        this.customerPhoneNumber = customerPhoneNumber;
        this.customerCreateDate = customerCreateDate;
        this.customerCreatedBy = customerCreatedBy;
        this.customerLastUpdate = customerLastUpdate;
        this.customerLastUpdatedBy = customerLastUpdatedBy;
        this.divisionID = divisionID;

    }

    //setters and getters... don't think I need setters, but they're there
    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerPostalCode() {
        return customerPostalCode;
    }

    public void setCustomerPostalCode(String customerPostalCode) {
        this.customerPostalCode = customerPostalCode;
    }

    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }

    public String getCreatedBy() {
        return customerCreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        this.customerCreatedBy = createdBy;
    }

    public Timestamp getLastUpdate() {
        return customerLastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.customerLastUpdate = lastUpdate;
    }

    public String getLastUpdatedBy() {
        return customerLastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.customerLastUpdatedBy = lastUpdatedBy;
    }

    public int getDivisionID() {
        return divisionID;
    }

    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }
}

//INFO FROM ERD
//Customer_ID INT(10) (PK)
//Customer_Name VARCHAR(50)
// Address VARCHAR(100)
// Postal_Code VARCHAR(50)
// Phone VARCHAR(50)
// Create_Date DATETIME
// Created_By VARCHAR(50)
// Last_Update TIMESTAMP
// Last_Updated_By VARCHAR(50)
// Division_ID INT(10) (FK)
