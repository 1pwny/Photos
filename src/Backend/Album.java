package Backend;

import java.util.ArrayList;
import java.util.Date;

public class Album {

	private ArrayList<Photo> photos;
	
	public Date earliest, latest;
	
	public Album() {
		photos = new ArrayList<Photo>();
	}
	
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

	public Photo removePhoto(Photo p) {
		photos.remove(p);
		
		if(p.date().equals(earliest) || p.date().equals(latest))
			recalcDates();
		
		return p;
	}
	public void recalcDates() {
		if(photos.size() == 0) {
			earliest = null;
			latest = null;
			return;
		}
		
		Date te = photos.get(0).date(), tl = photos.get(0).date();
		
		for(Photo p: photos) {
			if(p.date().before(te))
				te = p.date();
			if(p.date().after(tl))
				tl = p.date();
		}
	}
}