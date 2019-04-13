 /**
 * @author Anand Raju
 * @author Sammy Berger
 *
 * <h1>Bishop</h1>
 *
 *
 * An object that stores a Photo and various information about it, such as the date it was last edited, the
 * caption it might have, any tags attached to it, etc.
 *
 * */

package Backend;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Photo {

	private String path;
	private String caption;
	private ArrayList<Tag> tags;
	private Date date;

	
	/**
	 * @param p: a String representing the path to the photo
	 * 
	 * Will automatically use the path provided to determine the date the photo was edited.
	 */
	public Photo(String p) {
		path = p;
		date = new Date((new File(p)).lastModified());
		tags = new ArrayList<Tag>();
	}

	/**
	 * @param t: a Tag to remove
	 * 
	 * @return true if the tag existed, false if not
	 * 
	 * Will attempt to remove a given Tag from this photo
	 */
	public boolean removeTag(Tag t) {
		return tags.remove(t);
	}
	/**
	 * @param s: a String to remove
	 * 
	 * @return true if a tag with the same value exsts, false if not
	 * 
	 * Will attempt to remove a Tag with the same value as this String.
	 */
	public boolean removeTag(String s) {
		return tags.remove(new Tag(s));
	}

	public void tag(Tag t) {
		if(!tags.contains(t))
			tags.add(t);
	}
	public void tag(String s) {
		Tag t = new Tag(s);
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
		date = new Date((new File(path)).lastModified());
	}

	public String toString() {
		return path;
	}

	public boolean equals(Object o) {
		if(o instanceof String)
			return path.equals((String)o);

		if(o instanceof Photo) {
			return path.equals(((Photo)o).path);
		}

		return false;
	}
}
