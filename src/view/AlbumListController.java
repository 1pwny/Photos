package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import javafx.scene.control.TextField;

import Backend.User;
import Backend.Album;


public class AlbumListController {

	@FXML ListView<Album> listview;
	
	@FXML Button create;
	@FXML Button delete;
	@FXML Button rename;
	@FXML Button open;
	
	@FXML TextField search;
	
	private User user;
	private ObservableList<Album> obsList;
	
	public void start() {
		// create list of items
		// form arraylist
		user = new User("Test");
		
		Album album1 = new Album("Album1");
		Album album2 = new Album("Album2");
		Album album3 = new Album("Album3");
		Album album4 = new Album("Album4");
		
		user.addAlbum(album1);
		user.addAlbum(album2);
		user.addAlbum(album3);
		user.addAlbum(album4);
		
		obsList = FXCollections.observableArrayList(user.getAlbums());
		listview.setItems(obsList);


	}


	
}
