package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import com.gluonhq.charm.glisten.control.TextField; 


public class AlbumListController {

	@FXML ListView<Integer> listview;
	
	@FXML Button create;
	@FXML Button delete;
	@FXML Button rename;
	@FXML Button open;
	
	@FXML TextField search;

	
}
