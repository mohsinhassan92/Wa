package nl.vogelbescherming.wadvogels;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import nl.vogelbescherming.wadvogels.R;

public class ContentBaseActivity extends BaseActivity{

	private ImageView button;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContent(R.layout.content_base);
        showButton(false);
    }

	public void setButton(int drawable){
		setContentPart(R.id.ButtonContainer, R.layout.apply_button);
		setBackgroundToButton(R.id.apply_button,drawable);
	}

	public void showButton(boolean show){
		View button = findViewById(R.id.ButtonContainer);
		button.setVisibility(show ? View.VISIBLE : View.GONE);
	}

	public ImageView getButton(){
		return button;
	}

	@SuppressLint("NewApi")
	private void setBackgroundToButton(int applyButton, int drawable) {
		button = (ImageView) findViewById(applyButton);
		button.setImageResource(drawable);
	}
	

	public void setTitle(String str){
		//setHeader(R.layout.title, str);
		setContentPart(R.id.TitleContainer, R.layout.title);
        setText(R.layout.title, R.id.title_text, Typeface.NORMAL, str, false);
	}
	/***Changed setTitleHeader***/
	public void setTitleHeader(int headerGrootte){
		//setHeader(R.layout.title, str);
		setContentPart(R.id.TitleContainer, R.layout.title);
        setTextHeader(R.layout.title, R.id.title_text, Typeface.NORMAL, headerGrootte, false);
	}
	/***Changed setTitleSerial***/
	public void setTitleSerial(int serialNo){
		//setHeader(R.layout.title, str);
		setContentPart(R.id.titleSerial, R.layout.title);
        setTextSerialNo(R.layout.title, R.id.text_serial_no, Typeface.NORMAL, serialNo, false);
	}
	
	
	public void setContent(int contentLayoutId) {
		setContentPart(R.id.ContentContainer, contentLayoutId);
	}
}
