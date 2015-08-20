package nl.vogelbescherming.wadvogels;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import nl.vogelbescherming.wadvogels.R;
import nl.vogelbescherming.wadvogels.adapters.ListAdapter;
import nl.vogelbescherming.wadvogels.control.Controller;
import nl.vogelbescherming.wadvogels.model.Bird;
import nl.vogelbescherming.wadvogels.model.Location;
import nl.vogelbescherming.wadvogels.util.Utils;

public class AllBirdsActivity extends ContentBaseActivity {

    private ArrayList<Bird> birds = null;
    private ListAdapter la = null;
    private Boolean showAllBirds;
    private Spinner spinnerChance;
    private Spinner spinnerAppears;
    private Button btnSearch;
    private EditText etSearch;

    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContent(R.layout.activity_all_birds);
		final ViewGroup root = ViewGroup.class.cast(getLayoutInflater().inflate(R.layout.activity_all_birds, null));
		root.addView(createAlphabetTrack());
		setContentView(root);
		
		showAllBirds = getIntent().getExtras().getBoolean("ShowAllBirds");
        Location locationFromMap = (Location) getIntent().getExtras().get("locationFromMap");
        
        hideButtons();
        
        etSearch = (EditText) root.findViewById(R.id.filter);
        btnSearch = (Button) root.findViewById(R.id.buttonSearch);
        spinnerChance = (Spinner) root.findViewById(R.id.spinnerTrefkans);
        
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.simple_spinner_item, getResources().getStringArray(R.array.chance));
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerChance.setAdapter(adapter1);
        
        spinnerAppears = (Spinner) root.findViewById(R.id.spinnerAanwezigheid);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.simple_spinner_item, getResources().getStringArray(R.array.appears));
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAppears.setAdapter(adapter2);
        
        if (locationFromMap != null){
//        	setHeader("VOGELGIDS");
        	//birds = (ArrayList<Bird>) locationFromMap.getBirds(Controller.getBirds(this));
        	root.findViewById(R.id.filterContainer).setVisibility(View.GONE);
        } else {
	        if (showAllBirds != null && showAllBirds) {
//	        	setHeader("ZOEK OP NAAM");
	        	birds = (ArrayList<Bird>) Controller.getBirds(this);
	        } else {
//	        	setHeader("VOGELGIDS");
	        	birds = (ArrayList<Bird>) Controller.getFilteredBirds(this);
	        	root.findViewById(R.id.filterContainer).setVisibility(View.GONE);
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
        
        ListView listview = (ListView) root.findViewById(R.id.listView);
        
        la = new ListAdapter(this, R.layout.list_item, birds);
        listview.setAdapter(la);
        listview.setEmptyView(root.findViewById(R.id.emptyView));
        
        btnSearch.setOnClickListener(new View.OnClickListener() {

        	@Override
        	public void onClick(View v) {
        		int chance = spinnerChance.getSelectedItemPosition();
        		int appears = spinnerAppears.getSelectedItemPosition();
        		String searchText = etSearch.getText().toString().trim();
        		birds = Controller.getSelectedBirds(AllBirdsActivity.this, ++chance, ++appears, searchText);
        		la.setFiller(birds);
        		la.notifyDataSetChanged();
        		Utils.hideKeyBoard(AllBirdsActivity.this, etSearch);
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
    
    private ViewGroup createAlphabetTrack() {
		final LinearLayout layout = new LinearLayout(this);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int) (30 * getResources().getDisplayMetrics().density), LayoutParams.MATCH_PARENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		params.addRule(RelativeLayout.BELOW, R.id.filterContainer);
		layout.setLayoutParams(params);
		layout.setOrientation(LinearLayout.VERTICAL);

		final LinearLayout.LayoutParams textparams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		textparams.weight = 1;
		final int height = getResources().getDisplayMetrics().heightPixels;
		int iterate = 0;
		if (height >= 1024){
			iterate = 1; layout.setWeightSum(26);
		} else {
			iterate = 2; layout.setWeightSum(13);
		}
		for (char character = 'A'; character <= 'Z'; character+=iterate) {
			final TextView textview = new TextView(this);
			textview.setLayoutParams(textparams);
			textview.setTextColor(getResources().getColor(R.color.blue_bg));
			textview.setGravity(Gravity.CENTER_HORIZONTAL);
			textview.setText(Character.toString(character));
			textview.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					String text = textview.getText().toString().trim();
//					filterTextWatcher.onTextChanged(text, 0, 0, 1);
				}
			});
			layout.addView(textview);
		}

		return layout;
	}
	
	private TextWatcher filterTextWatcher = new TextWatcher() {
		@Override
        public void afterTextChanged(Editable s) {}

        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

        public void onTextChanged(CharSequence s, int start, int before,int count) {
            if(la != null)	la.getFilter().filter(s);
        }
    };
}
