package nl.vogelbescherming.wadvogels;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import nl.vogelbescherming.wadvogels.R;
import nl.vogelbescherming.wadvogels.control.Controller;

public class SnavelActivity extends BaseGridActivity {

	public static int MAX_NUMBER_SELECTED_ITEMS = 1;
	private static List<Drawable> list;
	private static List<Drawable> list_active;
	private static List<String> text;
	private final int TABLE_ITEM_NUMBER = 9;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			selectSeekBar(2, (Boolean) msg.obj);
		}
	};

	private List<Drawable> createList() {
		List<Drawable> temp = new ArrayList<Drawable>(TABLE_ITEM_NUMBER);
		temp.add(getResources().getDrawable(R.drawable.nose1));
		temp.add(getResources().getDrawable(R.drawable.nose2));
		temp.add(getResources().getDrawable(R.drawable.nose3));
		temp.add(getResources().getDrawable(R.drawable.nose4));
		temp.add(getResources().getDrawable(R.drawable.nose5));
		temp.add(getResources().getDrawable(R.drawable.nose6));
		temp.add(getResources().getDrawable(R.drawable.nose7));
		temp.add(getResources().getDrawable(R.drawable.nose8));
		temp.add(getResources().getDrawable(R.drawable.nose9));
		return temp;
	}

	private List<Drawable> createList_active() {
		List<Drawable> temp = new ArrayList<Drawable>(TABLE_ITEM_NUMBER);
		temp.add(getResources().getDrawable(R.drawable.nose1_active));
		temp.add(getResources().getDrawable(R.drawable.nose2_active));
		temp.add(getResources().getDrawable(R.drawable.nose3_active));
		temp.add(getResources().getDrawable(R.drawable.nose4_active));
		temp.add(getResources().getDrawable(R.drawable.nose5_active));
		temp.add(getResources().getDrawable(R.drawable.nose6_active));
		temp.add(getResources().getDrawable(R.drawable.nose7_active));
		temp.add(getResources().getDrawable(R.drawable.nose8_active));
		temp.add(getResources().getDrawable(R.drawable.nose9_active));
		return temp;
	}


	
	private List<String> createText() {
		List<String> temp = new ArrayList<String>(TABLE_ITEM_NUMBER);
		temp.add("LANG EN DUN");
		temp.add("HALFLANG EN DUN");
		temp.add("KORT EN DUN");
		temp.add("EENG OF GANS");
		temp.add("KEGELVORMIG");
		temp.add("DIK EN SCHERP");
		temp.add("HAAKVORMIG");
		temp.add("GEBOGEN");
		temp.add("DOLKVORMIG");
		return temp;
	}

	public static Drawable getDrawableFromPosition(int position) {
		return list.get(position);
	}

	public static List<Drawable> getItemList() {
		return list;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		list = createList();
		list_active = createList_active();
		text = createText();
		List<Integer> selectedItems = new ArrayList<Integer>();
		if (Controller.getMyBird().getBeak() != null
				&& Controller.getMyBird().getBeak().size() > 0)
			selectedItems.add(Controller.getMyBird().getBeak().get(0));
		preSetContent(list, list_active,R.layout.textdown_grid_item, 3, 0,
				MAX_NUMBER_SELECTED_ITEMS, selectedItems, false, false, text,
				handler);
		super.onCreate(savedInstanceState);
		showVogelVinderMenuAsActive();
		setSubHeaderTitle(getResources().getString(R.string.sub_header_snavel));
		/*
		 * setButton(R.drawable.verder_state); Display display =
		 * getWindowManager().getDefaultDisplay(); int width =
		 * display.getWidth(); // deprecated int height = display.getHeight();
		 * // deprecated
		 * 
		 * if (width > 325 && height > 485)
		 * createSeekBar(Controller.getMyBird(), 2);
		 * getButton().setOnClickListener(onSkipClickListener);
		 */
	
		View btn_vorige= findViewById(R.id.button_vorige);
		View btn_verder= findViewById(R.id.button_verder);
		btn_vorige.setBackgroundColor(getResources().getColor(R.color.active_button_color));		
		btn_vorige.setOnClickListener(onVorigeClickListener);
		btn_verder.setOnClickListener(onSkipClickListener);	
	}

}
