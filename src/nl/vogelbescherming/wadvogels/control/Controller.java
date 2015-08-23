package nl.vogelbescherming.wadvogels.control;

import android.content.Context;
import android.text.BoringLayout;
import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import nl.vogelbescherming.wadvogels.R;
import nl.vogelbescherming.wadvogels.fonts.Fonts;
import nl.vogelbescherming.wadvogels.model.AstronomicalTideGroup;
import nl.vogelbescherming.wadvogels.model.Bird;
import nl.vogelbescherming.wadvogels.model.Location;
import nl.vogelbescherming.wadvogels.util.XMLParser;


public class Controller {
	
	private static List<Bird> birds;
	private static List<Location> locations;
	private static ArrayList<AstronomicalTideGroup> astronomicalTide;
	private static Bird myBird = new Bird();
	
	private static Context context;
	//private static final String ASTRONOMICAL_URL = "http://live.getij.nl/wsgetij/hwlw.php?format=xml&from=20120802&&to=20120808&location=TEXNZE&Timezone=MET_DST&reference=LAT";

	public void init(Context context){
		this.context = context;
		(new Fonts()).getFonts(context);
	}
	public static Bird getMyBird() {
		return myBird;
	}
	public static void clearMyBird() {
		myBird = new Bird();
	}
	//public static getController

	public static void setSilhuette(Integer silhouette){
		//Log.d("HAI000","HAI000 "+silhouette);
		myBird.addSilhuette(silhouette);
	}
	public static void setBeak(Integer beak){
		myBird.addBeak(beak);
	}
	public static void addColor(Integer color){
		myBird.AddColor(color);
	}
	public static void setColor(List<Integer> color){
		myBird.setColor(color);
	}
	public static void setSize(List<Integer> sizes){
		myBird.setSize(sizes);
	}
	public static void addSize(Integer size){
		myBird.AddSize(size);
	}
	public static void addSize(int size){
		myBird.AddSize(size);
	}
	
	public static List<Bird> getBirds(Context context){
		if (birds == null)
			birds = createListBirds(context);
		
		Collections.sort(birds, new Comparator<Bird>() {
			@Override
			public int compare(Bird lhs, Bird rhs) {
				return lhs.getSort() - rhs.getSort();		
//				return lhs.getName().compareToIgnoreCase(rhs.getName());
			}
		});
		return birds;
	}
	
	public static ArrayList<Bird> getSelectedBirds(Context context, int chance, int appears, String text) {
		if (birds == null)
			birds = createListBirds(context);
		
		ArrayList<Bird> birdsList = new ArrayList<Bird>();
		for (Bird bird : birds) {
			if (bird.getChance() == chance && bird.getAppears().get(0) == appears && bird.getName().contains(text)) {
				birdsList.add(bird);
			}
		}

		return birdsList;
	}
	public static List<Bird> getFilteredBirds(Context context){
		Filter filter = new Filter(myBird);
		List<Bird> result = new ArrayList<Bird>();
//		if (!filter.isFilterEnable())
//			return getBirds(context);
		if (!filter.isFilterEnable())
			return result;
		
		result = filter.filterSulhuette(getBirds(context));
		result = filter.filterColors(result);
		result = filter.filterSizes(result);
		result = filter.filterBeak(result);
		
		//result = filter.sortBirds(result); // Remove repeted birds,
		Collections.sort(result, new Comparator<Bird>() {
			@Override
			public int compare(Bird lhs, Bird rhs) {
				int i = rhs.getChance() - lhs.getChance();
				if (i == 0)
					return lhs.getSort() - rhs.getSort();
				return i;
			}
		});
		result = filter.sortBirdsWithChance(result);
		
		return result;
	}
	
	private static List<Bird> createListBirds(Context context){
		List<Bird> newbirds = null;
		try {
			newbirds = XMLParser.getBirdsFromXML(context);
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return newbirds;
	}
	
	public static ArrayList<AstronomicalTideGroup> getAstronomicalTide(String code){
//		if (astronomicalTide != null)
//			return astronomicalTide;
		Calendar calendar = Calendar.getInstance();
		String now = new SimpleDateFormat("yyyyMMdd").format(calendar.getTime());
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 7);
		String to = new SimpleDateFormat("yyyyMMdd").format(calendar.getTime());
		String astronomical_url = String.format(context.getString(R.string.astronomical_url), code, now, to);
		Log.i("astronomical_url", astronomical_url);
		astronomicalTide = XMLParser.getXMLDataToTable(astronomical_url);
		return astronomicalTide;
	}
	public static List<Location> getLocations(Context context){
		if (locations != null)
			return locations;
		
		try {
			locations = XMLParser.getLocationsFromXML(context);
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return locations;
	}
	public static void createMyBird() {
		myBird = new Bird();
	}
	public static void clearSilhuette() {
		myBird.clearSilhuette();
	}
	public static void clearBeak() {
		myBird.clearBeak();
	}
	public static void clearColors() {
		myBird.clearColors();
	}
	public static void clearSizes() {
		myBird.clearSizes();
	}
}