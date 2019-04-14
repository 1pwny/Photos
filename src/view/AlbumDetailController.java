package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;

import Backend.Album;
import Backend.Photo;
import Backend.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.image.Image;
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
	
	public void start() throws FileNotFoundException {
		
		
		Boolean b = album.addPhoto(new Photo("file:img_folder/tree.jpg", "pretty tree!", 50, 50));
		
		
		obsList = FXCollections.observableArrayList(album.getPhotos());
		thumbnail_view.setItems(obsList);
		
		thumbnail_view.setCellFactory(new Callback<ListView<Photo>, ListCell<Photo>>() {
			
			@Override
			public ListCell<Photo> call(ListView<Photo> param) {
				// TODO Auto-generated method stub
				return new ImageCell();
			}
        }
    );
		
		Image image2 = new Image("file:img_folder/tree.jpg");
		slideshow_view.setImage(image2);
			
	}
	
	static class ImageCell extends ListCell<Photo> {
        @Override
        public void updateItem(Photo item, boolean empty) {
            super.updateItem(item, empty);
            ImageView cellview = new ImageView();
            if (item != null) {

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
	

}
