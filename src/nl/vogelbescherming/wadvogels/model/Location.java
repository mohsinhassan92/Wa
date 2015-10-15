package nl.vogelbescherming.wadvogels.model;

import java.io.Serializable;

/*
 * один элемент из xml из интернета
 */
public class Location implements Serializable{
	
	private String mName;
	private double mLat;
	private double mLng;
	private String mText;
	private String mLocationCode;
	
	public Location() {}
	
	public Location(String name, String lat, String lng, String text, String locationCode){
		mName = name;
		setLat(lat);
		setLng(lng);
		mText = text;
		mLocationCode = locationCode;
	}

	public String getName() {
		return mName;
	}

	public void setName(String mName) {
		this.mName = mName;
	}

	public double getLat() {
		return mLat;
	}

	public void setLat(String mLat) {
		this.mLat = Double.valueOf(mLat);
	}

	public double getLng() {
		return mLng;
	}

	public void setLng(String mLng) {
		this.mLng = Double.valueOf(mLng);
	}

	public String getText() {
		return mText;
	}

	public void setText(String mText) {
		this.mText = mText;
	}

	public String getmLocationCode() {
		return mLocationCode;
	}

	public void setmLocationCode(String mLocationCode) {
		this.mLocationCode = mLocationCode;
	}
	
//	public List<Bird> getBirds(List<Bird> allBirds){
//		List<Bird> birds = new ArrayList<Bird>();
//		String[] currentBirds = getBirds().split(",");
//		for (Bird bird : allBirds){
//			for (String birdName : currentBirds){
//				if (bird.getName().equalsIgnoreCase(birdName)){
//					birds.add(bird);
//				}
//			}
//		}
//		
//		return birds;
//	}
}
