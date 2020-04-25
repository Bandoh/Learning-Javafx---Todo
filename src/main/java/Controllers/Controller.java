// File Structure Todo| Date| Urgency | complete 
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
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Controller implements Initializable {
    private enum FilterBy {
        ALL, TODAY, COMPLETED, UPCOMING, IMPORTANT,OVERDUE,UNCOMPLETED
    }

    public ImageView close_btn;
    public Button side_add;
    public Button home_btn;
    public Button all_btn;
    public Button important_btn;
    public Button overdue_btn;
    public Button complete_btn;
    public Button today_btn;
    public Button upcoming_btn;
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

    public void filter_overdue(){
        data.clear();
        todo_list.getChildren().clear();
        data = read_data();
        addAll(data, FilterBy.OVERDUE);

    }
    public void filter_important(){
        data.clear();
        todo_list.getChildren().clear();
        data = read_data();
        addAll(data, FilterBy.IMPORTANT);

    }

    public void filter_today(){
        data.clear();
        todo_list.getChildren().clear();
        data = read_data();
        addAll(data, FilterBy.TODAY);

    }
    public void filter_upcoming(){
        data.clear();
        todo_list.getChildren().clear();
        data = read_data();
        addAll(data, FilterBy.UPCOMING);

    }
    public void filter_complete(){
        data.clear();
        todo_list.getChildren().clear();
        data = read_data();
        addAll(data, FilterBy.COMPLETED);

    }
    public void filter_all(){
        data.clear();
        todo_list.getChildren().clear();
        data = read_data();
        addAll(data, FilterBy.ALL);

    }


    private void basicAnim(Node node) {
        FadeTransition ft = new FadeTransition(Duration.millis(500), node);
        ft.setFromValue(0.0);
        ft.setToValue(0.8);
        // ft.setFromX(100);
        // ft.setToX(0.0);
        ft.setCycleCount(1);
        // ft.setAutoReverse(true);
        ft.play();
    }

    public void goHome(Stage stage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("ui/todo.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("css/s.css");

        stage.setScene(scene);
    }

    public void go_home() throws IOException {
        Stage stage = (Stage) home_btn.getScene().getWindow();
        goHome(stage);
    }

    private void delItem(String d) {

        List<Map<String, String>> data = read_data();
        String all = "";
        for (Map<String, String> i : data) {
            if (i.get("text").trim().equals(d.trim())) {

                // i.remove("text");
                // i.remove("urgency");
                todo_list.getChildren().clear();
                i.put("complete", "true");
                all += i.get("text").trim() + ";" + i.get("due") + ";" + i.get("urgency")+";"+i.get("complete")+ "\n";
            } else {
                all += i.get("text").trim() + ";" + i.get("due") + ";" + i.get("urgency")+";"+i.get("complete")+ "\n";
            }

        }
        write_to_file(all.trim(), false);
        List<Map<String, String>> data1 = read_data();
        addAll(data1, FilterBy.UNCOMPLETED);
    }

    private void populate(String a, String b, String c, String complete) {
        LocalDate d = LocalDate.parse(b);
        String dueDate = d.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL));
        checkBox = new CheckBox();
        if(new Boolean(complete)){
            checkBox.setSelected(true);
        }
        Label label = new Label();
        label.setText(a);
        label.setPrefWidth(480.0);
        HBox hBox = new HBox();
        hBox.getStyleClass().add("hbox");
        final String data = a;
        checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {

            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    delItem(data);
                }
            }
        });
        Label la = new Label(dueDate);
        Label l;
        if (!new Boolean(c)) {
            l = new Label("Normal");
            l.getStyleClass().add("labelstatetrue");
        } else {
            l = new Label("Urgent");
            l.getStyleClass().add("labelstatefalse");
        }
        la.setStyle("-fx-font-weight: bold; -fx-text-fill:-fx-primary-color;");
        l.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(checkBox, label, l);
        VBox vb = new VBox(10);
        vb.getStyleClass().add("vbox2");
        basicAnim(vb);
        vb.getOnMouseEntered();
        vb.getChildren().addAll(la, hBox);

        todo_list.getChildren().addAll(vb);
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
        }
    }

    public void add(String text) {
        // String text = get_input.getText();
        populate(text, "", "","false");
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
                String[] a = line.split(";");
                aMap.put("text", a[0]);
                aMap.put("due", a[1]);
                aMap.put("urgency", a[2]);
                aMap.put("complete", a[3]);
                d.add(aMap);
            }
            // return d;
        } catch (Exception e) {
            d = null;
            System.out.println(e);
            // return null;
        }
        return d;
    }

    private void addAll(List<Map<String, String>> data, FilterBy filter) {

        for (Map<String, String> i : data) {
            switch (filter) {
                case UNCOMPLETED: {
                    if(!new Boolean(i.get("complete"))){
                        populate(i.get("text"), i.get("due"), i.get("urgency"), i.get("complete"));
                    }
                    break;
                }
                case COMPLETED: {
                    if(new Boolean(i.get("complete"))){
                        populate(i.get("text"), i.get("due"), i.get("urgency"), i.get("complete"));
                    }
                    break;
                }
                case IMPORTANT: {
                    if(new Boolean(i.get("urgency"))){
                        populate(i.get("text"), i.get("due"), i.get("urgency"), i.get("complete"));
                    }
                    break;
                }
                case TODAY: {
                    LocalDate now = LocalDate.parse(i.get("due"));     
                    if (LocalDate.now().equals(now)) {
                        populate(i.get("text"), i.get("due"), i.get("urgency"), i.get("complete"));
                    }
                    break;
                }
                case UPCOMING: {
                    LocalDate now = LocalDate.parse(i.get("due"));     
                    if (LocalDate.now().isBefore(now)) {
                        populate(i.get("text"), i.get("due"), i.get("urgency"), i.get("complete"));
                    }
                    break;
                }
                case OVERDUE: {
                    LocalDate now = LocalDate.parse(i.get("due"));     
                    if (LocalDate.now().isAfter(now)  && !new Boolean(i.get("complete"))) {
                        populate(i.get("text"), i.get("due"), i.get("urgency"), i.get("complete"));
                    }
                    break;
                }
                default: {
                    populate(i.get("text"), i.get("due"), i.get("urgency"), i.get("complete"));
                    break;
                }

            }

        }
    }

    public void initialize(URL url, ResourceBundle rBundle) {
        data = read_data();
        addAll(data, FilterBy.UNCOMPLETED);

    }

    public void goto_add() throws IOException {

        changeScene("ui/addTodoPage.fxml");
    }

    private void changeScene(String fxml) throws IOException {
        Parent root;
        root = FXMLLoader.load(getClass().getClassLoader().getResource(fxml));
        basicAnim(root);
        bp.setCenter(root);
    }

    public void listByToday() {

    }

    public void ideas() {
    }

}
