package wgu.software2_c195.DAO;

import javafx.collections.ObservableList;
import wgu.software2_c195.Contacts;

import java.sql.SQLException;
/**This is the interface of contactsDAOImpl*/
public interface ContactsDAO {
    /** This method returns all contacts.
      @return list of all contacts
     @throws SQLException throws an exception that provides information on a database access error or other errors.*/
    public ObservableList<Contacts> allContacts() throws SQLException;
    /** This method returns a list of all contacts ids'
      @return list of all contacts id's*/
    public ObservableList<Integer>ContactIds();
    /** This method returns all contacts names.
      @return list of all contacts names*/
    ObservableList<String> ContactNames();
    /** This method returns a contacts name associated with the contacts' id.
      @param contactId the id of the contact
     @return contacts name associated with the id.*/
    String ContactName(int contactId);
}
