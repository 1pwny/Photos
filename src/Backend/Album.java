/**
 * <h1>Album</h1>
 *
 *
 * An object that stores multiple photos, along with a little bit of extra information: such as the
 * earliest and latest that a photo was edited.
 * 
 * @author Sammy Berger
 * 
 * @version 1.0
 * */

package Backend;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Album implements Serializable {

	private static final long serialVersionUID = 1L;

	private ArrayList<Photo> photos;

	public Date earliest, latest;
	public String name;

	
	/**
	 * Creates a new Album
	 */
	public Album() {
		photos = new ArrayList<Photo>();
		name = "";
	}
	
	/**
	 * makes an album with a specified name
	 * 
	 * @param n: the String name for the album
	 */
	public Album(String n) {
		photos = new ArrayList<Photo>();
		name = n;
	}

	/**
	 * adds a photo to the album
	 * 
	 * @param p: the Photo to add
	 * @return whether or not the photo was added successfully
	 */
	public boolean addPhoto(Photo p) {
		if(photos.contains(p)) {
			return false;
		}

		photos.add(p);
		if(earliest == null || p.date().before(earliest)) {
			earliest = p.date();
		}
		if(latest == null || p.date().after(latest)) {
			latest = p.date();
		}

		return true;
	}

	/**
	 * removes a photo from the album
	 * 
	 * @param p: the photo to remove
	 * @return the removed photo, in case you need it
	 */
	public Photo removePhoto(Photo p) {
		photos.remove(p);

		if(p.date().equals(earliest) || p.date().equals(latest))
			recalcDates();

		return p;
	}

	/**
	 * recalculates the earliest and latest dates that the album was edited, in case that's necessary.
	 */
	public void recalcDates() {
		if(photos.size() == 0) {
			earliest = null;
			latest = null;
			return;
		}

		for(Photo p: photos) {
			p.reDate();
		}
		
		Date te = photos.get(0).date(), tl = photos.get(0).date();

		for(Photo p: photos) {
			if(p.date().before(te))
				te = p.date();
			if(p.date().after(tl))
				tl = p.date();
			}
		}

	
	/**
	 * checks whether the album contains a given photo
	 * 
	 * @param p: the photo to check
	 * @return whether or not the album contains the photo
	 */
	public boolean contains(Photo p) {
		return photos.contains(p);
	}
	/**
	 * same as above, but by photo path instead of photo object
	 * 
	 * @param s: a String containing the photo's path
	 * @return whether or not the album has the photo
	 * @throws FileNotFoundException 
	 */
	public boolean contains(String s) throws FileNotFoundException {
		return photos.contains(new Photo(s));
	}
	
	/**
	 * returns a list of all photos in the album
	 * 
	 * @return an arraylist with all the album's photos
	 */
	public ArrayList<Photo> getPhotos(){
		return photos;
	}
	
	/**
	 * prints the album
	 */
	public void printAlbum() {

		for (Photo p: photos) {
			System.out.println(p);
		}
	}

	/**
	 * checks if a Photo with a given path is in the album. if so, returns it.
	 * 
	 * @param s: the photo path
	 * @return the photo (if it's there)
	 */
	public Photo getFromPath(String s) {
		for(Photo p: photos)
			if(p.equals(s))
				return p;
		
		return null;
	}
	
	
	/**
	 * checks if the photo is equal to another object.
	 * 
	 * @return True if and only if one of the following is true:
	 *  - the object is a String which equals the album's name
	 *  - the object is an Album with the same name
	 */
	@Override
	public boolean equals(Object o) {
		if(o instanceof String) {
			return name.equals((String)o);
		}
		
		if(o instanceof Album) {
			return name.equals(((Album)o).name);
		}
		
		return false;
	}
	
	
	/**
	 * returns the album name for easy ListView use
	 * 
	 * @return the name of the album
	 */
	@Override
	public String toString() {
		return name;
	}
	
	public void rename(String n) {
		name = n;
	}
}
