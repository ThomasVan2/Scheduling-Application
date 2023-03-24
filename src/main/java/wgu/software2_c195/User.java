package wgu.software2_c195;

import wgu.software2_c195.DAO.Query;
import wgu.software2_c195.DAO.UserDAOlmpl;

import java.util.Calendar;
/**User class*/
public class User {
    private int userId;
    private String name;
    private String password;
    private Calendar createDate;
    private String createdBy;
    private Calendar lastUpdate;
    private String lastUpdateDay;

    public User(int userId, String name, String password, Calendar createDate, String createdBy, Calendar lastUpdate, String lastUpdateDay) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate =lastUpdate;
        this.lastUpdateDay = lastUpdateDay;

    }
    /** @return name*/
    public String getName() {
        return name;
    }
    /** @return password*/
    public String getPassword() {
        return password;
    }
    /** @return create date*/
    public Calendar getCreateDate() {
        return createDate;
    }
    /** @return user id*/
    public int getUserId() {
        return userId;
    }
    /** @return last update time and date*/
    public Calendar getLastUpdate() {
        return lastUpdate;
    }
    /** @return created by*/
    public String getCreatedBy() {
        return createdBy;
    }
    /** @return last update date and time*/
    public String getLastUpdateDay() {
        return lastUpdateDay;
    }
    /** sets name*/
    public void setName(String name) {
        this.name = name;
    }
    /** sets password*/
    public void setPassword(String password) {
        this.password = password;
    }
    /** sets user id*/
    public void setUserId(int userId) {
        this.userId = userId;
    }
    /** sets created date*/
    public void setCreateDate(Calendar createDate) {
        this.createDate = createDate;
    }
    /** sets created by*/
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    /** sets last update date and time*/
    public void setLastUpdate(Calendar lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    /** sets last update*/
    public void setLastUpdateDay(String lastUpdateDay) {
        this.lastUpdateDay = lastUpdateDay;
    }
}
