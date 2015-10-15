package nl.vogelbescherming.wadvogels;

import java.util.ArrayList;
import java.util.Collections;

import nl.vogelbescherming.wadvogels.adapters.ListAdapter;
import nl.vogelbescherming.wadvogels.control.Controller;
import nl.vogelbescherming.wadvogels.model.Bird;
import nl.vogelbescherming.wadvogels.model.Location;
import nl.vogelbescherming.wadvogels.view.QuickScroll;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AllBirdsActivity extends ContentBaseActivity {

    private ArrayList<Bird> birds = null;
    private ListAdapter la = null;
    private ListView listview;
    private Boolean showAllBirds;
    private EditText etSearch;

    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final ViewGroup root = ViewGroup.class.cast(getLayoutInflater().inflate(R.layout.activity_all_birds, null));

		showAllBirds = getIntent().getExtras().getBoolean("ShowAllBirds");
        Location locationFromMap = (Location) getIntent().getExtras().get("locationFromMap");
		
        if (locationFromMap != null){
        	root.findViewById(R.id.filterContainer).setVisibility(View.GONE);
        } else {
	        if (showAllBirds != null && showAllBirds) {
	        	birds = (ArrayList<Bird>) Controller.getBirds(this);
	        } else {
	        	birds = (ArrayList<Bird>) Controller.getFilteredBirds(this);
	        	root.findViewById(R.id.filterContainer).setVisibility(View.GONE);
	        }
	        Collections.sort(birds);
        }
        
        if (birds.size() == 0) {
        	new AlertDialog.Builder(this)
    		.setMessage("Deze zoekterm heeft geen resultaten opgeleverd. Ga terug en probeer het opnieuw.")
    		.setPositiveButton("Ok", new OnClickListener() {
    			@Override
    			public void onClick(DialogInterface dialog, int arg1) {
    				onBackPressed();
    			}
    		}).show();
        }
		
        listview = (ListView) root.findViewById(R.id.listView);
        la = new ListAdapter(this, R.layout.list_item, birds);
        listview.setAdapter(la);
        listview.setEmptyView(root.findViewById(R.id.emptyView));
		
        /*******	MH	***********/
        /*		Alphabetical Scroll List	*/ //TODO
        //*/
		final QuickScroll fastTrack = QuickScroll.class.cast(root.findViewById(R.id.quickscroll));
		fastTrack.init(QuickScroll.TYPE_POPUP, listview, la, QuickScroll.STYLE_NONE);
		fastTrack.setFixedSize(2);
		fastTrack.setPopupColor(QuickScroll.BLUE_LIGHT, QuickScroll.BLUE_LIGHT_SEMITRANSPARENT, 1, Color.WHITE, 1);
		
		root.addView(createAlphabetTrack());
		//*/
		
		setContentView(root);
        hideButtons();

        etSearch = (EditText) root.findViewById(R.id.filter);
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
		if (height >= 1024) {
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
    
    @Override
    public void onBackPressed() {
    	super.onBackPressed();
    }
}
