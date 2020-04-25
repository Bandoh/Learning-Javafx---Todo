package Controllers;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;




public class AddController {

    public Button close_btn;
    public Button cancel_btn;
    public ToggleButton tog_btn;
    public Button save_btn;
    public TextArea text_area;
    public DatePicker date_picker;
    Controller c = new Controller();
    boolean urgToggle = false;







    public void close_app() {
        Scene scene = (Scene) close_btn.getScene();
        Stage stage = (Stage) scene.getWindow();
        stage.close();
    }

    public void toggleUrgency(){
        if(tog_btn.isSelected()){
            tog_btn.setText("Urgent");
            urgToggle = true;
        }
        else {tog_btn.setText("Normal");urgToggle = false;};
    }

    public void add_todo() throws IOException {
        System.out.println(date_picker.getValue().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));

        System.out.println(text_area.getText());
        c.write_to_file(text_area.getText() + ";" + date_picker.getValue() + ";"+urgToggle+";false", true);
        Stage stage = (Stage) text_area.getScene().getWindow();
        c.goHome(stage);
    }

    public void goToHome() throws IOException {
        Stage stage = (Stage) cancel_btn.getScene().getWindow();
        c.goHome(stage);
    }



}