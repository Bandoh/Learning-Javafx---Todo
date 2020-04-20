package Controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Controller implements Initializable {

    public Button add_btn;
    public Button close_btn;
    public TextField get_input;
    public VBox todo_list;
    public Scene scene;
    public Stage stage;
    CheckBox checkBox;
    File f = new File("here.txt");
    FileWriter fw;
    FileReader fr;
    List<Map<String, String>> data = new ArrayList<Map<String, String>>();

    public Controller() {

    }

    private void delItem(String d) {

        List<Map<String, String>> data = read_data();
        String all = "";
        for (Map<String, String> i : data) {
            if (i.get("text").trim().equals(d.trim())) {
                i.remove("text");
                i.remove("complete");
                todo_list.getChildren().clear();

            } else {
                all += i.get("text").trim() + "\n";
            }

        }
        System.out.println(all.trim());
        // write_to_file("", false);
        write_to_file(all.trim(), false);
        List<Map<String, String>> data1  = read_data();
        addAll(data1);
    }

    private void populate(String a) {

        Separator s = new Separator();
        checkBox = new CheckBox();
        Insets pad = new Insets(0.0, 5.0, 0.0, 5.0);
        System.out.println(checkBox.getStyle());
        this.scene = (Scene) add_btn.getScene();
        Label label = new Label();
        label.setText(a);
        HBox hBox = new HBox();
        hBox.getStyleClass().add("hbox");
        checkBox.setPadding(pad);
        final String data = a;
        checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {

            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                // TODO Auto-generated method stub
                if (newValue) {
                    // System.out.println(a);
                    delItem(data);
                }
            }
        });
        hBox.getChildren().addAll(checkBox, label);
        todo_list.getChildren().addAll(hBox, s);
        // todo_list.getItems().addAll(vbox);
        get_input.setText("");
    }

    private void write_to_file(String s, boolean a) {
        try {
            fw = new FileWriter(f, a);
            if (a) {
                fw.write("\n" + s);
            } else {
                fw.write(s);
            }

            fw.close();
        } catch (Exception e) {
            System.out.println(e);
            // TODO: handle exception
        }
    }

    public void add() {
        String text = get_input.getText();
        populate(text);
        write_to_file(text, true);

    }

    public void close_app() {
        this.scene = (Scene) close_btn.getScene();
        this.stage = (Stage) scene.getWindow();
        this.stage.close();
    }

    private List<Map<String, String>> read_data() {
        List<Map<String, String>> d = new ArrayList<Map<String, String>>();
        try {

            fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                Map<String, String> aMap = new HashMap<String, String>();
                aMap.put("text", line);
                aMap.put("complete", "false");
                d.add(aMap);
            }
            // return d;
        } catch (Exception e) {
            d = null;
            System.out.println(e);
            // TODO: handle exception
            // return null;
        }
        return d;
    }

    private void addAll(List<Map<String, String> >data){
        for (Map<String, String> i : data) {
            populate(i.get("text"));
        }
    }
    public void initialize(URL url, ResourceBundle rBundle) {
        // TODO Auto-generated method stub
        data = read_data();
        addAll(data);
     
    }
}
