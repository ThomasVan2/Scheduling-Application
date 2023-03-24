package wgu.software2_c195;

import wgu.software2_c195.DAO.StatesDao;
import wgu.software2_c195.DAO.StatesDaoImpl;

import java.sql.SQLException;
import java.time.LocalDateTime;

/**Customer Class*/
public class Customer {
    private int id;
    private String name;
    private String phonenumber;
    private String address;
    private String postalCode;
    private int divisionId;
    private LocalDateTime createData;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdateDay;
    StatesDao statesDao = new StatesDaoImpl();

    public Customer(int id, String name, String address , String postalCode, String phonenumber, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdateDay, int divisionId ) throws SQLException {
        this.id = id;
        this.name = name;
        this.phonenumber = phonenumber;
        this.address = address;
        this.postalCode = postalCode;
        this.divisionId = divisionId;
        this.createData = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateDay = lastUpdateDay;
    }
    /** sets name*/
    public void setName(String name) {
        this.name = name;
    }
    /** sets address*/
    public void setAddress(String address) {
        this.address = address;
    }
    /** sets id*/
    public void setId(int id) {
        this.id = id;
    }
    /** sets phone number*/
    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
    /** sets postal code*/
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    /** sets last updated by*/
    public void setLastUpdateDay(String lastUpdateDay) {
        this.lastUpdateDay = lastUpdateDay;
    }
    /** sets last update time and date*/
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    /** sets created by*/
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy;}

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }
    /** sets created date and time*/
    public void setCreateData(LocalDateTime createData) {
        this.createData = createData;
    }
    /** @return name*/
    public String getName() {
        return name;
    }
    /** @return id*/
    public int getId() {
        return id;
    }
    /** @return address*/
    public String getAddress() {
        return address;
    }
    /** @return phone number*/
    public String getPhonenumber() {
        return phonenumber;
    }
    /** @return postal code*/
    public String getPostalCode() {
        return postalCode;
    }
    /** @return last updated by*/
    public String getLastUpdateDay() {
        return lastUpdateDay;
    }
    /** @return created by*/
    public String getCreatedBy() {
        return createdBy;
    }
    /** @return last update date and time*/
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }
    /** @return created date and time*/
    public LocalDateTime getCreateData() {
        return createData;
    }
    /** @return division id*/
    public int getDivisionId() {
        return divisionId;
    }
    /** @return state name through its division id*/
    public String getState() throws SQLException {String name = statesDao.getStateName(divisionId); return name;}

}
