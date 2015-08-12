package nl.vogelbescherming.wadvogels.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import nl.vogelbescherming.wadvogels.BaseGridActivity;
import nl.vogelbescherming.wadvogels.R;
import nl.vogelbescherming.wadvogels.control.Controller;
import nl.vogelbescherming.wadvogels.fonts.Fonts;

public class BaseGridAdapter extends ArrayAdapter<Drawable> {

	private Context mContext;
	private int mGridItemLayout;
	private int mImageView;
	private List<Drawable> mObjects;
	private RelativeLayout backing;
	private int maxItemSelected;
	private int columnNumber;
	private ArrayList<Integer> selectImagePositions;
	private List<Integer> selectedItems;
	private boolean cellHeight;
	private boolean padding;
	private int rowNumber;
	private Handler mHandler;
	private List<String> text;

	private View selectedImage;

	public BaseGridAdapter(Context context, int gridItemLayout, int imageView,
			List<Drawable> objects, int maxItemSelected, int columnNumber,
			List<Integer> selectedItems, List<String> text, Handler handler) {
		super(context, gridItemLayout, objects);

		selectImagePositions = new ArrayList<Integer>(maxItemSelected);
		mContext = context;
		mHandler = handler;
		mGridItemLayout = gridItemLayout;
		mImageView = imageView;
		mObjects = objects;
		this.columnNumber = columnNumber;
		this.maxItemSelected = maxItemSelected;
		this.selectedItems = selectedItems;
		this.cellHeight = false;
		this.padding = false;
		this.text = text;
	}

	public BaseGridAdapter(Context context, int gridItemLayout, int imageView,
			List<Drawable> objects, int maxItemSelected, int columnNumber,
			int rowNumber, List<Integer> selectedItems, boolean padding,
			boolean cellHeight, List<String> text, Handler handler) {

		this(context, gridItemLayout, imageView, objects, maxItemSelected,
				columnNumber, selectedItems, text, handler);
		this.cellHeight = cellHeight;
		this.padding = padding;
		this.rowNumber = rowNumber;
	}

	private int getPixels(int dipValue, DisplayMetrics mMetrics) {
		int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				dipValue, mMetrics);
		return px;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		// assign the view we are converting to a local variable
		View v = convertView;
		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(mGridItemLayout, null);
		}

		Drawable drawable = mObjects.get(position);

		if (drawable != null) {
			ImageView iv = (ImageView) v.findViewById(mImageView);
			backing = (RelativeLayout) v.findViewById(R.id.backing);
			iv.setImageDrawable(drawable);
			TextView txt = (TextView) v.findViewById(R.id.text);
			if (txt != null) {
				// txt.setTypeface(Fonts.getTfFont());
				txt.setText(text.get(position));
				/*
				 * ViewGroup.MarginLayoutParams mlp =
				 * (ViewGroup.MarginLayoutParams) txt .getLayoutParams();
				 * mlp.setMargins(0, 0, 0, -10);
				 */

			}
			DisplayMetrics mMetrics = mContext.getResources()
					.getDisplayMetrics();
			int widthInPixels;
			int heightInPixels;
			if (columnNumber == 3) { /* padding */
				if (padding) {
					// widthInPixels =
					// (mMetrics.widthPixels/columnNumber)-getPixels(20,
					// mMetrics);
					// widthInPixels =
					// (mMetrics.widthPixels/columnNumber)-getPixels(mContext.getResources().getDimensionPixelSize(id),
					// mMetrics);
					widthInPixels = (mMetrics.widthPixels / columnNumber)
							- mContext.getResources().getDimensionPixelSize(
									R.dimen.image_padding);
				} else {
					widthInPixels = (mMetrics.widthPixels / columnNumber)
							- getPixels(10, mMetrics);
				}

				// if (cellHeight){//chitaem dryguu visotu
				// heightInPixels = (mMetrics.heightPixels/(rowNumber *
				// 2))-getPixels(10, mMetrics);
				// } else {
				// }
				heightInPixels = widthInPixels;

				if (maxItemSelected == 3) { /* fix for color page */
					heightInPixels = heightInPixels - getPixels(25, mMetrics);
				}

				RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
						widthInPixels, heightInPixels);
				int pixs = 0;
				if (padding) {
					pixs = getPixels(3, mMetrics);
					layoutParams.setMargins(pixs, pixs, pixs, pixs);
				} else {
					Log.i("Color", "bil");
					pixs = getPixels(15, mMetrics);
					layoutParams.setMargins(pixs, 0, pixs, pixs);
				}

				layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, 0);
				iv.setLayoutParams(layoutParams);
			
			} else if (columnNumber == 2) { /* padding */
				if (padding) {
					// widthInPixels =
					// (mMetrics.widthPixels/columnNumber)-getPixels(20,
					// mMetrics);
					// widthInPixels =
					// (mMetrics.widthPixels/columnNumber)-getPixels(mContext.getResources().getDimensionPixelSize(id),
					// mMetrics);
					widthInPixels = (mMetrics.widthPixels / columnNumber)
							- mContext.getResources().getDimensionPixelSize(
									R.dimen.image_padding);
				} else {
					widthInPixels = (mMetrics.widthPixels / columnNumber)
							- getPixels(25, mMetrics);
				}

				// if (cellHeight){//chitaem dryguu visotu
				// heightInPixels = (mMetrics.heightPixels/(rowNumber *
				// 2))-getPixels(10, mMetrics);
				// } else {
				// }
				heightInPixels = widthInPixels;

				if (maxItemSelected == 3) { /* fix for color page */
					heightInPixels = heightInPixels - getPixels(25, mMetrics);
				}

				RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
						widthInPixels, heightInPixels);
				int pixs = 0;
				if (padding) {
					pixs = getPixels(4, mMetrics);
					// layoutParams.setMargins(pixs,pixs,pixs,pixs);
				} else {
					Log.i("Color", "bil");
					pixs = getPixels(30, mMetrics);
					// layoutParams.setMargins(pixs,0,pixs,pixs);
				}
				layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, 0);
				iv.setLayoutParams(layoutParams);
				RelativeLayout rLyt=(RelativeLayout) v.findViewById(R.id.backing);
				rLyt.setPadding(0, 37, 0, 27);
