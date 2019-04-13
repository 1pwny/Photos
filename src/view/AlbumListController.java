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
		
		addAlbum(album1);
		addAlbum(album2);
		addAlbum(album3);
		addAlbum(album4);

	}
	
	public void addAlbum(Album a) {
		if(listview.getItems().contains(a))
			return;
		
		user.addAlbum(a);
		
		updateList();
	}
	public void addAlbum(String name) {
		if(listview.getItems().contains(name))
			return;
		
		Album newAl = new Album(name);
		user.addAlbum(newAl);
		
		updateList();
	}
	public boolean removeAlbum(String name) {
		boolean deleted = user.removeAlbum(name);
		
		updateList();
		
		return deleted;
	}
	public boolean removeAlbum(Album a) {
		return user.removeAlbum(a);
	}
	
	public void updateList() {
		obsList = FXCollections.observableArrayList(user.getAlbums());
		listview.setItems(obsList);
	}
	
}
