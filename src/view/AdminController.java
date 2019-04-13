package view;
import javafx.fxml.*;
import javafx.scene.control.Button;
import com.gluonhq.charm.glisten.control.TextField;
import javafx.scene.control.ListView;


public class AdminController {
	@FXML TextField user;
	@FXML ListView<Integer> user_list;
	@FXML Button add;
	@FXML Button delete;
}
