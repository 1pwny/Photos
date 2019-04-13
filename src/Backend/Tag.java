/**
 * @author Anand Raju
 * @author Sammy Berger
 * 
 * <h1>Tag</h1>
 * 
 * The code for Tags. Tags are, in essence, Strings. In fact, there's not too much reason not to just
 * use a String. But for a few minor reasons, we've decided to make and use a Tag class instead of just
 * Strings.
 * 
 * */

package Backend;


public class Tag {

	/**
	 * 
	 */

	private String t;
	
	public Tag(String s) {
		t = s;
	}
	
	public String value() {
		return t;
	}
	
	public String toString() {
		return t;
	}
	public boolean equals(Object o) {
		if(o instanceof String) {
			return t.equals((String)o);
		}
		
		if(o instanceof Tag) {
			return t.equals(((Tag)o).t);
		}
		
		return false;
	}
}
