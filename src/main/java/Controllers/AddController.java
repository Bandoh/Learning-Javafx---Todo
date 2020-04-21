package Controllers;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class AddController {

    public Button close_btn;
    public Button save_btn;
    public TextArea text_area;
    public DatePicker date_picker;
    Controller c = new Controller();

    public void close_app() {
        Scene scene = (Scene) close_btn.getScene();
        Stage stage = (Stage) scene.getWindow();
        stage.close();
    }

    public void add_todo() throws IOException {
        System.out.println(date_picker.getValue().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
        
        System.out.println(text_area.getText());
        c.write_to_file(text_area.getText()+";"+date_picker.getValue()+";false",true);
        Stage stage =(Stage) text_area.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("ui/todo.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("css/s.css");
        stage.setScene(scene);
    }

}