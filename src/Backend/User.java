/**
 * <h1>User</h1>
 *
 *
 * An object that stores a Photo and various information about it.
 *
 *
 *	@author Sammy Berger
 *
 * */

package Backend;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	public String username;
	private ArrayList<Album> albums;

	/**
	 * creates a new User with a given name
	 * 
	 * @param name
	 */
	public User(String name) {
		albums = new ArrayList<Album>();
		username = name;
	}
	
	public boolean equals(Object o) {
		if(o instanceof User) {
			return this.toString().equals(((User)o).toString());
		}

		return false;
	}

	/**
	 * adds an Album to the User's album list
	 * 
	 * @param a: the album
	 */
	public void addAlbum(Album a) {
		if(!albums.contains(a))
			albums.add(a);
	}
	/**
	 * creates an album with the given name and adds it to the User's album list
	 * 
	 * @param s: the name
	 */
	public void addAlbum(String s) {
		Album temp = new Album(s);
		if(!albums.contains(s))
			albums.add(temp);
	}
	
	/**
	 * removes an album from the User's album list
	 * 
	 * @param album
	 * @return if it was removed
	 */
	public boolean removeAlbum(Album album) {
		return albums.remove(album);
	}
	/**
	 * removes an album with a given name from the User's list
	 * 
	 * @param name
	 * @return if it was removed
	 */
	public boolean removeAlbum(String name) {
		return albums.remove(new Album(name));
	}
	/**
	 * removes the album at a given index in the User's list
	 * 
	 * @param index
	 * @return if it was removed
	 */
	public Album removeAlbum(int index) {
		if(index < 0 || index >= albums.size())
			return null;
		
		return albums.remove(index);
	}
	
	public Album getAlbum(String name) {
		
		Album get = new Album(name);
		for(Album a: albums) {
			
			if(get.equals(a))
				return a;
		}
		
		return null;
	}
	
	/**
	 * returns all the User's albums
	 * 
	 * @return
	 */
	public ArrayList<Album> getAlbums() {
		return albums;
	}

	/**
	 * return the username for easy viewing in the Admin screen
	 * 
	 * @return the username
	 */
	@Override
	public String toString() {
		return username;
	}
}
