package nl.vogelbescherming.wadvogels;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import nl.vogelbescherming.wadvogels.R;
import nl.vogelbescherming.wadvogels.adapters.TideTimesListAdapter;
import nl.vogelbescherming.wadvogels.control.Controller;
import nl.vogelbescherming.wadvogels.fonts.Fonts;
import nl.vogelbescherming.wadvogels.model.AstronomicalTideGroup;
import nl.vogelbescherming.wadvogels.model.Location;
import nl.vogelbescherming.wadvogels.model.Tide;
import nl.vogelbescherming.wadvogels.model.TideTimeAdapter;

public class BirdSpotsActivity extends ContentBaseActivity {

	private GoogleMap mMap;
	private List<Location> locations;
	private List<Tide> mTidesList;
//	private Map<LatLng, String> mAreaMap;
	private String code;

	private ListView listView;
	private TideTimeAdapter la;
	private static AsyncHttpClient client = new AsyncHttpClient();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContent(R.layout.activity_bird_spots);

		setButton(R.drawable.map_button_state);
		showButton(false);
		showAllBirdsButton(false);
		hideButtons();
		showVogelPlekkenMenuAsActive();
		hideTitleContainer();
		//		setSubHeaderTitle("Dit zijn de mooiste vogellocaties in het waddengebied.\nKlik voor meer informatie op een locatiepunt.");
		locations = Controller.getLocations(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		setUpMapIfNeeded();
	}

	private void setUpMapIfNeeded() {
		// Do a null check to confirm that we have not already instantiated the map.
		if (mMap == null) {
			// Try to obtain the map from the SupportMapFragment.
			mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
					.getMap();
			// Check if we were successful in obtaining the map.
			if (mMap != null) {
				setUpMap();
			}
		}
	}
	private void setUpMap() {
		if (locations != null) {
//			mAreaMap = new HashMap<LatLng, String>();
			for (Location loc : locations) {
				String str = loc.getText();
				String locationCode = loc.getmLocationCode();
				str = str.replaceAll("\\\\n", " ");
				BitmapDescriptor pin = BitmapDescriptorFactory.fromResource(R.drawable.pin);
				if (locationCode.equals("TEXNZE") || locationCode.equals("VLIELHVN") || locationCode.equals("TERSLNZE") || locationCode.equals("NES")
						|| locationCode.equals("SCHIERMNOG") || locationCode.equals("DENHDR") || locationCode.equals("WIERMGDN") || locationCode.equals("EEMHVN")) {
					pin = BitmapDescriptorFactory.fromResource(R.drawable.pin_yellow);
				}

//				mAreaMap.put(new LatLng(loc.getLat(), loc.getLng()), loc.getmLocationCode());

				mMap.addMarker(new MarkerOptions()
				.position(new LatLng(loc.getLat(), loc.getLng()))
				.title(loc.getName())
				.snippet(str)
				.icon(pin));
				// Setting a custom info window adapter for the google map
				mMap.setInfoWindowAdapter(new InfoWindowAdapter() {

					// Use default InfoWindow frame
					@Override
					public View getInfoWindow(Marker arg0) {
						return null;
					}

					// Defines the contents of the InfoWindow
					@Override
					public View getInfoContents(final Marker marker) {
						LatLng latlng = marker.getPosition();
//						String locationCode = mAreaMap.get(latlng);
						if (code.equals("TEXNZE") || code.equals("VLIELHVN") || code.equals("TERSLNZE") || code.equals("NES")
								|| code.equals("SCHIERMNOG") || code.equals("DENHDR") || code.equals("WIERMGDN") || code.equals("EEMHVN")) {
							View v = getLayoutInflater().inflate(R.layout.activity_tide_times, null);
							
							((TextView) v.findViewById(R.id.datum)).setTypeface(Fonts.getTfFont_bold());
							((TextView) v.findViewById(R.id.hoog)).setTypeface(Fonts.getTfFont_bold());
							((TextView)	v.findViewById(R.id.hoogte)).setTypeface(Fonts.getTfFont_bold());
							listView = (ListView) v.findViewById(R.id.tide_time_list);
					        TextView headerText = (TextView) v.findViewById(R.id.headerText);
					        headerText.setTypeface(Fonts.getTfFont_regular());
							String url = getResources().getString(R.string.url_get_regions_info) + code;
							
							la = new TideTimeAdapter(BirdSpotsActivity.this, mTidesList);
			                listView.setAdapter(la);
			                
					        client.get(url, mAsyncResponseHandler);
							
//							TextView title = (TextView) v.findViewById(R.id.title);
//							TextView snippet = (TextView) v.findViewById(R.id.snippet);
//							title.setText(marker.getTitle());
//							snippet.setText(marker.getSnippet().replaceAll("\n","!"));
							return v;
						} else {
							View v = getLayoutInflater().inflate(R.layout.info_map, null);
							View closebt = v.findViewById(R.id.close_bt);
							closebt.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
								}
							});
							TextView title = (TextView) v.findViewById(R.id.title);
							TextView snippet = (TextView) v.findViewById(R.id.snippet);
							title.setText(marker.getTitle());
							snippet.setText(marker.getSnippet().replaceAll("\n","!"));
							return v;
						}

					}
				});
				mMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

					@Override
					public void onInfoWindowClick(Marker marker) {
						marker.hideInfoWindow();
						//						showButton(false);
					}
				});
				mMap.setOnMapClickListener(new OnMapClickListener() {

					@Override
					public void onMapClick(LatLng latLng) {
						//						showButton(false);
					}
				});
				mMap.setOnMarkerClickListener(new OnMarkerClickListener() {

					@Override
					public boolean onMarkerClick(Marker arg0) {
						//						showButton(true);
						for (Location loc : locations) {
							if (loc.getName().equals(arg0.getTitle())){
								code = loc.getmLocationCode();
								break;
							}
						}
						return false;
					}
				});
			}
			mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(52.059246, 5.427246), 6.0f));
		}
	}
	
	private AsyncHttpResponseHandler mAsyncResponseHandler = new AsyncHttpResponseHandler() {

		@Override
		public void onStart() {
			super.onStart();
		}

		@Override
		public void onFinish() {
			super.onFinish();
		}

		@Override
		public void onSuccess(String content) {
			try {
				JSONObject json = new JSONObject(content);
				if (json.get("status").equals(true)) {
					String loc = json.getString("location");
					mTidesList = new ArrayList<Tide>();
					if (loc.equalsIgnoreCase(code)) {
						JSONArray array = json.getJSONArray("data");
						if (array != null && array.length() > 0) {
							for (int i = 0; i < array.length(); i++) {
								JSONObject obj = (JSONObject) array.get(i);
								mTidesList.add(new Tide(obj));
							}
						}
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onFailure(Throwable error, String content) {
			super.onFailure(error, content);
		}
	};
}
