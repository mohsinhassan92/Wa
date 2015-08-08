package nl.vogelbescherming.wadvogels.model;


import nl.vogelbescherming.wadvogels.R;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class ProgressFilters{
	private View layout;
	private TextView silhouet;
	private TextView snavel;
	private TextView groette;
	private TextView kleuren;
	private SeekBar progress;
	
	public ProgressFilters(LayoutInflater inflater) {
		layout = inflater.inflate(R.layout.progress_filters, null);
		
		silhouet = (TextView) layout.findViewById(R.id.silhouet);
		snavel   = (TextView) layout.findViewById(R.id.snavel);
		groette  = (TextView) layout.findViewById(R.id.groette);
		kleuren  = (TextView) layout.findViewById(R.id.kleuren);
		progress = (SeekBar)  layout.findViewById(R.id.progress);
	}
	
	public View getView() {
		return layout;
	}

	public View getLayout() {
		return layout;
	}
	
	public void setStatus(Bird myBird, int position){
		switch (position) {
		case 1:
			progress.setProgress(0);
			break;
		case 2:
			if (myBird.getSilhouette() != null)
				silhouet.setTextColor(Color.BLUE);
			progress.setProgress(progress.getMax() / 3);
			break;
		case 3:
			if (myBird.getSilhouette() != null)
				silhouet.setTextColor(Color.BLUE);
			if (myBird.getBeak() != null)
				snavel.setTextColor(Color.BLUE);
			progress.setProgress(progress.getMax()* 2 / 3);
			break;
		case 4:
			if (myBird.getSilhouette() != null)
				silhouet.setTextColor(Color.BLUE);
			if (myBird.getBeak() != null)
				snavel.setTextColor(Color.BLUE);
			if (myBird.getSizes() != null)
				groette.setTextColor(Color.BLUE);
			progress.setProgress(progress.getMax());
			break;
		}
	}
}