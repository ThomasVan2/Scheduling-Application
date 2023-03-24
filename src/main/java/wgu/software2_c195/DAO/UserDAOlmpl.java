package wgu.software2_c195.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import wgu.software2_c195.User;
import wgu.software2_c195.Utility;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
/**User database implement class*/
public class UserDAOlmpl implements UserDAO {
    ObservableList<User>allUsers;
    /** This method connects to the database and sets the data into a list.
     @throws SQLException throws am exception that provides information on a database access error or other errors. */
    public UserDAOlmpl() throws SQLException, ParseException {

        allUsers = FXCollections.observableArrayList();

        DBconnection.openConnection();
        String sqlStatement = "select * from users";
        User userResult;
        Query.makeQuery(sqlStatement);
        ResultSet result = Query.getResult();

        while (result.next()) {

            int userid = result.getInt("User_ID");
            String userNameG = result.getString("User_Name");
            String password = result.getString("Password");
            String createDate = result.getString("Create_Date");
            String createdBy = result.getString("Created_By");
            String lastUpdate = result.getString("Last_Update");
            String lastUpdateBy = result.getString("Last_Updated_By");
            Calendar createDateCalendar = Utility.calenderPick(createDate);
            Calendar lastUpdateCalendar = Utility.calenderPick(lastUpdate);

            userResult = new User(userid, userNameG, password, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateBy);
            allUsers.add(userResult);

        }

        DBconnection.closeConnection();


    }
    @Override
    /**{@inheritDoc}*/
    public User getUser(String userName){

        User user = null;

        for(int i = 0; i < allUsers.size(); i++) {

            if(userName.equals(allUsers.get(i).getName())) {

                user = allUsers.get(i);

            }
        }
        return user;
    }
    @Override
    /**{@inheritDoc}*/
    public ObservableList<User> getAllUsers(){return allUsers;}
    @Override
    /**{@inheritDoc}*/
    public ObservableList<Integer>getAllUsersId() {
        ObservableList<Integer>userIds = FXCollections.observableArrayList();
        for (int i = 0; i < allUsers.size(); i++) { userIds.add(allUsers.get(i).getUserId());}
        return userIds;
    }

}

