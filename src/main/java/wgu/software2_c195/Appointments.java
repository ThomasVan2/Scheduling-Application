package wgu.software2_c195;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
/**Appointments class*/
public class Appointments {

    private int appointmentId;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;
    private int costumerId;
    private int userId;
    private int contactId;

    public Appointments(int appointmentId, String title, String description, String location, String type, LocalDateTime start,
                        LocalDateTime end, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy, int costumerId, int userId, int contactId) {

        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.costumerId = costumerId;
        this.userId = userId;
        this.contactId = contactId;

    }
    /** sets appointment id. */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }
    /** sets title.*/
    public void setTitle(String title) {
        this.title = title;
    }
    /** sets description.*/
    public void setDescription(String description) {
        this.description = description;
    }
    /** sets location.*/
    public void setLocation(String location) {
        this.location = location;
    }
    /** sets type.*/
    public void setType(String type) {
        this.type = type;
    }
    /** sets start date.*/
    public void setStart(LocalDateTime start) {
        this.start = start;
    }
    /** sets end date.*/
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }
    /** sets create date.*/
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }
    /** sets created by.*/
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    /** sets last update.*/
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    /** sets last updated by.*/
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
    /** sets customer id.*/
    public void setCostumerId(int costumerId) {
        this.costumerId = costumerId;
    }
    /** sets user id.*/
    public void setUserId(int userId) {
        this.userId = userId;
    }
    /** sets contact id.*/
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
    /** @return appointment id*/
    public int getAppointmentId() {
        return appointmentId;
    }
    /** @return title*/
    public String getTitle() {
        return title;
    }
    /** @return  description*/
    public String getDescription() {
        return description;
    }
    /** @return location*/
    public String getLocation() {
        return location;
    }
    /** @return type*/
    public String getType() {
        return type;
    }
    /** @return start date and time*/
    public LocalDateTime getStart() { return start; }
    /** @return end date and time*/
    public LocalDateTime getEnd() { return end;}
    /** @return create date*/
    public LocalDateTime getCreateDate() {
        return createDate;
    }
    /** @return created by*/
    public String getCreatedBy() {
        return createdBy;
    }
    /** @return last update*/
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }
    /** @return lasted update by*/
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }
    /** @return customer id*/
    public int getCostumerId() {
        return costumerId;
    }
    /** @return user id*/
    public int getUserId() {
        return userId;
    }
    /** @return contact id*/
    public int getContactId() {
        return contactId;
    }
    /** @return start time in users time*/
    public LocalDateTime getUserStart() { return Utility.UserTime(start); }
    /** @return end time in users time*/
    public LocalDateTime getUserEnd() { return Utility.UserTime(end); }
}