/*				ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) rLyt
						.getLayoutParams();
				mlp.setMargins(0, 0, 0, 20);*/
				
			} else if (columnNumber == 1) {
				heightInPixels = (mMetrics.widthPixels / (mObjects.size() + 1));
				if (backing.getLayoutParams() != null)
					backing.getLayoutParams().height = heightInPixels;
				else
					backing.setLayoutParams(new AbsListView.LayoutParams(
							AbsListView.LayoutParams.MATCH_PARENT,
							heightInPixels));
				// backing.getLayoutParams().height = heightInPixels;
			}

		}
		if (padding) {
			v.setPadding(9, 9, 9, 9);
			// v.setBackgroundResource(R.drawable.topbar_bg);
		}
		if (isItemSelecked(position)) {
			selectItemInGrid(v, position);
		} else {
			v.setTag(false);
		}

		v.setOnClickListener(new OnClickListener() {

			@SuppressLint("UseValueOf")
			@Override
			public void onClick(View v) {

				// Log.d("HAI3","TEST HAI3");

				RelativeLayout backing = (RelativeLayout) v
						.findViewById(R.id.backing);
				if (v == null || v.getTag() == null)
					return;
				// Log.d("HAI4","TEST HAI4 "+v.getTag());
				if (v.getTag().equals(true)) {
					// Log.d("HAI5","TEST HAI5 "+position);
					// if (columnNumber == 1)
					// backing.setBackgroundResource(R.drawable.long_bt);
					// else
					backing.setBackgroundResource(R.drawable.cell);
					// backing.setBackgroundResource(android.R.color.transparent);//TODO:
					selectImagePositions.remove(new Integer(position));
					v.setTag(false);

				} else {
					// Log.d("HAI6","TEST HAI6 "+position);
					// if (selectImagePositions.size() < maxItemSelected) {
					// if (columnNumber == 1)
					// backing.setBackgroundResource(R.drawable.long_pressed);
					// else
					backing.setBackgroundResource(R.drawable.cell_select);

					selectedImage = v;

					// Log.d("HAI6x1","TEST HAI6x1 "+v);
					// Log.d("HAI6x2","TEST HAI6x2 "+selectImagePositions);

					if (maxItemSelected == 1) {
						// Log.d("HAI7","TEST HAI7");

						RelativeLayout selecetedBacking = (RelativeLayout) selectedImage
								.findViewById(R.id.backing);
						selecetedBacking.setBackgroundResource(R.drawable.cell);
						backing.setBackgroundResource(R.drawable.cell_select);
						selectedImage.setTag(false);

						int listSize1 = selectImagePositions.size();
						for (int j = 0; j < listSize1; j++) {
							// Log.d("HAI 1 Member name: ",
							// "HAI 1 Member name: "+Integer.toString(selectImagePositions.get(j)));
							selectImagePositions.remove(selectImagePositions
									.get(j));
						}

						selectImagePositions.clear();

						// int listSize2 = selectImagePositions.size();
						// for (int j = 0; j<listSize2; j++){
						// Log.d("HAI 2 Member name: ",
						// "HAI 2 Member name: "+Integer.toString(selectImagePositions.get(j)));
						// }

					}
					selectImagePositions.add(new Integer(position));

					// int listSize3 = selectImagePositions.size();
					// for (int j = 0; j<listSize3; j++){
					// Log.d("HAI 3 Member name: ",
					// "HAI 3 Member name: "+Integer.toString(selectImagePositions.get(j)));
					// }

					v.setTag(true);
					// }
				}

				mHandler.sendMessage(Message.obtain(null, 0,
						selectImagePositions.size() > 0));

				if (selectImagePositions.size() == maxItemSelected
						&& maxItemSelected == 1) {
					((BaseGridActivity) mContext).getResult();
					// Log.d("HAI8","TEST HAI8");
				}
			}
		});
		// the view must be returned to our activity
		return v;

	}

	private void selectItemInGrid(View v, int position) {
		backing = (RelativeLayout) v.findViewById(R.id.backing);
		if (selectImagePositions.size() < maxItemSelected) {
			// if (columnNumber != 1)
			// backing.setBackgroundResource(R.drawable.long_pressed);
			// else
			backing.setBackgroundResource(R.drawable.cell_select);
			selectImagePositions.add(Integer.valueOf(position));
			v.setTag(true);
		}
	}

	private boolean isItemSelecked(int position) {
		for (Integer itemPos : selectedItems) {
			if (itemPos == position)
				return true;
		}
		return false;
	}

	public ArrayList<Integer> getCheckedItems() {
		return selectImagePositions;
	}
}