import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

/**
 Presenter component in MVP pattern
 @author George Malakhov
 @version 1.0
  * */
public class Presenter implements IPresenter {
    /**object of Model*/
    private PostgreSQLJDBC_Model model;
    /**object of View*/
    private FileBaseView view;

    /**Source list for TableView to display data*/
    private ObservableList<Record> rec = FXCollections.observableArrayList();

    /**
     * Initialize Presenter {@link Presenter#model}, {@link Presenter#view}
     * @see Presenter
     * */
    public Presenter(PostgreSQLJDBC_Model model, FileBaseView view) {
        this.model = model;
        this.view = view;
    }

    /**
     * Flag to confirm end of add record process
     * */
    public static volatile boolean endingAdd;

    /**Log updater @see Updater */
    Updater updater = new Updater();

    /**
     * {@link IPresenter#getModel()}
     * */
    @Override
    public PostgreSQLJDBC_Model getModel() {
        return model;
    }

    /**
     * {@link IPresenter#setModel(PostgreSQLJDBC_Model)}
     * */
    @Override
    public void setModel(PostgreSQLJDBC_Model model) {
        this.model = model;
    }

    /**
     * {@link IPresenter#getView()}
     * */
    @Override
    public FileBaseView getView() {
        return view;
    }

    /**
     * {@link IPresenter#setView(FileBaseView)}
     * */
    @Override
    public void setView(FileBaseView view) {
        this.view = view;
    }

    /**
     * {@link IPresenter#init()}
     * */
    @Override
    public void init() {
        model.create();

        updater.updateLog("Upload values from databases");
        ArrayList<Record> listRecord = model.selectAll();
        filler(listRecord);
        update();
        view.getTable().setItems(rec);
    }

    /**
     * {@link IPresenter#addCall()}
     * */
    @Override
    public void addCall() {
        AddWindow addWindow = new AddWindow();
        updater.updateLog("Open add window");

        myLaunch(addWindow);

        Thread waiter = new Thread(() -> {
            while (!endingAdd) {
                Thread.onSpinWait();
            }
            ArrayList<Record> listRecord = model.selectAll();
            filler(listRecord);
            update();
        });
        waiter.start();
        endingAdd = false;
    }

    /**
     * {@link IPresenter#edit(int, String, Record)}
     * */
    @Override
    public void edit(int column, String newFullName, Record record) {
        model.update(column, newFullName, record.getLogin(), record.getHashCode(), record.getEmail());
    }

    /**
     * {@link IPresenter#init()}
     * */
    @Override
    public void delete() {
        updater.updateLog("Delete value from table");
        System.out.println(view.getTable().getFocusModel().getFocusedCell().getRow());

        int row = view.getTable().getFocusModel().getFocusedCell().getRow();
        ArrayList<Record> listRecord = model.selectAll();
        Record record = listRecord.get(row);


        ArrayList<Record> recList = model.delete(record.getLogin());
        filler(recList);
        update();
    }

    /**
     * {@link IPresenter#exit()}
     * */
    @Override
    public void exit() {
        ArrayList<Record> listRecord = model.selectAll();

        try {
            new RandomAccessFile("E:\\SysProgFX_V2\\SysProgFX\\dataBytes.bin", "rw").setLength(0);
            for (int i = 0; i < listRecord.size(); i++) {
                OutputStream out = new FileOutputStream("dataBytes.bin", true);
                outputRecord(listRecord.get(i).getLogin(), listRecord.get(i).getHashCode(), listRecord.get(i).getEmail(), out);
                out.close();
            }
            updater.updateLog("Save values from table");
            updater.updateLog("Close close FileBase manager");
        } catch (IOException e) {
            e.printStackTrace();
            updater.updateLog("Error! Saving to a binary file failed");
        }
    }

    /**
     * Sorting Record displayed jn TableView
     * @param list List of Record to display
     * */
    public void filler(ArrayList<Record> list)
    {
        rec.clear();
        for(int i = 0 ; i < list.size(); i++)
        {
            rec.add(list.get(i));
        }
    }

    /**
     * Update displayed Records
     * */
    public void update(){
        view.getTable().getItems().removeAll();
        view.getTable().setItems(rec);
    }

    /**
     * Open Window
     * @param applicationClass object of class that need to launch
     * */
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

    /**
     * Save record to binary file
     * @param login user login
     * @param hashCode user hashcode of password
     * @param email user email
     * @param out OutputStream that contain filepath to save
     * */
    public static void outputRecord(String login, String hashCode, String email, OutputStream out) {
        DataOutputStream dos = new DataOutputStream(out);
        try {
            dos.writeInt(login.length());
            dos.write(login.getBytes());

            dos.writeInt(hashCode.length());
            dos.write(hashCode.getBytes());

            dos.writeInt(email.length());
            dos.write(email.getBytes());

            dos.flush();
            dos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load Record from binary file
     * @param in InputStream that contain filepath to load
     * @return List of record
     * */
    public static ArrayList<Record> inputRecord(InputStream in) {
        DataInputStream dis = new DataInputStream(in);
        ArrayList<Record> listRecord = new ArrayList<>();
        try {
            while (dis.available() != 0) {
                int login = dis.readInt();
                byte[] bytesLogin = new byte[login];
                for (int i = 0; i < login; i++) {
                    bytesLogin[i] = dis.readByte();
                }
                String loginSTR = new String(bytesLogin);

                int hashCode = dis.readInt();
                byte[] bytesHashCode = new byte[hashCode];
                for (int i = 0; i < hashCode; i++) {
                    bytesHashCode[i] = dis.readByte();
                }
                String hashCodeSTR = new String(bytesHashCode);

                int email = dis.readInt();
                byte[] bytesEmail = new byte[email];
                for (int i = 0; i < email; i++) {
                    bytesEmail[i] = dis.readByte();
                }
                String emailSTR = new String(bytesEmail);

                Record record = new Record(loginSTR, hashCodeSTR, emailSTR);
                listRecord.add(record);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listRecord;
    }
}
