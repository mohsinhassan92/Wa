package nl.vogelbescherming.wadvogels;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import nl.vogelbescherming.wadvogels.R;
import nl.vogelbescherming.wadvogels.adapters.TideTimesListAdapter;
import nl.vogelbescherming.wadvogels.control.Controller;
import nl.vogelbescherming.wadvogels.fonts.Fonts;
import nl.vogelbescherming.wadvogels.model.AstronomicalTideGroup;

public class TideTimesActivity extends BaseActivity {
	
	private ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContent(R.layout.activity_tide_times);
		setHeader("GETIJDENTABEL");
		showListButton();
		String code = getIntent().getStringExtra("Code");
		
		//Log.d("TEST0",code);
		
		((TextView)findViewById(R.id.datum)).setTypeface(Fonts.getTfFont_bold());
		((TextView)findViewById(R.id.hoog)).setTypeface(Fonts.getTfFont_bold());
		((TextView)findViewById(R.id.hoogte)).setTypeface(Fonts.getTfFont_bold());
		listView = (ListView) findViewById(R.id.tide_time_list);

        TextView headerText = (TextView) findViewById(R.id.headerText);
        headerText.setTypeface(Fonts.getTfFont());
		
		new AstronomicalTideGroupGetter(code).execute();
	}
	
	private class AstronomicalTideGroupGetter extends AsyncTask<Void, Void, ArrayList<AstronomicalTideGroup>>{

		private String code;
		
		public AstronomicalTideGroupGetter(String code){
			this.code = code == null ? "TEXNZE" : code;
			//Log.d("TEST1",code);
		}

		@Override
		protected ArrayList<AstronomicalTideGroup> doInBackground(Void... params) {
			//Log.d("TEST2",code);
			ArrayList<AstronomicalTideGroup> groups = Controller.getAstronomicalTide(code);
			return groups;
		}

		@Override
		protected void onPostExecute(ArrayList<AstronomicalTideGroup> groups) {
            if(groups != null){
                Collections.sort(groups, new Comparator<AstronomicalTideGroup>() {
                    @Override
                    public int compare(AstronomicalTideGroup lhs, AstronomicalTideGroup rhs) {
                        long ord_lhs = Long.valueOf(lhs.getTimestamp());
                        long ord_rhs = Long.valueOf(rhs.getTimestamp());
                        if (ord_lhs > ord_rhs) //change 1 and -1 if need to reverse effect
                            return 1;
                        else if (ord_lhs == ord_rhs)
                            return 0;
                        else
                            return -1;
                    }
                });

                Log.d("TAG",groups.toString());
                TideTimesListAdapter la = new TideTimesListAdapter(TideTimesActivity.this, 0, groups);
                listView.setAdapter(la);
            }

		}
	}
}