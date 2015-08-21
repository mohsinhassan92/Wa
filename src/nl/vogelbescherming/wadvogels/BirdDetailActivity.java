package nl.vogelbescherming.wadvogels;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import nl.vogelbescherming.wadvogels.R;
import nl.vogelbescherming.wadvogels.fonts.Fonts;
import nl.vogelbescherming.wadvogels.model.Bird;
import nl.vogelbescherming.wadvogels.util.HackyScrollView;
import nl.vogelbescherming.wadvogels.util.HackyViewPager;

public class BirdDetailActivity extends ContentBaseActivity{
	
	private Bird bird; 						
	private boolean mStartPlaying = true;   
	private boolean ShowFullInfo = true;    
	private TextView description;
	private TableLayout infoBar;
	private RelativeLayout image_large_layout;
	private MediaPlayer mPlayer;
	private HackyViewPager mViewPager;
    private ImageView soundButton;
    private Bird clickBird;

	private boolean showMore = false;
	private int pos;
	private ArrayList<Bird> birds;
	private HackyScrollView scroll;
	private View info;
	private View titleBar1, titleBar2, soundView, backView;
	
	private void showHeader(boolean show){
		if (show){
			titleBar1.setVisibility(View.VISIBLE);
			titleBar2.setVisibility(View.VISIBLE);
			showBaseHeader(true);
		} else {
			titleBar1.setVisibility(View.GONE);
			titleBar2.setVisibility(View.GONE);
			showBaseHeader(false);
		}
	}
	
