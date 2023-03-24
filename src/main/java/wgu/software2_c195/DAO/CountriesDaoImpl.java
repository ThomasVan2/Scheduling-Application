package wgu.software2_c195.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import wgu.software2_c195.Countries;

import java.sql.ResultSet;
import java.sql.SQLException;
/**Countries database implementation class*/
public class CountriesDaoImpl implements CountriesDao {
    ObservableList<Countries>allCountries = FXCollections.observableArrayList();
    /** This method connects to the database and sets the data into a list.
     @throws SQLException throws am exception that provides information on a database access error or other errors. */
    public CountriesDaoImpl() throws SQLException {

        DBconnection.openConnection();
        String sqlStatement2 = "select * FROM countries";
        Query.makeQuery(sqlStatement2);
        String countryName = "none";
        int countryId = 0;
        ResultSet result2 = Query.getResult();
        while (result2.next()) {
            countryName = result2.getString("Country");
            countryId = result2.getInt("Country_ID");

            Countries addCountry = new Countries(countryId, countryName);

            allCountries.add(addCountry);
        }
        DBconnection.closeConnection();

    }

    @Override
    /**{@inheritDoc}*/
    public ObservableList<Countries> getAllCountries(){
        return allCountries;
    }

    @Override
    /**{@inheritDoc}*/
    public Integer getCountryId(String name) throws SQLException {

        int countryId = 12;

        for(int i = 0; i < allCountries.size(); i++) {

            if(allCountries.get(i).getName().equals(name)) {

                countryId = allCountries.get(i).getId();

            }

        }

        return countryId;
    }

    @Override
    /**{@inheritDoc}*/
    public String getCountryName(int id) throws SQLException {
        String countryName ="";
        for(int i = 0; i < allCountries.size(); i++) {

            if(allCountries.get(i).getId() == id) {

                countryName = allCountries.get(i).getName();
            }
        }
        return countryName;
    }

    @Override
    /**{@inheritDoc}*/
    public ObservableList<String> getAllCountriesName() throws SQLException {

        ObservableList<String>countryNames = FXCollections.observableArrayList();

        for(int i = 0; i < allCountries.size(); i++) {

            countryNames.add(allCountries.get(i).getName());

        }

        return countryNames;
    }
}
