/**
 * 
 * 
 * <h1>Tag</h1>
 * 
 * The code for Tags. Tags are, in essence, Strings. In fact, there's not too much reason not to just
 * use a String. But for a few minor reasons, we've decided to make and use a Tag class instead of just
 * Strings.
 * 
 * @author Sammy Berger
 * 
 * */

package Backend;

import java.io.Serializable;

public class Tag implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private String value;
	
	/**
	 * creates a new Tag with the given value
	 * 
	 * @param s: the Tag's value
	 */
	public Tag(String n, String v) {
		name = n;
		value = v;
	}
	
	
	/**
	 * returns the value of the tag
	 * 
	 * @return the value of the tag
	 */
	public String value() {
		return value;
	}
	
	public String name() {
		return name;
	}
	
	public void set_name(String n) {
		name = n;
	}
	
	public void set_value(String v) {
		value = v;
	}
	
	/**
	 * returns the name and value of the tag
	 * 
	 * @return the value of the tag
	 */
	@Override
	public String toString() {
		return name() + ": " + value();
	}
	/**
	 * checks if the Tag is equal to a given object
	 * 
	 * @return True if and only if either of the following is true:
	 *  - o is a String which is equal to this Tag's value
	 *  - o is another Tag which has the same value as this tag
	 */
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Tag)) {
			if(o instanceof String)
			return this.value().equals((String)o);
		}
		
		if(o instanceof Tag) {
			return this.value().equals(((Tag) o).value()) && this.name().equals(((Tag) o).name());
		}
		
		return false;
	}
}
