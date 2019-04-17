package Backend;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SearchTerm {

	private Tag t1,t2;
	private char andor;
	
	private Date early, late;
	
	private boolean isValid;
	
	/**
	 * checks if the given String is a valid search, and if it is, makes a new valid instance
	 * 
	 * @param s
	 */
	public SearchTerm(String s) {
		String[] parsed = s.split(" ");
		
		if(parsed.length != 1 && parsed.length != 3) {
			isValid = false;
			return;
		}
		
		t1 = new Tag(parsed[0].split("=")[0], parsed[0].split("=")[1]);
		
		if(parsed.length != 3) {
			andor = 'x';
		}
		
		else {
			if(parsed[1].equalsIgnoreCase("AND")) {
				andor = 'a';
			} else if(parsed[1].equalsIgnoreCase("OR")) {
				andor = 'o';
			} else {
				isValid = false;
				return;
			}
			t2 = new Tag(parsed[2].split("=")[0], parsed[2].split("=")[1]);
		}
		
		isValid = true;
	}

	/**
	 * same as above, but also accomodates a date range
	 * 
	 * @param s
	 * @param e
	 * @param l
	 */
	public SearchTerm(String s, String e, String l) {
		this(s);
		
		if(!isValid)
			return;
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		if(e != null && e.length() != 0) {
			try {
				early = sdf.parse(e);
			} catch(Exception ex) {
				isValid = false;
			}	
		} else {
			e = null;
		}
		if(l != null && l.length() != 0) {
			try {
				late = sdf.parse(l);
			} catch(Exception ex) {
				isValid = false;
			}	
		} else {
			late = null;
		}
		
		isValid = true;
	}
	
	/**
	 * @return if this is a valid SearchTerm
	 */
	public boolean isvalid() {
		return isValid;
	}
	
	/**
	 * checks if a given photo matches this searchterm
	 * 
	 * @param p
	 * @return
	 */
	public boolean allows(Photo p) {
		if(!isValid)
			return false;
		
		if(early != null && p.date().before(early))
			return false;
		if(late != null && p.date().after(late))
			return false;
		
		//System.out.println("Date is good");
		
		boolean hasT1 = p.getTags().contains(t1);
		if(andor == 'x')
			return hasT1;
		
		//System.out.println("sorting >1 tag");
		
		boolean hasT2 = p.getTags().contains(t2);
		if(andor == 'a') {
			return hasT1 && hasT2;
		} else if(andor == 'o') {
			return hasT1 || hasT2;
		}
		
		return false;
	}
	
	/**
	 * tries to print out the searched values in a reasonable manner
	 * 
	 */
	public String toString() {
		if(!isValid)
			return "invalid search";
		
		String ret = "[" + t1.toString();
		
		if(andor == 'a') {
			ret += "] and [" + t2.toString();
		} else if(andor == 'o') {
			ret += "] or [" + t2.toString();
		}
		
		ret += "], ";
		
		ret += (early == null) ? "any date" : (new SimpleDateFormat("MM/dd/yyyy").format(early));
		ret += " - ";
		ret += (late == null) ? "any date" : (new SimpleDateFormat("MM/dd/yyyy").format(late));
		
		return ret;
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		SearchTerm st = new SearchTerm("name=Sammy or hair=brown","1/1/1991", "9/9/2020");
		//System.out.println(st.t1);
		//System.out.println(st.t2);
		//System.out.println(st.early);
		//System.out.println(st.late);
		//System.out.println(st.isValid);
		//System.out.println(st.andor);
		
		//Photo p = new Photo("./stock_folor/dice.png");
		//p.tag("name","Sammy");
		//p.tag("hair","brown");
		//System.out.println(st.allows(p));
		
		System.out.println(st);
	}
}
