package nl.vogelbescherming.wadvogels;

import nl.vogelbescherming.wadvogels.R;
import nl.vogelbescherming.wadvogels.fonts.Fonts;


import android.os.Bundle;
import android.widget.TextView;

public class HelpActivity extends BaseActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContent(R.layout.activity_help);
		setHeader("HELP");
		showListButton();
		TextView text1 = (TextView) findViewById(R.id.text1);
		text1.setTypeface(Fonts.getTfFont());
		TextView text2 = (TextView) findViewById(R.id.text2);
		text2.setTypeface(Fonts.getTfFont());
		TextView text3 = (TextView) findViewById(R.id.text3);
		text3.setTypeface(Fonts.getTfFont());
	}
}