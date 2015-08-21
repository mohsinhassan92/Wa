package nl.vogelbescherming.wadvogels.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import nl.vogelbescherming.wadvogels.R;
import nl.vogelbescherming.wadvogels.fonts.Fonts;
import nl.vogelbescherming.wadvogels.model.AstronomicalTide;
import nl.vogelbescherming.wadvogels.model.AstronomicalTideGroup;

public class TideTimesListAdapter extends ArrayAdapter<AstronomicalTideGroup> {

	private Context mContext;
	private ArrayList<AstronomicalTideGroup> mObjects;
	private DisplayMetrics mMetrics;
	private int widthInPixels;
	
	public TideTimesListAdapter(Context context, int gridItemLayout, ArrayList<AstronomicalTideGroup> objects) {
		super(context, gridItemLayout, objects);
		
		mContext = context;
		mObjects = objects;
		mMetrics = mContext.getResources().getDisplayMetrics();
		widthInPixels = (mMetrics.widthPixels/3) - getPixels(24, mMetrics);
	}

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View v = convertView;
		View line;
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (v == null) {
			v = inflater.inflate(R.layout.tide_times_sublist_item, null);
		}
		LinearLayout container = (LinearLayout) v.findViewById(R.id.timesItemContainer);
        container.removeAllViews();
		AstronomicalTideGroup tideGroup = mObjects.get(position);
		//******************************************************************
		line = inflater.inflate(R.layout.tide_times_item, null);
		line.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, getPixels(20, mMetrics)));
		setBorders(line);
		line.setBackgroundColor(Color.WHITE);
		setLayoutParams(line, widthInPixels);
		container.addView(line);
		line = inflater.inflate(R.layout.tide_times_item, null);
		line.setBackgroundColor(Color.WHITE);
		TextView dateTime = (TextView) line.findViewById(R.id.datum);
		dateTime.setTypeface(Fonts.getTfFont());
		dateTime.setText(tideGroup.getDate());
		setBorders(line);
		setLayoutParams(line, widthInPixels);
		container.addView(line);
		
		//******************************************************************
		
		//List<TextView> textViews = createTextViewList(v);

        List<AstronomicalTide> list = tideGroup.getGroup();

        Collections.sort(list, new Comparator<AstronomicalTide>() {
            @Override
            public int compare(AstronomicalTide lhs, AstronomicalTide rhs) {
                long ord_lhs = Long.valueOf(lhs.getTimestamp());
                long ord_rhs = Long.valueOf(rhs.getTimestamp());
                if (ord_lhs < ord_rhs) //change 1 and -1 if need to reverse effect
                    return -1;
                else if (ord_lhs == ord_rhs)
                    return 0;
                else
                    return 1;
            }
        });
				
		for (int i = 0; i < list.size(); i++ ){
			AstronomicalTide tide = list.get(i);
			line = inflater.inflate(R.layout.tide_times_item, null);
			
			TextView datum = (TextView) line.findViewById(R.id.datum);
			datum.setTypeface(Fonts.getTfFont());
			TextView hoog = (TextView) line.findViewById(R.id.hoog);
			hoog.setTypeface(Fonts.getTfFont());
			TextView hoogte = (TextView) line.findViewById(R.id.hoogte);
			hoogte.setTypeface(Fonts.getTfFont());
			
			setBorders(line);
			datum.setText(tide.getHours_minuts());
			hoog.setText(tide.getTide());
			hoogte.setText(tide.getVal());
			if ((i % 2) == 0){
				line.setBackgroundColor(Color.parseColor("#e6e7e8"));
			} else {
				line.setBackgroundColor(Color.WHITE);
			}
			//line.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, getPixels(20, mMetrics)));
			setLayoutParams(line, widthInPixels);
			container.addView(line);
		}
		
		return v;
	}
	private void setBorders(View line) {
		View border1 = line.findViewById(R.id.border1);
		border1.setMinimumHeight(getPixels(20, mMetrics));
		border1.invalidate();
		View border2 = line.findViewById(R.id.border2);
		border2.setMinimumHeight(getPixels(20, mMetrics));
		border2.invalidate();
	}

	private void setLayoutParams(View line, int widthInPixels) {
		List<TextView> textViews = createTextViewList(line);
		for (TextView tv : textViews){
			tv.setWidth(widthInPixels);
			tv.invalidate();
		}
	}

	private List<TextView> createTextViewList(View v) {
		List<TextView> list = new ArrayList<TextView>();
		list.add((TextView) v.findViewById(R.id.datum));
		list.add((TextView) v.findViewById(R.id.hoog));
		list.add((TextView) v.findViewById(R.id.hoogte));
		
		return list;
	}

	private int getPixels(int dipValue, DisplayMetrics mMetrics){
        int px = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, mMetrics);
        return px;
	}

}