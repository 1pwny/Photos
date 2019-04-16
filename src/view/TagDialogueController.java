package view;

import java.util.ArrayList;

import Backend.Photo;
import Backend.Tag;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TagDialogueController {

	@FXML ListView<Tag> listView;

	@FXML Button add;
	@FXML Button edit;
	@FXML Button delete;

	@FXML TextField name_field;
	@FXML Text name_text;
	
	@FXML TextField value_field;
	@FXML Text value_text;
	
	private ArrayList<Tag> photo_tags;
	private Photo photo;

	private ObservableList<Tag> obsList;

	Stage stage_var;
	
	
	public void initData(Photo p) {
		photo = p;
		photo_tags = p.getTags();
	}
	
	
	public void start(Stage mainStage) {
		// create list of items
		// form arraylist
		stage_var = mainStage;
		obsList = FXCollections.observableArrayList(photo_tags);

		listView.setItems(obsList);
		disableButtons();

		//setting the listener for those items
		listView.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> selectedTag(mainStage));
		
		if(obsList.size() > 0)
			listView.getSelectionModel().select(0);
	}

	public void changeTable(ActionEvent e) {

		Button command = (Button)e.getSource();
		int index = listView.getSelectionModel().getSelectedIndex();

		if(command == add) {
			Tag tag = parseTag(new Tag("",""));

			if(tag.name().length() > 0 && tag.value().length() > 0) {

				if(!obsList.contains(tag)) {
					obsList.add(tag);

					name_field.clear();
					value_field.clear();
	
					listView.getSelectionModel().select(obsList.indexOf(tag));
				}

				else {
					errorMessage("Song already exists!");
				}
			} else {
				errorMessage("Each song needs a name and an artist!");
			}
		}

		else if(command == edit){
			Tag tag = parseTag(obsList.get(index));

			if(tag.name().length() > 0 && tag.value().length() > 0) {
				if(tag.equals(obsList.get(index)) || !obsList.contains(tag)) {
					//System.out.println("valid edit!");
					
					obsList.set(index, tag);

					name_field.clear();
					value_field.clear();
					
					listView.getSelectionModel().select(obsList.indexOf(tag));
				}

				else {
					errorMessage("Song already exists!");
				}
			} else {
				errorMessage("Each song needs a name and an artist!");
			}
		}

		else{

			obsList.remove(index);
			if(obsList.size() > 0) {
				
				if(index == obsList.size()) 
					index--;				
				
				listView.getSelectionModel().select(index);
			}
			
			else {
				
				name_text.setText("Tag Name:");
				value_text.setText("Tag Value:");
				disableButtons();
			}
				
		}

	}

	private void selectedTag(Stage mainStage) {

		try {

			Tag selected = obsList.get(listView.getSelectionModel().getSelectedIndex());
			enableButtons();

			name_text.setText("Tag Name: " + selected.name());
			value_text.setText("Tag Value: "+ selected.value());

			if(selected.value().equals(""))
				value_text.setText("Tag Value: "+ "N/A");

			else
				value_text.setText("Tag Value: "+ selected.value());

			if(selected.value().equals(""))
				name_text.setText("Tag Name: "+ "N/A");

			else
				name_text.setText("Tag Name: " + selected.name());

		}

		catch(IndexOutOfBoundsException e){
		}

	}
	
	public void updatePhoto() {
		obsList = FXCollections.observableArrayList(photo.getTags());
	}

	private void disableButtons(){

		edit.setDisable(true);
		delete.setDisable(true);
	}

	private void enableButtons(){

		edit.setDisable(false);
		delete.setDisable(false);
	}

	private Tag parseTag(Tag t) {
		
		Tag tag = new Tag("", "");
		tag.set_name(retText(t, name_field));
		tag.set_value(retText(t, value_field));
		
		return tag;
		
	}
	
	private String retText(Tag tag, TextField t) {
		
		String tag_text = "";
		
		String text = t.getText();
		text = removeExternalSpaces(text);
		if(text.length() > 0)
			tag_text = text;
		else
			tag_text = tag.name();
		
		return tag_text;
	}
		
	
	public static String removeExternalSpaces(String s) {
		int starting = 0, ending = 0;
		
		if(s.length() == 0)
			return s;
		
		for(starting  = 0; starting < s.length(); starting++) {
			if(s.charAt(starting) != ' ')
				break;
		}
		
		if(starting == s.length())
			return "";
		
		for(ending = s.length(); ending > 0; ending--) {
			if(s.charAt(ending-1) != ' ')
				break;
		}
		
		//System.out.println(s + ", [" + s.substring(starting,ending) + "]");
		
		return s.substring(starting, ending);
	}

	private void errorMessage(String message) {
		/* To be completed */

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.initOwner(stage_var);
		alert.setTitle("Error!");
		alert.setHeaderText(message);
		alert.showAndWait();
	}

	// Method for when deselecting the item the user chose in the listview when you click outside the list
	public void deselect(){

		listView.getSelectionModel().clearSelection();
		disableButtons();

		name_text.setText("Tag Name:");
		value_text.setText("Tag Value:");

	}


}