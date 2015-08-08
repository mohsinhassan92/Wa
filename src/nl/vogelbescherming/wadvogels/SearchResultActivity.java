package nl.vogelbescherming.wadvogels;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import nl.vogelbescherming.wadvogels.R;
import nl.vogelbescherming.wadvogels.adapters.ListAdapter;
import nl.vogelbescherming.wadvogels.control.Controller;
import nl.vogelbescherming.wadvogels.model.Bird;
import nl.vogelbescherming.wadvogels.model.Location;

public class SearchResultActivity extends ContentBaseActivity {

    private ListAdapter la = null;

    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContent(R.layout.activity_search_result);
        Boolean showAllBirds = getIntent().getExtras().getBoolean("ShowAllBirds");
        Location locationFromMap = (Location) getIntent().getExtras().get("locationFromMap");
        ArrayList<Bird> birds = null;
        
        if (locationFromMap != null){
//        	setHeader("VOGELGIDS");
        	//birds = (ArrayList<Bird>) locationFromMap.getBirds(Controller.getBirds(this));
        	findViewById(R.id.filterContainer).setVisibility(View.GONE);
        } else {
	        if (showAllBirds != null && showAllBirds){
//	        	setHeader("ZOEK OP NAAM");
	        	birds = (ArrayList<Bird>) Controller.getBirds(this);
	        } else {
//	        	setHeader("VOGELGIDS");
	        	birds = (ArrayList<Bird>) Controller.getFilteredBirds(this);
	        	findViewById(R.id.filterContainer).setVisibility(View.GONE);
//	        	setSubHeader("Gevonden resultaten");
	        }
        }
        if(birds.size() == 0){
        	new AlertDialog.Builder(this)
    		.setMessage("Deze zoekterm heeft geen resultaten opgeleverd. Ga terug en probeer het opnieuw.")
    		.setPositiveButton("Ok", new OnClickListener() {
    			@Override
    			public void onClick(DialogInterface dialog, int arg1) {
    				onBackPressed();
    			}
    		}).show();
        }
        ListView listview = (ListView) findViewById(R.id.listView);
        
        la = new ListAdapter(this, R.layout.list_item, birds);
        listview.setAdapter(la);
        listview.setEmptyView(findViewById(R.id.emptyView));
        /*listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<Bird> content = la.getFiller();
                Intent i = new Intent(SearchResultActivity.this, BirdDetailActivity.class);
                i.putExtra("CurrentBird", content.get(position));
                i.putExtra("Birds", content);
                i.putExtra("Pos", position);

                startActivity(i);

            }
        });*/
        ((TextView) findViewById(R.id.filter)).addTextChangedListener(filterTextWatcher);
	}
	
	private TextWatcher filterTextWatcher = new TextWatcher() {
		@Override
        public void afterTextChanged(Editable s) {}

        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

        public void onTextChanged(CharSequence s, int start, int before,int count) {
            if(la != null)la.getFilter().filter(s);
        }
    };
}
