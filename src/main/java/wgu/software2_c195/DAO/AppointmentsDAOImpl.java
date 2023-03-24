package wgu.software2_c195.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import wgu.software2_c195.Appointments;
import wgu.software2_c195.Utility;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;
/**Appointment database implement class*/
public class AppointmentsDAOImpl implements AppointmentsDAO {
    ObservableList<Appointments> allAppointments;
    ObservableList<Appointments> monthlyAppiontments;
    ObservableList<Appointments> weeklyAppointments;
    ObservableList<String>Types;
    DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    ObservableList<Appointments> selectedContact;
    ObservableList<Appointments> typeAndMonth;
    ObservableList<Appointments> getTypes;
    /** This method connects to the database and sets the data into a list.
      @throws SQLException throws am exception that provides information on a database access error or other errors. */
    public AppointmentsDAOImpl() throws SQLException, ParseException {

        allAppointments = FXCollections.observableArrayList();

        DBconnection.openConnection();
        String sqlStatement = "select * from appointments";
        Appointments appointmentResult;
        Query.makeQuery(sqlStatement);
        ResultSet result = Query.getResult();

        while (result.next()) {

            int appointmentIdG = result.getInt("Appointment_ID");
            String title = result.getString("Title");
            String description = result.getString("Description");
            String location = result.getString("Location");
            String type = result.getString("Type");
            String createDate = result.getString("Create_Date");
            String createBy = result.getString("Created_By");
            String lastUpdate = result.getString("Last_Update");
            String lastUpdateBy = result.getString("Last_Updated_By");
            int costumerId = result.getInt("Customer_ID");
            int userId = result.getInt("User_ID");
            int contactId = result.getInt("Contact_ID");
            LocalDateTime startCalendar = LocalDateTime.parse(result.getString("Start"), DATE_TIME_FORMATTER);
            LocalDateTime endCalendar = LocalDateTime.parse(result.getString("End"), DATE_TIME_FORMATTER);
            LocalDateTime createDateCalendar = LocalDateTime.parse(result.getString("Create_Date"), DATE_TIME_FORMATTER);
            LocalDateTime lastUpdateCalendar = LocalDateTime.parse(result.getString("Last_Update"), DATE_TIME_FORMATTER);

            appointmentResult = new Appointments(appointmentIdG, title, description, location, type, startCalendar, endCalendar, createDateCalendar, createBy, lastUpdateCalendar,
                    lastUpdateBy, costumerId,userId,contactId);
            allAppointments.add(appointmentResult);
        }

        DBconnection.closeConnection();

    }
    @Override
    /**{@inheritDoc}*/
    public Appointments getAppointment(int appointmentId) {

        Appointments appointment = null;

        for(int i = 0; i < allAppointments.size(); i++) {

            if(allAppointments.get(i).getAppointmentId() == appointmentId) {

                appointment = allAppointments.get(i);

            }

        }
        return appointment;
    }
    @Override
    /**{@inheritDoc}*/
    public int generateAppId() {
        int newId = 0;

        for(int i = 0; i < allAppointments.size(); i++) {

            newId = allAppointments.get(i).getAppointmentId();

        }

        return  newId + 1;
    }
    @Override
    /**{@inheritDoc}*/
    public ObservableList<Appointments> getAllAppointments(){
            return allAppointments;
    }
    @Override
    /**{@inheritDoc}*/
    public ObservableList<Appointments> getMonthlyAppointments() throws ParseException {

        monthlyAppiontments = FXCollections.observableArrayList();
        LocalDate currentDate = LocalDate.now(ZoneOffset.UTC);
        LocalDate currentDay = LocalDate.from(currentDate);
        LocalDate monthToday = currentDay.plusMonths(1);

        for(int i = 0; i < getAllAppointments().size(); i++) {

          LocalDateTime appointmentTimeDate = allAppointments.get(i).getStart();
          LocalDate appointmentsDate = appointmentTimeDate.toLocalDate();

            if (currentDate.compareTo(appointmentsDate) <= 0 && appointmentsDate.compareTo(monthToday) < 0) {

                monthlyAppiontments.add(getAllAppointments().get(i));

            }

        }

        return monthlyAppiontments;

    }
    @Override
    /**{@inheritDoc}*/
    public ObservableList<Appointments> getWeeklyAppointments() {

        weeklyAppointments = FXCollections.observableArrayList();

        LocalDate currentDate = LocalDate.now(ZoneOffset.UTC);
        LocalDate currentDay = LocalDate.from(currentDate);
        LocalDate weekToday = currentDay.plusWeeks(1);

        for(int i = 0; i < getAllAppointments().size(); i++) {

            LocalDateTime appointmentTimeDate = allAppointments.get(i).getStart();
            LocalDate appointmentsDate = appointmentTimeDate.toLocalDate();

            if (currentDate.compareTo(appointmentsDate) <= 0 && appointmentsDate.compareTo(weekToday) < 0) {

                weeklyAppointments.add(getAllAppointments().get(i));
                System.out.println(getAllAppointments().get(i));
            }

        }

        return weeklyAppointments;

    }
    @Override
    /**{@inheritDoc}*/
    public ObservableList<String> Types() {
        Types = FXCollections.observableArrayList("Planning Session", "Emergency Meeting", "Brainstorming Meeting", "De-Briefing");
        return Types;
    }

