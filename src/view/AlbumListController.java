package view;

import java.util.Optional;

import Backend.Album;
import Backend.Photo;
import Backend.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;


public class AlbumListController {

	@FXML ListView<Album> listview;
	
	@FXML Button create;
	@FXML Button delete;
	@FXML Button rename;
	@FXML Button open;
	
	@FXML TextField search;
	
	private User user;
	private ObservableList<Album> obsList;
	
	
	public void initData(User u) {
		user = u;
	}
	
	public void start() {
		// create list of items
		// form arraylist
		
		obsList = FXCollections.observableArrayList(user.getAlbums());
		listview.setItems(obsList);
		
	}
	
	public void makeAlbum() {
		TextInputDialog dialog = new TextInputDialog("enter name here");
		 
		dialog.setTitle("Photos");
		dialog.setHeaderText("What do you want to name this album?");
		dialog.setContentText("Name:");
		 
		Optional<String> result = dialog.showAndWait();
		 
		result.ifPresent(name -> {
		    addAlbum(name);
		});
	}
	
	//--------------------
	
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
	
	public boolean copyTo(Photo p, Album a, Album b) {
		if(!(a.contains(p)) || b.contains(p))
			return false;
		
		b.addPhoto(p);
		
		return true;
	}
	
	public boolean moveTo(Photo p, Album a, Album b) {
		if(!(a.contains(p)) || b.contains(p))
			return false;
		
		copyTo(p, a, b);
		a.removePhoto(p);
		
		return true;
	}
	
	public void updateList() {
		obsList = FXCollections.observableArrayList(user.getAlbums());
		listview.setItems(obsList);
	}
	
	
	
}
