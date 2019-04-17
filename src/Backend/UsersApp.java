package Backend;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;


public class UsersApp implements Serializable {
	
	private static final long serialVersionUID = 1L;
	public static String storeDir;
	public static String storeFile = "users.dat";
	public ArrayList<User> app_users;
	
	
	public UsersApp(ArrayList<User> allUsers) throws IOException {
		
		app_users = allUsers;
		storeDir = new File(".").getCanonicalPath() + "/dat";
		storeFile = "users.dat";
		
	}
	
	public void writeApp() throws IOException {
		
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeDir + File.separator + storeFile));
		oos.writeObject(this);
	}
	
	public UsersApp readApp() throws IOException, ClassNotFoundException {
		
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeDir + File.separator + storeFile));
		UsersApp gapp = (UsersApp)ois.readObject();
		return gapp;
			
	} 
	
	public ArrayList<User> getUsers() {
		return app_users;
	}
	
	public User getUser(String name) {
		
		User get = new User(name);
		for(User a: app_users) {
			
			if(get.equals(a))
				return a;
		}
		
		return null;
	}
	
}
