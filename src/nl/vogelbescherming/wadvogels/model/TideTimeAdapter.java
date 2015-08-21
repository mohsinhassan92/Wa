package nl.vogelbescherming.wadvogels.model;

import java.util.List;

import nl.vogelbescherming.wadvogels.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
			v.setTag(holder);
		} else {
			holder = (ItemHolder) v.getTag();
		}

		Tide tide = getItem(position);

		return v;
	}

	private static class ItemHolder {
		public TextView name_tv;
		public ImageView pic_iv;
	}
}
