import java.util.ArrayList;

/**
 Interface of Model component in MVP pattern
 @author George Makakhov
 @version 1.0
  * */
public interface PostgreSQLJDBC_Model {
    /**
     * Create database
     * */
    void create();

    /**
     * Insert new record in database
     * @param login user login
     * @param hashCode user hashcode of password
     * @param email user email
     * */
    void insert(String login, String hashCode, String email);

    /** Select all record from database
     * @return List of all record in database
     * */
    ArrayList<Record> selectAll();

    /**Update record in database
     * @param column  number of column record that must be performed
     * @param newValue String value of new data
     * @param login user login
     * @param hashCode user hashcode of password
     * @param email user email
     * @return List of all record in database
     * */
    ArrayList<Record> update(int column, String newValue, String login, String hashCode, String email);

    /**
     * Delete record from database
     * @param login User login that must to deleted
     * @return List of all record in database
     * */
    ArrayList<Record> delete(String login);
}
