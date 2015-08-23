package nl.vogelbescherming.wadvogels.adapters;

import java.util.List;

import nl.vogelbescherming.wadvogels.R;
import nl.vogelbescherming.wadvogels.fonts.Fonts;
import nl.vogelbescherming.wadvogels.model.Tide;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TideTimeAdapter extends android.widget.ArrayAdapter<Tide> {

	private List<Tide> itemsList;
	private Context mContext;

	public TideTimeAdapter(Context context, List<Tide> itemsList) {
		super(context, R.layout.tide_times_item, itemsList);
		this.itemsList = itemsList;
		this.mContext = context;
	}

	public int getCount() {
		return itemsList.size();
	}

	public Tide getItem(int position) {
		return itemsList.get(position);
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		View v = convertView;
		ItemHolder holder = new ItemHolder();

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.tide_times_item, null);
			holder.root = v.findViewById(R.id.root);
			holder.dateTime = (TextView) v.findViewById(R.id.dateTimeTV);
			holder.tide = (TextView) v.findViewById(R.id.tideTV);
			holder.height = (TextView) v.findViewById(R.id.heightTV);
			holder.dateTime.setTypeface(Fonts.getTfFont_regular());
			holder.tide.setTypeface(Fonts.getTfFont_regular());
			holder.height.setTypeface(Fonts.getTfFont_regular());
			v.setTag(holder);
		} else {
			holder = (ItemHolder) v.getTag();
		}

		if (position % 2 == 0) {
			holder.root.setBackgroundColor(mContext.getResources().getColor(R.color.bg));
		} else {
			holder.root.setBackgroundColor(mContext.getResources().getColor(R.color.white));
		}
		
		Tide tideItem = getItem(position);
		holder.dateTime.setText(tideItem.getDate() + " " + tideItem.getTime());
		holder.tide.setText(tideItem.getTide());
		holder.height.setText(tideItem.getHeight());

		return v;
	}

	private static class ItemHolder {
		public TextView dateTime, tide, height;
		public View root;
	}
}
