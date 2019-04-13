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
	
	public void gotoAdmin(ActionEvent e) throws IOException {
		
		Parent adminviewParent = FXMLLoader.load(getClass().getResource("AdminView.fxml"));
		Scene adminScene = new Scene(adminviewParent);
		
		Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
		
		window.setScene(adminScene);
		window.show();
	}
}


