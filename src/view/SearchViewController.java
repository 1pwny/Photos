package view;

import java.io.IOException;
import java.util.ArrayList;

import Backend.Photo;
import Backend.User;
import Backend.UsersApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import view.AlbumDetailController.ImageCell;

public class SearchViewController {

	@FXML ListView<Photo> resultsList;
	@FXML Button makeAlbum;
	@FXML TextField searchBar;
	@FXML TextField startDate;
	@FXML TextField endDate;
	
	private ObservableList<Photo> photos;
	
	
	private UsersApp app; //just for keeping a list of all users
	private User user;
	private Stage stage_var;
	
	public void initData(UsersApp ua, User u, ArrayList<Photo> results) {
		app = ua;
		user = u;
		photos = FXCollections.observableArrayList(results);
	}
	
	public void start(Stage primaryStage) {
		
		stage_var = primaryStage;
		primaryStage.setTitle("Search Results");
		
		resultsList.setItems(photos);
		resultsList.setCellFactory(param -> new ImageCell());
		
	}
	
}
