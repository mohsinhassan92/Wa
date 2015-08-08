package nl.vogelbescherming.wadvogels;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import nl.vogelbescherming.wadvogels.R;
import nl.vogelbescherming.wadvogels.fonts.Fonts;

public class FilmActivity extends BaseActivity{
	private TextView filmTitle;
	private ImageView mVideoView;
	
	@Override
	protected void onResume() {
		DisplayMetrics mMetrics = getResources().getDisplayMetrics();
		Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.preview);		
		
		int x= mMetrics.widthPixels - getPixels(40,mMetrics);
	    mVideoView.getLayoutParams().height = bm.getHeight() * x / bm.getWidth();
		super.onResume();
	}
	
	private int getPixels(int dipValue, DisplayMetrics mMetrics){
        int px = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, mMetrics);
        return px;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContent(R.layout.activity_film);
//		setHeader("FILM");
	   
	    mVideoView = (ImageView) findViewById(R.id.video);
	    //uri = Uri.parse("android.resource://com.rosberry.wadden.android/raw/waddenapp");
	    filmTitle = (TextView) findViewById(R.id.film_title);
	    filmTitle.setText("Film over het verstoren van broed vogels");
	    filmTitle.setTypeface(Fonts.getTfFont());
	    
	    TextView vogelweb = (TextView) findViewById(R.id.vogelweb);
	    //vogelweb.setTextColor(0x127bbe);
	    vogelweb.setTypeface(Fonts.getTfFont(),Typeface.BOLD);
	    
	    TextView info1 = (TextView) findViewById(R.id.info1);
	    info1.setText("Mijn Vogelweb is plek om uw mooiste en leukste beelden en geluiden van vogels te laten horen en zien! En er is meer... ");
	    info1.setTypeface(Fonts.getTfFont());
	    
	    TextView info2 = (TextView) findViewById(R.id.info2);
	    info2.setText("Begin vandaag nog uw eigen weblog, geef vogelwaarnemingen door, plaats een kleine advertentie of deel uw mooiste vogelfotos. Laat iedereen meegenieten en");
	    info2.setTypeface(Fonts.getTfFont());
	    
	    mVideoView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(FilmActivity.this, FilmActionActivity.class);
				startActivity(i);
			}
		});
	    
	}
	
}