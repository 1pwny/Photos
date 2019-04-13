package Backend;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Album implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ArrayList<Photo> photos;
	
	public Date earliest, latest;
	public String name;
	
	public Album() {
		photos = new ArrayList<Photo>();
		name = "";
	}
	
	public Album(String n) {
		photos = new ArrayList<Photo>();
		name = n;
	}
	
	public void addPhoto(Photo p) {
		photos.add(p);
	}
	
	public void printAlbum() {
		
		for (Photo p: photos) {
			System.out.println(p);
		}
	}
}