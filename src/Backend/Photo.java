 

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
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
