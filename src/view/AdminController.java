package view;
import java.util.ArrayList;

import Backend.Tag;
import Backend.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import javafx.stage.Stage; 


public class AdminController {
	
	@FXML TextField user;
	@FXML Text user_text;
	
	@FXML ListView<User> listview;
	@FXML Button add;
	@FXML Button delete;
	
	private ArrayList<User> user_list;
	private ObservableList<User> obsList;
	private Stage stage_var;
	
	public void initData(ArrayList<User> users) {
		
		user_list = (users != null) ? users : new ArrayList<User>();
	}
	
	public void start(Stage mainStage) {
		
		obsList = FXCollections.observableArrayList(user_list);
		listview.setItems(obsList);

		//setting the listener for those items
		listview.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> selectedUser(mainStage));
		
		if(obsList.size() > 0)
			listview.getSelectionModel().select(0);
	}
	
	public void selectedUser(Stage mainstage) {
		
		try {
			
			User selected = obsList.get(listview.getSelectionModel().getSelectedIndex());
			user_text.setText("Username: " + selected.toString());
			delete.setDisable(false);
		}
		
		catch(IndexOutOfBoundsException e){
			
		}
	}
	
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
	

	public void updateList() {
		obsList = FXCollections.observableArrayList(user_list);
		listview.setItems(obsList);
	}
	
	private void errorMessage(String message) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.initOwner(stage_var);
		alert.setTitle("Error!");
		alert.setHeaderText(message);
		alert.showAndWait();
	}
	
	
	
}
