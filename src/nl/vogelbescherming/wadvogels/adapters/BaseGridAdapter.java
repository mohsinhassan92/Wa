package nl.vogelbescherming.wadvogels.adapters;

import java.util.ArrayList;
import java.util.List;

import nl.vogelbescherming.wadvogels.BaseGridActivity;
import nl.vogelbescherming.wadvogels.GrootteActivity;
import nl.vogelbescherming.wadvogels.KleurActivity;
import nl.vogelbescherming.wadvogels.R;
import nl.vogelbescherming.wadvogels.SilhuetteActivity;
import nl.vogelbescherming.wadvogels.SnavelActivity;
import nl.vogelbescherming.wadvogels.control.Controller;
import nl.vogelbescherming.wadvogels.fonts.Fonts;
import nl.vogelbescherming.wadvogels.view.CircleImageView;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
	private int TABLE_ITEM_NUMBER_SILHOUTE = 9;
	private int TABLE_ITEM_NUMBER_SNAVEL = 9;
	public static List<View> viewListGrootteOnResume = new ArrayList<View>();
	private List<Drawable> listSilhouteInActive = new ArrayList<Drawable>();
	private List<Drawable> listSnavelInActive = new ArrayList<Drawable>();
	private View selectedImage;

	private List<Drawable> createListSilhouteInActive() {
		List<Drawable> temp = new ArrayList<Drawable>(
				TABLE_ITEM_NUMBER_SILHOUTE);
		temp.add(mContext.getResources().getDrawable(R.drawable.black_bird_1_inactive));
		temp.add(mContext.getResources().getDrawable(R.drawable.black_bird_2_inactive));
		temp.add(mContext.getResources().getDrawable(R.drawable.black_bird_3_inactive));
		temp.add(mContext.getResources().getDrawable(R.drawable.black_bird_4_inactive));
		temp.add(mContext.getResources().getDrawable(R.drawable.black_bird_5_inactive));
		temp.add(mContext.getResources().getDrawable(R.drawable.black_bird_6_inactive));
		temp.add(mContext.getResources().getDrawable(R.drawable.black_bird_7_inactive));
		temp.add(mContext.getResources().getDrawable(R.drawable.black_bird_8_inactive));
		temp.add(mContext.getResources().getDrawable(R.drawable.black_bird_9_inactive));
		return temp;
	}

	private List<Drawable> createListSnavelInActive() {
		List<Drawable> temp = new ArrayList<Drawable>(TABLE_ITEM_NUMBER_SNAVEL);
		temp.add(mContext.getResources().getDrawable(R.drawable.nose1_inactive));
		temp.add(mContext.getResources().getDrawable(R.drawable.nose2_inactive));
		temp.add(mContext.getResources().getDrawable(R.drawable.nose3_inactive));
		temp.add(mContext.getResources().getDrawable(R.drawable.nose4_inactive));
		temp.add(mContext.getResources().getDrawable(R.drawable.nose5_inactive));
		temp.add(mContext.getResources().getDrawable(R.drawable.nose6_inactive));
		temp.add(mContext.getResources().getDrawable(R.drawable.nose7_inactive));
		temp.add(mContext.getResources().getDrawable(R.drawable.nose8_inactive));
		temp.add(mContext.getResources().getDrawable(R.drawable.nose9_inactive));
		return temp;
	}

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
		listSilhouteInActive=createListSilhouteInActive();
		listSnavelInActive=createListSnavelInActive();
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
		listSilhouteInActive=createListSilhouteInActive();
		listSnavelInActive=createListSnavelInActive();
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
		listSilhouteInActive=createListSilhouteInActive();
		listSnavelInActive=createListSnavelInActive();
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
		listSilhouteInActive=createListSilhouteInActive();
		listSnavelInActive=createListSnavelInActive();
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
				((CircleImageView) v.findViewById(mImageView))
						.setBackgroundColor(mColors.get(position));
			} else {
				((ImageView) v.findViewById(mImageView))
						.setImageDrawable(drawable);
			}
		
			TextView txt = (TextView) v.findViewById(R.id.text);
			if (txt != null) {
				// txt.setTypeface(Fonts.getTfFont());
				txt.setText(text.get(position));
				txt.setTypeface(Fonts.getTfFont_interstate_light());
			}

			Display display = ((Activity) mContext).getWindowManager()
					.getDefaultDisplay();
			int width = display.getWidth();
			int height = display.getHeight();

			DisplayMetrics mMetrics = mContext.getResources()
					.getDisplayMetrics();
			int widthInPixels = 0;
			int heightInPixels;
			
			//Silhoute__Snavel
			if (columnNumber == 3) {

				if (padding) {
					widthInPixels = (mMetrics.widthPixels / columnNumber)
							- mContext.getResources().getDimensionPixelSize(
									R.dimen.image_padding);
				} else {
					if (width >= 200 && width < 320) {
						Log.d("SCREEN_SIZE", "200-LDPI");
					} else if (width >= 320 && width < 480) {
						Log.d("SCREEN_SIZE", "320-MDPI");
					} else if (width >= 480 && width < 720) {
						Log.d("SCREEN_SIZE", "480-HDPI");
						widthInPixels = (mMetrics.widthPixels / columnNumber)
								- getPixels(10, mMetrics);
					} else if (width >= 720 && width < 960) {
						Log.d("SCREEN_SIZE", "720-XHDPI");
						// Nexus_Note
						widthInPixels = (mMetrics.widthPixels / columnNumber)
								- getPixels(0, mMetrics);
					} else if (width >= 960 && width < 1280) {
						Log.d("SCREEN_SIZE", "960-XXHDPI");
						// S4
						widthInPixels = (mMetrics.widthPixels / columnNumber)
								- getPixels(0, mMetrics);
					} else if (width >= 1280) {
						Log.d("SCREEN_SIZE", "1280-XXXHDPI");
					} else {
						Log.d("SCREEN_SIZE", "::UnIdentified::");
					}
				}

				heightInPixels = widthInPixels;
				/*** COLOR SCREEN ***/
				if (maxItemSelected == 3) { /* fix for color page */
				
					heightInPixels = heightInPixels - getPixels(65, mMetrics);
				//	txt.setVisibility(View.GONE);
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

					if (width >= 200 && width < 320) {
						Log.d("SCREEN_SIZE", "200-LDPI");
					} else if (width >= 320 && width < 480) {
						Log.d("SCREEN_SIZE", "320-MDPI");
					} else if (width >= 480 && width < 720) {
						Log.d("SCREEN_SIZE", "480-HDPI");
						rLyt.setPadding(0, 45, 0, 45);
					} else if (width >= 720 && width < 960) {
						Log.d("SCREEN_SIZE", "720-XHDPI");
						// Nexus_Note
						rLyt.setPadding(0, 70, 0, 70);
					} else if (width >= 960 && width < 1280) {
						Log.d("SCREEN_SIZE", "960-XXHDPI");
						// S4
						rLyt.setPadding(0, 140, 0, 140);
					} else if (width >= 1280) {
						Log.d("SCREEN_SIZE", "1280-XXXHDPI");
					} else {
						Log.d("SCREEN_SIZE", "::UnIdentified::");
					}
				}
				//Grootte Views
			} else if (columnNumber == 2) { 
				/* padding */
				if (padding) {
			
					widthInPixels = (mMetrics.widthPixels / columnNumber)
							- mContext.getResources().getDimensionPixelSize(
									R.dimen.image_padding);
				} else {
					if (width >= 200 && width < 320) {
						Log.d("SCREEN_SIZE", "200-LDPI");
					} else if (width >= 320 && width < 480) {
						Log.d("SCREEN_SIZE", "320-MDPI");
					} else if (width >= 480 && width < 720) {
						Log.d("SCREEN_SIZE", "480-HDPI");
						widthInPixels = (mMetrics.widthPixels / columnNumber)
								- getPixels(65, mMetrics);
						RelativeLayout relativeTextLongGrid = (RelativeLayout) v
								.findViewById(R.id.relative_item_text);
						ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) relativeTextLongGrid
								.getLayoutParams();
						mlp.setMargins(10, 0, 0, 10);
					} else if (width >= 720 && width < 960) {
						Log.d("SCREEN_SIZE", "720-XHDPI");
						// Nexus_Note
						widthInPixels = (mMetrics.widthPixels / columnNumber)
								- getPixels(47, mMetrics);

						RelativeLayout relativeTextLongGrid = (RelativeLayout) v
								.findViewById(R.id.relative_item_text);
						ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) relativeTextLongGrid
								.getLayoutParams();
						if(position==1 || position==2){
							mlp.setMargins(80, 0, 0, 10);
						}
						else{
							mlp.setMargins(60, 0, 0, 10);
						}
					} else if (width >= 960 && width < 1280) {
						Log.d("SCREEN_SIZE", "960-XXHDPI");
						// S4
						widthInPixels = (mMetrics.widthPixels / columnNumber)
								- getPixels(25, mMetrics);
					} else if (width >= 1280) {
						Log.d("SCREEN_SIZE", "1280-XXXHDPI");
					} else {
						Log.d("SCREEN_SIZE", "::UnIdentified::");
					}
			
				}
			
				heightInPixels = widthInPixels;

				if (maxItemSelected == 3) { /* fix for color page */
					heightInPixels = heightInPixels - getPixels(25, mMetrics);
				}

				RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
						widthInPixels, heightInPixels);
				int pixs = 0;
				if (padding) {
					pixs = getPixels(4, mMetrics);
		
				} else {
					Log.i("Color", "bil");
					pixs = getPixels(30, mMetrics);
					layoutParams.setMargins(37, 0, 0, 0);
				}
				layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, 0);
				v.findViewById(mImageView).setLayoutParams(layoutParams);
				RelativeLayout rLyt = (RelativeLayout) v
						.findViewById(R.id.backing);
				rLyt.setGravity(Gravity.CENTER);
				rLyt.setPadding(0, 47, 0, 0);
			

			} else if (columnNumber == 1) {
				heightInPixels = (mMetrics.widthPixels / (mObjects.size() + 1));
				if (backing.getLayoutParams() != null)
					backing.getLayoutParams().height = heightInPixels;
				else
					backing.setLayoutParams(new AbsListView.LayoutParams(
							AbsListView.LayoutParams.MATCH_PARENT,
							heightInPixels));
			}

		}
		if (padding) {
			v.setPadding(9, 9, 9, 9);
	
		}
		v.findViewById(R.id.image).setTag("unselected");
		if (mContext instanceof GrootteActivity) {
			viewListGrootteOnResume.add(v);
			if (BaseGridActivity.positionGrootte != null
					&& BaseGridActivity.positionGrootte.size() > 0) {
				if (BaseGridActivity.positionGrootte.get(0) == position) {
					selectItem(v, position);
				} else if (BaseGridActivity.positionGrootte.size() > 1
						&& BaseGridActivity.positionGrootte.get(1) == position) {
					selectItem(v, position);
				}
			}
		} else if (mContext instanceof SilhuetteActivity) {
			//Controller.setSize(BaseGridActivity.positionGrootte);
			Controller.clearSilhuette();
			Controller.setSilhuette(position);
			int size=Controller.getFilteredBirds(mContext).size();
			if(size==0)
			{
				inActiveSilhoute(v,position);
			}
			if (BaseGridActivity.positionSilhuette != -1
					&& BaseGridActivity.positionSilhuette == position) {
				selectItem(v, position);
			}
			Controller.clearSilhuette();
		} else if (mContext instanceof SnavelActivity) {
		
		//	Controller.setSize(BaseGridActivity.positionGrootte);
		//	Controller.setSilhuette(BaseGridActivity.positionSilhuette);
		
			Controller.clearBeak();
			Controller.setBeak(position);
			int size=Controller.getFilteredBirds(mContext).size();
			if(size==0)
			{
				inActiveSnavel(v,position);
			}
			
			if (BaseGridActivity.positionSnavel != -1
					&& BaseGridActivity.positionSnavel == position) {
				selectItem(v, position);
			}
			Controller.clearBeak();
		} else if (mContext instanceof KleurActivity) {
			if (BaseGridActivity.positionKleur != null
					&& BaseGridActivity.positionKleur.size() > 0) {
				if (BaseGridActivity.positionKleur.get(0) == position) {
					selectItemKleur(v, position);
				} else if (BaseGridActivity.positionKleur.size() > 1
						&& BaseGridActivity.positionKleur.get(1) == position) {
					selectItemKleur(v, position);
				} else if (BaseGridActivity.positionKleur.size() > 2
						&& BaseGridActivity.positionKleur.get(2) == position) {
					selectItemKleur(v, position);
				}
			}
		}
		notifyDataSetChanged();
		saveChecked();
		return v;

	}

	private void saveChecked() {
		if (mContext instanceof SilhuetteActivity) {
			Controller.clearSilhuette();
			if (BaseGridActivity.positionSilhuette != -1) {
				Controller.setSilhuette(BaseGridActivity.positionSilhuette);
			}
		}
		if (mContext instanceof SnavelActivity) {
			Controller.clearBeak();
			if (BaseGridActivity.positionSnavel != -1) {
				Controller.setBeak(BaseGridActivity.positionSnavel);
			}
		}
		if (mContext instanceof GrootteActivity) {
			Controller.clearSizes();
			if (BaseGridActivity.positionGrootte != null && BaseGridActivity.positionGrootte.size() > 0) {
				for (int i = 0; i < GrootteActivity.MAX_NUMBER_SELECTED_ITEMS
						&& i < BaseGridActivity.positionGrootte.size(); i++) {
					Controller.addSize(BaseGridActivity.positionGrootte.get(i));
				}
			}
		}
		if (mContext instanceof KleurActivity) {
			Controller.clearColors();
			if (BaseGridActivity.positionKleur != null && BaseGridActivity.positionKleur.size() > 0) {
				for (int i = 0; i < KleurActivity.MAX_NUMBER_SELECTED_ITEMS
						&& i < BaseGridActivity.positionKleur.size(); i++) {
					Controller.addColor(BaseGridActivity.positionKleur.get(i));
				}
			}
		}
	}

	private void inActiveSilhoute(View iv,int position){
		
		iv.setBackgroundResource(R.drawable.cell);
		ImageView img=(ImageView) iv.findViewById(R.id.image);
		img.setImageDrawable(listSilhouteInActive.get(position));
		img.setTag("inActive");
		TextView text = (TextView) iv.findViewById(R.id.text);
		text.setTextColor(mContext.getResources().getColor(R.color.inactive_bird_color));
		iv.setClickable(false);
		

	}
	private void inActiveSnavel(View iv,int position){
		iv.setBackgroundResource(R.drawable.cell);
		ImageView img=(ImageView) iv.findViewById(R.id.image);
		img.setImageDrawable(listSnavelInActive.get(position));
		img.setTag("InActive");
		TextView text = (TextView) iv.findViewById(R.id.text);
		text.setTextColor(mContext.getResources().getColor(R.color.inactive_bird_color));
		iv.setClickable(false);

	}

	private void selectItem(View v, int position) {
		v.setBackgroundResource(R.drawable.cell_select);
		((ImageView) v.findViewById(R.id.image))
				.setImageDrawable(mObjects_active.get(position));
		TextView text = (TextView) v.findViewById(R.id.text);
		text.setTextColor(Color.WHITE);
		v.findViewById(R.id.image).setTag("selected");
	}

	private void selectItemKleur(View iv_temp2, int position) {
		iv_temp2.setBackgroundResource(R.drawable.cell);
		CircleImageView image_color = ((CircleImageView) iv_temp2
				.findViewById(R.id.image));
		// image_color.setImageDrawable(mObjects_active.get(position));
		image_color.setBackgroundColor(mColors.get(position));

		Display display = ((Activity) mContext).getWindowManager()
				.getDefaultDisplay();
		int width = display.getWidth();
		int height = display.getHeight();

		if (width >= 200 && width < 320) {
			Log.d("SCREEN_SIZE", "200-LDPI");
		} else if (width >= 320 && width < 480) {
			Log.d("SCREEN_SIZE", "320-MDPI");
		} else if (width >= 480 && width < 720) {
			Log.d("SCREEN_SIZE", "480-HDPI");
			// Nexus_S
			image_color.setBorderWidth(3);
		} else if (width >= 720 && width < 960) {
			Log.d("SCREEN_SIZE", "720-XHDPI");
			// Nexus_Note
			image_color.setBorderWidth(7);
		} else if (width >= 960 && width < 1280) {
			Log.d("SCREEN_SIZE", "960-XXHDPI");
			// S4
			image_color.setBorderWidth(10);
		} else if (width >= 1280) {
			Log.d("SCREEN_SIZE", "1280-XXXHDPI");
		} else {
			Log.d("SCREEN_SIZE", "::UnIdentified::");
		}
	
		image_color.setBorderColor(mContext.getResources().getColor(
				R.color.active_button_color));
		
		TextView text = (TextView) iv_temp2.findViewById(R.id.text);
		text.setVisibility(View.VISIBLE);
		text.setTextColor(mContext.getResources().getColor(
				R.color.inactive_button_color));
		image_color.setTag("selected");
	}

	// Not to be Used By Us Now....Shoaib_Akhtar
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
			// */
			else {
				selectImagePositions.remove(itemPos);
			}// */
		}
		return false;
	}

	public ArrayList<Integer> getCheckedItems() {
		return selectImagePositions;
	}
}