package wgu.software2_c195.DAO;

import javafx.collections.ObservableList;
import wgu.software2_c195.Countries;

import java.sql.SQLException;
/**This is the interface of CountriesDAOImpl*/
public interface CountriesDao {
    /** This method returns a list of all countries.
      @return List of countries*/
    public ObservableList<Countries> getAllCountries() throws SQLException;
    /** This method returns a country id based on the countries name.
      @return country id*/
    public Integer getCountryId(String name) throws SQLException;
    /** This method returns a contries name.
      @param id A country id
      @return A country name*/
    public String getCountryName(int id) throws SQLException;
    /** This method returns a list of all the countries names.
      @return list of al countries names.*/
    public ObservableList<String>getAllCountriesName() throws SQLException;
}
