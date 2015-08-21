package nl.vogelbescherming.wadvogels.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Tide {
	private int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTide() {
		return tide;
	}
	public void setTide(String tide) {
		this.tide = tide;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	private String date;
	private String tide;
	private String time;
	private String height;
	
	public Tide(JSONObject json) throws JSONException {
		id = json.getInt("id");
		date = json.getString("date");
		tide = json.getString("tide");
		time = json.getString("time");
		height = json.getString("height");
	}
}
