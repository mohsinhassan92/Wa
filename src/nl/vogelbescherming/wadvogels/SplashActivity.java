package nl.vogelbescherming.wadvogels;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		// *
		if (BaseGridActivity.isTablet(this)) {
			setContentView(R.layout.activity_splash_tab);
		}
		// */
		(new Handler()).postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent mainIntent = new Intent(SplashActivity.this,
						MainActivity.class);
				startActivity(mainIntent);
				finish();
			}
		}, 2000);
	}

	@Override
	public void onBackPressed() {
		this.finish();
		super.onBackPressed();
	}

}
