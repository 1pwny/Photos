/**
 * This controller shows search results, lets you search more, make an album from them, and go back to the previous view
 *
 * @author Sammy Berger
 */

package view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import Backend.Album;
import Backend.Photo;
import Backend.SearchTerm;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import view.AlbumDetailController.ImageCell;

public class SearchViewController {

	@FXML ListView<Photo> resultList;
	@FXML Button makeAlbum;
	@FXML Button back;
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

	/**
	 * makes an album out of the search results and goes to it
	 *
	 * @param e
	 */
	public void makeAlbum(ActionEvent e) {
		TextInputDialog dialog = new TextInputDialog("enter name here");

		dialog.setTitle("Photos");
		dialog.setHeaderText("What do you want to name this album?");
		dialog.setContentText("Name:");

		Optional<String> result = dialog.showAndWait();

		result.ifPresent(name -> {
			if(user.getAlbum(name) != null) {
				errorMessage("That album name already exists!");
				return;
			}

			Album newAl = new Album(name);
			for(Photo p: photos)
				newAl.addPhoto(p);

			user.addAlbum(newAl);

			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("AlbumDetailView.fxml"));

				Parent viewParent = loader.load();

				Scene viewScene = new Scene(viewParent);
				AlbumDetailController detail = loader.getController();

				detail.initData(app, user, newAl);
				detail.start(stage_var);
				stage_var.setScene(viewScene);
				stage_var.show();
			} catch(IOException ie) {

			}

		});
	}

	/**
	 * goes back to the Album List View
	 */
	public void goBack(ActionEvent e) throws IOException {
		String fxml = "AlbumListView.fxml";

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(fxml));

		Parent viewParent = loader.load();


		Scene viewScene = new Scene(viewParent);
		AlbumListController listController = loader.getController();


		listController.initData(app, user);
		listController.start(stage_var);

		stage_var.setScene(viewScene);
		stage_var.show();
	}

	/**
	 * re-searches the users' albums after they press 'enter' in the search textfield
	 *
	 * @param ae
	 */
	public void onEnter(ActionEvent ae) {
		SearchTerm st = new SearchTerm(searchBar.getText(), startDate.getText(), endDate.getText());

		if(!st.isvalid())
			errorMessage("Invalid search query.");

		ArrayList<Photo> allresults = user.sortBy(st);

		if(allresults.size() == 0) {
			errorMessage("No such photos");
			return;
		}

		photos = FXCollections.observableArrayList(allresults);
		resultList.setItems(photos);
	}
}
