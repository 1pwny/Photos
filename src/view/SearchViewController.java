package view;

import java.util.ArrayList;

import Backend.Photo;
import Backend.SearchTerm;
import Backend.User;
import Backend.UsersApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import view.AlbumDetailController.ImageCell;

public class SearchViewController {

	@FXML ListView<Photo> resultList;
	@FXML Button makeAlbum;
	@FXML TextField searchBar;
	@FXML TextField startDate;
	@FXML TextField endDate;
	
	private ObservableList<Photo> photos;
	private SearchTerm st;
	
	private UsersApp app; //just for keeping a list of all users
	private User user;
	private Stage stage_var;
	
	/**
	 * receives the list of users and current user for persistance, and gets the list of results from the search
	 * 
	 * @param ua
	 * @param u
	 * @param results
	 */
	public void initData(UsersApp ua, User u, ArrayList<Photo> results, SearchTerm used) {
		app = ua;
		user = u;
		photos = FXCollections.observableArrayList(results);
		st = used;
	}
	
	/**
	 * Once running, configures the result list and gets ready to search again or make an album
	 * 
	 * @param primaryStage
	 */
	public void start(Stage primaryStage) {
		
		stage_var = primaryStage;
		primaryStage.setTitle("Search Results for: " + st);
		
		//System.out.println(photos.size());
		
		//resultsList = new ListView<Photo>();
		resultList.setItems(photos);
		resultList.setCellFactory(param -> new ImageCell());
		
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
	
	public void onEnter(ActionEvent ae) {
		System.out.println("pressed enter?");
	}
}
