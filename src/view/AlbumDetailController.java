package view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Optional;

import Backend.Album;
import Backend.Photo;
import Backend.Tag;
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
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
/**
 * @author anand
 * 
 * This is the controller responsible for showing the actual Photos in the album. 
 * 
 * */
public class AlbumDetailController {
	
	@FXML ImageView slideshow_view;
	@FXML Button slide_left;
	@FXML Button slide_right;
	
	@FXML Text caption_text;
	@FXML Button edit_caption;
	
	@FXML Text tags_text;
	@FXML Button edit_tags;
	
	@FXML Text date_text;
	
	@FXML ListView<Photo> thumbnail_view;
	
	@FXML Button add;
	@FXML Button remove;
	@FXML Button copy;
	@FXML Button move;
	
	private UsersApp app; 
	private User user;
	private ArrayList<Album> all_albums;
	private Album album;
	
	
	private ObservableList<Photo> obsList;
	
	Stage stage_var;
	DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
	
	/**
	 * Used for passing the specific albums and album data for the controller to use
	 * 
	 * @param ap The UsersApp to write the saved data
	 * @param u The user data to pass back to AlbumListView
	 * @param selected The chosen album to be viewed. 
	 * 
	 * */
	public void initData(UsersApp ap, User u, Album selected) {
		// TODO Auto-generated method stub
		
		app = ap;
		user = u;
		all_albums = u.getAlbums();
		album = selected;
		
	}
	
	
	/**
	 * Upon running, loads all the images from stock folder, then displays thumbnails using a cell factory and a listener. 
	 * 
	 * When the window is closed, it saves all the data to the user app. 
	 * 
	 * @param mainStage
	 * 
	 * */
	public void start(Stage mainStage) throws FileNotFoundException, IOException {
		
		stage_var = mainStage;
		mainStage.setTitle(album.name);
		
		String path = new File(".").getCanonicalPath() + "/stock_folder";
		File stock_folder = new File(path);
		File[] files = null;
		
		if(stock_folder.isDirectory())
			files = stock_folder.listFiles();
		
		for(File file : files) {
			String name = file.getName();
			Boolean b = album.addPhoto(new Photo(path + "/" + name, name));
		}
		
		
		obsList = FXCollections.observableArrayList(album.getPhotos());
		thumbnail_view.setItems(obsList);
		
		thumbnail_view.setCellFactory(param -> new ImageCell());
		
		thumbnail_view.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> selectedPhoto(mainStage));
		
