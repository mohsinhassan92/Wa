/*package nl.vogelbescherming.wadvogels;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;

import java.util.ArrayList;
import java.util.List;

import nl.vogelbescherming.wadvogels.R;
import nl.vogelbescherming.wadvogels.control.Controller;

public class GrootteActivity_old extends BaseGridActivity{
	
	public static int MAX_NUMBER_SELECTED_ITEMS = 1;
	private static List<Drawable> list;
	private static List<String> text;
	private final int TABLE_ITEM_NUMBER = 4;
	private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
        	selectSeekBar(3,(Boolean) msg.obj);
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
		temp.add("Als een mus (6 t/m 16 cm)");
		temp.add("Als een merel (17 t/m 30 cm)");
		temp.add("Als een kraai (30 t/m 65 cm)");
		temp.add("Als een reiger (Meer dan 65 cm)");

		return temp;
	}
	public static Drawable getDrawableFromPosition(int position){
		return list.get(position);
	}
	public static List<Drawable> getItemList() {
		return list;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		list = createList();
		text = createText();
		List<Integer> selectedItems = new ArrayList<Integer>();
		if (Controller.getMyBird().getSizes() != null && Controller.getMyBird().getSizes().size() > 0)
			selectedItems = Controller.getMyBird().getSizes();
		
		preSetContent(list, R.layout.long_grid_item,1,0,MAX_NUMBER_SELECTED_ITEMS, selectedItems,false,false, text,handler);
        super.onCreate(savedInstanceState);
//        setHeader("VOGELVINDER");
        setSubHeaderTitle("Hoe groot is de vogel ongeveer?");
        setButton(R.drawable.verder_state);
        Display display = getWindowManager().getDefaultDisplay();
        int width = display.getWidth();  // deprecated
        int height = display.getHeight();  // deprecated
        
        if (width > 325 && height > 485)
        	createSeekBar(Controller.getMyBird(), 3);
        getButton().setOnClickListener(onSkipClickListener);
	}
}*/