	private void showHeaders(boolean show) {
		if (show){
			titleBar1.setVisibility(View.VISIBLE);
			titleBar2.setVisibility(View.VISIBLE);
		} else {
			titleBar1.setVisibility(View.GONE);
			titleBar2.setVisibility(View.GONE);
		}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContent(R.layout.activity_bird_detail);
//		setHeader("VOGELGIDS");

		showVogelVinderMenuAsActive();
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		hideButtons();
		
		bird = (Bird) getIntent().getExtras().getSerializable("CurrentBird");
		birds = (ArrayList<Bird>) getIntent().getExtras().getSerializable("Birds");
		pos = getIntent().getExtras().getInt("Pos");
		
		
		((TextView) findViewById(R.id.backTV)).setTypeface(Fonts.getTfFont_regular());
		((TextView) findViewById(R.id.soundTV)).setTypeface(Fonts.getTfFont_regular());
		
		info = findViewById(R.id.info);
		titleBar1 = findViewById(R.id.titleBar1);
		titleBar2 = findViewById(R.id.titleBar2);
		soundView = findViewById(R.id.soundView);
		backView = findViewById(R.id.backView);
		scroll = (HackyScrollView) findViewById(R.id.scroll);
		
		description = ((TextView) findViewById(R.id.description));
		infoBar =((TableLayout) findViewById(R.id.infoBar));
		soundButton = (ImageView) findViewById(R.id.soundButton);
        clickBird = bird;
        
        backView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
       
        soundView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try{
		            if (mStartPlaying){
		                mPlayer = new MediaPlayer();
		                Uri uri = Uri.parse("android.resource://"+ getPackageName() + "/raw/" + clickBird.getSoundName());
		                mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		                mPlayer.setDataSource(getApplicationContext(),uri);
		                mPlayer.prepare();
		                mPlayer.start();
		                mPlayer.setOnCompletionListener(new OnCompletionListener() {

		                    @Override
		                    public void onCompletion(MediaPlayer mp) {
		                        mp.release();
		                    }
		                }); 
		            } 
		            else{
		            	if (mPlayer != null){
			            	Log.i("Sound", "off");
                            try {
                                mPlayer.pause();
                                mPlayer.release();
                            } catch (IllegalStateException ex) {
                            }
			                mPlayer = null;
		            	}
		            }
		            mStartPlaying = !mStartPlaying;
		        }
		        catch (IOException e){
		            Log.e("MP", "prepare() failed");
		        }
			}
		});
		
		//*********************************************************************************************************************
		mViewPager = (HackyViewPager) findViewById(R.id.profiles);
		mViewPager.setOffscreenPageLimit(1);
        mViewPager.setAdapter(new ProfilePagerAdapter());
        
		mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int i, float v, int i2) {
			}

			@Override
			public void onPageSelected(int index) {
				
				Log.i("Count", mViewPager.getChildCount()+"");
				bird = birds.get(index);
                clickBird = bird;
				if (mPlayer != null) {
                    try {
                        mPlayer.pause();
                        mPlayer.release();
                    } catch (Exception e) {
                    }
					mPlayer = null;
					mStartPlaying = true;
				}
				createContent();
			}

			@Override
			public void onPageScrollStateChanged(int i) {
			}
		});
		calcLayoutWH(mViewPager, 0);
		createContent();
		mViewPager.setSaveEnabled(false);
        scroll.setViewPager(mViewPager);
	}

    @Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
			showHeader(false);
			calcLayoutWH(mViewPager, 2);
		} else {
			showHeader(true);
			calcLayoutWH(mViewPager, 1);
		}
	}
	@Override
	protected void onResume() {
		super.onResume();
		int t = birds.indexOf(bird);
		if (t != -1)
			pos = t;
		mViewPager.setCurrentItem(pos);
	}
	private void createContent() {
		
		TextView name = (TextView) findViewById(R.id.name);	
		TextView category = (TextView) findViewById(R.id.category);		
		name.setText(bird.getName());
		
		String[] chances = getResources().getStringArray(R.array.chance);
        String[] appears = getResources().getStringArray(R.array.appears);
		category.setText(chances[Integer.valueOf(bird.getChance()) - 1] + " | " + appears[bird.getAppears().get(0) - 1]);
		
		name.setTypeface(Fonts.getTfFont_regular());
		category.setTypeface(Fonts.getTfFont_regular());
		
		TextView voorkomen_title =(TextView) findViewById(R.id.voorkomen_title);
		voorkomen_title.setText("Voorkomen");
		setTypeFace(voorkomen_title, true);
		
		TextView voorkomen_content =(TextView) findViewById(R.id.voorkomen_content);
		String text = appears[bird.getAppears().get(0) - 1];
		voorkomen_content.setText(text);
		setTypeFace(voorkomen_content, false);
		
		TextView aantal_title =(TextView) findViewById(R.id.trefkans_title);
		aantal_title.setText("Trefkans");
		setTypeFace(aantal_title, true);
		
		TextView aantal_content =(TextView) findViewById(R.id.trefkans_content);
		String chance = chances[Integer.valueOf(bird.getChance()) - 1];
		aantal_content.setText(chance);
		setTypeFace(aantal_content, false);
		
		TextView broedperiode_title =(TextView) findViewById(R.id.grootte_title);
		broedperiode_title.setText("Grootte");
		setTypeFace(broedperiode_title, true);
		
		TextView broedperiode_content =(TextView) findViewById(R.id.grootte_content);
		broedperiode_content.setText(bird.getGrootte());
		setTypeFace(broedperiode_content, false);
		
		TextView description = (TextView) findViewById(R.id.description);
		description.setText(bird.getDescription());
        setTypeFace(description, false);
		
		TextView snavel_title = (TextView) findViewById(R.id.snavel_title);
		setTypeFace(snavel_title, true);
		
		TextView snavel_con = (TextView) findViewById(R.id.snavel_content);
		snavel_con.setText(bird.getSnavel());
		setTypeFace(snavel_con, false);
		
		TextView poten_title = (TextView) findViewById(R.id.poten_title);
		setTypeFace(poten_title, true);
		
		TextView poten_con = (TextView) findViewById(R.id.poten_content);
		poten_con.setText(bird.getPoten());
		setTypeFace(poten_con, false);
		
		TextView opvallende_title = (TextView) findViewById(R.id.opvallende_kenmerken_title);
		setTypeFace(opvallende_title, true);
		
		TextView opvallende_con = (TextView) findViewById(R.id.opvallende_kenmerken_content);
		opvallende_con.setText(bird.getOpvallende());
		setTypeFace(opvallende_con, false);
		
		TextView gedrag_title = (TextView) findViewById(R.id.gedrag_title);
		setTypeFace(gedrag_title, true);
		
		TextView gedrag_con = (TextView) findViewById(R.id.gedrag_content);
		gedrag_con.setText(bird.getGedrag());
		setTypeFace(gedrag_con, false);
		
		TextView voedsel_title = (TextView) findViewById(R.id.voedsel_title);
		setTypeFace(voedsel_title, true);
		
		TextView voedsel_con = (TextView) findViewById(R.id.voedsel_content);
		voedsel_con.setText(bird.getVoedsel());
		setTypeFace(voedsel_con, false);
		
		TextView leefgebied_title = (TextView) findViewById(R.id.leefgebied_title);
		setTypeFace(leefgebied_title, true);
		
		TextView leefgebied_con = (TextView) findViewById(R.id.leefgebied_content);
		leefgebied_con.setText(bird.getLeefgebied());
		setTypeFace(leefgebied_con, false);
		
		TextView verspreiding_title = (TextView) findViewById(R.id.verspreiding_in_europa_title);
		setTypeFace(verspreiding_title, true);
		
		TextView verspreiding_con = (TextView) findViewById(R.id.verspreiding_in_europa_content);
		verspreiding_con.setText(bird.getVerspreiding());
		setTypeFace(verspreiding_con, false);
		
		TextView engelse_title = (TextView) findViewById(R.id.engelse_naam_title);
		setTypeFace(engelse_title, true);
		
		TextView engelse_con = (TextView) findViewById(R.id.engelse_naam_content);
		engelse_con.setText(bird.getEngelse());
		setTypeFace(engelse_con, false);

		TextView latin_title = (TextView) findViewById(R.id.latijnse_naam_title);
		setTypeFace(latin_title, true);
		
		TextView latin_con = (TextView) findViewById(R.id.latijnse_naam_content);
		latin_con.setText(bird.getLatijnse());
		setTypeFace(latin_con, false);
		
		TextView meer_title = (TextView) findViewById(R.id.meer_informative_title);
		setTypeFace(meer_title, true);
		
		TextView meer_con = (TextView) findViewById(R.id.meer_informative_content);
		//meer_con.setMovementMethod(LinkMovementMethod.getInstance());
		meer_con.setText(Html.fromHtml(bird.getMeerInfo()));
//		meer_con.setText(bird.getMeerInfo());
		
		setTypeFace(meer_con, false);

		final View inShadow = findViewById(R.id.in_shadow);
		showMore = true;
//		if(showMore){
//			inShadow.setVisibility(View.VISIBLE);
//			less.setVisibility(View.GONE);
//		}
		
	}
	
	@Override
	protected void onPause() {
		if (mPlayer != null) {
            try {
                mPlayer.pause();
                mPlayer.release();
            } catch (Exception e) {
                // A TI DUMAL IA BUDU SLEDIT' ZA SOSTOIANIEM PLEERA? DA POSHLO ONO NAHUI
                // - IA UJE CHAS KAK DOMA DOLJEN BIT'
            }
	        mPlayer = null;
        }
		super.onPause();
	}
	private LayoutParams calcLayoutWH(View view, int flag) {
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		LayoutParams rlp = (LayoutParams) view.getLayoutParams();
		if (flag == 1)
			rlp.height = (int) (metrics.heightPixels - (155 * metrics.density));
			//rlp.height = LayoutParams.MATCH_PARENT;
		if (flag == 0)
			rlp.height = metrics.heightPixels / 3;
		if (flag == 2)
			rlp.height = metrics.heightPixels;
		return rlp;
	}
	private void setTypeFace(TextView textView, boolean isTitle) {
		if (isTitle)
			textView.setTypeface(Fonts.getTfFont_condensed_bold());
		else
			textView.setTypeface(Fonts.getTfFont());
	}
	private void click(View hidev, View visiblev){
    	hidev.setVisibility(View.GONE);
    	visiblev.setVisibility(View.VISIBLE);
    	if (ShowFullInfo){
    		info.setVisibility(View.GONE);
    		scroll.setScrollingEnabled(false);
//    		showBaseHeader(false);
    		hideFooterMenu();
    		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
    	} else {
    		info.setVisibility(View.VISIBLE);
    		scroll.setScrollingEnabled(true);
//    		showBaseHeader(true);
    		showFooterMenu();
    		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    	}
    	if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			calcLayoutWH(mViewPager, 2);
		} else {
			calcLayoutWH(mViewPager, ShowFullInfo ? 1: 0);
		}
    	ShowFullInfo = !ShowFullInfo;
    }
	//************************************************************************************************************************************
	private class ProfilePagerAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            LayoutInflater inflater = LayoutInflater.from(container.getContext());
            View view = inflater.inflate(R.layout.viewpager_container, null);

            final ImageView fullScreenIV = (ImageView) view.findViewById(R.id.full_screen);
            final ImageView image = (ImageView) view.findViewById(R.id.image_small);
            final ImageView image_large = (ImageView) view.findViewById(R.id.image_large);
            
            fullScreenIV.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (fullScreenIV.getTag().toString().equals("small")) {
						click(image, image_large);
						v.setTag("large");
					} else {
						click(image_large, image);
						v.setTag("small");
					}
				}
			});
            
            bird = birds.get(position);
            Uri uri = Uri.parse("android.resource://"+ getPackageName() + "/drawable/if" + bird.getImageName());
            image.setImageURI(uri);
            //uri = Uri.parse("android.resource://"+ getPackageName() + "/drawable/if" + bird.getImageName());
            image_large.setImageURI(uri);
            
            container.addView(view);
            return view;
        }
        
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
		@Override
		public int getCount() {
			return birds.size();
		}
		
		@Override
		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}
	}
}