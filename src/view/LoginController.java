package view;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import Backend.Album;
import Backend.Photo;
import Backend.User;
import Backend.UsersApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage; 

/**
 * 
 * This controller is responsible for handling the Login Screen for the user, and is also the controller
 * responsible for reading the stored serialized data.
 * 
 * @author Anand Raju
 * @author Sammy Berger
 * */
public class LoginController<ListController> {


	@FXML TextField login_field;
	@FXML Button submit;
	
	private ArrayList<User> allUsers = new ArrayList<User>();
	private UsersApp app = null;
	
	private Stage stage_var;
	
	
	/**
	 * 
	 * 
	 * Upon starting, the controller checks to see if there's any user data, and if there is, loads the
	 * stored data and gets a list of all the saved users on the app. 
	 * 
	 * */
	public void start(Stage mainStage) throws ClassNotFoundException, IOException {
		stage_var = mainStage;
		
		if(app == null) {
			
			try {
				
				app = new UsersApp(allUsers);
				app = app.readApp();
				// System.out.print("Got Something!");
				allUsers = app.getUsers();
				
			}
			
			catch(FileNotFoundException e) {
				// System.out.print("No file to be found");
				app = new UsersApp(allUsers);
				loadStockUser();
				allUsers = app.getUsers();

			}	
		}
		
		else
			allUsers = app.getUsers();
		
	}
	
	/**
	 * 
	 * Helper method that creates the Stock User, who contains all the stock photos. 
	 * 
	 * */
	private void loadStockUser() {
		
		User stock = new User("Stock");
		Album newAl = new Album("Stock_Images");
		String path = path();
		
		if(path != "") {
			
			File stock_folder = new File(path);
			File[] files = null;
			
			if(stock_folder.isDirectory())
				files = stock_folder.listFiles();
			
			for(File file : files) {
				String name1 = file.getName();
				try {
					Boolean b = newAl.addPhoto(new Photo(path + "/" + name1, name1));
					
				} catch (FileNotFoundException e) {
					
					//errorMessage("Couldn't add photo");
				}
			}
			
		}
		
		stock.addAlbum(newAl);
		app.getUsers().add(stock);
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
	 * 
	 * EventHandler for when the user submits a username. 
	 * 
	 * If the name is 'admin', this controller passes data to the AdminController and sends the user to the
	 * AdminView. 
	 * 
	 * Otherwise, the controller checks to see if a user with the username exists, if it does, it passes
	 * data to the AlbumListController and sends the user to his list of Albums.
	 * 
	 * If that user doesn't exist, it shows an error saying the user doesn't exist. 
	 * 
	 * 
	 * */
	public void gotoUser(ActionEvent e) throws IOException {
		
		String name = login_field.getText();
		String fxml;
		
		if(name.toLowerCase().equals("admin")) {
			fxml = "AdminView.fxml";
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource(fxml));
			
			Parent viewParent = loader.load();
			
			AdminController admin = loader.getController();

			Scene viewScene = new Scene(viewParent);
			Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
			
			admin.initData(app, allUsers);
			admin.start(window);
			window.setScene(viewScene);
			window.show();
		}
		
		else {
			
			User user = null;
			
			for(User u: allUsers) {
				if(u.username.equals(name)) {
					user = u;
				}
			}
			
			if(user == null) {
				errorMessage("User doesn't exist");
			}
			
			else {
				
				fxml = "AlbumListView.fxml";
				
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource(fxml));
				
				Parent viewParent = loader.load();
				
				
				Scene viewScene = new Scene(viewParent);
				Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
				AlbumListController listController = loader.getController();
				
				
				listController.initData(app, user);
				listController.start(window);
				
				window.setScene(viewScene);
				window.show();
			}
			
		}
		
		
	}
	
	/**
	 * 
	 * Makes and error message pop up with custom message
	 * 
	 * @param message  the message you want to show
	 * 
	 * */
	private void errorMessage(String message) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.initOwner(stage_var);
		alert.setTitle("Error!");
		alert.setHeaderText(message);
		alert.showAndWait();
	}
	
	/**
	 * 
	 * Method responsible for passing data to the LoginController
	 * 
	 * */
	public void initData(UsersApp ap) {
		app = ap;
	}
}


