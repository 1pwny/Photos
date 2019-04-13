package view;


import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

import Backend.Album;
import Backend.User;
import javafx.scene.control.TextField; 

public class LoginController<ListController> {

	@FXML TextField login_field;
	@FXML Button submit;
	
	public void gotoUser(ActionEvent e) throws IOException {
		
		String user = login_field.getText();
		String fxml;
		
		if(user.toLowerCase().equals("admin")) {
			fxml = "AdminView.fxml";
			Parent viewParent = FXMLLoader.load(getClass().getResource(fxml));
			Scene viewScene = new Scene(viewParent);
			
			Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
			
			window.setScene(viewScene);
			window.show();
		}
		
		else {
			fxml = "AlbumListView.fxml";
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource(fxml));
			
			Parent viewParent = loader.load();
			
			
			Scene viewScene = new Scene(viewParent);
			Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
			AlbumListController listController = loader.getController();
			
			User test_user = new User(user);
			
			Album album1 = new Album("Album1");
			Album album2 = new Album("Album2");
			Album album3 = new Album("Album3");
			Album album4 = new Album("Album4");
			
			test_user.addAlbum(album1);
			test_user.addAlbum(album2);
			test_user.addAlbum(album3);
			test_user.addAlbum(album4);
			
			listController.initData(test_user);
			listController.start(window);
			
			
			
			window.setScene(viewScene);
			window.show();
		}
		
		
	}
}


