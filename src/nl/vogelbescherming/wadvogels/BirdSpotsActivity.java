package nl.vogelbescherming.wadvogels;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import nl.vogelbescherming.wadvogels.R;
import nl.vogelbescherming.wadvogels.control.Controller;
import nl.vogelbescherming.wadvogels.model.Location;

public class BirdSpotsActivity extends ContentBaseActivity {

    private GoogleMap mMap;
    private List<Location> locations;
    private String code;
    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
        setContent(R.layout.activity_bird_spots);
		setHeader("VOGELPLEKKEN");
		showListButton();
		setButton(R.drawable.map_button_state);
		showButton(false);
		
		setTitle("Dit zijn de mooiste vogellocaties in het waddengebied.\nKlik voor meer informatie op een locatiepunt.");
		locations = Controller.getLocations(this);
		
		getButton().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(BirdSpotsActivity.this, TideTimesActivity.class);
				//code = "TEXNZE";
				i.putExtra("Code", code);
				startActivity(i);
			}
		});
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
        if(locations != null) {
        	
        	for (Location loc : locations){
                String str = loc.getText();
                str = str.replaceAll("\\\\n", " ");
                mMap.addMarker(new MarkerOptions()
                .position(new LatLng(loc.getLat(), loc.getLng()))
                .title(loc.getName())
                .snippet(str)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.blue_pin)));
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
         
                        // Getting view from the layout file info_window_layout
                        View v = getLayoutInflater().inflate(R.layout.info_map, null);
                        View closebt = v.findViewById(R.id.close_bt);
                        closebt.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								
							}
						});
                        // Getting reference to the TextView to set latitude
                        TextView title = (TextView) v.findViewById(R.id.title);
         
                        // Getting reference to the TextView to set longitude
                        TextView snippet = (TextView) v.findViewById(R.id.snippet);
                        
                        // Setting the latitude
                        title.setText(marker.getTitle());
                        // Setting the longitude
                        snippet.setText(marker.getSnippet().replaceAll("\n","!"));
                        // Returning the view containing InfoWindow contents
                        return v;
         
                    }
                });
        		mMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
					
					@Override
					public void onInfoWindowClick(Marker marker) {
						marker.hideInfoWindow();
						showButton(false);
					}
				});
        		mMap.setOnMapClickListener(new OnMapClickListener() {
					
					@Override
					public void onMapClick(LatLng latLng) {
						showButton(false);
					}
				});
        		mMap.setOnMarkerClickListener(new OnMarkerClickListener() {
					
					@Override
					public boolean onMarkerClick(Marker arg0) {
						showButton(true);
						for (Location loc : locations){
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
}
