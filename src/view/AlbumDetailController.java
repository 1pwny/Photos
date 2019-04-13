package view;

import Backend.Album;
import Backend.Photo;
import Backend.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

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
	
	private Album album;
	private ObservableList<Photo> obsList;
	
	
	public void initData(Album selected) {
		// TODO Auto-generated method stub
		album = selected;
		
	}
	
	public void start() {
		
		for(int i = 0; i < 5; i++) {
			
			String path = "Path" + i;
			Boolean b = album.addPhoto(new Photo(path));
		}
		
		
		if(album.getPhotos() != null) {
			
			obsList = FXCollections.observableArrayList(album.getPhotos());
			
			if(obsList != null) 
				thumbnail_view.setItems(obsList);
			
			else {
				System.out.print("Error! obslist is null!");
			}
		}
			
		
		else {
			System.out.print("Error! Photos is null!");
		}
		
	}

	

}
