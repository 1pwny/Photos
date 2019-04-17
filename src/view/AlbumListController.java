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

/**
 * This controller is responsible for showing the list of albums created under this user.
 * 
 * @author Anand Raju
 * @author Sammy Berger
 * 
 * 
 */
public class AlbumListController {

	@FXML ListView<Album> listview;
	
	@FXML Button create;
	@FXML Button delete;
	@FXML Button rename;
	@FXML Button open;
	@FXML Button logout;
	
	@FXML TextField search_field;
	@FXML Button search_button;
	
	private User user;
	private ObservableList<Album> obsList;
	private Stage stage_var;
	
	private UsersApp app; //just for keeping a list of all users
	
	/**
	 * 
	 * Method for passing data to the controller
	 * 
	 * @param ap UsersApp object to grab the stock user
	 * @param u User data needed to list the albums
	 * 
	 * */
	public void initData(UsersApp ap, User u) {
		app = ap;
		user = u;
	}
	
	/**
	 * 
	 * Upon starting the controller, the user's albums are put into an observable list. The handler for closing
	 * the window is also set to save the user's data.
	 * 
	 * 
	 * */
	
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
	
	/**
	 * 
	 * Eventhandler responsible for opening an Album and listing all its photos. Upon clicking the 
	 * 'Open' button, the controller passes data to the detial view and switches to the AlbumDetailView.
	 * 
	 * */
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
	
	/**
	 * 
	 * Eventhandler responsible for creating an Album. Upon clicking the 'create' button, a text input dialogue
	 * is created asking the user for an album name. Once the user inputs a valid name, the new album is created,
	 * and each photo from the Stock user's album is added to the newly created album. 
	 * 
	 * */
	
	public void makeAlbum(ActionEvent e) throws FileNotFoundException, IOException {
		
		TextInputDialog dialog = new TextInputDialog("enter name here");
		 
		dialog.setTitle("Photos");
		dialog.setHeaderText("What do you want to name this album?");
		dialog.setContentText("Name:");
		 
		Optional<String> result = dialog.showAndWait();
		 
		result.ifPresent(name -> {
			if(listview.getItems().contains(name))
				return;
			
			Album newAl = new Album(name);
			Button command = (Button) e.getSource();
			
			if(command == create) {
				
				String path = path();
				
				Album stock = app.getUser("Stock").getAlbum("Stock_Images");
				
				for(Photo p: stock.getPhotos()) {
					
					String caption = p.getCaption();
					
					try {
						Boolean b = newAl.addPhoto(new Photo(path + "/" + caption, caption));
						
					} catch (FileNotFoundException er) {
						
						//errorMessage("Couldn't add photo");
					}
					
				}
				
				user.addAlbum(newAl);
				
				updateList();
			}
			
			else if(command == search_button) {
				
				// PUT SEARCH RESULTS HERE
				ArrayList<Photo> search_list = new ArrayList<Photo>();
				newAl.getPhotos().addAll(search_list);
				
				//CODE FOR LOADING IN SEARCHVIEW CONTROLLER BELOW
			}
		});
	}
	
	/**
	 * 
	 * Helper method to get path to the stock_folder
	 * 
	 * @return path to the stock images folder
	 * 
	 * */
	private String path() {
		
		try {
			
			return new File(".").getCanonicalPath() + "/stock_folder";
			
		} catch (IOException e) {

			errorMessage("Can't Load Stock Images");
			return "";
		}
	}
	
	
	/**
	 * 
	 * EventHandler for deleting the selected album from the list
	 * 
	 * */
	public void delAlbum() {
		int index = listview.getSelectionModel().getSelectedIndex();
		
		user.removeAlbum(index);
		
		updateList();
	}
	
	/**
	 * 
	 * Makes and error message pop up with custom message
	 * 
	 * @param message  the message you want to show
	 * 
	 * */
	private void errorMessage(String message) {
		/* To be completed */

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.initOwner(stage_var);
		alert.setTitle("Error!");
		alert.setHeaderText(message);
		alert.showAndWait();
	}
	
	/**
	 * 
	 * EventHandler for renaming a selected Album. Upon clicking the 'edit' button, a TextInput dialogue is
	 * created. Once the user enters a valid input, the album is renamed. 
	 * 
	 * */
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
	
	
	
	/**
	 * 
	 * Helper method for simply updating the list after changes
	 * 
	 * */
		
	public void updateList() {
		obsList = FXCollections.observableArrayList(user.getAlbums());
		listview.setItems(obsList);
	}
	
	
	/**
	 * 
	 * Handles logging out to the LoginView when the logout button is pressed. 
	 * 
	 * First the event writes to the UsersApp and then sends a null app to the Login Controller such that
	 * the controller will read a fresh list of updated users upon starting
	 *
	 * 
	 * @param e an Action event.
	 * 
	 * */
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
