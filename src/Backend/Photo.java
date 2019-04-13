 

package Backend;

import java.util.ArrayList;

public class Photo {

	private String path;
	private String caption;
	private ArrayList<Tag> tags;
	
	public Photo(String p) {
		path = p;
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
