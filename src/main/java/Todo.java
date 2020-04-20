import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.fxml.FXMLLoader;

public class Todo extends Application {
    Button btn;

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ui/todo.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("css/s.css");
        stage.setScene(scene);

        // stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("BanTodo");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}