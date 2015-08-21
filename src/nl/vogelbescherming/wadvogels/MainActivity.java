package nl.vogelbescherming.wadvogels;

import nl.vogelbescherming.wadvogels.R;
import nl.vogelbescherming.wadvogels.control.Controller;
import nl.vogelbescherming.wadvogels.fonts.Fonts;


import android.os.Bundle;
import android.os.IInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends BaseActivity {
	
	private View button1;
	private View button2;
	private View button3;
	private View button4;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		new Controller().init(this);
        super.onCreate(savedInstanceState);
        setContent(R.layout.activity_main);
        
        hideTabs();
        hideFooterMenu();
        hideButtons();
        
        button1 = findViewById(R.id.relativeLayout1);
        button1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Intent i = new Intent(MainActivity.this, BirdGuideActivity.class);
				Intent intent = new Intent(MainActivity.this, GrootteActivity.class);
				startActivity(intent);
			}
		});
        button2 = findViewById(R.id.relativeLayout2);
        button2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, SearchResultActivity.class);
				i.putExtra("ShowAllBirds", true);
				i.putExtra("Caller", "MainActivity");
				startActivity(i);
			}
		});
        button3 = findViewById(R.id.relativeLayout3);
        button3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, BirdSpotsActivity.class);
				startActivity(i);
			}
		});
        
        button4 = findViewById(R.id.infoIV);
        button4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, InfoActivity.class);
				startActivity(i);
			}
		});
	}
	@Override
	protected void onResume() {
		Controller.clearMyBird();
		super.onResume();
	}
	private RelativeLayout.LayoutParams calcLayoutWH(View view) {
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) view.getLayoutParams();
		rlp.addRule(RelativeLayout.CENTER_IN_PARENT);
		rlp.width = metrics.widthPixels * 7 / 10;
		return rlp;
	}
}