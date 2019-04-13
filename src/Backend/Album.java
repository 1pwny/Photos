package Backend;

import java.util.ArrayList;
import java.util.Date;

public class Album {

	private ArrayList<Photo> photos;
	
	public Date earliest, latest;
	
	public Album() {
		photos = new ArrayList<Photo>();
	}
	
	public boolean addPhoto(Photo p) {
		return false;
	}
}