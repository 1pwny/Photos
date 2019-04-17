package view;

import java.util.ArrayList;

import Backend.Photo;
import Backend.Tag;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * 
 * This is the dialogue controller that creates a seperate window, specifically
 * to handle adding, edititng and deleting tags. 
 * 
 * @author Anand Raju
 * @author Sammy Berger
 * 
 * */
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
	
	/**
	 * 
	 * Method responsible for passing data to the TagDialogue Controller
	 * 
	 * */
	public void initData(Photo p) {
		photo = p;
		photo_tags = p.getTags();
	}
	
	/**
	 * 
	 * Upon starting, the controller sets an observable list of Tags, and adds a listener for each of the 
	 * selected Tags. 
	 * 
	 * 
	 * */
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
	
	
	/**
	 * 
	 * EventHandler responsible for adding, editing and deleting the tags. Upon clicking the 'add' button.
	 * The method takes the input from both textfields and creates a new tag if it doesn't exist.
	 * 
	 * Clicking the 'edit' button results in the method taking input from the textfields and changing the 
	 * selected Tag's name and value.
	 * 
	 * Clicking the 'delete' button results in the method removing the tag from the list. 
	 * 
	 * The photo is then updated to reflect the changes back in the detailview. 
	 * 
	 * */
	public void changeTable(ActionEvent e) {

		Button command = (Button)e.getSource();
		int index = listView.getSelectionModel().getSelectedIndex();

		if(command == add) {
			Tag tag = parseTag(new Tag("",""));

			if(tag.name().length() > 0 && tag.value().length() > 0) {

				if(!obsList.contains(tag)) {
					photo.tag(tag);

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
					
					photo.set(index, tag);

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

		else {

			photo.removeTag(index);
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
		
		updatePhoto();

	}
	
	/**
	 * 
	 * Listener method that displays the tag name and tag value upon selection. 
	 * 
	 * 
	 * */
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
	
	/**
	 * 
	 * helper method that refreshes the tags on the listview. 
	 * 
	 * */
	public void updatePhoto() {
		obsList = FXCollections.observableArrayList(photo.getTags());
		listView.setItems(obsList);
	}
	
	/**
	 * 
	 * helper method that disables edit and delete buttons when an item in the listview isn't selected.
	 * 
	 * */
	private void disableButtons(){

		edit.setDisable(true);
		delete.setDisable(true);
	}
	
	/**
	 * 
	 * helper method that enables edit and delete buttons when an item in the listview is selected.
	 * 
	 * */
	private void enableButtons(){

		edit.setDisable(false);
		delete.setDisable(false);
	}
	
	/**
	 * 
	 * helper method that makes a valid Tag based on the input of the textfields
	 * 
	 * @return a Tag to be added to the list.
	 * 
	 * */
	private Tag parseTag(Tag t) {
		
		Tag tag = new Tag("", "");
		tag.set_name(retText(t, name_field));
		tag.set_value(retText(t, value_field));
		
		return tag;
		
	}
	
	/**
	 * 
	 * @return Returns text and returns empty if there is nothing in the text field. 
	 * 
	 * */
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
		
	/**
	 * 
	 * @retuns text that is stripped of external spaces and whitespace.
	 * 
	 * */
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
	
	
	/**
	 * 
	 * Makes and error message pop up with custom message
	 * 
	 * @param message  the message you want to show
	 * 
	 * */
	private void errorMessage(String message) {
		/* To be completed */

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.initOwner(stage_var);
		alert.setTitle("Error!");
		alert.setHeaderText(message);
		alert.showAndWait();
	}

	/**
	 * 
	 * Method for when deselecting the item the user chose in the listview when you click outside the list
	 * 
	 * */
	public void deselect(){

		listView.getSelectionModel().clearSelection();
		disableButtons();

		name_text.setText("Tag Name:");
		value_text.setText("Tag Value:");

	}


}