package nl.vogelbescherming.wadvogels;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
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
import java.util.List;

import nl.vogelbescherming.wadvogels.R;
import nl.vogelbescherming.wadvogels.adapters.ListAdapter;
import nl.vogelbescherming.wadvogels.control.Controller;
import nl.vogelbescherming.wadvogels.fonts.Fonts;
import nl.vogelbescherming.wadvogels.model.Bird;
import nl.vogelbescherming.wadvogels.model.Location;
import nl.vogelbescherming.wadvogels.util.Utils;
import nl.vogelbescherming.wadvogels.view.CustomSpinner;
import nl.vogelbescherming.wadvogels.view.CustomSpinner.OnSpinnerEventsListener;

public class SearchResultActivity extends ContentBaseActivity {

	private ListView listview;
	private ArrayList<Bird> birds = null;
	private ListAdapter la = null;
	private Boolean showAllBirds;
	private CustomSpinner spinnerChance;
	private Spinner spinnerAppears;
	private Button btnSearch;
	private EditText etSearch;
	private View backView;
	ArrayAdapter<String> adapter2;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContent(R.layout.activity_search_result);
		showAllBirds = getIntent().getExtras().getBoolean("ShowAllBirds");
		Location locationFromMap = (Location) getIntent().getExtras().get("locationFromMap");

	//	showZoekOpNaamMenuAsActive();
		hideButtons();

		backView = (View) findViewById(R.id.backView);
		etSearch = (EditText) findViewById(R.id.filter);
		etSearch.setTypeface(Fonts.getTfFont_interstate_regular());
		btnSearch = (Button) findViewById(R.id.buttonSearch);
		btnSearch.setTypeface(Fonts.getTfFont_interstate_regular());
		spinnerChance = (CustomSpinner) findViewById(R.id.spinnerTrefkans);

		CustomAdapter adapter1 = new CustomAdapter(this, R.layout.simple_spinner_item, getResources().getStringArray(R.array.chance_items));
		View v = getLayoutInflater().inflate(R.layout.simple_spinner_item, null);
		final TextView textView = (TextView) v.findViewById(R.id.titleTV);
		adapter1.setView(v);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerChance.setAdapter(adapter1);
		spinnerChance.setSelection(spinnerChance.getCount()-1);

		spinnerAppears = (Spinner) findViewById(R.id.spinnerAanwezigheid);
		adapter2 = new ArrayAdapter<String>(this, R.layout.simple_spinner_item2, getResources().getStringArray(R.array.appears_items));
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerAppears.setAdapter(adapter2);
		spinnerAppears.setSelection(spinnerAppears.getCount()-1);

		spinnerChance.setSpinnerEventsListener(new OnSpinnerEventsListener() {
			
			@Override
			public void onSpinnerOpened() {
				textView.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.arrow_up_blue), null);
			}
			
			@Override
			public void onSpinnerClosed() {
				textView.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.arrow_down_blue), null);
			}
		});

		if (locationFromMap != null) {
			hideSearchViews();
		} else {
			if (showAllBirds != null && showAllBirds) {
				birds = (ArrayList<Bird>) Controller.getBirds(this);
			} else {
				birds = (ArrayList<Bird>) Controller.getFilteredBirds(this);
			}
		}
		hideSpinners();
		//if (getIntent().getStringExtra("Caller") == null || !getIntent().getStringExtra("Caller").equals("MainActivity")) {
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
			showZoekOpNaamMenuAsActive();
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
				//listview.setVisibility(View.VISIBLE);
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
	//	etSearch.addTextChangedListener(filterTextWatcher);
	}
	
	@Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (spinnerChance.hasBeenOpened() && hasFocus) {
            spinnerChance.performClosedEvent();
        }
    }

	class CustomAdapter extends ArrayAdapter<String>
	{
		View v;

		public CustomAdapter(Context context, int resource, String[] objects) {
			super(context, resource, objects);
		}

		public void setView(View view) {
			v = view;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			return createViewFromResource(position, convertView, parent, android.R.layout.simple_spinner_dropdown_item);
		}

		private View createViewFromResource(int position, View convertView, ViewGroup parent,
				int resource) {
			View view;
			TextView text;
			view = v;
			text = (TextView) view;
			String item = getItem(position);
			if (item instanceof CharSequence) {
				text.setText((CharSequence)item);
			} else {
				text.setText(item.toString());
			}
			text.setTypeface(Fonts.getTfFont_interstate_regular());

			return view;
		}
	}

	private void hideSearchViews() {
		findViewById(R.id.filterContainer).setVisibility(View.GONE);
		spinnerAppears.setVisibility(View.GONE);
		spinnerChance.setVisibility(View.GONE);
		btnSearch.setVisibility(View.GONE);
	}

	private void hideSpinners(){
		spinnerAppears.setVisibility(View.GONE);
		spinnerChance.setVisibility(View.GONE);
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
