package view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import Backend.Album;
import Backend.Photo;
import Backend.User;
import Backend.UsersApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class AlbumListController {

	@FXML ListView<Album> listview;
	
	@FXML Button create;
	@FXML Button delete;
	@FXML Button rename;
	@FXML Button open;
	@FXML Button logout;
	
	@FXML TextField search;
	
	private User user;
	private ObservableList<Album> obsList;
	private Stage stage_var;
	
	private UsersApp app; //just for keeping a list of all users
	
	public void initData(UsersApp ap, User u) {
		app = ap;
		user = u;
	}
	
	public void start(Stage primaryStage) {
		// create list of items
		// form arraylist
		
		stage_var = primaryStage;
		primaryStage.setTitle(user.username);
		
		obsList = FXCollections.observableArrayList(user.getAlbums());
		listview.setItems(obsList);
		
		primaryStage.setOnCloseRequest(param -> {
			
			try {
				app.writeApp();
			} 
			
			catch (IOException e) {
				errorMessage("Something went wrong, your changes will not be saved");
			
		}});
		
	}
	
	public void gotoDetail(ActionEvent e) throws IOException {
		
		Album selected = obsList.get(listview.getSelectionModel().getSelectedIndex());
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("AlbumDetailView.fxml"));
		
		Parent viewParent = loader.load();
		
		Scene viewScene = new Scene(viewParent);
		Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
		AlbumDetailController detail = loader.getController();
		
		detail.initData(app, user, selected);
		detail.start(window);
		window.setScene(viewScene);
		window.show();
		
		
	}
	
	public void makeAlbum() throws FileNotFoundException, IOException {
		TextInputDialog dialog = new TextInputDialog("enter name here");
		 
		dialog.setTitle("Photos");
		dialog.setHeaderText("What do you want to name this album?");
		dialog.setContentText("Name:");
		 
		Optional<String> result = dialog.showAndWait();
		 
		result.ifPresent(name -> {
			if(listview.getItems().contains(name))
				return;
			
			Album newAl = new Album(name);
			String path = path();
			
			Album stock = app.getUser("Stock").getAlbum("Stock_Images");
			
			for(Photo p: stock.getPhotos()) {
				
				String caption = p.getCaption();
				
				try {
					Boolean b = newAl.addPhoto(new Photo(path + "/" + caption, caption));
					
				} catch (FileNotFoundException e) {
					
					//errorMessage("Couldn't add photo");
				}
				
			}
			
			user.addAlbum(newAl);
			
			updateList();
		});
	}
	
	private String path() {
		
		try {
			
			return new File(".").getCanonicalPath() + "/stock_folder";
			
		} catch (IOException e) {

			errorMessage("Can't Load Stock Images");
			return "";
		}
	}
	
	public void delAlbum() {
		int index = listview.getSelectionModel().getSelectedIndex();
		
		user.removeAlbum(index);
		
		updateList();
	}
	
	private void errorMessage(String message) {
		/* To be completed */

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.initOwner(stage_var);
		alert.setTitle("Error!");
		alert.setHeaderText(message);
		alert.showAndWait();
	}
	
	public void renAlbum() {
		int index = listview.getSelectionModel().getSelectedIndex();
		
		if(index < 0)
			return;
		
		TextInputDialog dialog = new TextInputDialog("enter new name here");
		 
		dialog.setTitle("Photos");
		dialog.setHeaderText("You are renaming the album \"" + listview.getItems().get(index) + "\":");
		dialog.setContentText("New name:");
		 
		Optional<String> result = dialog.showAndWait();
		 
		result.ifPresent(newname -> {
		    user.getAlbums().get(index).rename(newname);
		    updateList();
		    listview.refresh();
		});
	}
	//--------------------
		
	public void updateList() {
		obsList = FXCollections.observableArrayList(user.getAlbums());
		listview.setItems(obsList);
	}
	
	public void logOut(ActionEvent e) throws IOException, ClassNotFoundException {
		
		app.writeApp();
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("Login.fxml"));
		
		Parent viewParent = loader.load();
		
		
		Scene viewScene = new Scene(viewParent);
		Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
		LoginController login = loader.getController();
		login.initData(null);
		login.start(window);
		window.setScene(viewScene);
		window.show();
	}
	
}
