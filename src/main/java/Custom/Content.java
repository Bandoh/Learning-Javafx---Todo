package Custom;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class Content extends AnchorPane {

    public Content(String fxml){
        System.out.println(getClass().getClassLoader().getResource(fxml));
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(fxml));
        
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}