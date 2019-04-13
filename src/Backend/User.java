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

}
