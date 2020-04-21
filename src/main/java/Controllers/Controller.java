package Controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.animation.TranslateTransition;
import javafx.animation.Animation.Status;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Controller implements Initializable {

    public Button close_btn;
    public Button side_add;
    public BorderPane bp;
    public VBox todo_list;
    public Scene scene;
    public Stage stage;
    CheckBox checkBox;
    public Pane main_pane;
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
                i.remove("urgency");
                todo_list.getChildren().clear();

            } else {
                all += i.get("text").trim()+";"+i.get("due")+";"+i.get("urgency") + "\n";
            }

        }
        System.out.println(all.trim());
        // write_to_file("", false);
        write_to_file(all.trim(), false);
        List<Map<String, String>> data1 = read_data();
        addAll(data1);
    }

    private void populate(String a,String b,String c) {
        LocalDate d = LocalDate.parse(b);
       String dueDate =  d.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL));
        Separator s = new Separator();
        checkBox = new CheckBox();
        Insets pad = new Insets(0.0, 5.0, 0.0, 5.0);
        System.out.println(checkBox.getStyle());
        Label label = new Label();
        label.setText(a);
        label.setPrefWidth(480.0);
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
        Label la = new Label(dueDate);
        // vBox.getChildren().addAll(b);
        Label l ;
        if(new Boolean(c)){
            l = new Label("Normal");
            l.getStyleClass().add("labelstatetrue");
        }
        else{
            l = new Label("Urgent");
            l.getStyleClass().add("labelstatefalse");
        }
        la.setStyle("-fx-font-weight: bold");
        // l.setPrefSize(130.0, 50.0);
        // l.setPadding(new Insets(5.0, 5.0, 5.0, 5.0));
        l.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(checkBox, label,l );
        todo_list.getChildren().addAll(la, hBox,s);
        // todo_list.getItems().addAll(vbox);
    }

    public void write_to_file(String s, boolean a) {
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

    public void add(String text) {
        // String text = get_input.getText();
        populate(text,"","");
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
                String[]a = line.split(";");
                aMap.put("text", a[0]);
                aMap.put("due", a[1]);
                aMap.put("urgency", a[2]);
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

    private void addAll(List<Map<String, String>> data) {
        for (Map<String, String> i : data) {
            populate(i.get("text"),i.get("due"),i.get("urgency"));
        }
    }

    public void initialize(URL url, ResourceBundle rBundle) {
        // TODO Auto-generated method stub
        // close_btn.setStyle("-fx-background-image: url('/images/c.png')");
        data = read_data();
        addAll(data);

    }

    public void goto_add() throws IOException {

        changeScene("ui/addTodoPage.fxml");
    }

    private void changeScene( String fxml) throws IOException {
        Parent root;
        root = FXMLLoader.load(getClass().getClassLoader().getResource(fxml));
            bp.setCenter(root);
            // bp.getChildren().add(fxmlLoader);
  

       
    }

    public void list_todos() {
    }

    public void ideas() {
    }

}


