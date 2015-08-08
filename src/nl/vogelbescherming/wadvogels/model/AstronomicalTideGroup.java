package nl.vogelbescherming.wadvogels.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
/*
 * Группировка AstronomicalTide по дате
 */
public class AstronomicalTideGroup {

	private String datetime; // дата
	private List<AstronomicalTide> list; // список
    private long timestamp = 0;
	
	public AstronomicalTideGroup(){
		list = new ArrayList<AstronomicalTide>();
	}
	public void setDate(String date) {
		String year = date.substring(0, 4); 
		String month = date.substring(4, 6);
		String day = date.substring(6, 8);
		
		datetime = day + "-" + month + "-" + year;

        Calendar astroCal = Calendar.getInstance();
        astroCal.set(Integer.valueOf(year),Integer.valueOf(month),Integer.valueOf(day));
        timestamp = astroCal.getTimeInMillis();

	}
	public String getDate() {
		return datetime;
	}
	public void add(AstronomicalTide astronomicalTide) {
		list.add(astronomicalTide);
	}
	public List<AstronomicalTide> getGroup() {
		return list;
	}

    public long getTimestamp(){
        return timestamp;
    }

    @Override
    public String toString() {
        return datetime+" = "+getTimestamp();
    }
}
