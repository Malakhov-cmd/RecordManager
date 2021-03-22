import java.io.*;
import java.util.ArrayList;

/**
 * Record data
 *
 * @author George Malakhov
 * @version 1.0
 */
public class Record {
    /**
     * user login
     */
    private String login;
    /**
     * user hashcode of password
     */
    private String hashCode;
    /**
     * user email
     */
    private String email;

     /**
     * Initialize Record {@link Record#login}, {@link Record#hashCode} {@link Record#email}
     * @see Record
     * */
    public Record(String login, String hashCode, String email) {
        this.login = login;
        this.hashCode = hashCode;
        this.email = email;
    }

    /**
     * Get login
     * @return login
     * */
    public String getLogin() {
        return login;
    }

    /**
     * Set login
     * @param login new login value
     * */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Get hashcode
     * @return hashcode value
     * */
    public String getHashCode() {
        return hashCode;
    }

    /**
     * Set hashcode
     * @param hashCode new hashcode value
     * */
    public void setHashCode(String hashCode) {
        this.hashCode = hashCode;
    }

    /**
     * Get email
     * @return e,ail value
     * */
    public String getEmail() {
        return email;
    }

    /**
     * Set email
     * @param email new e,ail value
     * */
    public void setEmail(String email) {
        this.email = email;
    }
}
