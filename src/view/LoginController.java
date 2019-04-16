package view;


import java.io.IOException;
import java.util.ArrayList;

import Backend.Album;
import Backend.User;
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

public class LoginController<ListController> {

	@FXML TextField login_field;
	@FXML Button submit;
	
	private ArrayList<User> allUsers;
	private Stage stage_var;
	
	public void start(Stage mainStage) {
		stage_var = mainStage;
	}
	
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
			
			admin.initData(allUsers);
			admin.start(window);
			window.setScene(viewScene);
			window.show();
		}
		
		else {
			if(allUsers == null) {
				allUsers = new ArrayList<User>();
			}
			
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
				
				
				listController.initData(allUsers, user);
				listController.start(window);
				
				window.setScene(viewScene);
				window.show();
			}
			
		}
		
		
	}
	
	private void errorMessage(String message) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.initOwner(stage_var);
		alert.setTitle("Error!");
		alert.setHeaderText(message);
		alert.showAndWait();
	}
	
	public void setAllUsers(ArrayList<User> al) {
		allUsers = al;
	}
}


