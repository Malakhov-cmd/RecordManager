import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 The window required to process work with Record data
 @author George Makakhov
 @version 1.0
  * */
public class FileBaseFX extends Application implements Initializable, FileBaseView {
    /** Class of model in MVP pattern
     * Provide work with database*/
    private PostgreSQLJDBC_Model postgreSQLJDBC_model = new PostgreSQLJDBC_Table();
    /** Class of Presenter in MVP pattern
     * Provide business logic*/
    private IPresenter presenter = new Presenter(postgreSQLJDBC_model, this);

    /** TableView to visualize element in database*/
    public TableView table = new TableView<Record>();

    /** Add record button*/
    public Button addRecord;
    /** Delete record button*/
    public Button deleteRecord;
    /** Back to main window button*/
    public Button back;

    /** MenuItem that contains Example usage*/
    public MenuItem fileBaseExample;
    /** MenuItem that contains instruction to usage*/
    public MenuItem fileBaseInstruction;

    /**Log updater @see Updater */
    Updater updater = new Updater();

    /**
     * Load graphic FXML file
     * @param stage represents a window in a JavaFX desktop application
     * @exception IOException Wrong load FXML file
     **/
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("View/FileBase.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("MultyApp");
        stage.show();

        stage.setOnCloseRequest(this::setOnCloseRequest);
    }

    /**
     * Action perform for Graphic element
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        presenter.setModel(postgreSQLJDBC_model);
        presenter.setView(this);

        table.setEditable(true);

        TableColumn<Record, String> loginColumn = new TableColumn<>("Login");
        loginColumn.setCellValueFactory(new PropertyValueFactory<>("login"));
        loginColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        loginColumn.setMinWidth(160);
        table.getColumns().add(loginColumn);

        TableColumn<Record, String> hashCodeColumn = new TableColumn<>("Hashcode");
        hashCodeColumn.setCellValueFactory(new PropertyValueFactory<>("hashCode"));
        hashCodeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        hashCodeColumn.setMinWidth(250);
        table.getColumns().add(hashCodeColumn);

        TableColumn<Record, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        emailColumn.setMinWidth(200);
        table.getColumns().add(emailColumn);

        presenter.init();

        addRecord.setOnAction(this::addSetOnAction);

        table.setOnMouseClicked(mouseEvent -> {
            deleteRecord.setOnAction(this::deleteSetOnAction);
        });

        back.setOnAction(this::backSetOnAction);

        EventHandler<TableColumn.CellEditEvent<Record, String>> cellEditEventEventHandler = event -> {
            updater.updateLog("Update value from table");
            TablePosition<Record, String> pos = event.getTablePosition();

            String newFullName = event.getNewValue();

            int row = pos.getRow();
            int column = pos.getColumn();

            Record record = event.getTableView().getItems().get(row);

            getPresenter().edit(column, newFullName, record);
        };

        loginColumn.setOnEditCommit(cellEditEventEventHandler);
        hashCodeColumn.setOnEditCommit(cellEditEventEventHandler);
        emailColumn.setOnEditCommit(cellEditEventEventHandler);

        fileBaseExample.setOnAction(this::fileBaseExambple);
        fileBaseInstruction.setOnAction(this::fileBaseInstruction);
    }

    /**
     * Get Presenter of this view
     * @return current Presenter
     * */
    @Override
    public IPresenter getPresenter() {
        return presenter;
    }

    /**
     * Action on case of close FileBaseFX window
     * @param event Event that present current window
     * */
    @Override
    public void setOnCloseRequest(WindowEvent event) {
        getPresenter().exit();
    }

    /**
     * Action on case of delete record from FileBaseFX window
     * @param event Event that present action from delete button
     * */
    @Override
    public void deleteSetOnAction(ActionEvent event) {
        presenter.delete();
    }

    /**
     * Action on case of close FileBaseFX window
     * @param event Event that present action from back button
     * */
    @Override
    public void backSetOnAction(ActionEvent event) {
        getPresenter().exit();
        Stage stage = (Stage) back.getScene().getWindow();
        stage.close();
    }

    /**
     * Action on case of add record to FileBaseFX window
     * @param event Event that present action from  add button
     * */
    @Override
    public void addSetOnAction(ActionEvent event) {
        presenter.addCall();
    }

    /**
     * Action on case of show example of FileBaseFX window
     * @param event Event that present action from  Example MenuItem
     * */
    @Override
    public void fileBaseExambple(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "You can enter any sequence of characters, provided that" + "\n" +
                " the fields are unique and the email field value is less than 50 characters.");
        alert.showAndWait();

        updater.updateLog("View example in module file manager");
    }

    /**
     * Action on case of show instruction of FileBaseFX window
     * @param event Event that present action from  Instruction MenuItem
     * */
    @Override
    public void fileBaseInstruction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "To delete, select the rows and click delete." + "\n" +
                "To change the contents of a cell, click on it 2 times, enter the new values, and press enter." + "\n" +
                "To add new values, click the add button, enter the data for the new record in the pop-up window" +"\n" +
                ", and then press ok."+"\n" +
                "To save all your data just close the window or click back." );
        alert.showAndWait();

        updater.updateLog("View instructions in module file manager");
    }

    /**
     * TableView from FileBaseFX
     * @return  TableView table from FileBaseFX
     * */
    public TableView getTable() {
        return table;
    }
}
