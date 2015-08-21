package nl.vogelbescherming.wadvogels.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.google.android.gms.appdatasearch.GetRecentContextCall;

import nl.vogelbescherming.wadvogels.BirdDetailActivity;
import nl.vogelbescherming.wadvogels.R;
import nl.vogelbescherming.wadvogels.fonts.Fonts;
import nl.vogelbescherming.wadvogels.model.Bird;

public class ListAdapter extends BaseAdapter implements Filterable {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Bird> mContent;
    private ArrayList<Bird> mFilteredBirds;
	private int mGridItemLayout;

	public ListAdapter(Context cntxt, int gridItemLayout, ArrayList<Bird> fillerList) {
        mContext = cntxt;
        mGridItemLayout = gridItemLayout;
        mInflater = LayoutInflater.from(cntxt);
        mContent = fillerList;
        mFilteredBirds = mContent;
    }

    @Override
    public int getCount() {
        return mFilteredBirds.size();
    }

    @Override
    public Bird getItem(int position) {
        return mFilteredBirds.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }
    static class ViewHolder {

        ImageView iv;
        TextView text;
        TextView subtext;
        View itemContainer;
    }
    @Override
    public View getView(final int position, View rootView, ViewGroup parent) {
        ViewHolder viewHolder;

        final Bird bird = getItem(position);

        if (rootView == null) {
            viewHolder = new ViewHolder();
            rootView = mInflater.inflate(mGridItemLayout, null);
            rootView.setTag(viewHolder);

            viewHolder.iv = (ImageView) rootView.findViewById(R.id.image);
            viewHolder.text = (TextView) rootView.findViewById(R.id.text);
            viewHolder.subtext = (TextView) rootView.findViewById(R.id.subtext);
            viewHolder.itemContainer = (View) rootView.findViewById(R.id.layoutItem);

        } else viewHolder = (ViewHolder) rootView.getTag();
        
        if (position % 2 == 0) {
        	viewHolder.itemContainer.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        } else {
        	viewHolder.itemContainer.setBackgroundColor(mContext.getResources().getColor(R.color.light_grey_list_item));
        }

        Log.i("ImageName", "ImageName - "+bird.getImageName());
        Uri uri = Uri.parse("android.resource://"+ mContext.getPackageName() + "/drawable/ir" + bird.getImageName());
        viewHolder.iv.setImageURI(uri);
        viewHolder.iv.invalidate();
        viewHolder.text.setText(bird.getName());
        viewHolder.text.setTypeface(Fonts.getTfFont());
        
        String[] chances = mContext.getResources().getStringArray(R.array.chance);
        String[] appears = mContext.getResources().getStringArray(R.array.appears);
        
		String text = appears[bird.getAppears().get(0) - 1] + " | "+chances[Integer.valueOf(bird.getChance()) - 1];
		
		//viewHolder.subtext.setSelected(true);
        viewHolder.subtext.setText(text);
        viewHolder.subtext.setTypeface(Fonts.getTfFont_italic());
		
        rootView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, BirdDetailActivity.class);
                i.putExtra("CurrentBird", bird);
                i.putExtra("Birds", mFilteredBirds);
                i.putExtra("Pos", position);

                mContext.startActivity(i);
            }
	    });
        
        return rootView;
    }

    @Override
	public Filter getFilter() {
		return new BirdFilter();
	}
    
    private class BirdFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constrants) {
            String s = constrants.toString().toLowerCase();

            final List<Bird> filtered = new LinkedList<Bird>();

            for(Bird bird : mContent) {
            	if (bird.getName().toLowerCase().contains(s)) {
                    filtered.add(bird);
                }
            }

            FilterResults results = new FilterResults();
            results.count = filtered.size();
            results.values = filtered;
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint,	FilterResults results) {
        	mFilteredBirds = new ArrayList<Bird>((List<Bird>) results.values);
            notifyDataSetInvalidated();
        }
    }

    public void setFiller(ArrayList<Bird> filler){
        mContent = filler;
        mFilteredBirds = mContent;
    }

    public ArrayList<Bird> getFiller(){
        return mFilteredBirds;
    }
}

	