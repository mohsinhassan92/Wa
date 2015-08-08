package nl.vogelbescherming.wadvogels;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;

import java.util.ArrayList;
import java.util.List;

import nl.vogelbescherming.wadvogels.R;
import nl.vogelbescherming.wadvogels.control.Controller;

public class KleurActivity extends BaseGridActivity{
	
	public static int MAX_NUMBER_SELECTED_ITEMS = 3;
	private static List<Integer> list;
	private List<Drawable> drawablelist;
	private final int TABLE_ITEM_NUMBER = 12;
    private static List<String> text;
	private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
        	selectSeekBar(4,(Boolean) msg.obj);
        }
	};
	private List<Integer> createList() {
		List<Integer> temp = new ArrayList<Integer>(TABLE_ITEM_NUMBER);
		temp.add(Color.parseColor("#000000"));
		temp.add(Color.parseColor("#ffffff"));
		temp.add(Color.parseColor("#b1b3b4"));
		temp.add(Color.parseColor("#fbe742"));
		temp.add(Color.parseColor("#fc9c63"));
		temp.add(Color.parseColor("#cd061d"));
		temp.add(Color.parseColor("#7894ab"));
		temp.add(Color.parseColor("#75832d"));
		temp.add(Color.parseColor("#90611b"));
		return temp;
	}
    private List<String> createText() {
        List<String> temp = new ArrayList<String>(TABLE_ITEM_NUMBER);
        temp.add("Zwart");
        temp.add("Wit");
        temp.add("Grijs");
        temp.add("Geel");
        temp.add("Oranje");
        temp.add("Rood");
        temp.add("Blauw");
        temp.add("Groen");
        temp.add("Bruin");
        return temp;
    }
	private List<Drawable> createDrawablesFromColors(List<Integer> list2) {
		List<Drawable> temp = new ArrayList<Drawable>();
		for (Integer color : list2){
			temp.add(new ColorDrawable(color));
		}
		return temp;
	}
	public static Integer getDrawableFromPosition(int position){
		return list.get(position);
	}
	public static List<Integer> getItemList() {
		return list;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		list = createList();
        text = createText();
		drawablelist = createDrawablesFromColors(list);
		List<Integer> selectedItems = new ArrayList<Integer>();
		if (Controller.getMyBird().getColors()!= null && Controller.getMyBird().getColors().size() > 0)
			selectedItems = Controller.getMyBird().getColors();
		preSetContent(drawablelist,R.layout.grid_item, 3,3/*rownumber*/,MAX_NUMBER_SELECTED_ITEMS, selectedItems, true/*padding*/, true/*cell height*/, text,handler);
        super.onCreate(savedInstanceState);
        setTitle("Welke kleuren vallen het meest op?");
        setButton(R.drawable.resultaten_state);
        
        Display display = getWindowManager().getDefaultDisplay();
        int width = display.getWidth();  // deprecated
        int height = display.getHeight();  // deprecated
        /*/
        if (width > 325 && height > 485)
        	createSeekBar(Controller.getMyBird(), 4);
        //*/
        getButton().setOnClickListener(this);
	}
}
