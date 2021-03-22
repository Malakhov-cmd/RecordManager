import javafx.application.Application;
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
 * The window required to process work with Sequences
 *
 * @author George Malakhov
 * @version 1.0
 */
public class SeqChecker extends Application implements Initializable {
    /**
     * MenuItem that contains example usage
     */
    public MenuItem seqCheckerExample;
    /**
     * MenuItem that contains instruction to usage
     */
    public MenuItem seqCheckerInstruction;

    /**
     * TextArea to enter sequence
     */
    public TextArea seqArea;

    /**
     * Button to start checking and calculating sequence
     */
    public Button seqEnter;
    /**
     * Button to clear TextArea
     */
    public Button seqAreaRefresh;

    /**
     * Log updater @see Updater
     */
    Updater updater = new Updater();

    /**
     * Load graphic FXML file
     *
     * @param stage represents a window in a JavaFX desktop application
     * @throws IOException Wrong load FXML file
     **/
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("View/SeqChecker.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Sequence checker");
        stage.show();

        stage.setOnCloseRequest(windowEvent -> {
            updater.updateLog("Close Sequence Checker tab");
        });
    }

    /**
     * Action perform for Graphic element
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updater.updateLog("Open Sequence Checker tab");

        seqEnter.setOnAction(event -> {
            updater.updateLog("Start checking the sequence");

            Graph graph = new Graph();
            graph.testingString(seqArea.getText());
            if (graph.GetError() == null || graph.GetError().equals(" ")) {
                try {
                    Resulter resulter = new Resulter(seqArea.getText());
                    resulter.start();

                    Throwable t = new NullPointerException("Details");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, t.getMessage());
                    alert.setHeaderText("Result");

                    TextArea stackTrace = new TextArea();

                    if (Resulter.isFlag()) {
                        stackTrace.setText("Result of calculation: " + Resulter.getResult() + "\n" +
                                "The first branch of our design worked");
                    } else {
                        stackTrace.setText("Result of calculation: " + Resulter.getResult() + "\n" +
                                "The second branch of our design worked");
                    }
                    alert.getDialogPane().setExpandableContent(stackTrace);
                    alert.showAndWait();

                    updater.updateLog("Successfully end checking the sequence");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Throwable t = new NullPointerException("Error");
                Alert alert = new Alert(Alert.AlertType.ERROR, t.getMessage());
                alert.setHeaderText("Detail");

                TextArea stackTrace = new TextArea();

                stackTrace.setText(graph.GetError());

                alert.getDialogPane().setExpandableContent(stackTrace);
                alert.showAndWait();

                updater.updateLog("Get an error while checking the sequence");
            }
        });

        seqAreaRefresh.setOnAction(actionEvent -> {
            seqArea.clear();
            updater.updateLog("Clear text area in module checking the sequence");
        });


        seqCheckerExample.setOnAction(actionEvent -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "double result;" + "\n" +
                    "double oper1 = -0.14;" + "\n" +
                    "double oper2 = 0;" + "\n" +
                    "if(oper1 > oper2)" + "\n" +
                    "{ result = Math.pow(oper1, 2);" + "\n" +
                    "{ else { result = Math.pow(oper2, 2);}");
            alert.showAndWait();

            updater.updateLog("View example in module checking the sequence");
        });

        seqCheckerInstruction.setOnAction(actionEvent -> {
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

        });
    }
}
