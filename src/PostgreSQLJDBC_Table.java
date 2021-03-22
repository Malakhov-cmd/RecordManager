import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 Work with database
 Model component in MVP pattern
 @author George Malakhov
 @version 1.0
  * */
public class PostgreSQLJDBC_Table implements PostgreSQLJDBC_Model {
    Updater updater = new Updater();

    /**
     * Create database
     * */
    public void create() {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/postgres",
                            "postgres", "Orel-5287");
            System.out.println("Opened database successfully");
            updater.updateLog("Opened database successfully");

            stmt = c.createStatement();
            String sql = "CREATE TABLE RECORD( " +
                    " LOGIN           TEXT    NOT NULL, " +
                    " HASHCODE        TEXT    NOT NULL, " +
                    " EMAIL           CHAR(50), " +
                    " CONSTRAINT      UNIQUE_LOG UNIQUE(LOGIN)," +
                    " CONSTRAINT      UNIQUE_HASH UNIQUE(HASHCODE)," +
                    " CONSTRAINT      UNIQUE_MAIL UNIQUE(EMAIL)," +
                    " CONSTRAINT      UNIQUE_CONS UNIQUE(LOGIN, HASHCODE, EMAIL));";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        updater.updateLog("Table created successfully");
        System.out.println("Table created successfully");
    }

    /**
     * Insert new record in database
     * @param login user login
     * @param hashCode user hashcode of password
     * @param email user email
     * */
    public void insert(String login, String hashCode, String email) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/postgres",
                            "postgres", "Orel-5287");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "INSERT INTO RECORD (LOGIN, HASHCODE, EMAIL) "
                    + "VALUES (" + "'" +login+ "'" + ", " + "'" +hashCode + "'" +", " + "'" +email+ "'" + ");";
            stmt.executeUpdate(sql);

            stmt.close();
            c.commit();
            c.close();

            updater.updateLog("Record created successfully");
            System.out.println("Records created successfully");
        } catch (Exception e) {
            String error = new String(e.getClass().getName() + ": " + e.getMessage());
            updater.updateLog(error);
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    /** Select all record from database
     * @return List of all record in database
     * */
    public ArrayList<Record> selectAll() {
        ArrayList<Record> records = new ArrayList<>();

        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/postgres",
                            "postgres", "Orel-5287");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM RECORD;");
            while (rs.next()) {
                String login = rs.getString("LOGIN");
                String hashCode = rs.getString("HASHCODE");
                String email = rs.getString("EMAIL");

                Record record = new Record(login, hashCode, email);
                records.add(record);
            }
            rs.close();
            stmt.close();
            c.close();

            System.out.println("Operation done successfully");
            updater.updateLog("Select all record successfully");
        } catch (Exception e) {
            String error = e.getClass().getName() + ": " + e.getMessage();
            updater.updateLog(error);
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return records;
    }

    /**Update record in database
     * @param column  number of column record that must be performed
     * @param newValue String value of new data
     * @param login user login
     * @param hashCode user hashcode of password
     * @param email user email
     * @return List of all record in database
     * */
    public ArrayList<Record> update(int column, String newValue,String login, String hashCode, String email){
        ArrayList<Record> records = new ArrayList<>();

        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/postgres",
                            "postgres", "Orel-5287");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql;
            switch (column){
                case 0 -> {
                    sql = "UPDATE RECORD set LOGIN = " + "'" +newValue + "'" +" where LOGIN="+"'" + login + "'" + ";";
                }
                case 1 -> {
                    sql = "UPDATE RECORD set HASHCODE = " + "'" + newValue + "'" +" where LOGIN=" + "'" + login + "'" + ";";
                }
                case 2 -> {
                    sql = "UPDATE RECORD set EMAIL = " + "'" +newValue + "'" +" where LOGIN=" + "'" + login + "'" + ";";
                }
                default -> throw new IllegalStateException("Unexpected value: " + column);
            }
            stmt.executeUpdate(sql);
            c.commit();

            ResultSet rs = stmt.executeQuery("SELECT * FROM RECORD;");
            while (rs.next()) {
                String loginSTR = rs.getString("LOGIN");
                String hashCodeSTR = rs.getString("HASHCODE");
                String emailSTR = rs.getString("EMAIL");

                Record record = new Record(loginSTR, hashCodeSTR, emailSTR);
                records.add(record);
            }
            rs.close();
            stmt.close();
            c.close();

            System.out.println("Operation done successfully");
            updater.updateLog("Record update successfully");
        } catch (Exception e) {
            String error = new String(e.getClass().getName() + ": " + e.getMessage());
            updater.updateLog(error);
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return records;
    }


    /**
     * Delete record from database
     * @param login User login that must to deleted
     * @return List of all record in database
     * */
    public ArrayList<Record> delete(String login){
        ArrayList<Record> records = new ArrayList<>();

        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/postgres",
                            "postgres", "Orel-5287");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql;
            sql = "DELETE from RECORD where LOGIN= " + "'" + login + "'" + ";";
            stmt.executeUpdate(sql);
            c.commit();

            ResultSet rs = stmt.executeQuery("SELECT * FROM RECORD;");
            while (rs.next()) {
                String loginSTR = rs.getString("LOGIN");
                String hashCodeSTR = rs.getString("HASHCODE");
                String emailSTR = rs.getString("EMAIL");

                Record record = new Record(loginSTR, hashCodeSTR, emailSTR);
                records.add(record);
            }
            rs.close();
            stmt.close();
            c.close();

            updater.updateLog("Record delete successfully");
            System.out.println("Operation done successfully");
        } catch (Exception e) {
            String error = new String(e.getClass().getName() + ": " + e.getMessage());
            updater.updateLog(error);
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return records;
    }
}
