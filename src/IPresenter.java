/**
 Interface of Presenter component in MVP pattern
 @author George Makakhov
 @version 1.0
  * */
public interface IPresenter {
    /**
     * Get Model of this Presenter connected
     * @return current Model
     * */
    PostgreSQLJDBC_Model getModel();

    /**
     * Set Model of this Presenter connected
     * @param model  model to connect to Presenter
     * */
    void setModel(PostgreSQLJDBC_Model model);

    /**
     * Get View of this Presenter connected
     * @return current View
     * */
    FileBaseView getView();

    /**
     * Set Model of this Presenter connected
     * @param view  view to connect to Presenter
     * */
    void setView(FileBaseView view);

    /** Initial operations that must be done before another*/
    void init();

    /** Open AddWindow window to entered data and add new record*/
    void addCall();

    /** Edit value in record
     * @param column number of column record that must be performed
     * @param newFullName String value of new data
     * @param record new object of Record with changed value*/
    void edit(int column, String newFullName, Record record);

    /** Delete record and update view*/
    void delete();

    /** Close FileBaseFX window and save all values to binary file*/
    void exit();
}
