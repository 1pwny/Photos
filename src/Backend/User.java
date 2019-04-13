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
	}

}
