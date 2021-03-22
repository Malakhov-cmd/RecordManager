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
 The window required to process multiply two number with assembly function
 @author George Makakhov
 @version 1.0
  * */

public class AssemblyMulty extends Application implements Initializable {
    /** x value input TextArea*/
    public TextArea xInPutArea;
    /** y value input TextArea*/
    public TextArea yInPutArea;

    /** Start calculating button*/
    public Button startCalc;

    /**Entered x value*/
    private int x;
    /**Entered y value*/
    private int y;

    /**Log updater @see Updater */
    Updater updater = new Updater();

    /**Digit pattern to check correct input*/
    private Pattern patternDigit = Pattern.compile("\\d*");

    /**
     * Load graphic FXML file
     * @param stage represents a window in a JavaFX desktop application
     * @exception IOException Wrong load FXML file
     **/
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("View/AssemblyMulty.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("MultyApp");
        stage.show();

        stage.setOnCloseRequest(windowEvent -> {
            updater.updateLog("Close Assembly tab");
        });
    }

    /**
     * Action perform for Graphic element
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updater.updateLog("Open Assembly tab");

        /**
         * Action event on click to Start Calculate button
         * */
        startCalc.setOnAction(actionEvent -> {

            if (patternDigit.matcher(xInPutArea.getText()).matches() && patternDigit.matcher(yInPutArea.getText()).matches()) {
                x = Integer.parseInt(xInPutArea.getText());
                y = Integer.parseInt(yInPutArea.getText());

                MultyJNI multyJNI = new MultyJNI();

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Correct! " + "\n" +
                        "Result is " + multyJNI.calc(x, y));
                alert.showAndWait();

                updater.updateLog("Successfully calculate");
            } else {
                Throwable t = new NullPointerException("Error");
                Alert alert = new Alert(Alert.AlertType.ERROR, t.getMessage());
                alert.setHeaderText("Detail");

                TextArea stackTrace = new TextArea();

                stackTrace.setText("Incorrect x or y input");

                alert.getDialogPane().setExpandableContent(stackTrace);
                alert.showAndWait();

                updater.updateLog("Get an error while calculate with Assembly");
            }
        });
    }
}
