import javafx.event.ActionEvent;
import javafx.scene.control.TableView;
import javafx.stage.WindowEvent;

/**
 Interface of View component in MVP pattern
 @author George Makakhov
 @version 1.0
  * */
public interface FileBaseView {
    /**
     * Get Presenter of this view
     * @return current Presenter
     * */
    IPresenter getPresenter();

    /**
     * Action on case of close FileBaseFX window
     * @param event Event that present current window
     * */
    void setOnCloseRequest(WindowEvent event);

    /**
     * Action on case of delete record from FileBaseFX window
     * @param event Event that present action from delete button
     * */
    void deleteSetOnAction(ActionEvent event);

    /**
     * Action on case of close FileBaseFX window
     * @param event Event that present action from back button
     * */
    void backSetOnAction(ActionEvent event);

    /**
     * Action on case of add record to FileBaseFX window
     * @param event Event that present action from  add button
     * */
    void addSetOnAction(ActionEvent event);

    /**
     * Action on case of show example of FileBaseFX window
     * @param event Event that present action from  Example MenuItem
     * */
    void fileBaseExambple(ActionEvent event);

    /**
     * Action on case of show instruction of FileBaseFX window
     * @param event Event that present action from  Instruction MenuItem
     * */
    void fileBaseInstruction(ActionEvent event);

    /**
     * TableView from FileBaseFX
     * @return  TableView table from FileBaseFX
     * */
    TableView getTable();
}
