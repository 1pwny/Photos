 /**
 * @author Anand Raju
 * @author Sammy Berger
 * 
 * <h1>Bishop</h1>
 * 
 *
 * An object that stores a Photo and various information about it.
 *
 * 
 * */

package Backend;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class Photo {

	private String path;
	private String caption;
	private ArrayList<Tag> tags;
	private Date date;
	
	public Photo(String p) {
		path = p;
		date = new Date((new File(p)).lastModified());
		tags = new ArrayList<Tag>();
	}
	
	public boolean removeTag(Tag t) {
		return false;
	}
	public boolean removeTag(String s) {
		return false;
	}
	public boolean recaption(String s) {
		if(s == null) {
			return false;
		}
		
		caption = s;
		return true;
	}
	public Date date() {
		return date;
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
