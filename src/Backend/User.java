/**
 * @author Anand Raju
 * @author Sammy Berger
 *
 * <h1>User</h1>
 *
 *
 * An object that stores a Photo and various information about it.
 *
 *
 * */

package Backend;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	public static final String storeDir = "dat";
	public static final String storefile = "album.dat";

	public String username;
	private ArrayList<Album> albums;

	public User(String name) {
		albums = new ArrayList<Album>();
		username = name;
	}

	public void addAlbum(Album a) {
		if(!albums.contains(a))
			albums.add(a);
	}
	public void addAlbum(String s) {
		Album temp = new Album(s);
		if(!albums.contains(s))
			albums.add(temp);
	}
	public boolean removeAlbum(Album a) {
		return albums.remove(a);
	}
	public boolean removeAlbum(String s) {
		return albums.remove(new Album(s));
	}
	public Album removeAlbum(int index) {
		if(index < 0 || index >= albums.size())
			return null;
		
		return albums.remove(index);
	}
	
	public ArrayList<Album> getAlbums() {
		return albums;
	}

}
