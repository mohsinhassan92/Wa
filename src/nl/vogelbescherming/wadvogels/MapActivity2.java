package nl.vogelbescherming.wadvogels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.vogelbescherming.wadvogels.adapters.TideTimeAdapter;
import nl.vogelbescherming.wadvogels.control.Controller;
import nl.vogelbescherming.wadvogels.fonts.Fonts;
import nl.vogelbescherming.wadvogels.model.Location;
import nl.vogelbescherming.wadvogels.model.Tide;
import nl.vogelbescherming.wadvogels.util.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
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

public class MapActivity2 extends ContentBaseActivity {

	private GoogleMap mMap;
	private Map<LatLng, String> mAreaMap;
	// private String code;
	private boolean isInfoWinShown;
	private Marker mMarker;
	private ListView listView;
	private TideTimeAdapter la;
	private Map<String, ArrayList<Tide>> mTidesMap;
	private static AsyncHttpClient client = new AsyncHttpClient();

	private LatLng [] yellowLocations = {new LatLng(53.0789159, 4.8200289), new LatLng(53.251759, 4.949379), 
			new LatLng(53.395207, 5.335025), new LatLng(53.446928, 5.773243), new LatLng(53.493794, 6.258443), 
			new LatLng(52.959004, 4.760235), new LatLng(53.400416, 6.015836), new LatLng(53.438141, 6.826703)};

	String [] locationCodes = {"TEXNZE", "VLIELHVN", "TERSLNZE", "NES", "SCHIERMNOG", "DENHDR", "WIERMGDN", "EEMHVN"};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContent(R.layout.activity_bird_spots);
		setButton(R.drawable.map_button_state);
		showButton(false);
		showAllBirdsButton(false);
		hideButtons();
		showGetijdenMenuAsActive();
		hideTitleContainer();

		mAreaMap = new HashMap<LatLng, String>();
		mTidesMap = new HashMap<String, ArrayList<Tide>>();

		if (Utils.isOnline(this)) {
			for (String code : locationCodes) {
				String url = getResources().getString(R.string.url_get_regions_info) + code;
				Log.i("MapActivity1", "Location before\n" + code);
				client.get(url, mAsyncResponseHandler);
			}
		}
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

		for (int i = 0; i < yellowLocations.length; i++) {
			mAreaMap.put(new LatLng(yellowLocations[i].latitude, yellowLocations[i].longitude), locationCodes[i]);
			mMap.addMarker(new MarkerOptions()
			.position(new LatLng(yellowLocations[i].latitude, yellowLocations[i].longitude))
			.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_yellow)));
		}

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
				mMarker = marker;
				String code = mAreaMap.get(marker.getPosition());
				isInfoWinShown = true;
				View v = getLayoutInflater().inflate(R.layout.activity_tide_times, null);
				((TextView) v.findViewById(R.id.datum)).setTypeface(Fonts.getTfFont_regular());
				((TextView) v.findViewById(R.id.hoog)).setTypeface(Fonts.getTfFont_regular());
				((TextView)	v.findViewById(R.id.hoogte)).setTypeface(Fonts.getTfFont_regular());
				ArrayList<Tide> list = mTidesMap.get(code);
				if (list != null) {
					listView = (ListView) v.findViewById(R.id.tide_time_list);
					la = new TideTimeAdapter(MapActivity2.this, list);
					listView.setAdapter(la);
				}
				if(BaseActivity.isTablet(MapActivity2.this))
				{
					v.setLayoutParams(new RelativeLayout.LayoutParams(300, RelativeLayout.LayoutParams.WRAP_CONTENT));						
				}
				return v;
			}
		});

		mMap.setOnInfoWindowClickListener(onInfoWindowClickListener);

		mMap.setOnMapClickListener(new OnMapClickListener() {

			@Override
			public void onMapClick(LatLng latLng) {
			}
		});
		mMap.setOnMarkerClickListener(new OnMarkerClickListener() {

			@Override
			public boolean onMarkerClick(Marker marker) {
				return false;
			}
		});
		mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(yellowLocations[0].latitude, yellowLocations[0].longitude), 8.0f));
	}

	private OnInfoWindowClickListener onInfoWindowClickListener = new OnInfoWindowClickListener() {

		@Override
		public void onInfoWindowClick(Marker marker) {
			marker.hideInfoWindow();
			if(!BaseActivity.isTablet(MapActivity2.this))
			{
				mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 8.0f));
			}
			isInfoWinShown = false;
			//showButton(false);
		}
	};

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
				ArrayList<Tide> list = new ArrayList<Tide>();
				if (json.get("status").equals(true)) {
					String loc = json.getString("location");
					JSONArray array = json.getJSONArray("data");
					Log.i("MapActivity1", "Location\n" + loc + "\nArray" + array);
					if (array != null && array.length() > 0) {
						for (int i = 0; i < array.length(); i++) {
							JSONObject obj = (JSONObject) array.get(i);
							list.add(new Tide(obj));
						}
					}
					mTidesMap.put(loc, list);
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

	@Override
	public void onBackPressed() {
		if (isInfoWinShown && mMarker != null) {
			onInfoWindowClickListener.onInfoWindowClick(mMarker);
		} else {
			super.onBackPressed();
		}
	}
}
