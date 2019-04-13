package view;


import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

import com.gluonhq.charm.glisten.control.TextField; 

public class LoginController {

	@FXML TextField login_field;
	@FXML Button submit;
	
	public void gotoUser(ActionEvent e) throws IOException {
		
		String user = login_field.getText();
		String fxml;
		
		if(user.toLowerCase().equals("admin")) {
			fxml = "AdminView.fxml";
		}
		
		else {
			fxml = "AlbumListView.fxml";
		}
		
		Parent viewParent = FXMLLoader.load(getClass().getResource(fxml));
		Scene viewScene = new Scene(viewParent);
		
		Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
		
		window.setScene(viewScene);
		window.show();
	}
}