		if(obsList.size() > 0)
			thumbnail_view.getSelectionModel().select(0);
			
	}
	
	/**
	 * 
	 * ImageCell is a type of cell that allows for the listview to display a 50x50 thumbnail image along with
	 * it's caption right next to it. 
	 * 
	 * Due to grading purposes we made the imageviews small, so the resolution and quality is low when uploading the images. 
	 * 
	 * */
	static class ImageCell extends ListCell<Photo> {
        @Override
        public void updateItem(Photo item, boolean empty) {
            super.updateItem(item, empty);
            ImageView cellview = new ImageView();
            
            if (empty) {
                setText(null);
                setGraphic(null);
            }
            
            else {

            	Image img = item.getImage();
            	cellview.setImage(img);
                cellview.setFitWidth(50);
                cellview.setFitHeight(50);
                cellview.setPreserveRatio(true);
                setText(item.getCaption());
                setGraphic(cellview);
            }
        }
    }
	
	/**
	 * 
	 * A listener for the selected photo. Once a photo is selected it displays it's thumbnail, caption, and tags
	 * 
	 * Due to grading purposes we made the thumbnails small, so the resolution and quality is low when the images is expanded.
	 * 
	 * */
	private void selectedPhoto(Stage mainStage) {

		try {

			Photo selected = obsList.get(thumbnail_view.getSelectionModel().getSelectedIndex());

			slideshow_view.setImage(selected.getImage());
			caption_text.setText(selected.getCaption());
			date_text.setText(df.format(selected.date()));
			
			String tags = "";
			
			for(Tag t: selected.getTags()) {
				
				tags += "(" + t.toString() + "),";
			}
			
			tags_text.setText(tags);
			
		}

		catch(IndexOutOfBoundsException e){
			
		}

	}
	
	/**
	 * 
	 * Eventhandler for editing the tags. When the 'edit tags' button is pressed, the function calls a dialogue
	 * view that lets the user add, edit, and delete tags. Once the dialogue is closed, the photo is updated.
	 * 
	 * */
	
	public void gotoTags(ActionEvent e) throws IOException {
		
		Photo selected = obsList.get(thumbnail_view.getSelectionModel().getSelectedIndex());
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("TagDialogue.fxml"));
		
		Parent viewParent = loader.load();
		
		Scene viewScene = new Scene(viewParent);
		Stage window = new Stage();
		TagDialogueController detail = loader.getController();
		
		detail.initData(selected);
		detail.start(window);
		window.setScene(viewScene);
		window.initModality(Modality.APPLICATION_MODAL);
		window.showAndWait();
		updatePhoto(selected);
		
	}
	
	
	/**
	 * 
	 * Eventhandler responsible for going back to the AlbumListView. Passes it's stored UserApp and user data
	 * back to the view to display all the user's albums. 
	 * 
	 * */
	public void backtoAlbum(ActionEvent e) throws IOException {
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("AlbumListView.fxml"));
		
		Parent viewParent = loader.load();
		
		
		Scene viewScene = new Scene(viewParent);
		Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
		AlbumListController albumlist = loader.getController();
		
		albumlist.initData(app, user);
		albumlist.start(window);
		window.setScene(viewScene);
		window.show();
	}
	
	
	/**
	 * 
	 * Event handler for editing the caption. When pressing 'Edit Caption' button, it opens up a TextInput
	 * Dialog which asks the user to input a caption. Once it gets a result, it immidiately recaptions and updates the Photo. 
	 * 
	 * */
	public void editCaption() {
		
		TextInputDialog dialog = new TextInputDialog("Enter Caption here");
		 
		dialog.setTitle("Caption");
		dialog.setHeaderText("What do you want to name this photo?");
		dialog.setContentText("Caption:");
		 
		Optional<String> result = dialog.showAndWait();
		
		if(result.isPresent()) {
			
			Photo selected = obsList.get(thumbnail_view.getSelectionModel().getSelectedIndex());
			selected.recaption(result.get());
			updatePhoto(selected);
			thumbnail_view.refresh();
		}
		
		
	}
	
	
	/**
	 * 
	 * Eventhandler for the slideshow buttons. 
	 * 
	 * @param e If '>>' selects the Photo the "right" (The Image ListView is vertical, so below) 
	 * and '<<' selects the photo to the "left".
	 * 
	 * */
	public void next_prev_Photo(ActionEvent e) {
		Button command = (Button) e.getSource();
		int index = (command == slide_right) ? thumbnail_view.getSelectionModel().getSelectedIndex() + 1 : 
												thumbnail_view.getSelectionModel().getSelectedIndex() - 1; 
		
		thumbnail_view.getSelectionModel().select(index);
	}
	
	/**
	 * 
	 * Essentially refreshes the photo, making sure the controller dispalys the right caption and the right tags. 
	 * 
	 * @param p The Photo you want to refresh
	 * 
	 * */
	public void updatePhoto(Photo p) {
		
		p.reDate();
		caption_text.setText(p.getCaption());
		String tags = "";
		
		for(Tag t: p.getTags()) {
			
			tags += "[" + t.toString() + "], ";
		}
		
		tags_text.setText(tags);

	}

	
	public void uploadPhoto() throws FileNotFoundException {
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Add Image");
		fileChooser.getExtensionFilters().addAll(
				new ExtensionFilter("jpeg files", "*.jpg"),
				new ExtensionFilter("png files", "*.png"));
		
		File selectedFile = fileChooser.showOpenDialog(stage_var);
		if (selectedFile != null) {
			
			String path = "";
			try {
				path = selectedFile.getCanonicalPath();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Photo photo = new Photo(path, selectedFile.getName());
			Boolean b = album.addPhoto(photo);
			updateAlbum();
			
		 }
	}
	
	/**
	 * 
	 * A helper method that refreshes the whole album
	 * 
	 * */
	public void updateAlbum() {
		
		obsList = FXCollections.observableArrayList(album.getPhotos());
		thumbnail_view.setItems(obsList);

	}
	
	/**
	 * Eventhandler for the 'move' and copy buttons. First, a choicebox dialogue opens up allowing the user to choose which
	 * album they want to move the selected photo to. If they confirm an album, the photo is either copied or moved
	 * depending on the album. 
	 * 
	 * @param e ActionEvent can either be 'move' or 'copy' buttons
	 * 
	 * */
	public void photo_manuver(ActionEvent e) {
		
		Photo selected = obsList.get(thumbnail_view.getSelectionModel().getSelectedIndex());
		
		Button command = (Button) e.getSource();
		String f = (command == move) ? "move" : "copy";
		
		ChoiceDialog<Album> dialog = new ChoiceDialog<>(all_albums.get(0), all_albums);
		dialog.setTitle("Albums");
		dialog.setHeaderText("Select an Album to " + f + " to..");
		dialog.setContentText("Album:");
		
		Optional<Album> result = dialog.showAndWait();
		if (result.isPresent()){
		    
			if(command == move) {
				Boolean b = moveTo(selected, album, result.get());
				updateAlbum();
			}
			
			else if(command == copy) {
				Boolean b = copyTo(selected, album, result.get());
			}
			
		}

	}
	
	/**
	 * 
	 * Eventhandling for removing the photo from the album. 
	 * 
	 * */
	
	public void removePhoto() {
		
		int index = thumbnail_view.getSelectionModel().getSelectedIndex();
		Photo selected = obsList.get(index);
		// obsList.remove(thumbnail_view.getSelectionModel().getSelectedIndex());
		album.removePhoto(selected);
		thumbnail_view.getSelectionModel().select(index - 1);
		updateAlbum();
	}
	
	
	/**
	 * 
	 * Takes a photo, and copies it from one album to another.
	 * 
	 * @param p The photo you want to copy
	 * @param a The Source Album you're copying the photo from
	 * @param b The Destination Album you're copying the photo to
	 * 
	 * */
	public boolean copyTo(Photo p, Album a, Album b) {
		if(!(a.contains(p)) || b.contains(p))
			return false;
		
		b.addPhoto(p);
		
		return true;
	}
	
	/**
	 * 
	 * Takes a photo, and moves it from one album to another.
	 * 
	 * @param p The photo you want to move
	 * @param a The Source Album you're moving, and removing the photo from
	 * @param b The Destination Album you're moving the photo to
	 * 
	 * */
	public boolean moveTo(Photo p, Album a, Album b) {
		if(!(a.contains(p)) || b.contains(p))
			return false;
		
		copyTo(p, a, b);
		a.removePhoto(p);
		
		return true;
	}
}