    @Override
    /**{@inheritDoc}*/
    public void UpdateAppointment(Appointments appointments) throws SQLException {
        int appointmentId = appointments.getAppointmentId();
        DBconnection.openConnection();
        String sqlStatement = "update appointments SET Title = ? ,Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = '" + appointmentId + "'";
        PreparedStatement ps = DBconnection.connection.prepareStatement(sqlStatement);
        ps.setString(1, appointments.getTitle());
        ps.setString(2, appointments.getDescription());
        ps.setString(3, appointments.getLocation());
        ps.setString(4, appointments.getType());
        ps.setString(5, String.valueOf(appointments.getStart()));
        ps.setString(6, String.valueOf(appointments.getEnd()));
        ps.setString(7, String.valueOf(appointments.getLastUpdate()));
        ps.setString(8 ,appointments.getLastUpdatedBy());
        ps.setInt(9, appointments.getCostumerId());
        ps.setInt(10, appointments.getUserId());
        ps.setInt(11, appointments.getContactId());
        ps.execute();

    }
    @Override
    /**{@inheritDoc}*/
    public void addAppointment(Appointments appointment) throws SQLException {

        DBconnection.openConnection();
        String sqlStatement = "insert into appointments (Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID)" +
                "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = DBconnection.connection.prepareStatement(sqlStatement);
        ps.setInt(1, appointment.getAppointmentId());
        ps.setString(2, appointment.getTitle());
        ps.setString(3, appointment.getDescription());
        ps.setString(4, appointment.getLocation());
        ps.setString(5, appointment.getType());
        ps.setString(6, String.valueOf(appointment.getStart()));
        ps.setString(7, String.valueOf(appointment.getEnd()));
        ps.setString(8, String.valueOf(appointment.getCreateDate()));
        ps.setString(9, appointment.getCreatedBy());
        ps.setString(10, String.valueOf(appointment.getLastUpdate()));
        ps.setString(11, appointment.getLastUpdatedBy());
        ps.setInt(12, appointment.getCostumerId());
        ps.setInt(13, appointment.getUserId());
        ps.setInt(14, appointment.getContactId());
        ps.execute();

    }

    @Override
    /**{@inheritDoc}*/
    public void deleteAppointment(int appointmentId) throws SQLException {
        DBconnection.openConnection();
        String sqlStatement = "delete FROM appointments WHERE Appointment_ID = '" + appointmentId + "'";
        PreparedStatement ps = DBconnection.connection.prepareStatement(sqlStatement);
        ps.execute();
        for(int i = 0; i < allAppointments.size(); i++) {
            if(appointmentId == allAppointments.get(i).getAppointmentId())
                allAppointments.remove(i);
        }
    }
    @Override
    /**{@inheritDoc}*/
    public void appointmentAlert(){

        Instant now = Instant.now();

        LocalDateTime localNow = LocalDateTime.ofInstant(now, ZoneOffset.UTC);

        LocalDateTime interval = LocalDateTime.now(ZoneOffset.UTC).plusMinutes(15);

        for(int i = 0; i < allAppointments.size(); i++) {

            if(allAppointments.get(i).getStart().isBefore(interval) && allAppointments.get(i).getStart().isAfter(localNow)) {

                Utility.confDisplay("Appointment coming up: Appointment ID: " + allAppointments.get(i).getAppointmentId() +" Appointment Date/Time: " + Utility.UserTime(allAppointments.get(i).getStart()) );
                return;
            }
        }

        Utility.confDisplay("No Appointments within the next 15 minutes");
    }
    @Override
    /**{@inheritDoc}*/
    public ObservableList<Appointments> selectedContact(int id) {

        selectedContact = FXCollections.observableArrayList();

        for(int i = 0; i < allAppointments.size(); i++)  {

            if(allAppointments.get(i).getContactId() == id) {

                selectedContact.add(allAppointments.get(i));
            }
        }
        return selectedContact;
    }
    @Override
    /**{@inheritDoc}*/
    public ObservableList<Appointments> byTypeAndMonth(String Type, String month) throws ParseException {

        typeAndMonth = FXCollections.observableArrayList();
        SimpleDateFormat fmt = new SimpleDateFormat("MMMM", Locale.US);
            Calendar cal = Calendar.getInstance();
            cal.setTime(fmt.parse(month));
            int monthValue = cal.get(Calendar.MONTH) + 1;

        for(int i = 0; i < allAppointments.size(); i++) {

          if(allAppointments.get(i).getType().equals(Type) && allAppointments.get(i).getStart().getMonth().getValue() == monthValue) {

            typeAndMonth.add(allAppointments.get(i));
          }
        }
        return typeAndMonth;
    }

    @Override
    /**{@inheritDoc}*/
    public ObservableList<Appointments> getTypes(String Type) {

        getTypes = FXCollections.observableArrayList();

        for(int i = 0; i < getAllAppointments().size(); i++) {

            if(getAllAppointments().get(i).getType().equals(Type)) {

                getTypes.add(getAllAppointments().get(i));
            }
         }
        return getTypes;
     }
    @Override
    /**{@inheritDoc}*/
     public Appointments customerAppointment(int id) {

        for (int i = 0; i < allAppointments.size(); i++) {

            if(allAppointments.get(i).getCostumerId() == id) {

                return allAppointments.get(i);

            }

        }
        return null;
     }

}
