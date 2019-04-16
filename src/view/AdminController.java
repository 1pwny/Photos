package view;
import java.io.IOException;
import java.util.ArrayList;

import Backend.Tag;
import Backend.User;
import Backend.UsersApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import javafx.stage.Stage; 

/**
 * 
 * @author Anand Raju
 * @author Sammy Berger
 * 
 * This controller is responsible for the actual admin view, and is responsible for adding and 
 * deleting users.
 * 
 * */
public class AdminController {
	
	@FXML TextField user;
	@FXML Text user_text;
	
	@FXML ListView<User> listview;
	@FXML Button add;
	@FXML Button delete;
	@FXML Button logout;
	
	private UsersApp app;
	private ArrayList<User> user_list;
	private ObservableList<User> obsList;
	private Stage stage_var;
	
	/**
	 * @param mainstage
	 * 
	 * upon starting, the controller sets an observable list containing users from the UserApp, and 
	 * 
	 * */
	public void start(Stage mainStage) {
		
		obsList = FXCollections.observableArrayList(user_list);
		listview.setItems(obsList);

		//setting the listener for those items
		listview.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> selectedUser(mainStage));
		
		if(obsList.size() > 0)
			listview.getSelectionModel().select(0);
	}
	
	/**
	 * The Listener for selecting a user. Upon selection displays the username.
	 * 
	 * */
	public void selectedUser(Stage mainstage) {
		
		try {
			
			User selected = obsList.get(listview.getSelectionModel().getSelectedIndex());
			user_text.setText("Username: " + selected.toString());
			delete.setDisable(false);
		}
		
		catch(IndexOutOfBoundsException e){
			
		}
	}
	
	/**
	 * This function handles the events for adding and deleting a user. Will not add a user with the same username. 
	 * 
	 * @param e ActionEvent from either add or delete button
	 * */
	
	public void add_remove_User(ActionEvent e) {
		
		Button command = (Button)e.getSource();
		int index = listview.getSelectionModel().getSelectedIndex();

		if(command == add) {
			
			if(user.getText().length() > 0) {
				
				User u = new User(user.getText());
				
				if(obsList == null || !(obsList.contains(u))) {
					
					user_list.add(u);
					user_text.setText("Username: " + u.toString());
					user.clear();

				}
				
				else {
					errorMessage("User already exists!");
				}
			}

			else {
				
				errorMessage("You have to input a username!");
			}
		}
		
		else if(command == delete){

			user_list.remove(index);
			if(obsList.size() > 0) {
				
				if(index == obsList.size()) 
					index--;				
				
				listview.getSelectionModel().select(index);
			}
			
			else {
				
				user_text.setText("Username:");
				delete.setDisable(true);
			}
				
		}
		
		updateList();
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
		
		//loginController.start(window);
		window.setScene(viewScene);
		window.show();
	}
	
	/**
	 * This method simply refreshes the listview every time the list is updated
	 * 
	 * */
	public void updateList() {
		obsList = FXCollections.observableArrayList(user_list);
		listview.setItems(obsList);
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
	 * The way we initialize data in the controller when changing scenes. 
	 * 
	 * @param ap  a Users app for the controller to write data
	 * @param allUsers  an array list of all users
	 * 
	 * 
	 * */
	public void initData(UsersApp ap, ArrayList<User> allUsers) {
		// TODO Auto-generated method stub
		app = ap;
		user_list = (allUsers != null) ? allUsers : new ArrayList<User>();
		
	}
	
	
	
}
