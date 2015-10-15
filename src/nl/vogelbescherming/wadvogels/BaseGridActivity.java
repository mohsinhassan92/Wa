package nl.vogelbescherming.wadvogels;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract.CommonDataKinds.Im;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import nl.vogelbescherming.wadvogels.R;
import nl.vogelbescherming.wadvogels.adapters.BaseGridAdapter;
import nl.vogelbescherming.wadvogels.control.Controller;
import nl.vogelbescherming.wadvogels.view.CircleImageView;

public class BaseGridActivity extends ContentBaseActivity implements
		OnClickListener, OnItemClickListener {

	private List<Drawable> listDrawables;
	private List<Integer> colorsList;
	private List<Drawable> listDrawables_active;
	private GridView gvMain;
	private BaseGridAdapter adapter;
	private int columnNumber;
	private int maxItemselected;
	private List<Integer> selectedItems = new ArrayList<Integer>();
	private boolean cellHeight;
	private boolean padding;
	private int rowNumber;
	private Handler handler;
	private List<String> text;
	private int list_item_id;
	private static List<View> viewListGrootte = new ArrayList<View>();
	// public static List<View> viewListGrootteOnResume = new ArrayList<View>();
	public static List<Integer> positionGrootte;
	public static int positionSilhuette = -1;
	public static int positionSnavel = -1;
	public static List<Integer> positionKleur = new ArrayList<Integer>();
	private static List<View> viewListKleur = new ArrayList<View>();

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContent(R.layout.grid);

		if (positionGrootte == null) {
			positionGrootte = new ArrayList<Integer>();
		}
		
		adapter = new BaseGridAdapter(this, list_item_id, R.id.image,
				listDrawables, listDrawables_active, maxItemselected,
				columnNumber, rowNumber, selectedItems, padding, cellHeight,
				text, handler, colorsList);
		gvMain = (GridView) findViewById(R.id.gridView);
		adjustGridView();
		gvMain.setAdapter(adapter);
		gvMain.setOnItemClickListener((AdapterView.OnItemClickListener) this);

		Display display = getWindowManager().getDefaultDisplay();
		int width = display.getWidth();
		int height = display.getHeight();
		/*/
		if (width >= 200 && width < 320) {
			Toast.makeText(this, "200-LDPI", Toast.LENGTH_SHORT).show();
		} else if (width >= 320 && width < 480) {
			Toast.makeText(this, "320-MDPI", Toast.LENGTH_SHORT).show();
		} else if (width >= 480 && width < 720) {
			Toast.makeText(this, "480-HDPI", Toast.LENGTH_SHORT).show();
		} else if (width >= 720 && width < 960) {
			Toast.makeText(this, "720-XHDPI", Toast.LENGTH_SHORT).show();
		} else if (width >= 960 && width < 1280) {
			Toast.makeText(this, "960-XXHDPI", Toast.LENGTH_SHORT).show();
		} else if (width >= 1280) {
			Toast.makeText(this, "1280-XXXHDPI", Toast.LENGTH_SHORT).show();
		}
		//*/
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

		// **************************************GrootteActivity*************************************************
		if (this instanceof GrootteActivity) {
			Boolean flag = false;
			View iv = v;
			View iv_temp = v;
			View iv_temp2 = v;
			if (iv.findViewById(R.id.image).getTag().toString()
					.equals("unselected")) {

				for (int i = 0; i < parent.getChildCount(); i++) {
					if (parent.getChildAt(i).findViewById(R.id.image).getTag()
							.equals("selected")) {
						flag = true;
					}
				}
				if (flag == false) {
					if (viewListGrootte != null)	viewListGrootte.clear();
					if (positionGrootte != null)	positionGrootte.clear();
				}
				if (viewListGrootte.size() < 2) {
					viewListGrootte.add(v);
					positionGrootte.add(position);
				} else if (viewListGrootte.size() >= 2
						&& viewListGrootte != null
						&& (viewListGrootte.get(0) != v && viewListGrootte
								.get(1) != v)) {
					View view = viewListGrootte.get(0);
					view.setBackgroundResource(R.drawable.cell);
					((ImageView) view.findViewById(R.id.image))
							.setImageDrawable(listDrawables.get(positionGrootte
									.get(0)));
					TextView text = (TextView) view.findViewById(R.id.text);
					text.setTextColor(this.getResources().getColor(
							R.color.inactive_button_color));
					view.findViewById(R.id.image).setTag("unselected");
					positionGrootte.remove(0);
					viewListGrootte.remove(0);
					positionGrootte.add(position);
					viewListGrootte.add(v);
				}
				
				for (int i = 0; i < parent.getChildCount(); i++) {
					unSelectGrootteAll(parent.getChildAt(i), i);
					adapter.notifyDataSetChanged();
				}
			
				for (int i = 0; i < viewListGrootte.size(); i++) {
					selectGrootte(viewListGrootte.get(i), i);
				}
			} else {
				unSelectGrootte(iv_temp2, position);
			}
		}
		// **************************************SilhuetteActivity*************************************************
		else if (this instanceof SilhuetteActivity) {
			
			
			View iv = v;
			View iv_temp = v;
			if (iv.findViewById(R.id.image).getTag().toString()
					.equals("unselected")) {
				for (int i = 0; i < parent.getChildCount(); i++) {
					iv_temp = parent.getChildAt(i);
					iv_temp.setBackgroundResource(R.drawable.cell);
					((ImageView) iv_temp.findViewById(R.id.image))
							.setImageDrawable(listDrawables.get(i));
					TextView text = (TextView) iv_temp.findViewById(R.id.text);
					text.setTextColor(this.getResources().getColor(
							R.color.inactive_button_color));
					iv_temp.findViewById(R.id.image).setTag("unselected");
				}
				iv.setBackgroundResource(R.drawable.cell_select);
				((ImageView) iv.findViewById(R.id.image))
						.setImageDrawable(listDrawables_active.get(position));
				TextView text = (TextView) iv.findViewById(R.id.text);
				text.setTextColor(Color.WHITE);
				iv.findViewById(R.id.image).setTag("selected");
				positionSilhuette = position;
				adapter.notifyDataSetChanged();
			} else if(iv.findViewById(R.id.image).getTag().toString()
					.equals("selected")){
				unSelect_Silhuette_Snavel(v, position);
				positionSilhuette = -1;
			}
		}
		// **************************************SnavelActivity*************************************************
		else if (this instanceof SnavelActivity) {
			View iv = v;
			View iv_temp = v;

			if (iv.findViewById(R.id.image).getTag().toString()
					.equals("unselected")) {
				for (int i = 0; i < parent.getChildCount(); i++) {
					iv_temp = parent.getChildAt(i);
					iv_temp.setBackgroundResource(R.drawable.cell);
					((ImageView) iv_temp.findViewById(R.id.image))
							.setImageDrawable(listDrawables.get(i));
					TextView text = (TextView) iv_temp.findViewById(R.id.text);
					text.setTextColor(this.getResources().getColor(
							R.color.inactive_button_color));
					iv_temp.findViewById(R.id.image).setTag("unselected");
				}
				iv.setBackgroundResource(R.drawable.cell_select);
				((ImageView) iv.findViewById(R.id.image))
						.setImageDrawable(listDrawables_active.get(position));
				TextView text = (TextView) iv.findViewById(R.id.text);
				text.setTextColor(Color.WHITE);
				iv.findViewById(R.id.image).setTag("selected");
				positionSnavel = position;
				adapter.notifyDataSetChanged();
			} else if(iv.findViewById(R.id.image).getTag().toString()
					.equals("selected")) {
				unSelect_Silhuette_Snavel(v, position);
				positionSnavel = -1;
			}
		}
		// *******************************************KleurActivity***********************************
		else if (this instanceof KleurActivity) {
			adapter.notifyDataSetChanged();
			Boolean flag = false;
			View iv = v;
			View iv_temp = v;
			View iv_temp2 = v;
			CircleImageView image_color;
			CircleImageView img_color;
			if (iv.findViewById(R.id.image).getTag().toString()
					.equals("unselected")) {

				for (int i = 0; i < parent.getChildCount(); i++) {
					if (((CircleImageView) (parent.getChildAt(i)
							.findViewById(R.id.image))).getTag().equals(
							"selected")) {
						flag = true;
					}
				}
				if (flag == false) {
					if (viewListKleur != null)	viewListKleur.clear();
					if (positionKleur != null)	positionKleur.clear();
				}
				if (viewListKleur.size() < 3) {
					viewListKleur.add(v);
					positionKleur.add(position);
				} else if (viewListKleur.size() >= 3
						&& viewListKleur != null
						&& (viewListKleur.get(0) != v
								&& viewListKleur.get(1) != v && viewListKleur
								.get(2) != v)) {
					View view = viewListKleur.get(0);
					view.setBackgroundResource(R.drawable.cell);
					img_color = (CircleImageView) view.findViewById(R.id.image);
					img_color.setBackgroundColor(colorsList.get(positionKleur
							.get(0)));
					/*
					 * img_color.setImageDrawable(listDrawables.get(positionKleur
					 * .get(0)));
					 */
					if (positionKleur.get(0) == 1) {
						img_color.setBorderWidth(0);
						img_color.setBorderColor(this.getResources().getColor(
								R.color.inactive_button_color));
					} else {
						img_color.setBorderWidth(0);
						img_color.setBorderColor(Color.parseColor("#ffffff"));
					}

					TextView text = (TextView) view.findViewById(R.id.text);
					text.setTextColor(this.getResources().getColor(
							R.color.inactive_button_color));
					img_color.setTag("unselected");
					positionKleur.remove(0);
					viewListKleur.remove(0);
					positionKleur.add(position);
					viewListKleur.add(v);
				}

				for (int i = 0; i < parent.getChildCount(); i++) {
					unSelectKleurAll(parent.getChildAt(i), i);
				}

				for (int i = 0; i < viewListKleur.size(); i++) {
					selectKleur(viewListKleur.get(i), i);
				}
			} else {
				unSelectKleur(iv, position);
			}

		}
		saveChecked();
		TextView textBwBtns=(TextView) findViewById(R.id.textBwBtns);
		if (Controller.getFilteredBirds(BaseGridActivity.this) != null) {
			int size = Controller.getFilteredBirds(BaseGridActivity.this).size();
			textBwBtns.setText(size + " Resultaten");
		}
	}

	private void selectGrootte(View iv_temp2, int position) {
		iv_temp2.setBackgroundResource(R.drawable.cell_select);
		((ImageView) iv_temp2.findViewById(R.id.image))
				.setImageDrawable(listDrawables_active.get(positionGrootte
						.get(position)));
		TextView text = (TextView) iv_temp2.findViewById(R.id.text);
		text.setTextColor(Color.WHITE);
		iv_temp2.findViewById(R.id.image).setTag("selected");
	}

	private void unSelectGrootteAll(View view, int position) {
		view.setBackgroundResource(R.drawable.cell);
		((ImageView) view.findViewById(R.id.image))
				.setImageDrawable(listDrawables.get(position));
		TextView text = (TextView) view.findViewById(R.id.text);
		text.setTextColor(this.getResources().getColor(
				R.color.inactive_button_color));
		view.findViewById(R.id.image).setTag("unselected");
	}

	private void unSelectGrootte(View iv, int position) {
		iv.setBackgroundResource(R.drawable.cell);
		((ImageView) iv.findViewById(R.id.image))
				.setImageDrawable(listDrawables.get(position));
		TextView text = (TextView) iv.findViewById(R.id.text);
		text.setTextColor(this.getResources().getColor(
				R.color.inactive_button_color));
		iv.findViewById(R.id.image).setTag("unselected");
		for (int counter = 0; counter < positionGrootte.size(); counter++) {
			if (positionGrootte.get(counter) == position) {
				positionGrootte.remove(counter);
				viewListGrootte.remove(counter);
			}
		}

	}

	private void unSelect_Silhuette_Snavel(View iv, int position) {
		iv.setBackgroundResource(R.drawable.cell);
		((ImageView) iv.findViewById(R.id.image))
				.setImageDrawable(listDrawables.get(position));
		TextView text = (TextView) iv.findViewById(R.id.text);
		text.setTextColor(this.getResources().getColor(
				R.color.inactive_button_color));
		iv.findViewById(R.id.image).setTag("unselected");

	}

	private void selectKleur(View iv_temp2, int position) {
		iv_temp2.setBackgroundResource(R.drawable.cell);
		CircleImageView image_color = ((CircleImageView) iv_temp2
				.findViewById(R.id.image));

		image_color.setBackgroundColor(colorsList.get(positionKleur
				.get(position)));
		// image_color.setImageDrawable(listDrawables_active.get(positionKleur.get(position)));

		Display display = getWindowManager().getDefaultDisplay();
		int width = display.getWidth();
		int height = display.getHeight();

		if (width >= 200 && width < 320) {
			Log.d("SCREEN_SIZE", "200-LDPI");
		} else if (width >= 320 && width < 480) {
			Log.d("SCREEN_SIZE", "320-MDPI");
		} else if (width >= 480 && width < 720) {
			Log.d("SCREEN_SIZE", "480-HDPI");
			image_color.setBorderWidth(3);
		} else if (width >= 720 && width < 960) {
			Log.d("SCREEN_SIZE", "720-XHDPI");
			// Nexus_Note
			image_color.setBorderWidth(2);
		} else if (width >= 960 && width < 1280) {
			Log.d("SCREEN_SIZE", "960-XXHDPI");
			// S4
			image_color.setBorderWidth(10);
		} else if (width >= 1280) {
			Log.d("SCREEN_SIZE", "1280-XXXHDPI");
		} else {
			Log.d("SCREEN_SIZE", "::UnIdentified::");
		}
		image_color.setBorderColor(this.getResources().getColor(
				R.color.active_button_color));
		TextView text = (TextView) iv_temp2.findViewById(R.id.text);
		text.setTextColor(Color.WHITE);
		image_color.setTag("selected");

	}

	private void unSelectKleur(View iv, int position) {
		iv.setBackgroundResource(R.drawable.cell);
		CircleImageView img_color = (CircleImageView) iv
				.findViewById(R.id.image);
		img_color.setBackgroundColor(colorsList.get(position));
		// img_color.setImageDrawable(listDrawables.get(position));
		if (position == 1) {
			img_color.setBorderWidth(0);
			img_color.setBorderColor(this.getResources().getColor(
					R.color.inactive_button_color));
		} else {
			img_color.setBorderWidth(0);
			img_color.setBorderColor(Color.parseColor("#ffffff"));
		}
		TextView text = (TextView) iv.findViewById(R.id.text);
		text.setTextColor(this.getResources().getColor(
				R.color.inactive_button_color));
		iv.findViewById(R.id.image).setTag("unselected");

		for (int counter = 0; counter < positionKleur.size(); counter++) {
			if (positionKleur.get(counter) == position) {
				positionKleur.remove(counter);
				viewListKleur.remove(counter);
			}
		}
	}

	private void unSelectKleurAll(View iv_temp, int position) {
		iv_temp.setBackgroundResource(R.drawable.cell);
		CircleImageView img_color = (CircleImageView) iv_temp
				.findViewById(R.id.image);
		img_color.setBackgroundColor(colorsList.get(position));
		// img_color.setImageDrawable(listDrawables.get(position));
		if (position == 1) {
			img_color.setBorderWidth(0);
			img_color.setBorderColor(this.getResources().getColor(
					R.color.inactive_button_color));
		} else {
			img_color.setBorderWidth(0);
			img_color.setBorderColor(Color.parseColor("#ffffff"));
		}
		TextView text = (TextView) iv_temp.findViewById(R.id.text);
		text.setTextColor(this.getResources().getColor(
				R.color.inactive_button_color));
		iv_temp.findViewById(R.id.image).setTag("unselected");
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		/*
		 * if (this instanceof GrootteActivity) { if
		 * (BaseGridAdapter.viewListGrootteOnResume != null) { for (int i = 0; i
		 * < BaseGridAdapter.viewListGrootteOnResume.size(); i++) {
		 * unSelectGrootteAll(BaseGridAdapter.viewListGrootteOnResume.get(i),
		 * i); adapter.notifyDataSetChanged(); } }
		 * BaseGridAdapter.viewListGrootteOnResume.clear(); if (viewListGrootte
		 * != null) { for (int i = 0; i < viewListGrootte.size(); i++) {
		 * selectGrootte(viewListGrootte.get(i), i); } } }
		 */super.onResume();
	}
	
	public static List<View> getGrooteViews() {
		return viewListGrootte;
	}
	
	public static List<View> getKleurViews() {
		return viewListKleur;
	}

	private void adjustGridView() {
		gvMain.setNumColumns(columnNumber);
	}

	public void preSetContent(List<Drawable> list, List<Drawable> list_active,
			int list_item_id, int columnNumber, int rowNubmer,
			int maxItemselected, List<Integer> selectedItems, boolean padding,
			boolean cellHeight, List<String> text, Handler handler) {
		listDrawables = list;
		listDrawables_active = list_active;
		this.columnNumber = columnNumber;
		this.rowNumber = rowNubmer;
		this.maxItemselected = maxItemselected;
		this.selectedItems = selectedItems;
		this.padding = padding;
		this.cellHeight = cellHeight;
		this.text = text;
		this.handler = handler;
		this.list_item_id = list_item_id;
	}

	public void preSetContent(List<Drawable> list, List<Drawable> list_active,
			int list_item_id, int columnNumber, int rowNubmer,
			int maxItemselected, List<Integer> selectedItems, boolean padding,
			boolean cellHeight, List<String> text, Handler handler,
			List<Integer> colorsList) {
		listDrawables = list;
		listDrawables_active = list_active;
		this.columnNumber = columnNumber;
		this.rowNumber = rowNubmer;
		this.maxItemselected = maxItemselected;
		this.selectedItems = selectedItems;
		this.padding = padding;
		this.cellHeight = cellHeight;
		this.text = text;
		this.handler = handler;
		this.list_item_id = list_item_id;
		this.colorsList = colorsList;
	}

	@Override
	public void onBackPressed() {
		Class<?> backActivity = null;
		Intent intent = null;
		if (this instanceof SilhuetteActivity) {
			backActivity = GrootteActivity.class;
			intent = new Intent(this, backActivity);
		} else if (this instanceof SnavelActivity) {
			backActivity = SilhuetteActivity.class;
			intent = new Intent(this, backActivity);
		} else if (this instanceof GrootteActivity) {
			backActivity = MainActivity.class;
			intent = new Intent(this, backActivity);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		} else if (this instanceof KleurActivity) {
			backActivity = SnavelActivity.class;
			intent = new Intent(this, backActivity);
		}
		// intent = new Intent(this, backActivity);
		startActivity(intent);
		finish();
	}

	// public void getResult() {
	// saveCheaked();
	// }

	private void startNext() {
		Intent intent = null;
		if (this instanceof SilhuetteActivity) {
			intent = new Intent(this, SnavelActivity.class);
		}
		if (this instanceof SnavelActivity) {
			intent = new Intent(this, KleurActivity.class);
		}
		if (this instanceof GrootteActivity) {
			intent = new Intent(this, SilhuetteActivity.class);
		}
		if (this instanceof KleurActivity) {
			intent = new Intent(this, SearchResultActivity.class);
			intent.putExtra("ShowAllBirds", false);
			intent.putExtra("Caller", "KleurActivity");
		}
		saveChecked();
		startActivity(intent);
	}

	private void saveChecked() {
		if (this instanceof SilhuetteActivity) {
			Controller.clearSilhuette();
			if (positionSilhuette != -1) {
				Controller.setSilhuette(positionSilhuette);
			}
		}
		if (this instanceof SnavelActivity) {
			Controller.clearBeak();
			if (positionSnavel != -1) {
				Controller.setBeak(positionSnavel);
			}
		}
		if (this instanceof GrootteActivity) {
			Controller.clearSizes();
			if (positionGrootte != null && positionGrootte.size() > 0) {
				for (int i = 0; i < GrootteActivity.MAX_NUMBER_SELECTED_ITEMS
						&& i < positionGrootte.size(); i++) {
					Controller.addSize(positionGrootte.get(i));
				}
			}
		}
		if (this instanceof KleurActivity) {
			Controller.clearColors();
			if (positionKleur != null && positionKleur.size() > 0) {
				for (int i = 0; i < KleurActivity.MAX_NUMBER_SELECTED_ITEMS
						&& i < positionKleur.size(); i++) {
					Controller.addColor(positionKleur.get(i));
				}
			}
		}
	}

	@Override
	public void onClick(View v) {
		// Log.d("HAI0000x1","HAI0000x1");
		// getResult();
	}

	protected OnClickListener onSkipClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (BaseGridActivity.this instanceof SilhuetteActivity) {
				Controller.clearSilhuette();
			} else if (BaseGridActivity.this instanceof SnavelActivity) {
				Controller.clearBeak();
			} else if (BaseGridActivity.this instanceof GrootteActivity) {
				Controller.clearSizes();
			} else if (BaseGridActivity.this instanceof KleurActivity) {
				Controller.clearColors();
			}
			startNext();
		}
	};
	protected OnClickListener onVorigeClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Class<?> backActivity = null;
			if (BaseGridActivity.this instanceof SilhuetteActivity) {
				backActivity = GrootteActivity.class;
			} else if (BaseGridActivity.this instanceof SnavelActivity) {
				backActivity = SilhuetteActivity.class;
			} else if (BaseGridActivity.this instanceof KleurActivity) {
				backActivity = SnavelActivity.class;
			}
			Intent intent = new Intent(BaseGridActivity.this, backActivity);
			startActivity(intent);
			finish();
		}
	};

}