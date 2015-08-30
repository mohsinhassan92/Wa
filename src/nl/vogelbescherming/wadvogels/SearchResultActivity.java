package nl.vogelbescherming.wadvogels;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.ViewGroup;
import java.util.ArrayList;

import nl.vogelbescherming.wadvogels.R;
import nl.vogelbescherming.wadvogels.adapters.ListAdapter;
import nl.vogelbescherming.wadvogels.control.Controller;
import nl.vogelbescherming.wadvogels.model.Bird;
import nl.vogelbescherming.wadvogels.model.Location;
import nl.vogelbescherming.wadvogels.util.Utils;

public class SearchResultActivity extends ContentBaseActivity {

	private ListView listview;
    private ArrayList<Bird> birds = null;
    private ListAdapter la = null;
    private Boolean showAllBirds;
    private Spinner spinnerChance;
    private Spinner spinnerAppears;
    private Button btnSearch;
    private EditText etSearch;
    private View backView;

    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContent(R.layout.activity_search_result);
		showAllBirds = getIntent().getExtras().getBoolean("ShowAllBirds");
        Location locationFromMap = (Location) getIntent().getExtras().get("locationFromMap");
        
        showZoekOpNaamMenuAsActive();
        hideButtons();
        
        backView = (View) findViewById(R.id.backView);
        etSearch = (EditText) findViewById(R.id.filter);
        btnSearch = (Button) findViewById(R.id.buttonSearch);
        spinnerChance = (Spinner) findViewById(R.id.spinnerTrefkans);
        
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.simple_spinner_item, getResources().getStringArray(R.array.chance_items));
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerChance.setAdapter(adapter1);
        spinnerChance.setSelection(spinnerChance.getCount()-1);
        
        spinnerAppears = (Spinner) findViewById(R.id.spinnerAanwezigheid);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.simple_spinner_item2, getResources().getStringArray(R.array.appears_items));
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAppears.setAdapter(adapter2);
//        spinnerAppears.setSelection(spinnerAppears.getCount()-1);
        
        if (locationFromMap != null) {
//        	setHeader("VOGELGIDS");
        	//birds = (ArrayList<Bird>) locationFromMap.getBirds(Controller.getBirds(this));
        	hideSearchViews();
        } else {
	        if (showAllBirds != null && showAllBirds) {
//	        	setHeader("ZOEK OP NAAM");
	        	birds = (ArrayList<Bird>) Controller.getBirds(this);
	        } else {
//	        	setHeader("VOGELGIDS");
	        	birds = (ArrayList<Bird>) Controller.getFilteredBirds(this);
//	        	hideSearchViews();
//	        	setSubHeader("Gevonden resultaten");
	        }
        }
        
//        if (getIntent().getStringExtra("Caller") == null || !getIntent().getStringExtra("Caller").equals("MainActivity")) {
        	if(birds.size() == 0) {
            	new AlertDialog.Builder(this)
        		.setMessage("Deze zoekterm heeft geen resultaten opgeleverd. Ga terug en probeer het opnieuw.")
        		.setPositiveButton("Ok", new OnClickListener() {
        			@Override
        			public void onClick(DialogInterface dialog, int arg1) {
        				onBackPressed();
        			}
        		}).show();
            }
//        }
        
        listview = (ListView) findViewById(R.id.listView);
		
        la = new ListAdapter(this, R.layout.list_item, birds);
        listview.setAdapter(la);
        listview.setEmptyView(findViewById(R.id.emptyView));
        
        if (getIntent().getStringExtra("Caller") != null && getIntent().getStringExtra("Caller").contains("KleurActivity")) {
        	hideSearchViews();
        	showBackButton(true);
        	showVogelVinderMenuAsActive();
            listview.setVisibility(View.VISIBLE);
        } else {
            listview.setVisibility(View.GONE);
        }
        
        btnSearch.setOnClickListener(new View.OnClickListener() {

        	@Override
        	public void onClick(View v) {
        		int chance = spinnerChance.getSelectedItemPosition();
        		int appears = spinnerAppears.getSelectedItemPosition();
        		String searchText = etSearch.getText().toString().trim();
        		birds = Controller.getSelectedBirds(SearchResultActivity.this, ++chance, ++appears, searchText);
        		la.setFiller(birds);
        		la.notifyDataSetChanged();
//        		listview.setVisibility(View.VISIBLE);
        		Utils.hideKeyBoard(SearchResultActivity.this, etSearch);
        	}
        });
        
        backView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
        
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
        etSearch.addTextChangedListener(filterTextWatcher);
	}
    
    private void hideSearchViews() {
    	findViewById(R.id.filterContainer).setVisibility(View.GONE);
    	spinnerAppears.setVisibility(View.GONE);
    	spinnerChance.setVisibility(View.GONE);
    	btnSearch.setVisibility(View.GONE);
    }
    
    private void showBackButton(boolean isShow) {
    	if (isShow)	backView.setVisibility(View.VISIBLE);
    	else	backView.setVisibility(View.GONE);
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
