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

public class MapActivity1 extends ContentBaseActivity {

	private GoogleMap mMap;
	private List<Location> locations;
	private Map<LatLng, String> mAreaMap;

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

		locations = Controller.getLocations(this);
		mAreaMap = new HashMap<LatLng, String>();
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
			for (Location loc : locations) {
				String str = loc.getText();
				str = str.replaceAll("\\\\n", " ");
				mAreaMap.put(new LatLng(loc.getLat(), loc.getLng()), loc.getmLocationCode());
				mMap.addMarker(new MarkerOptions()
				.position(new LatLng(loc.getLat(), loc.getLng()))
				.title(loc.getName())
				.snippet(str)
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin)));
			}
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
				View v = getLayoutInflater().inflate(R.layout.info_map, null);
				if(BaseActivity.isTablet(MapActivity1.this))
				{
					v.setLayoutParams(new RelativeLayout.LayoutParams(300, RelativeLayout.LayoutParams.WRAP_CONTENT));					
				}
				View closebt = v.findViewById(R.id.close_bt);
				closebt.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {

					}
				});
				TextView title = (TextView) v.findViewById(R.id.title);
				TextView snippet = (TextView) v.findViewById(R.id.snippet);
				ImageView image = (ImageView) v.findViewById(R.id.imageView1);
				if (mAreaMap.get(marker.getPosition()).equalsIgnoreCase("Staatsbosbeheer")) {
					image.setImageDrawable(getResources().getDrawable(R.drawable.natuurmonumenten)); //TODO
				} else if (mAreaMap.get(marker.getPosition()).equalsIgnoreCase("Natuurmonumenten")) {
					image.setImageDrawable(getResources().getDrawable(R.drawable.natuurmonumenten));
				}
				title.setText(marker.getTitle());
				snippet.setText(marker.getSnippet().replaceAll("\n","!"));
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
		mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(locations.get(0).getLat(), locations.get(0).getLng()), 8.0f));
	}

	private OnInfoWindowClickListener onInfoWindowClickListener = new OnInfoWindowClickListener() {

		@Override
		public void onInfoWindowClick(Marker marker) {
			marker.hideInfoWindow();
			if(!BaseActivity.isTablet(MapActivity1.this))
			{
				mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 8.0f));
			}
		}
	};

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}
}
