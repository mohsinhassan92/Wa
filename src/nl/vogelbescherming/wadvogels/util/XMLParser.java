package nl.vogelbescherming.wadvogels.util;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.util.Log;

import org.apache.http.HttpStatus;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import nl.vogelbescherming.wadvogels.R;
import nl.vogelbescherming.wadvogels.model.AstronomicalTide;
import nl.vogelbescherming.wadvogels.model.AstronomicalTideGroup;
import nl.vogelbescherming.wadvogels.model.Bird;
import nl.vogelbescherming.wadvogels.model.Location;


public class XMLParser {

	public static List<Bird> getBirdsFromXML(Context context) throws XmlPullParserException, IOException {
		Resources res = context.getResources();
		List<Bird> birds = new ArrayList<Bird>();
		//XmlPullParser 
		XmlResourceParser xpp = res.getXml(R.xml.data_waddenapp);
		xpp.next();
		String str ="";
		int eventType = xpp.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT){
			if (eventType == XmlPullParser.START_TAG){
				str = xpp.getName();
				Log.i("S","S = " + str);
				if (str.equals("bird")){
					Bird bird = createBird(xpp);
					birds.add(bird);
				}
			}
			eventType = xpp.next();
		}
		return birds;
	}

	private static Bird createBird(XmlResourceParser xpp) {
		Bird bird = new Bird();
		int count = xpp.getAttributeCount();
		int counter = 0;

		if (count > 0){
			Log.i("Log", "res = " + xpp.getAttributeName(counter));
			if (counter < count){
				if (xpp.getAttributeName(counter).equalsIgnoreCase("id")){
					String id = xpp.getAttributeValue(counter);
					bird.setId(Integer.parseInt(id));
					counter++;
				}
			}
			Log.i("Log", "res = " + xpp.getAttributeName(counter));
			if (counter < count){
				if (xpp.getAttributeName(counter).equalsIgnoreCase("name")){
					String name = xpp.getAttributeValue(counter);
					bird.setName(name);
					counter++;
				}
			}
			Log.i("Log", "res = " + xpp.getAttributeName(counter));
			if (counter < count){
				if (xpp.getAttributeName(counter).equalsIgnoreCase("silhuette1")){
					String silhouette = xpp.getAttributeValue(counter);
					bird.parseSilhuette(Integer.parseInt(silhouette));
					counter++;
				}
			}Log.i("Log", "res = " + xpp.getAttributeName(counter));
			if (counter < count){
				if (xpp.getAttributeName(counter).equalsIgnoreCase("silhuette2")){
					String silhouette = xpp.getAttributeValue(counter);
					bird.parseSilhuette(Integer.parseInt(silhouette));
					counter++;
				}
			}Log.i("Log", "res = " + xpp.getAttributeName(counter));
			if (counter < count){
				if (xpp.getAttributeName(counter).equalsIgnoreCase("silhuette3")){
					String silhouette = xpp.getAttributeValue(counter);
					bird.parseSilhuette(Integer.parseInt(silhouette));
					counter++;
				}
			}Log.i("Log", "res = " + xpp.getAttributeName(counter));
			if (counter < count){
				if (xpp.getAttributeName(counter).equalsIgnoreCase("silhuette4")){
					String silhouette = xpp.getAttributeValue(counter);
					bird.parseSilhuette(Integer.parseInt(silhouette));
					counter++;
				}
			}Log.i("Log", "res = " + xpp.getAttributeName(counter));
			if (counter < count){
				if (xpp.getAttributeName(counter).equalsIgnoreCase("beak1")){
					String beek = xpp.getAttributeValue(counter);
					bird.parseBeak(Integer.parseInt(beek));
					counter++;
				}
			}Log.i("Log", "res = " + xpp.getAttributeName(counter));
			if (counter < count){
				if (xpp.getAttributeName(counter).equalsIgnoreCase("beak2")){
					String beek = xpp.getAttributeValue(counter);
					bird.parseBeak(Integer.parseInt(beek));
					counter++;
				}
			}Log.i("Log", "res = " + xpp.getAttributeName(counter));
			if (counter < count){
				if (xpp.getAttributeName(counter).equalsIgnoreCase("beak3")){
					String beek = xpp.getAttributeValue(counter);
					bird.parseBeak(Integer.parseInt(beek));
					counter++;
				}
			}Log.i("Log", "res = " + xpp.getAttributeName(counter));
			if (counter < count){
				if (xpp.getAttributeName(counter).equalsIgnoreCase("color1")){
					String color = xpp.getAttributeValue(counter);
					bird.AddColor(Integer.parseInt(color));
					counter++;
				}
			}Log.i("Log", "res = " + xpp.getAttributeName(counter));
			if (counter < count){
				if (xpp.getAttributeName(counter).equalsIgnoreCase("color2")){
					String color = xpp.getAttributeValue(counter);
					bird.AddColor(Integer.parseInt(color));
					counter++;
				}
			}Log.i("Log", "res = " + xpp.getAttributeName(counter));
			if (counter < count){
				if (xpp.getAttributeName(counter).equalsIgnoreCase("color3")){
					String color = xpp.getAttributeValue(counter);
					bird.AddColor(Integer.parseInt(color));
					counter++;
				}
			}Log.i("Log", "res = " + xpp.getAttributeName(counter));
			if (counter < count){
				if (xpp.getAttributeName(counter).equalsIgnoreCase("color4")){
					String color = xpp.getAttributeValue(counter);
					bird.AddColor(Integer.parseInt(color));
					counter++;
				}
			}Log.i("Log", "res = " + xpp.getAttributeName(counter));
			if (counter < count){
				if (xpp.getAttributeName(counter).equalsIgnoreCase("color5")){
					String color = xpp.getAttributeValue(counter);
					bird.AddColor(Integer.parseInt(color));
					counter++;
				}
			}Log.i("Log", "res = " + xpp.getAttributeName(counter));
			if (counter < count){
				if (xpp.getAttributeName(counter).equalsIgnoreCase("color6")){
					String color = xpp.getAttributeValue(counter);
					bird.AddColor(Integer.parseInt(color));
					counter++;
				}
			}Log.i("Log", "res = " + xpp.getAttributeName(counter));
			if (counter < count){
				if (xpp.getAttributeName(counter).equalsIgnoreCase("size1")){
					String size1 = xpp.getAttributeValue(counter);
					bird.parseSize(Integer.parseInt(size1));
					counter++;
				}
			}Log.i("Log", "res = " + xpp.getAttributeName(counter));
			if (counter < count){
				if (xpp.getAttributeName(counter).equalsIgnoreCase("size2")){
					String size1 = xpp.getAttributeValue(counter);
					bird.parseSize(Integer.parseInt(size1));
					counter++;
				}
			}Log.i("Log", "res = " + xpp.getAttributeName(counter));
			//------------------------------------------------------------------------------
			if (counter < count){
				if (xpp.getAttributeName(counter).equalsIgnoreCase("appears1")){
					String appears = xpp.getAttributeValue(counter);
					bird.addAppears(Integer.parseInt(appears));
					counter++;
				}
			}Log.i("Log", "res = " + xpp.getAttributeName(counter));
			if (counter < count){
				if (xpp.getAttributeName(counter).equalsIgnoreCase("appears2")){
					String appears = xpp.getAttributeValue(counter);
					bird.addAppears(Integer.parseInt(appears));
					counter++;
				}
			}Log.i("Log", "res = " + xpp.getAttributeName(counter));
			if (counter < count){
				if (xpp.getAttributeName(counter).equalsIgnoreCase("chance")){
					String message = xpp.getAttributeValue(counter);
					bird.setChance(message);
					counter++;
				}
			}Log.i("Log", "res = " + xpp.getAttributeName(counter));
			if (counter < count){
				if (xpp.getAttributeName(counter).equalsIgnoreCase("description")){
					String message = xpp.getAttributeValue(counter);
					bird.setDescription(message);
					counter++;
				}
			}Log.i("Log", "res = " + xpp.getAttributeName(counter));
			if (counter < count){
				if (xpp.getAttributeName(counter).equalsIgnoreCase("Grootte")){
					String message = xpp.getAttributeValue(counter);
					bird.setGrootte(message);
					counter++;
				}
			}Log.i("Log", "res = " + xpp.getAttributeName(counter));
			if (counter < count){
				if (xpp.getAttributeName(counter).equalsIgnoreCase("Snavel")){
					String message = xpp.getAttributeValue(counter);
					bird.setSnavel(message);
					counter++;
				}
			}Log.i("Log", "res = " + xpp.getAttributeName(counter));
			if (counter < count){
				if (xpp.getAttributeName(counter).equalsIgnoreCase("Poten")){
					String message = xpp.getAttributeValue(counter);
					bird.setPoten(message);
					counter++;
				}
			}Log.i("Log", "res = " + xpp.getAttributeName(counter));
			if (counter < count){
				if (xpp.getAttributeName(counter).equalsIgnoreCase("Opvallende")){
					String message = xpp.getAttributeValue(counter);
					bird.setOpvallende(message);
					counter++;
				}
			}Log.i("Log", "res = " + xpp.getAttributeName(counter));
			if (counter < count){
				if (xpp.getAttributeName(counter).equalsIgnoreCase("Gedrag")){
					String message = xpp.getAttributeValue(counter);
					bird.setGedrag(message);
					counter++;
				}
			}Log.i("Log", "res = " + xpp.getAttributeName(counter));
			if (counter < count){
				if (xpp.getAttributeName(counter).equalsIgnoreCase("Voedsel")){
					String message = xpp.getAttributeValue(counter);
					bird.setVoedsel(message);
					counter++;
				}
			}Log.i("Log", "res = " + xpp.getAttributeName(counter));
			if (counter < count){
				if (xpp.getAttributeName(counter).equalsIgnoreCase("Leefgebied")){
					String message = xpp.getAttributeValue(counter);
					bird.setLeefgebied(message);
					counter++;
				}
			}Log.i("Log", "res = " + xpp.getAttributeName(counter));
			if (counter < count){
				if (xpp.getAttributeName(counter).equalsIgnoreCase("Verspreiding")){
					String message = xpp.getAttributeValue(counter);
					bird.setVerspreiding(message);
					counter++;
				}
			}Log.i("Log", "res = " + xpp.getAttributeName(counter));
			if (counter < count){
				if (xpp.getAttributeName(counter).equalsIgnoreCase("Engelse")){
					String message = xpp.getAttributeValue(counter);
					bird.setEngelse(message);
					counter++;
				}
			}Log.i("Log", "res = " + xpp.getAttributeName(counter));
			if (counter < count){
				if (xpp.getAttributeName(counter).equalsIgnoreCase("Latijnse")){
					String message = xpp.getAttributeValue(counter);
					bird.setLatijnse(message);
					counter++;
				}
			}Log.i("Log", "res = " + xpp.getAttributeName(counter));
			if (counter < count){
				if (xpp.getAttributeName(counter).equalsIgnoreCase("MeerInfo")){
					String message = xpp.getAttributeValue(counter);
					bird.setMeerInfo(message);
					counter++;
				}
			}Log.i("Log", "res = " + xpp.getAttributeName(counter));
			if (counter < count){
				if (xpp.getAttributeName(counter).equalsIgnoreCase("Sort")){
					String message = xpp.getAttributeValue(counter);
					bird.setSort(Integer.parseInt(message));
					counter++;
				}
			}
//			if (counter < count){
//				if (xpp.getAttributeName(counter).equalsIgnoreCase("message")){
//					String message = xpp.getAttributeValue(counter);
//					bird.setMessage(message);
//					counter++;
//				}
//			}
			
		}
		return bird;
	}
	
	//-------------------------------------------------------------------------------------
	public static List<Location> getLocationsFromXML(Context context) throws XmlPullParserException, IOException {
		Resources res = context.getResources();
		List<Location> locations = new ArrayList<Location>();
		//XmlPullParser 
		XmlResourceParser xpp = res.getXml(R.xml.locations);
		xpp.next();
		String str ="";
		int eventType = xpp.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT){
			if (eventType == XmlPullParser.START_TAG){
				str = xpp.getName();
				if (str.equals("Row")){
					Location location = createLocation(xpp);
					locations.add(location);
				}
			}
			eventType = xpp.next();
		}
		return locations;
	}

	public static ArrayList<AstronomicalTideGroup> getXMLDataToTable(String urlString){
		
		String currentDate = "";
		
		//List<AstronomicalTide> astronomicalTides = new ArrayList<AstronomicalTide>();
		ArrayList<AstronomicalTideGroup> astronomicalTideGroups = new ArrayList<AstronomicalTideGroup>();
		AstronomicalTide astronomicalTide = null;
		AstronomicalTideGroup astGroup = null;
		
		XmlPullParserFactory factory;
		XmlPullParser xpp = null;
		try {
			factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			xpp = factory.newPullParser();
		} catch (XmlPullParserException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//XmlPullParser xpp = null;
		try{
			URL url = new URL(urlString);
			URLConnection connection = url.openConnection();
			HttpURLConnection httpConn = (HttpURLConnection)connection;
			httpConn.setDoInput(true);
			httpConn.setRequestProperty("charset", "utf-8");
			int responseCode = httpConn.getResponseCode();
			if(responseCode == HttpStatus.SC_OK) {
			    InputStream xmlStream = httpConn.getInputStream();
				xpp.setInput(xmlStream, null);
			    int eventType = xpp.getEventType();
			    while (eventType != XmlPullParser.END_DOCUMENT){
					if (eventType == XmlPullParser.START_TAG){
						String str = xpp.getName();
						Log.i("S","S = " + str);
						if (str.equals("location")){
							eventType = xpp.next();
							Log.i("S","location = " + xpp.getText());
							AstronomicalTide.setLocation(xpp.getText());
						} else if (str.equals("reference")){
							eventType = xpp.next();
							AstronomicalTide.setReference(xpp.getText());
						} else if (str.equals("timezone")){
							eventType = xpp.next();
							AstronomicalTide.setTimezone(xpp.getText());
						} else if (str.equals("source")){
							eventType = xpp.next();
							AstronomicalTide.setSource(xpp.getText());
						} else if (str.equals("value")){
							astronomicalTide = new AstronomicalTide();
						} else if (str.equals("datetime")){
							/*
							if (xpp.getAttributeName(0).equalsIgnoreCase("summertime")){
								String summertime = xpp.getAttributeValue(0);
								if (summertime.equals("yes"))
									astronomicalTide.setSummerTime(true);
								else
									astronomicalTide.setSummerTime(false);
							}*/
							astronomicalTide.setSummerTime(false); // 29-01-2014 summertime was removed from feed!
							eventType = xpp.next();
							String date = xpp.getText();
							date = date.substring(0, 8);
							if (!currentDate.equals(date)){
								if (astGroup != null){
									astronomicalTideGroups.add(astGroup);
								}
								currentDate = date;
								astGroup = new AstronomicalTideGroup();
								astGroup.setDate(date);
							}
							
							astronomicalTide.setDatetime(xpp.getText());
						} else if (str.equals("tide")){
							eventType = xpp.next();
							astronomicalTide.setTide(xpp.getText());
						} else if (str.equals("val")){
							eventType = xpp.next();
							astronomicalTide.setVal(xpp.getText());
							astGroup.add(astronomicalTide);
						}
					}
					eventType = xpp.next();
				}
			}
		} catch (IOException ex){
			ex.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		
		return astronomicalTideGroups;
	}
	private static Location createLocation(XmlResourceParser xpp) {
		Location location = new Location();
		int count = xpp.getAttributeCount();
		int counter = 0;

		if (count > 0){
			if (counter < count){
				if (xpp.getAttributeName(counter).equalsIgnoreCase("Name")){
					String name = xpp.getAttributeValue(counter);
					location.setName(name);
					counter++;
				}
			}
			if (counter < count){
				if (xpp.getAttributeName(counter).equalsIgnoreCase("Lat")){
					String lat = xpp.getAttributeValue(counter);
					location.setLat(lat);
					counter++;
				}
			}
			if (counter < count){
				if (xpp.getAttributeName(counter).equalsIgnoreCase("Lng")){
					String lng = xpp.getAttributeValue(counter);
					location.setLng(lng);
					counter++;
				}
			}
			if (counter < count){
				if (xpp.getAttributeName(counter).equalsIgnoreCase("Text")){
					String text = xpp.getAttributeValue(counter);
					location.setText(text);
					counter++;
				}
			}
			if (counter < count){
				if (xpp.getAttributeName(counter).equalsIgnoreCase("LocationCode")){
					String LocationCode = xpp.getAttributeValue(counter);
					location.setmLocationCode(LocationCode);
					counter++;
				}
			}
		}
		return location;
	}
	
}