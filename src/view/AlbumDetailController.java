package view;

import Backend.Album;
import Backend.Photo;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import javafx.scene.control.TextField;
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
	
	@FXML ListView<Photo> thumbnails;
	
	@FXML Button add;
	@FXML Button remove;
	@FXML Button copy;
	@FXML Button move;

	

}