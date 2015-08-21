package nl.vogelbescherming.wadvogels.adapters;

import android.R.color;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.ColorRes;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.webkit.WebView.FindListener;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.appdatasearch.GetRecentContextCall;

import nl.vogelbescherming.wadvogels.BaseGridActivity;
import nl.vogelbescherming.wadvogels.R;
import nl.vogelbescherming.wadvogels.control.Controller;
import nl.vogelbescherming.wadvogels.fonts.Fonts;
import nl.vogelbescherming.wadvogels.view.CircleImageView;

@SuppressLint("NewApi")
public class BaseGridAdapter extends ArrayAdapter<Drawable> {

	private Context mContext;
	private int mGridItemLayout;
	private int mImageView;
	private List<Drawable> mObjects;
	private List<Integer> mColors;
	private List<Drawable> mObjects_active;
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
			List<Drawable> objects, List<Drawable> objects_active,
			int maxItemSelected, int columnNumber, List<Integer> selectedItems,
			List<String> text, Handler handler) {
		super(context, gridItemLayout, objects);

		selectImagePositions = new ArrayList<Integer>(maxItemSelected);
		mContext = context;
		mHandler = handler;
		mGridItemLayout = gridItemLayout;
		mImageView = imageView;
		mObjects = objects;
		mObjects_active = objects_active;
		this.columnNumber = columnNumber;
		this.maxItemSelected = maxItemSelected;
		this.selectedItems = selectedItems;
		this.cellHeight = false;
		this.padding = false;
		this.text = text;
	}
	
	public BaseGridAdapter(Context context, int gridItemLayout, int imageView,
			List<Drawable> objects, List<Drawable> objects_active,
			int maxItemSelected, int columnNumber, List<Integer> selectedItems,
			List<String> text, Handler handler, List<Integer> colors) {
		super(context, gridItemLayout, objects);

		selectImagePositions = new ArrayList<Integer>(maxItemSelected);
		mContext = context;
		mHandler = handler;
		mGridItemLayout = gridItemLayout;
		mImageView = imageView;
		mObjects = objects;
		mObjects_active = objects_active;
		this.columnNumber = columnNumber;
		this.maxItemSelected = maxItemSelected;
		this.selectedItems = selectedItems;
		this.cellHeight = false;
		this.padding = false;
		this.text = text;
		this.mColors = colors;
	}

	public BaseGridAdapter(Context context, int gridItemLayout, int imageView,
			List<Drawable> objects, List<Drawable> objects_active,
			int maxItemSelected, int columnNumber, int rowNumber,
			List<Integer> selectedItems, boolean padding, boolean cellHeight,
			List<String> text, Handler handler) {

		this(context, gridItemLayout, imageView, objects, objects_active,
				maxItemSelected, columnNumber, selectedItems, text, handler);
		this.cellHeight = cellHeight;
		this.padding = padding;
		this.rowNumber = rowNumber;
	}
	
	public BaseGridAdapter(Context context, int gridItemLayout, int imageView,
			List<Drawable> objects, List<Drawable> objects_active,
			int maxItemSelected, int columnNumber, int rowNumber,
			List<Integer> selectedItems, boolean padding, boolean cellHeight,
			List<String> text, Handler handler, List<Integer> colors) {

		this(context, gridItemLayout, imageView, objects, objects_active,
				maxItemSelected, columnNumber, selectedItems, text, handler);
		this.cellHeight = cellHeight;
		this.padding = padding;
		this.rowNumber = rowNumber;
		this.mColors = colors;
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
			backing = (RelativeLayout) v.findViewById(R.id.backing);
			if (R.layout.grid_item == mGridItemLayout) {
					((CircleImageView)	v.findViewById(mImageView)).setBackgroundColor(mColors.get(position));
			} else {
				((ImageView)	v.findViewById(mImageView)).setImageDrawable(drawable);
			}
			/*
			 * LinearLayout.LayoutParams loparams = (LinearLayout.LayoutParams)
			 * iv.getLayoutParams(); loparams.setMargins(0, 0, 0, 20);
			 * v.setLayoutParams(loparams);
			 */
			TextView txt = (TextView) v.findViewById(R.id.text);
			/*
			 * LinearLayout.LayoutParams loparams = (LinearLayout.LayoutParams)
			 * txt.getLayoutParams(); loparams.setMargins(0, 0, 0, 20);
			 * txt.setLayoutParams(loparams);
			 */if (txt != null) {
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
			if (columnNumber == 3) {

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
							- getPixels(0, mMetrics);
				}

				// if (cellHeight){//chitaem dryguu visotu
				// heightInPixels = (mMetrics.heightPixels/(rowNumber *
				// 2))-getPixels(10, mMetrics);
				// } else {
				// }
				heightInPixels = widthInPixels;

				/*** COLOR SCREEN ***/

				if (maxItemSelected == 3) { /* fix for color page */
					heightInPixels = heightInPixels - getPixels(65, mMetrics);
					txt.setVisibility(View.GONE);
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
				v.findViewById(mImageView).setLayoutParams(layoutParams);

				/*** COLOR SCREEN ***/

				if (R.layout.grid_item == mGridItemLayout) {
					if (position == 1) {
						CircleImageView view_color = (CircleImageView) v
								.findViewById(mImageView);
						view_color.setBorderColor(mContext.getResources()
								.getColor(R.color.inactive_button_color));
					}

					RelativeLayout rLyt = (RelativeLayout) v
							.findViewById(R.id.backing);
					rLyt.setPadding(0, 140, 0, 140);
				}

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
				v.findViewById(mImageView).setLayoutParams(layoutParams);
				RelativeLayout rLyt = (RelativeLayout) v
						.findViewById(R.id.backing);
				rLyt.setGravity(Gravity.CENTER);
				rLyt.setPadding(0, 47, 0, 0);
				/*
				 * TextView text=(TextView) rLyt.findViewById(R.id.text);
				 * LayoutParams params = new
				 * LayoutParams(LayoutParams.WRAP_CONTENT
				 * ,LayoutParams.WRAP_CONTENT);
				 * 
				 * text.setGravity(Gravity.CENTER); ((MarginLayoutParams)
				 * params).setMargins(0, 0, 0, 20);
				 */// text.setPadding(20, 20, 20, 20);

				/*
				 * ViewGroup.MarginLayoutParams mlp =
				 * (ViewGroup.MarginLayoutParams) rLyt .getLayoutParams();
				 * mlp.setMargins(0, 0, 0, 20);
				 */
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

					if (R.layout.grid_item == mGridItemLayout) {
						CircleImageView img_color = (CircleImageView) v
								.findViewById(mImageView);
						// img_color
						// .setBackgroundResource(R.drawable.circle_color);
						/*
						 * DisplayMetrics mMetrics = mContext.getResources()
						 * .getDisplayMetrics(); int widthInPixels = 300; int
						 * heightInPixels = 105;
						 * 
						 * RelativeLayout.LayoutParams layoutParams = new
						 * RelativeLayout.LayoutParams( widthInPixels,
						 * heightInPixels); layoutParams
						 * .addRule(RelativeLayout.CENTER_IN_PARENT, 0);
						 * img_color.setLayoutParams(layoutParams);
						 */
						if (position == 1) {
							img_color.setBorderWidth(0);
							img_color.setBorderColor(mContext.getResources()
									.getColor(R.color.inactive_button_color));
						} else {
							img_color.setBorderWidth(0);
							img_color.setBorderColor(Color
									.parseColor("#ffffff"));
						}
					} else {
						ImageView img = (ImageView) v.findViewById(mImageView);
						img.setImageDrawable(mObjects.get(position));
						TextView text = (TextView) v.findViewById(R.id.text);
						text.setTextColor(mContext.getResources().getColor(
								R.color.inactive_button_color));
						backing.setBackgroundResource(R.drawable.cell);
					}
					// backing.setBackgroundResource(android.R.color.transparent);//TODO:
					selectImagePositions.remove(new Integer(position));
					v.setTag(false);

				} else {
					if (R.layout.grid_item == mGridItemLayout) {
						CircleImageView img_color = (CircleImageView) v
								.findViewById(mImageView);
						img_color.setBorderWidth(10);
						img_color.setBorderColor(mContext.getResources()
								.getColor(R.color.active_button_color));
					} else {
						backing.setBackgroundResource(R.drawable.cell_select);
						ImageView img = (ImageView) v.findViewById(mImageView);
						img.setImageDrawable(mObjects_active.get(position));
					}

					TextView text = (TextView) v.findViewById(R.id.text);
					text.setTextColor(Color.WHITE);
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

						// i m HERE Now.
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
					if (maxItemSelected == 2) {
						// Log.d("HAI7","TEST HAI7");

						RelativeLayout selecetedBacking = (RelativeLayout) selectedImage
								.findViewById(R.id.backing);
						selecetedBacking.setBackgroundResource(R.drawable.cell);
						backing.setBackgroundResource(R.drawable.cell_select);
						selectedImage.setTag(false);
						int listSize2 = selectImagePositions.size();
//						for (int j = 0; j < listSize2-1; j++) {
//							selectImagePositions.remove(selectImagePositions
//									.get(j));
//						}
						if (listSize2 >= 3)
						selectImagePositions.remove(selectImagePositions
									.get(0));
						//selectImagePositions.clear();
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

		if (selectImagePositions.size() < maxItemSelected || position == 0) {
			// if (columnNumber != 1)
			// backing.setBackgroundResource(R.drawable.long_pressed);
			// else
			if (maxItemSelected == 3) {
				CircleImageView img_color = (CircleImageView) v
						.findViewById(mImageView);
				img_color.setBorderWidth(10);
				img_color.setBorderColor(mContext.getResources().getColor(
						R.color.active_button_color));

			} else {
				backing.setBackgroundResource(R.drawable.cell_select);
			}
			ImageView img = (ImageView) v.findViewById(mImageView);

			img.setImageDrawable(mObjects_active.get(position));
			TextView text = (TextView) v.findViewById(R.id.text);
			text.setTextColor(Color.WHITE);
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