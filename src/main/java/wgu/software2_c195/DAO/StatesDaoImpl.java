package wgu.software2_c195.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import wgu.software2_c195.States;

import java.sql.ResultSet;
import java.sql.SQLException;
/**States database implement class*/
public class StatesDaoImpl implements StatesDao{
    ObservableList<States>allStates;
    /** This method connects to the database and sets the data into a list.
     @throws SQLException throws am exception that provides information on a database access error or other errors. */
    public StatesDaoImpl() throws SQLException {

        allStates = FXCollections.observableArrayList();

        DBconnection.openConnection();
        String sqlStatement2 = "select * FROM first_level_divisions";
        Query.makeQuery(sqlStatement2);
        String stateName = "none";
        int stateId = 0;
        int countryId;
        ResultSet result2 = Query.getResult();
        while (result2.next()) {
            stateName = result2.getString("Division");
            stateId = result2.getInt("Division_ID");
            countryId = result2.getInt("Country_ID");
            States addState = new States(stateId, stateName, countryId);

            allStates.add(addState);
        }
        DBconnection.closeConnection();
    }

    @Override
    /**{@inheritDoc}*/
    public ObservableList<States> getAllStates() throws SQLException {
        return allStates;
    }

    @Override
    /**{@inheritDoc}*/
    public Integer getStateId(String name) throws SQLException {

        int stateId = 0;

        for(int i = 0; i < allStates.size(); i++) {

            if(allStates.get(i).getName().equals(name)) {

                stateId = allStates.get(i).getId();

            }

        }

        return stateId;
    }
    @Override
    /**{@inheritDoc}*/
    public String getStateName(int id) throws SQLException {
        String name = "";

        for(int i = 0; i < allStates.size(); i++) {

            if(allStates.get(i).getId() == id) {

                name = allStates.get(i).getName();

            }

        }

        return name;
    }

    @Override
    /**{@inheritDoc}*/
    public ObservableList<String> getAllStatesName() throws SQLException {

        ObservableList<String>stateNames = FXCollections.observableArrayList();

        for(int i = 0; i < allStates.size(); i++) {

            String name = allStates.get(i).getName();

            stateNames.add(name);

            }
        return stateNames;

    }

    @Override
    /**{@inheritDoc}*/
    public ObservableList<String> returnStates(int countryId) {

        ObservableList<String>states = FXCollections.observableArrayList();

        for(int i = 0; i < allStates.size(); i++) {

            if(countryId == allStates.get(i).getCountryId()) {

                String name = allStates.get(i).getName();

                states.add(name);

                System.out.println(name);
            }

        }

        return states;
    }

    @Override
    /**{@inheritDoc}*/
    public States getState(String name) {

        States state = null;

        for (int i = 0; i < allStates.size(); i++) {

            if(allStates.get(i).getName().equals(name)) {

               state = allStates.get(i);
            }
        }
        return state;
    }

}
