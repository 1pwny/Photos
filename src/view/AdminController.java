package view;
import java.util.ArrayList;

import Backend.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField; 


public class AdminController {
	
	@FXML TextField user;
	@FXML ListView<User> user_list;
	@FXML Button add;
	@FXML Button delete;
	
	public void initData(ArrayList<User> users) {
		user_list.setItems(FXCollections.observableArrayList(users));
	}
	
}
