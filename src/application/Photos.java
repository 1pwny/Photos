package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import view.LoginController;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;


public class Photos extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/Login.fxml"));

			
			Pane root = (Pane)loader.load();
			Scene scene = new Scene(root);
			// scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Photos Login");
			primaryStage.setResizable(false);
			
			LoginController login = loader.getController();
			login.start(primaryStage);
			
			primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
