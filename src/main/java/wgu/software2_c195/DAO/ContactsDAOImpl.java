package wgu.software2_c195.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import wgu.software2_c195.Contacts;

import java.sql.ResultSet;
import java.sql.SQLException;
/**Contact database implement class*/
public class ContactsDAOImpl implements ContactsDAO{
    ObservableList<Contacts>allContacts;
    ObservableList<Integer>contactIds;
    ObservableList<String>contactNames;
    /** This method connects to the database and sets the data into a list.
     @throws SQLException throws am exception that provides information on a database access error or other errors. */
    public ContactsDAOImpl() throws SQLException {

        allContacts = FXCollections.observableArrayList();

        DBconnection.openConnection();
        String sqlStatement2 = "select * FROM contacts";
        Query.makeQuery(sqlStatement2);
        String Name = "none";
        int Id = 0;
        String email = "none";
        ResultSet result2 = Query.getResult();
        while (result2.next()) {
            Name = result2.getString("Contact_Name");
            Id = result2.getInt("Contact_ID");
            email = result2.getString("Email");

            Contacts addContacts = new Contacts(Id, Name, email);

            allContacts.add(addContacts);
        }
        DBconnection.closeConnection();

    }

    @Override
    /**{@inheritDoc}*/
    public ObservableList<Contacts> allContacts() throws SQLException {
        return allContacts;
    }

    @Override
    /**{@inheritDoc}*/
    public ObservableList<Integer> ContactIds() {

        contactIds = FXCollections.observableArrayList();

        for(int i = 0; i < allContacts.size(); i++) {

            contactIds.add(allContacts.get(i).getId());

        }

        return contactIds;
    }

    @Override
    /**{@inheritDoc}*/
    public ObservableList<String> ContactNames() {

        contactNames = FXCollections.observableArrayList();

        for(int i = 0; i < allContacts.size(); i++) {

            contactNames.add(allContacts.get(i).getName());

        }

        return contactNames;
    }
    @Override
    /**{@inheritDoc}*/
    public String ContactName(int contactId) {

        String name = "";

        for(int i = 0; i < allContacts.size(); i++) {

            if(allContacts.get(i).getId() == contactId) {

                name = allContacts.get(i).getName();

            }
        }

        return name;
    }

}
