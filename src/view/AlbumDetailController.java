package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

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
	
	
	private ArrayList<Album> all_albums;
	private Album album;
	
	private ObservableList<Photo> obsList;
	
	Stage stage_var;
	DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
	
	
	public void initData(ArrayList<Album> list, Album selected) {
		// TODO Auto-generated method stub
		all_albums = list;
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
		
		thumbnail_view.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> selectedPhoto(mainStage));
		
		if(obsList.size() > 0)
			thumbnail_view.getSelectionModel().select(0);
			
	}
	
	
	private void selectedPhoto(Stage mainStage) {

		try {

			Photo selected = obsList.get(thumbnail_view.getSelectionModel().getSelectedIndex());
			//enableButtons();

			slideshow_view.setImage(selected.getImage());
			caption_text.setText(selected.getCaption());
			date_text.setText(df.format(selected.date()));
		}

		catch(IndexOutOfBoundsException e){
		}

	}
	
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
	
	public void uploadPhoto() {
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Add Image");
		fileChooser.getExtensionFilters().addAll(
				new ExtensionFilter("jpeg files", "*.jpg"),
				new ExtensionFilter("png files", "*.png"));
		
		File selectedFile = fileChooser.showOpenDialog(stage_var);
		if (selectedFile != null) {
			
			Photo photo = new Photo(selectedFile.toURI().toString(), selectedFile.getName(), 50, 50);
			Boolean b = album.addPhoto(photo);
			updateAlbum();
			//obsList.add(photo);
			
		 }
	}
	
	public void updateAlbum() {
		
		obsList = FXCollections.observableArrayList(album.getPhotos());
		thumbnail_view.setItems(obsList);
	}
	
	public boolean copyTo(Photo p, Album a, Album b) {
		if(!(a.contains(p)) || b.contains(p))
			return false;
		
		b.addPhoto(p);
		
		return true;
	}
	
	public boolean moveTo(Photo p, Album a, Album b) {
		if(!(a.contains(p)) || b.contains(p))
			return false;
		
		copyTo(p, a, b);
		a.removePhoto(p);
		
		return true;
	}
}
