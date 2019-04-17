 /**
 * <h1>Photo</h1>
 *
 *
 * An object with a Photo, and all the information about that photo you might need: its path, its image, its caption,
 * the list of tags assigned to it, and the date it was last edited.
 *
 * @author Sammy Berger
 *
 * */

package Backend;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;

import javafx.scene.image.Image;

public class Photo implements Serializable {

	private static final long serialVersionUID = 1L;
	private String path;
	private String caption;
	private ArrayList<Tag> tags;
	private Date date;

	
	/**
	 * Will automatically use the path provided to determine the date the photo was edited.
	 * @throws FileNotFoundException 
	 * 
	 * @param p: a String representing the path to the photo
	 */
	public Photo(String p) throws FileNotFoundException {
		path = p;
		//image = new Image(new FileInputStream(p));
		
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MILLISECOND, 0);

		date = c.getTime();
		tags = new ArrayList<Tag>();
	}
	
	/**
	 * Makes a photo and also starts it with a caption
	 * 
	 * @param p: a String representing the path to the photo
	 * @param cap: the photo's caption
	 * @throws FileNotFoundException 
	 */
	public Photo(String p, String cap) throws FileNotFoundException {
		path = p;
		//image = new Image(new FileInputStream(p));
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MILLISECOND, 0);

		date = c.getTime();
		tags = new ArrayList<Tag>();
		caption = cap;
	}
	
	
	
	/**
	 * Makes a photo and also uses the provided length and width to scale the image
	 * 
	 * @param p: a String representing the path to the photo
	 * @param l: length
	 * @param w: width
	 */
	/* public Photo(String p, int l, int w) {
		path = p;
		image = new Image(p, l, w, false, false);
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MILLISECOND, 0);

		date = c.getTime();
		tags = new ArrayList<Tag>();
	} */
	
	/**
	 * Does everything from above, but combines it into a single method
	 * 
	 * @param p
	 * @param cap
	 * @param l
	 * @param w
	 */
	/* public Photo(String p, String cap, int l, int w) {
		path = p;
		image = new Image(p, l, w, false, false);
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MILLISECOND, 0);

		date = c.getTime();
		tags = new ArrayList<Tag>();
		caption = cap;
	} */
	
	/**
	 * Will attempt to remove a given Tag from this photo
	 * 
	 * @param t: a Tag to remove
	 * @return true if the tag existed, false if not
	 * 
	 */
	public boolean removeTag(Tag t) {
		return tags.remove(t);
	}
	/**
	 * Will attempt to remove a Tag with the same value as this String.
	 * 
	 * @param s: a String to remove
	 * @return true if a tag with the same value exsts, false if not
	 */
	public boolean removeTag(String name, String value) {
		return tags.remove(new Tag(name, value));
	}
	
	public void removeTag(int index) {
		// TODO Auto-generated method stub
		tags.remove(index);
		
	}

	public void tag(Tag t) {
		if(!tags.contains(t))
			tags.add(t);
	}
	
	public void tag(String n, String v) {
		Tag t = new Tag(n, v);
		if(!tags.contains(t))
			tags.add(t);
	}
	
	/**
	 * @param s: A new caption
	 * @return 
	 * 
	 * Will try to recaption the photo using 's'
	 */
	public boolean recaption(String s) {
		if(s == null) {
			return false;
		}

		caption = s;
		return true;
	}

	/**
	 * @return Date: returns the date
	 */
	public Date date() {
		return date;
	}
	/**
	 * updates the date of this picture
	 */
	public void reDate() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MILLISECOND, 0);

		date = c.getTime();;
	}

	public String toString() {
		return path;
	}
	
	public Image getImage() {
		try {
			FileInputStream stream = new FileInputStream(path);
			return new Image(stream, 50, 50, false, false);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			return null;
		}
		
	}
	
	public String getCaption() {
		return caption;
	}

	public boolean equals(Object o) {
		if(o instanceof String)
			return path.equals((String)o);

		if(o instanceof Photo) {
			return path.equals(((Photo)o).path);
		}

		return false;
	}

	public ArrayList<Tag> getTags() {
		// TODO Auto-generated method stub
		return tags;
	}

	public void set(int index, Tag tag) {
		// TODO Auto-generated method stub
		tags.set(index, tag);
		
	}

	
}
