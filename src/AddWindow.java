import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

/**
 The window required to fill in the information about the new record
@author George Makakhov
@version 1.0
 * */

public class AddWindow extends Application implements Initializable {
    /** Login input TextArea*/
    public TextArea loginInPutArea;
    /** Hashcode input TextArea*/
    public TextArea hashCodeInPutArea;
    /** Email input TextArea*/
    public TextArea emailInPutArea;
    /** Add button to try enter record*/
    public Button Add;

    /**Entered login*/
    private String login;
    /**Entered hashcode*/
    private String hashCode;
    /**Entered email*/
    private String email;

    /**Log updater @see Updater */
    Updater updater = new Updater();

    /**Email pattern to check correct input*/
    private Pattern patternEmail = Pattern.compile("^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$");

    /**
     * Load graphic FXML file
     * @param stage represents a window in a JavaFX desktop application
     * @exception IOException Wrong load FXML file
     **/
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("View/AddRecord.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("MultyApp");

        stage.show();
    }

    /**
     * Action perform for Graphic element
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /**
         * Action event on click to Add button
         * */
        Add.setOnAction(event ->
        {
            if (patternEmail.matcher(emailInPutArea.getText()).matches()) {

                this.login = loginInPutArea.getText();
                this.hashCode = hashCodeInPutArea.getText();
                this.email = emailInPutArea.getText();

                PostgreSQLJDBC_Table postgreSQLJDBC_table = new PostgreSQLJDBC_Table();
                postgreSQLJDBC_table.insert(this.login, this.hashCode, this.email);

                updater.updateLog("Add value from add window");
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error! Incorrect type of mail");
                alert.showAndWait();

                updater.updateLog("Error! Incorrect type of mail");
            }
            Presenter.endingAdd = true;
            Stage stage = (Stage) Add.getScene().getWindow();
            stage.close();
        });
    }
}
