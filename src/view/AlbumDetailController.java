package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
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
	
	Stage stage_var;
	
	
	public void initData(Album selected) {
		// TODO Auto-generated method stub
		album = selected;
		
	}
	
	public void start(Stage mainStage) throws FileNotFoundException, IOException {
		
		stage_var = mainStage;
		
		String path = new File(".").getCanonicalPath() + "/stock_folder";
		File stock_folder = new File(path);
		File[] files = null;
		
		if(stock_folder.isDirectory())
			files = stock_folder.listFiles();
		
		for(File file : files) {
			String name = file.getName();
			Boolean b = album.addPhoto(new Photo("file:stock_folder/" + name, name, 50, 50));
		}
		
		
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
	
	public void uploadPhoto() {
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Add Image");
		fileChooser.getExtensionFilters().addAll(
				new ExtensionFilter("jpeg files", "*.jpg"),
				new ExtensionFilter("png files", "*.png"));
		fileChooser.showOpenDialog(stage_var);
		
		File selectedFile = fileChooser.showOpenDialog(stage_var);
		 if (selectedFile != null) {
			 
			 Photo photo = new Photo("file:" + selectedFile.getName(), selectedFile.getName(), 50, 50)
			 Boolean b = album.addPhoto(photo);
			 obsList.add(photo);
		 }
	}
	

}
