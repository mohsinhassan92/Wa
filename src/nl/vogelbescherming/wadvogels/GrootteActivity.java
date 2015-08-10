package nl.vogelbescherming.wadvogels;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;

import java.util.ArrayList;
import java.util.List;

import nl.vogelbescherming.wadvogels.R;
import nl.vogelbescherming.wadvogels.control.Controller;

public class GrootteActivity extends BaseGridActivity {

	public static int MAX_NUMBER_SELECTED_ITEMS = 1;
	private static List<Drawable> list;
	private static List<String> text;
	private final int TABLE_ITEM_NUMBER = 4;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			selectSeekBar(3, (Boolean) msg.obj);
		}
	};

	private List<Drawable> createList() {
		List<Drawable> temp = new ArrayList<Drawable>(TABLE_ITEM_NUMBER);
		temp.add(getResources().getDrawable(R.drawable.bird_on_bt_1));
		temp.add(getResources().getDrawable(R.drawable.bird_on_bt_2));
		temp.add(getResources().getDrawable(R.drawable.bird_on_bt_3));
		temp.add(getResources().getDrawable(R.drawable.bird_on_bt_4));
		return temp;
	}

	private List<String> createText() {
		List<String> temp = new ArrayList<String>(TABLE_ITEM_NUMBER);
		temp.add("ALS EEN MUS\n(MINDER DAN 16 CM)");
		temp.add("ALS EEN MEREL\n(17 T/M 30 CM)");
		temp.add("ALS EEN KRAAI\n(30 T/M 65 CM)");
		temp.add("ALS EEN REIGER\n(MEER DAN 65 CM)");
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

		// hideTabs();
		list = createList();
		text = createText();
		List<Integer> selectedItems = new ArrayList<Integer>();
		if (Controller.getMyBird().getSizes() != null
				&& Controller.getMyBird().getSizes().size() > 0)
			selectedItems = Controller.getMyBird().getSizes();

		preSetContent(list, R.layout.long_grid_item, 2, 0,
				MAX_NUMBER_SELECTED_ITEMS, selectedItems, false, false, text,
				handler);
		super.onCreate(savedInstanceState);
		// setHeader("VOGELVINDER");
		showButton(false);
		/**** Method Changed SetTitle()-Previously receive string as Parameter ****/

		setTitleSerial(R.string.serial_no2);
		setTitleHeader(R.string.header_grootte);

/*		setButton(R.drawable.verder_state);		
		Display display = getWindowManager().getDefaultDisplay();
		int width = display.getWidth(); // deprecated
		int height = display.getHeight(); // deprecated

		if (width > 325 && height > 485)
			createSeekBar(Controller.getMyBird(), 3);
		getButton().setOnClickListener(onSkipClickListener);*/
	}
}