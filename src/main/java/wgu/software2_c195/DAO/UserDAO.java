package wgu.software2_c195.DAO;

import javafx.collections.ObservableList;
import wgu.software2_c195.User;

import java.sql.SQLException;
import java.text.ParseException;
/**This is the interface of UserDAOImpl*/
public interface UserDAO {
    /** This method returns a user based in the name.
      @param userName name of the user.
     @return returns the user*/
    public User getUser(String userName);
    /** This method returns all users as a list.
      @return list of all the users.*/
    public ObservableList<User> getAllUsers() throws SQLException, ParseException;
    /** This method returns a list of all the users ids'.
      @return list of users id.*/
    public ObservableList<Integer>getAllUsersId();

}
