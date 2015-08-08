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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContent(R.layout.activity_info);
        View vogned = findViewById(R.id.vogned);
        createExpandView(vogned, "Vogelbescherming Nederland", "par_1");
        View ruvo = findViewById(R.id.ruvo);
        createExpandView(ruvo, "Rust voor vogels, Ruimte voor mensen", "par_2");
        View waar = findViewById(R.id.waar);
        createExpandView(waar, "Waarom is rust voor vogels zo belangrijk?", "par_3");
        View gebr = findViewById(R.id.gebr);
        createExpandView(gebr, "Gebruikstips", "par_4");
        View cred = findViewById(R.id.cred);
        createExpandView(cred, "Credits", "par_5");
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private View createExpandView(View parent, String title, String bodyName) {
        WebView webView = new WebView(this);
        webView.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        webView.getSettings().setBuiltInZoomControls(false);
        if (Utils.hasHoneycomb())	webView.getSettings().setDisplayZoomControls(false);
        webView.loadUrl("file:///android_asset/html/" + bodyName + ".html");
        //web.setOverScrollMode(View.OVER_SCROLL_NEVER);


        TextView tv = ((TextView) parent.findViewById(R.id.title));
        tv.setText(title);
        tv.setTypeface(Fonts.getTfFont());
        parent.setTag(tv);
        LinearLayout toolbar = (LinearLayout) parent.findViewById(R.id.toolbar);
        ((LinearLayout.LayoutParams) toolbar.getLayoutParams()).bottomMargin = -50;
        toolbar.setVisibility(View.GONE);
        toolbar.removeAllViews();
        toolbar.addView(webView);
        parent.setOnClickListener(this);
        return parent;
    }

    @Override
    public void onClick(final View view) {

        final View toolbar = view.findViewById(R.id.toolbar);
        if (toolbar == null) return;
        if (toolbar.getTag() != null) return;
        TextView tv = (TextView) view.getTag();
        if (toolbar.getVisibility() == View.VISIBLE) {
            tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_right, 0);
        } else
            tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);
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
//	@Override
//	public void onClick(View v) {
//		Uri uri = null;
//		switch (v.getId()) {
//		case R.id.website:
//			uri = Uri.parse("http://www.vogelbescherming.nl/");
//			break;
//		case R.id.facebook:
//			uri = Uri.parse("http://www.facebook.com/vogelbeschermingnederland");
//			break;
//		case R.id.twitter:
//			uri = Uri.parse("https://twitter.com/vogelnieuws");
//			break;
//		}
//		Intent browserIntent = new Intent(Intent.ACTION_VIEW, uri);
//		startActivity(browserIntent);
//	}
}
