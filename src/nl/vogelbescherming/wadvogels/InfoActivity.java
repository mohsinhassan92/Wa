package nl.vogelbescherming.wadvogels;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import nl.vogelbescherming.wadvogels.R;
import nl.vogelbescherming.wadvogels.expandView.ExpandAnimation;
import nl.vogelbescherming.wadvogels.fonts.Fonts;
import nl.vogelbescherming.wadvogels.util.Utils;

public class InfoActivity extends BaseActivity implements OnClickListener {
	private final String phoneNo = "4333";
	private final String textSMS = "VOGELS";
	private int itemNo = 1;
	private View backButtonTab;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContent(R.layout.activity_info);

		hideButtons();
		showAllBirdsButton(false);
		hideFooterMenu();
		if (BaseActivity.isTablet(this)) {
			backButtonTab = findViewById(R.id.home_iv);
			backButtonTab.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					onBackPressed();
				}
			});
		}
		View vogned = findViewById(R.id.vogned);
		View ruvo = findViewById(R.id.ruvo);
		View waar = findViewById(R.id.waar);
		View gebr = findViewById(R.id.gebr);
		View cred = findViewById(R.id.cred);
		ruvo.setBackgroundColor(getResources().getColor(R.color.bg));
		waar.setBackgroundColor(getResources().getColor(R.color.white));
		gebr.setBackgroundColor(getResources().getColor(R.color.bg));
		cred.setBackgroundColor(getResources().getColor(R.color.white));
		vogned.setBackgroundColor(getResources().getColor(R.color.bg));
		createExpandView(ruvo, "Rust voor vogels, Ruimte voor mensen.", "par_2");
		createExpandView(waar, "Waarom is rust zo belangrijk voor vogels?",
				"par_3");
		if(BaseActivity.isTablet(InfoActivity.this))
		{
			createExpandView(gebr, "Gebruikstips", "par_4_tab");
		}
		else{
			createExpandView(gebr, "Gebruikstips", "par_4");			
		}
		createExpandView(cred, "Credits", "par_5");
		createExpandView(vogned, "Vogelbescherming Nederland", "par_1");
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private View createExpandView(View parent, String title, String bodyName) {

		WebView webView = new WebView(this);
		webView.setBackgroundColor(getResources().getColor(
				android.R.color.white));
		webView.getSettings().setBuiltInZoomControls(false);
		if (Utils.hasHoneycomb())
			webView.getSettings().setDisplayZoomControls(false);
		webView.loadUrl("file:///android_asset/html/" + bodyName + ".html");
		TextView tv = ((TextView) parent.findViewById(R.id.title));
		// if (itemNo%2 == 0) {
		// tv.setBackgroundColor(getResources().getColor(R.color.bg));
		// } else {
		// tv.setBackgroundColor(getResources().getColor(R.color.white));
		// }
		tv.setText(title);
		tv.setTextColor(getResources().getColor(R.color.text_color));
		tv.setTypeface(Fonts.getTfFont_interstate_regular());
		parent.setTag(tv);
		LinearLayout toolbar = (LinearLayout) parent.findViewById(R.id.toolbar);
		// ((LinearLayout.LayoutParams) toolbar.getLayoutParams()).bottomMargin
		// = -50;
		((LinearLayout.LayoutParams) toolbar.getLayoutParams()).topMargin = 16;
		toolbar.setVisibility(View.GONE);
		toolbar.removeAllViews();
		toolbar.addView(webView);
		parent.setOnClickListener(this);
		itemNo++;
		return parent;
	}

	@Override
	public void onClick(final View view) {

		final View toolbar = view.findViewById(R.id.toolbar);
		if (toolbar == null)
			return;
		if (toolbar.getTag() != null)
			return;
		TextView tv = (TextView) view.getTag();
		if (toolbar.getVisibility() == View.VISIBLE) {
			tv.setCompoundDrawablesWithIntrinsicBounds(0, 0,
					R.drawable.ic_arrow_down, 0);
		} else
			tv.setCompoundDrawablesWithIntrinsicBounds(0, 0,
					R.drawable.ic_arrow_up, 0);
		ExpandAnimation expandAni = new ExpandAnimation(toolbar, 500);
		expandAni.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				toolbar.setTag(animation);
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				toolbar.setTag(null);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

		});
		toolbar.startAnimation(expandAni);
	}
	// @Override
	// public void onClick(View v) {
	// Uri uri = null;
	// switch (v.getId()) {
	// case R.id.website:
	// uri = Uri.parse("http://www.vogelbescherming.nl/");
	// break;
	// case R.id.facebook:
	// uri = Uri.parse("http://www.facebook.com/vogelbeschermingnederland");
	// break;
	// case R.id.twitter:
	// uri = Uri.parse("https://twitter.com/vogelnieuws");
	// break;
	// }
	// Intent browserIntent = new Intent(Intent.ACTION_VIEW, uri);
	// startActivity(browserIntent);
	// }
}
