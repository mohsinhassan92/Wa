/*package nl.vogelbescherming.wadvogels;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import nl.vogelbescherming.wadvogels.R;
import nl.vogelbescherming.wadvogels.adapters.BaseGridAdapter;
import nl.vogelbescherming.wadvogels.control.Controller;

public class BaseGridActivity_old extends ContentBaseActivity implements OnClickListener {

    private List<Drawable> listDrawables;
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

    *//**
     * Called when the activity is first created.
     *//*
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContent(R.layout.grid);

        adapter = new BaseGridAdapter(this, list_item_id, R.id.image, listDrawables, maxItemselected, columnNumber, rowNumber, selectedItems, padding, cellHeight, text, handler);
        gvMain = (GridView) findViewById(R.id.gridView);
        adjustGridView();
        gvMain.setAdapter(adapter);
        //gvMain.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Log.d("HAI0000x2","HAI0000x2");
        saveCheaked();
    }

    private void adjustGridView() {
        gvMain.setNumColumns(columnNumber);
        //gvMain.setVerticalSpacing(10);
        //gvMain.setHorizontalSpacing(10);
    }

    public void preSetContent(List<Drawable> list, int list_item_id, int columnNumber, int rowNubmer,
                              int maxItemselected, List<Integer> selectedItems, boolean padding,
                              boolean cellHeight, List<String> text, Handler handler) {
        listDrawables = list;
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
    @Override
    public void onBackPressed() {
    	//Log.d("HAI BACK","HAI BACK");
        Class<?> backActivity = null;
        if (this instanceof SilhuetteActivity) {
            backActivity = MainActivity.class;
        } else if (this instanceof SnavelActivity) {
            backActivity = SilhuetteActivity.class;
        } else if (this instanceof GrootteActivity) {
            backActivity = SnavelActivity.class;
        } else if (this instanceof KleurActivity) {
            backActivity = GrootteActivity.class;
        }
        Intent intent = new Intent(this, backActivity);
        startActivity(intent);
        finish();
    }
    public void getResult() {
        saveCheaked();
        startNext();
    }

    private void startNext() {
        Intent intent = null;
        if (this instanceof SilhuetteActivity) {
            intent = new Intent(this, SnavelActivity.class);
        }
        if (this instanceof SnavelActivity) {
            intent = new Intent(this, GrootteActivity.class);
        }
        if (this instanceof GrootteActivity) {
            intent = new Intent(this, KleurActivity.class);
        }
        if (this instanceof KleurActivity) {
            intent = new Intent(this, SearchResultActivity.class);
            intent.putExtra("ShowAllBirds", false);
        }
        startActivity(intent);
    }

    private void saveCheaked() {
        boolean flag = true;

        List<Integer> list = adapter.getCheckedItems();
        if (list == null || list.size() == 0)
            flag = false;

        if (this instanceof SilhuetteActivity) {
            //Log.d("HAI0000-1","HAI00000-1");
            for (int i = 0; i < SilhuetteActivity.MAX_NUMBER_SELECTED_ITEMS && flag; i++){
            	Controller.setSilhuette(list.get(i));
             //Log.d("HAI0000-2","HAI0000-2 "+Integer.toString(list.get(i)));
             }
            
        }
        if (this instanceof SnavelActivity) {
            for (int i = 0; i < SnavelActivity.MAX_NUMBER_SELECTED_ITEMS && flag; i++){
                Controller.setBeak(list.get(i));
            }
        }
        if (this instanceof GrootteActivity) {
            Controller.setSize(list);
        }
        if (this instanceof KleurActivity) {
            Controller.setColor(list);
        }
    }

    @Override
    public void onClick(View v) {
    	//Log.d("HAI0000x1","HAI0000x1"); 
        getResult();
    }

    protected OnClickListener onSkipClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (BaseGridActivity_old.this instanceof SilhuetteActivity) {
                Controller.clearSilhuette();
            } else if (BaseGridActivity_old.this instanceof SnavelActivity) {
                Controller.clearBeak();
            } else if (BaseGridActivity_old.this instanceof GrootteActivity) {
                Controller.clearSizes();
            } else if (BaseGridActivity_old.this instanceof KleurActivity) {
                Controller.clearColors();
            }
            startNext();
        }
    };
}*/