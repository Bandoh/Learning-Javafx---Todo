import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class EventH implements EventHandler<ActionEvent>{
Button btn;
public void setButton(Button btn){
    this.btn = btn;
}
    public void handle(ActionEvent event) {
        // TODO Auto-generated method stub
        if(event.getSource()==this.btn){
            System.out.println("Its unique");
            this.btn = null;
        }

    }

}