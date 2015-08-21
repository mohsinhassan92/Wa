package nl.vogelbescherming.wadvogels;

import nl.vogelbescherming.wadvogels.R;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class FilmActionActivity extends Activity{
	private VideoView video;
	private Uri uri;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_film_action);
		uri = Uri.parse("android.resource://com.rosberry.wadden.android/raw/waddenapp");
		video = (VideoView) findViewById(R.id.video);
		video.setVideoURI(uri);
	    video.setMediaController(new MediaController(this));
	    video.requestFocus();
	    video.start();
	}
	@Override
	protected void onResume() {
		super.onResume();
		video.resume();		
	}
	@Override
	protected void onPause() {
		super.onPause();
		video.pause();
	}
}