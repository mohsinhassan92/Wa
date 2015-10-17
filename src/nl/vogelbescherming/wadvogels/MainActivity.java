package nl.vogelbescherming.wadvogels;

import nl.vogelbescherming.wadvogels.R;
import nl.vogelbescherming.wadvogels.control.Controller;
import nl.vogelbescherming.wadvogels.fonts.Fonts;

import android.os.Bundle;
import android.os.IInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewDebug.FlagToString;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends BaseActivity {

	// private static final int FLAG_ACTIVITY_CLEAR_TOP = 0;
	private View button1;
	private View button2;
	private View button3;
	private View button4;
	private View buttonInfo;
	private TextView text_tv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		new Controller().init(this);
		super.onCreate(savedInstanceState);
		getIntent().addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		if (BaseGridActivity.isTablet(this)) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			setContent(R.layout.activity_main_tab);
			hideListIcon();
			
			button1 = findViewById(R.id.relativeLayout1);
			button1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// Intent i = new Intent(MainActivity.this,
					// BirdGuideActivity.class);
					Intent intent = new Intent(MainActivity.this,
							VogelvinderActivityTablet.class);
					startActivity(intent);

				}
			});

			button2 = findViewById(R.id.relativeLayout2);
			button2.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent i = new Intent(MainActivity.this,
							SearchResultBirdDetailTabletActivity.class);
					i.putExtra("ShowAllBirds", true);
					i.putExtra("Caller", "MainActivity");
					startActivity(i);
				}
			});
			
			button3 = findViewById(R.id.relativeLayout3);
			button3.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent i = new Intent(MainActivity.this,
							MapActivity1.class);
					startActivity(i);
				}
			});
			
			button4 = findViewById(R.id.relativeLayout4);
			button4.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent i = new Intent(MainActivity.this,
							MapActivity2.class);
					startActivity(i);
				}
			});

			buttonInfo = findViewById(R.id.infoIV);
			buttonInfo.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent i = new Intent(MainActivity.this, InfoActivity.class);
					startActivity(i);
				}
			});

		} else {
			setContent(R.layout.activity_main);
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}

		// getIntent().addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		hideTabs();
		hideButtons();
		hideFooterMenu();
		hideHomeIcon();
		hideTopHeaderContainer();
		
		if (!BaseGridActivity.isTablet(this)) {
			clearData();
			text_tv=(TextView)findViewById(R.id.title_tv);
			text_tv.setTypeface(Fonts.getTfFont_interstate_regular());
			button1 = findViewById(R.id.relativeLayout1);
			button1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// Intent i = new Intent(MainActivity.this,
					// BirdGuideActivity.class);
					Intent intent = new Intent(MainActivity.this,
							GrootteActivity.class);
					startActivity(intent);
				}
			});

			button2 = findViewById(R.id.relativeLayout2);
			button2.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent i = new Intent(MainActivity.this,
							SearchResultActivity.class);
					i.putExtra("ShowAllBirds", true);
					i.putExtra("Caller", "MainActivity");
					startActivity(i);
				}
			});
			
			button3 = findViewById(R.id.relativeLayout3);
			button3.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent i = new Intent(MainActivity.this,
							MapActivity1.class);
					startActivity(i);
				}
			});
			
			button4 = findViewById(R.id.relativeLayout4);
			button4.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent i = new Intent(MainActivity.this,
							MapActivity2.class);
					startActivity(i);
				}
			});
			
			buttonInfo = findViewById(R.id.infoIV);
			buttonInfo.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent i = new Intent(MainActivity.this, InfoActivity.class);
					startActivity(i);
				}
			});
		}
	}

	@Override
	public void onBackPressed() {
		/*
		 * Intent i = new Intent(this,SplashActivity.class);
		 * i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		 * //i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); startActivity(i);
		 */
		Intent setIntent = new Intent(Intent.ACTION_MAIN);
		setIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		// startActivity(setIntent);
		super.onBackPressed();
	}

	@Override
	protected void onResume() {
		Controller.clearMyBird();
		super.onResume();
	}

	private RelativeLayout.LayoutParams calcLayoutWH(View view) {
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) view
				.getLayoutParams();
		rlp.addRule(RelativeLayout.CENTER_IN_PARENT);
		rlp.width = metrics.widthPixels * 7 / 10;
		return rlp;
	}
}