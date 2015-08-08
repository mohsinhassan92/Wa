package nl.vogelbescherming.wadvogels;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import nl.vogelbescherming.wadvogels.R;
import nl.vogelbescherming.wadvogels.control.Controller;
import nl.vogelbescherming.wadvogels.fonts.Fonts;
import nl.vogelbescherming.wadvogels.model.Bird;

public class BaseActivity extends FragmentActivity {
	private final LinearLayout.LayoutParams layoutFillParent = new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.MATCH_PARENT,
			LinearLayout.LayoutParams.MATCH_PARENT);
	
	private LayoutInflater mInflater;
	private LinearLayout tabs;
	private final int[] tabIds = new int[] { R.id.btnZoek,
			R.id.btnVOGELBESCHERMING, R.id.btnVOGELPLEKKEN, R.id.btnVOGELVINDER };
	private ViewGroup vg;
	private TextView silhouet;
	private TextView snavel;
	private TextView groette;
	private TextView kleuren;
	private SeekBar progress;
	private ImageView label;
	private boolean isTabsDown;
	private ImageView list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.base);
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		tabs = (LinearLayout) findViewById(R.id.radiogroup);
		
		for (int i = 0; i < tabIds.length; i++) {
			View tab = tabs.findViewById(tabIds[i]);
			tab.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent i = null;
					switch (v.getId()) {
					case R.id.btnZoek:
						if (!(BaseActivity.this instanceof FilmActivity)){
							i = new Intent(BaseActivity.this, SearchResultActivity.class);
                            i.putExtra("ShowAllBirds", true);
						}
						break;
					case R.id.btnVOGELBESCHERMING:
						if (!(BaseActivity.this instanceof InfoActivity)){
							i = new Intent(BaseActivity.this, InfoActivity.class);
						}
						break;
					case R.id.btnVOGELVINDER:
						if (!(BaseActivity.this instanceof SilhuetteActivity)){
							Controller.clearMyBird();
							i = new Intent(BaseActivity.this, SilhuetteActivity.class);
						}
						break;
					case R.id.btnVOGELPLEKKEN:
						if (!(BaseActivity.this instanceof BirdSpotsActivity)){
							i = new Intent(BaseActivity.this, BirdSpotsActivity.class);
						}
						break;
					}
					if (i != null){
						BaseActivity.this.startActivity(i);
					}
				}
			});
		}
		
		label = (ImageView)findViewById(R.id.label);
		label.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				moveTabs((ImageView) v, isTabsDown);
				isTabsDown = !isTabsDown;
			}
		});
	}
	
	@Override
	protected void onResume() {
		tabs.setVisibility(View.GONE);
		isTabsDown = true;
		label.setImageResource(R.drawable.plus);
		super.onResume();
	}

	public void hideTabs(){
		findViewById(R.id.radiogroupContainer).setVisibility(View.GONE);
	}
	
	public void moveTabs(final ImageView image, final boolean up){
		View view = findViewById(R.id.radiogroupContainer);
		View view2 = findViewById(R.id.container);
		Log.i("FLAG", up+"");
		Animation move = null;
		if (up){
			move = AnimationUtils.loadAnimation(this, R.anim.scroll_up);
		} else {
			move = AnimationUtils.loadAnimation(this, R.anim.scroll_down);
			
		}
		
		if(up) {
			tabs.setVisibility(View.VISIBLE);
			tabs.startAnimation(move);
			view2.startAnimation(move);
		} else {
			tabs.startAnimation(move);
			view2.startAnimation(move);
		}
		
		move.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				if (up){
					image.setImageResource(R.drawable.minus);
				}
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				if (!up){
					tabs.setVisibility(View.GONE);
					image.setImageResource(R.drawable.plus);
				}
			}
		});
	}
	
	public void setHeader(String text) {
		setHeader(R.layout.top, text);
        showBackButton();
	}
	
	public void showBaseHeader(boolean show){
		View header = findViewById(R.id.baseHeaderContainer);
		if (show){
			header.setVisibility(View.VISIBLE);
		} else {
			header.setVisibility(View.GONE);
		}
	}

    public void showBackButton(){
        View back = findViewById(R.id.backbutton);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
	
	protected void setText(int where_id, int id, int typeface, String text,
			boolean flag) {
		TextView view_text;
		if (flag) {
			View view = getLayoutInflater().inflate(where_id, null);
			view_text = (TextView) view.findViewById(id);
		} else {
			view_text = (TextView) findViewById(id);
		}
		if (typeface == Typeface.BOLD)
			view_text.setTypeface(Fonts.getTfFont_bold());
		else
			view_text.setTypeface(Fonts.getTfFont());
		view_text.setText(text);
	}

	public void setSubHeader(String text) {

		final ViewGroup vg = (ViewGroup) findViewById(R.id.subheader);
		if (vg == null)
			return;
		vg.setVisibility(View.VISIBLE);
		setText(R.id.subheader, R.id.subheader_text, Typeface.NORMAL, text,
				false);
	}
	public void createSeekBar(Bird filterBird, int position) {

		vg = (ViewGroup) findViewById(R.id.seek_bar);
		if (vg == null)
			return;
		vg.setVisibility(View.VISIBLE);
		
		silhouet = (TextView) vg.findViewById(R.id.silhouet);
		snavel   = (TextView) vg.findViewById(R.id.snavel);
		groette  = (TextView) vg.findViewById(R.id.groette);
		kleuren  = (TextView) vg.findViewById(R.id.kleuren);
		progress = (SeekBar)  vg.findViewById(R.id.progress);
		
		progress.setEnabled(false);
		
		silhouet.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(BaseActivity.this, SilhuetteActivity.class);
				startActivity(i);
			}
		});
		snavel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(BaseActivity.this, SnavelActivity.class);
				startActivity(i);
			}
		});
		groette.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(BaseActivity.this, GrootteActivity.class);
				startActivity(i);
			}
		});
		kleuren.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(BaseActivity.this, KleurActivity.class);
				startActivity(i);
			}
		});
		vg.setOnTouchListener(swipe);
		setStatus(filterBird, position);
	}
	private OnTouchListener swipe = new OnTouchListener() {
		private boolean isInTouch = false;
		private float lastX = 0;
		private float lastY = 0;
		private float xC;
		private float yC;
		private int yPath = 0;
		private int xPath = 0;
		
		private boolean isScroll = false;
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			Log.i("TAG", "MotionEvent 0 " + event.getAction()); 
			if (event.getPointerCount() > 1){
				return false;
			}
			
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				Log.i("TAG", "ACTION_DOWN");
				lastX = event.getX();
				lastY = event.getY();
				isInTouch = true;
				return true;
			} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
				return true;
			} else if (event.getAction() == MotionEvent.ACTION_UP){
				v.setSelected(false);
				
				float xCoord = event.getX();
				if (xCoord - lastX > 9) {
					Class <?> nextClass = findNextFilter(BaseActivity.this, false);
					if (nextClass != null){
						Intent intent = new Intent(BaseActivity.this, nextClass);
						startActivity(intent);
						overridePendingTransition(R.anim.pull_in_from_right, R.anim.pull_out_to_right);
					}
					isInTouch = false;
					return true;
				} else if (lastX - xCoord > 9){
					Class <?> nextClass = findNextFilter(BaseActivity.this, true);
					if (nextClass != null){
						Intent intent = new Intent(BaseActivity.this, nextClass);
						startActivity(intent);
						overridePendingTransition(R.anim.pull_in_from_left, R.anim.pull_out_to_left);
					}
					isInTouch = false;
					return true;
				}
			} else {
				isInTouch = false;
			}
			return false;
		}
		
	};
	
	private Class<?> findNextFilter(BaseActivity that, boolean toRight) {
		if (that instanceof SilhuetteActivity){
			if (toRight)
				return SnavelActivity.class;
			else
				return null;
		} else if (that instanceof SnavelActivity){
			if (toRight)
				return GrootteActivity.class;
			else
				return SilhuetteActivity.class;
		} else if (that instanceof GrootteActivity){
			if (toRight)
				return KleurActivity.class;
			else
				return SnavelActivity.class;
		} else if (that instanceof KleurActivity){
			if (toRight)
				return null;
			else
				return GrootteActivity.class;
		}
		return null;
	}
	
	public void selectSeekBar(int position, boolean switcher) {
		if (vg != null){
			switch (position) {
			case 1:
				silhouet.setTextColor(switcher ? Color.parseColor("#97d4fb") : Color.parseColor("#000000"));
				break;
			case 2:
				snavel.setTextColor(switcher ? Color.parseColor("#97d4fb") : Color.parseColor("#000000"));		
				break;
			case 3:
				groette.setTextColor(switcher ? Color.parseColor("#97d4fb") : Color.parseColor("#000000"));
				break;
			case 4:
				kleuren.setTextColor(switcher ? Color.parseColor("#97d4fb") : Color.parseColor("#000000"));
				break;
			}
		}
	}

	public void showListButton() {
		list.setVisibility(View.VISIBLE);
	}
	
	private void setStatus(Bird myBird, int position){
		progress.setProgress((int) (((double)(position - 1)/3)*progress.getMax()));
		if (myBird.getSilhouette() != null)
			silhouet.setTextColor(Color.parseColor("#97d4fb"));
		if (myBird.getBeak() != null)
			snavel.setTextColor(Color.parseColor("#97d4fb"));
		if (myBird.getSizes() != null && myBird.getSizes().size() > 0)
			groette.setTextColor(Color.parseColor("#97d4fb"));
		if (myBird.getColors() != null && myBird.getColors().size() > 0)
			kleuren.setTextColor(Color.parseColor("#97d4fb"));
	}
	
	public void setHeader(int headerLayoutId, String text) {
		setContentPart(R.id.baseHeaderContainer, headerLayoutId);
		setText(headerLayoutId, R.id.text, Typeface.BOLD, text, false);

		list =((ImageView) findViewById(R.id.homebutton));
		
		list.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(BaseActivity.this,
						MainActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtra("ShowAllBirds", true);
				startActivity(intent);
				finish();
			}
		});
	}

	public void setContent(int contentLayoutId) {
		setContentPart(R.id.baseContentContainer, contentLayoutId);
	}

	/*
	 * public void setFooter() { //setFooter(R.layout.footer); }
	 * 
	 * public void setFooter(View footer) {
	 * setContentPart(R.id.baseFooterContainer, footer); }
	 * 
	 * public void setFooter(int footerLayoutId) {
	 * setContentPart(R.id.baseFooterContainer, footerLayoutId); }
	 */

	protected void setContentPart(int containerId, int layoutId) {
		final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		setContentPart(containerId, inflater.inflate(layoutId, null));
	}

	private void setContentPart(int containerId, View view) {
		final ViewGroup vg = (ViewGroup) findViewById(containerId);
		vg.setVisibility(view != null ? View.VISIBLE : View.GONE);
		vg.removeAllViews();
		if (view != null)
			vg.addView(view, layoutFillParent);
	}

	public LayoutInflater getLayoutInflater() {
		if (mInflater == null)
			mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		return mInflater;
	}
	
}