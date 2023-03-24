package wgu.software2_c195.DAO;

import javafx.collections.ObservableList;
import wgu.software2_c195.States;


import java.sql.SQLException;
/**This is the interface of StatesDAOImpl*/
public interface StatesDao {
    /** This method returns a list of all the states.
      @return list of all the states.
     @throws SQLException throws an exception that provides information on a database access error or other errors*/
    public ObservableList<States> getAllStates() throws SQLException;
    /** This method returns the state id associated with the name.
      @param name name of a state.
     @return the associated id.*/
    public Integer getStateId(String name) throws SQLException;
    /** This method returns the name of a state.
      @param id id of state.
     @return name of the state.*/
    public String getStateName(int id) throws SQLException;
    /** This method returns a list of all the name of the states.
      @return list of names of the states.*/
    public ObservableList<String>getAllStatesName() throws SQLException;
    /** This method returns a list a states associated with the country id.
      @param countryId id of the country.
     @return list of states within the country.*/
    public ObservableList<String> returnStates(int countryId);
    /** This method returns a state.
      @param name name of state
     @return the state associated with the name.*/
    public States getState(String name);

}
