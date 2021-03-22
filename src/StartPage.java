import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The start window
 *
 * @author George Malakhov
 * @version 1.0
 */
public class StartPage extends Application implements Initializable {
    /**
     * MenuItem to open SeqChecker window
     *
     * @see SeqChecker
     */
    public MenuItem enterSeq;
    /**
     * MenuItem to open modal window with information how to work with SeqChecker
     *
     * @see SeqChecker
     */
    public MenuItem infoSeq;

    /**
     * MenuItem to open FileBaseFX window
     *
     * @see FileBaseFX
     */
    public MenuItem enterRecord;

    /**
     * MenuItem to open AssemblyMulty window
     *
     * @see AssemblyMulty
     */
    public MenuItem enterAssembly;

    /**
     * Open modal window with information of this program
     */
    public MenuItem aboutProgram;
    /**
     * Open modal window with information about programer
     */
    public MenuItem aboutProgrammist;

    /**
     * TextArea to display logs
     */
    public TextArea logArea = new TextArea();

    /**
     * Load graphic FXML file
     *
     * @param stage represents a window in a JavaFX desktop application
     * @throws IOException Wrong load FXML file
     **/
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("View/StartPage.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("MultyApp");
        stage.show();
    }

    /**
     * Action perform for Graphic element
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Updater updateLog = new Updater(logArea);
        updateLog.updateLog("Start");

        enterSeq.setOnAction(event -> {
            SeqChecker checker = new SeqChecker();
            myLaunch(checker);
        });

        infoSeq.setOnAction(actionEvent -> {
            Throwable t = new NullPointerException("Instruction");
            Alert alert = new Alert(Alert.AlertType.INFORMATION, t.getMessage());
            alert.setHeaderText("Details");

            TextArea stackTrace = new TextArea();

            stackTrace.setText("Task: perform the operation of comparing two variables and raise them to the specified degree" + "\n" +
                    "Numbers: can be integers, real numbers, and negative numbers without using the power notation \" E\"" + "\n" +
                    "Variables: Variable number one should be called \"result\"." + "\n" +
                    "Variable number two should be called \"oper1\"." + "\n" +
                    "Variable number three should be called \"oper2\"." + "\n" +
                    "All variables must be of type double" + "\n" +
                    "Comparison signs: allowed signs \"<\", \">\", \"=\", \">=\", \"<=\", \"!=\"" + "\n" +
                    "Transitions to new lines: According to the example");

            alert.getDialogPane().setExpandableContent(stackTrace);
            alert.showAndWait();

            updateLog.updateLog("View instruction to Sequence Checker");
        });

        enterRecord.setOnAction(actionEvent -> {
            FileBaseFX fileBase = new FileBaseFX();
            myLaunch(fileBase);

        });

        enterAssembly.setOnAction(actionEvent -> {
            AssemblyMulty assemblyMulty = new AssemblyMulty();
            myLaunch(assemblyMulty);
        });

        aboutProgram.setOnAction(actionEvent -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "the program provides the following features: " + "\n" +
                    "Сhecking for the correctness and execution of the user-written code-sequence" + "\n" +
                    "Storage and processing of data entered by the user" + "\n" +
                    " using the database" + "\n" +
                    "Сalculating the product of two numbers using an assembly" + "\n" +
                    " function library");
            alert.showAndWait();

            updateLog.updateLog("View information about program");
        });

        aboutProgrammist.setOnAction(actionEvent -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Created by: Malakhov Geargey");
            alert.showAndWait();

            updateLog.updateLog("View information about programist");
        });
    }

    /**
     * Open Window
     *
     * @param applicationClass object of class that need to launch
     */
    public static void myLaunch(Application applicationClass) {
        Platform.runLater(() -> {
            try {
                Stage primaryStage = new Stage();
                applicationClass.start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
