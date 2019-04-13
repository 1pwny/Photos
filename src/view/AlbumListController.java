package view;

import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.Button;
import com.gluonhq.charm.glisten.control.TextField;
import javafx.scene.control.ListView;


public class AlbumListController {

	@FXML ListView<Integer> listview;
	
	@FXML Button create;
	@FXML Button delete;
	@FXML Button rename;
	@FXML Button open;
	
	@FXML TextField search;

	
}
