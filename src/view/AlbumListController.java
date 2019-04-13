package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import com.gluonhq.charm.glisten.control.TextField;

import Backend.User;
import Backend.Album;


public class AlbumListController {

	@FXML ListView<Integer> listview;
	
	@FXML Button create;
	@FXML Button delete;
	@FXML Button rename;
	@FXML Button open;
	
	@FXML TextField search;
	
	private User user;
	private ObservableList<Album> obsList;
	
	public void start(Stage mainStage) {
		// create list of items
		// form arraylist
		user = new User()

		listView.setItems(obsList);
		disableButtons();

		//setting the listener for those items
		listView.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> selectedSong(mainStage));
		
		if(obsList.size() > 0)
			listView.getSelectionModel().select(0);
	}

	
}
