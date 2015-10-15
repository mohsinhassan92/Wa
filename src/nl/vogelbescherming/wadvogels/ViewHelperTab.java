package nl.vogelbescherming.wadvogels;

import android.widget.ImageView;
import android.widget.TextView;

public class ViewHelperTab {
	
	private ImageView imageView;
	private TextView textView;
	private Boolean selected;
	
	public ImageView getImageView() {
		return imageView;
	}
	public void setImageView(ImageView imageView) {
		this.imageView = imageView;
	}
	public TextView getTextView() {
		return textView;
	}
	public void setTextView(TextView textView) {
		this.textView = textView;
	}
	public Boolean getSelected() {
		return selected;
	}
	public void setSelected(Boolean selected) {
		this.selected = selected;
	}
}
