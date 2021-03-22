import javafx.scene.control.TextArea;

import java.util.Date;

/**
 * Linkage class to update logs on StartPage
 *
 * @author George Malakhov
 * @version 1.0
 * @see StartPage
 */
public class Updater {
    /**
     * TextArea to display logs
     */
    public static TextArea logAreaStatic;

    /**
     * Initialize Updater
     *
     * @see Updater
     */
    public Updater() {
    }

    /**
     * Initialize Updater
     *
     * @param logArea link to TextArea from StartPage
     * @see Updater
     * @see StartPage
     */
    public Updater(TextArea logArea) {
        logAreaStatic = logArea;
    }

    /**
     * Add log to TextArea
     *
     * @param message Name of action or exception
     */
    public void updateLog(String message) {
        Date date = new Date();
        logAreaStatic.appendText(date + " - " + message + '\n');
    }
}
