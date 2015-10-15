package nl.vogelbescherming.wadvogels.model;

import java.util.Calendar;

/*
 * один элемент из xml из интернета
 */
public class AstronomicalTide {

	private static String location;
	private static String reference;
	private static String timezone;
	private static String source;
	
	private String datetime;
	private boolean isSummerTime;
	private String tide;
	private String val;
	private String dd_mm_yyyy;
	private String hours_minuts;

    private long timestamp = 0;
	
	public AstronomicalTide(){
	}
	
	public static String getLocation() {
		return location;
	}

	public static void setLocation(String location) {
		AstronomicalTide.location = location;
	}

	public static String getReference() {
		return reference;
	}

	public static void setReference(String reference) {
		AstronomicalTide.reference = reference;
	}

	public static String getTimezone() {
		return timezone;
	}

	public static void setTimezone(String timezone) {
		AstronomicalTide.timezone = timezone;
	}

	public static String getSource() {
		return source;
	}

	public static void setSource(String source) {
		AstronomicalTide.source = source;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		
		String year = datetime.substring(0, 4); 
		String month = datetime.substring(4, 6);
		String day = datetime.substring(6, 8);
		
		dd_mm_yyyy = day + "-" + month + "-" + year;
		
		String hours = datetime.substring(8, 10);
		String minuts = datetime.substring(10, 12);
		
		hours_minuts = hours + ":" + minuts;

        Calendar astroCal = Calendar.getInstance();
        astroCal.set(Integer.valueOf(year),Integer.valueOf(month),Integer.valueOf(day),Integer.valueOf(hours),Integer.valueOf(minuts));
        timestamp = astroCal.getTimeInMillis();
		
		this.datetime = datetime;
	}
	public String getDd_mm_yyyy() {
		return dd_mm_yyyy;
	}
	public String getHours_minuts() {
		return hours_minuts;
	}
	public boolean isSummerTime() {
		return isSummerTime;
	}

	public void setSummerTime(boolean isSummerTime) {
		this.isSummerTime = isSummerTime;
	}

	public String getTide() {
		return tide;
	}

	public void setTide(String tide) {
		if (tide.equals("LW"))
			tide = "Laagwater";
		else if (tide.equals("HW"))
			tide = "Hoogwater";
		this.tide = tide;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

    public long getTimestamp(){
        return timestamp;
    }

}
