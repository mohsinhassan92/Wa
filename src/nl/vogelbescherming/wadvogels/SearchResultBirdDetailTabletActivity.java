package nl.vogelbescherming.wadvogels;

import java.io.IOException;
import java.util.ArrayList;

import nl.vogelbescherming.wadvogels.adapters.ListAdapter;
import nl.vogelbescherming.wadvogels.control.Controller;
import nl.vogelbescherming.wadvogels.fonts.Fonts;
import nl.vogelbescherming.wadvogels.model.Bird;
import nl.vogelbescherming.wadvogels.model.Location;
import nl.vogelbescherming.wadvogels.util.HackyScrollView;
import nl.vogelbescherming.wadvogels.util.HackyViewPager;
import nl.vogelbescherming.wadvogels.util.Utils;
import nl.vogelbescherming.wadvogels.view.CustomSpinner;
import nl.vogelbescherming.wadvogels.view.CustomSpinner.OnSpinnerEventsListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SearchResultBirdDetailTabletActivity extends ContentBaseActivity
		implements OnItemClickListener {

	private ListView listview;
	private ArrayList<Bird> birds = null;
	private ListAdapter la = null;
	private Boolean showAllBirds;
	private Boolean showAllBirdsTabs;

	private CustomSpinner spinnerChance;
	private Spinner spinnerAppears;
	private Button btnSearch;
	private Button btnSearchAllBirds;
	private EditText etSearch;
	private EditText etSearchAllBirds;
	private View backView;
	ArrayAdapter<String> adapter2;
	private View containerListView;
	private View containerSearchArea;
	private ImageView backButtonTab;
	private View containerSearchAllBirds;

	// Bird Detail Activity
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
	// private ArrayList<Bird> birdsD;
	private HackyScrollView scroll;
	private View info;
	private View titleBar1, titleBar2, soundView, backViewD;
	private View dummyView;

	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++Bird Detail
	// Activity+++++++++++++++++++++++++++++
	private void showHeader(boolean show) {
		if (show) {
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
		if (show) {
			titleBar1.setVisibility(View.VISIBLE);
			titleBar2.setVisibility(View.VISIBLE);
		} else {
			titleBar1.setVisibility(View.GONE);
			titleBar2.setVisibility(View.GONE);
		}
	}

	// +++++++++++++++++++++++++++++++++++++++++++++++Bird Detail
	// Activity++++++++++++++++++++++++++++++
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContent(R.layout.activity_search_result_tab);
		setRequestedOrientation(Configuration.ORIENTATION_LANDSCAPE);
		View viewBirdDetail = findViewById(R.id.linear_bird_detail);
		viewBirdDetail.invalidate();
		showAllBirds = getIntent().getExtras().getBoolean("ShowAllBirds");
		showAllBirdsTabs = getIntent().getExtras()
				.getBoolean("ShowAllBirdsTab");
		Location locationFromMap = (Location) getIntent().getExtras().get(
				"locationFromMap");

		showZoekOpNaamMenuAsActive();
		hideButtons();

		backView = (View) findViewById(R.id.backView);
		etSearch = (EditText) findViewById(R.id.filter);
		etSearchAllBirds = (EditText) findViewById(R.id.filter_allbirds);
		btnSearch = (Button) findViewById(R.id.buttonSearch);
		btnSearchAllBirds = (Button) findViewById(R.id.buttonSearch_allbirds);
		spinnerChance = (CustomSpinner) findViewById(R.id.spinnerTrefkans);

		CustomAdapter adapter1 = new CustomAdapter(this,
				R.layout.simple_spinner_item, getResources().getStringArray(
						R.array.chance_items));
		View v = getLayoutInflater()
				.inflate(R.layout.simple_spinner_item, null);
		final TextView textView = (TextView) v.findViewById(R.id.titleTV);
		adapter1.setView(v);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerChance.setAdapter(adapter1);
		spinnerChance.setSelection(spinnerChance.getCount() - 1);

		spinnerAppears = (Spinner) findViewById(R.id.spinnerAanwezigheid);
		adapter2 = new ArrayAdapter<String>(this,
				R.layout.simple_spinner_item2, getResources().getStringArray(
						R.array.appears_items));
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerAppears.setAdapter(adapter2);
		spinnerAppears.setSelection(spinnerAppears.getCount() - 1);
		spinnerChance.setSpinnerEventsListener(new OnSpinnerEventsListener() {

			@Override
			public void onSpinnerOpened() {
				textView.setCompoundDrawablesWithIntrinsicBounds(null, null,
						getResources().getDrawable(R.drawable.arrow_up_blue),
						null);
			}

			@Override
			public void onSpinnerClosed() {
				textView.setCompoundDrawablesWithIntrinsicBounds(null, null,
						getResources().getDrawable(R.drawable.arrow_down_blue),
						null);
			}
		});

		if (locationFromMap != null) {
			hideSearchViews();
		} else {
			if (showAllBirdsTabs != null && showAllBirdsTabs) {
				birds = (ArrayList<Bird>) Controller.getBirds(this);
			} else {
				birds = (ArrayList<Bird>) Controller.getFilteredBirds(this);
			}
			if (showAllBirds != null && showAllBirds) {
				birds = (ArrayList<Bird>) Controller.getBirds(this);
			} else {
				birds = (ArrayList<Bird>) Controller.getFilteredBirds(this);
			}

		}
		/*
		 * if (showAllBirds != null && showAllBirds &&
		 * (getIntent().getStringExtra("Caller")==null)) { birds =
		 * (ArrayList<Bird>) Controller.getBirds(this); }else if(showAllBirds !=
		 * null && showAllBirds){ birds = (ArrayList<Bird>)
		 * Controller.getBirds(this); } else { birds = (ArrayList<Bird>)
		 * Controller.getFilteredBirds(this); }
		 */

		if (birds.size() == 0) {
			new AlertDialog.Builder(this)
					.setMessage(
							"Deze zoekterm heeft geen resultaten opgeleverd. Ga terug en probeer het opnieuw.")
					.setPositiveButton("Ok", new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int arg1) {
							onBackPressed();
						}
					}).show();
		}
		// ************************************************************************
		// mViewPager = (HackyViewPager) findViewById(R.id.profiles);
		// scroll = (HackyScrollView) findViewById(R.id.scroll);
		// ************************************************************************

		dummyView = findViewById(R.id.dummyView);
		containerSearchAllBirds = findViewById(R.id.linear_search_container_allbirds_tab);
		containerListView = findViewById(R.id.container_listView_tab);
		containerSearchArea = findViewById(R.id.linear_search_container_tab);
		backButtonTab = (ImageView) findViewById(R.id.home_iv);
		listview = (ListView) findViewById(R.id.listView);
		la = new ListAdapter(this, R.layout.list_item, birds);
		listview.setAdapter(la);
		listview.setEmptyView(findViewById(R.id.emptyView));
		listview.setOnItemClickListener(this);

		/*
		 * if(showAllBirds!=null && showAllBirds) { hideSearchViews();
		 * backView.setVisibility(View.GONE); showVogelVinderMenuAsActive();
		 * containerSearchArea.setVisibility(View.GONE);
		 * containerSearchAllBirds.setVisibility(View.VISIBLE);
		 * 
		 * }
		 */if (showAllBirdsTabs != null && showAllBirdsTabs) {
			hideSearchViews();
			backView.setVisibility(View.GONE);
			showVogelVinderMenuAsActive();
			containerSearchArea.setVisibility(View.GONE);
			containerSearchAllBirds.setVisibility(View.VISIBLE);
		}
		if (getIntent().getStringExtra("Caller") != null
				&& (getIntent().getStringExtra("Caller")
						.contains("VogelvinderActivityTablet"))) {
			hideSearchViews();
			backView.setVisibility(View.GONE);
			showVogelVinderMenuAsActive();
			containerSearchArea.setVisibility(View.GONE);
			containerListView.setVisibility(View.VISIBLE);
			backButtonTab.setImageDrawable(getResources().getDrawable(
					R.drawable.backarrow_zoek_tab));

		} else if ((showAllBirds != null && showAllBirds)
				|| (showAllBirdsTabs != null && showAllBirdsTabs)) {
			containerListView.setVisibility(View.VISIBLE);
		} else {
			containerListView.setVisibility(View.INVISIBLE);
		}
		backButtonTab.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				onBackPressed();

			}
		});
		etSearch.addTextChangedListener(filterTextWatcher);
		etSearchAllBirds.addTextChangedListener(filterTextWatcher);

		// **********************************Bird Detail**********************************
		if (getIntent().getStringExtra("Caller") != null) {
			if (getIntent().getStringExtra("Caller").contains(
					"VogelvinderActivityTablet")
					|| getIntent().getStringExtra("Caller").contains(
							"MainActivity")) {
				/*
				 * ((TextView) findViewById(R.id.backTV1)).setTypeface(Fonts
				 * .getTfFont_regular());
				 */

				if (getIntent().getStringExtra("Caller").contains(
						"VogelvinderActivityTablet")) {
					dummyView.setVisibility(View.VISIBLE);
				}
				((TextView) findViewById(R.id.soundTV)).setTypeface(Fonts
						.getTfFont_regular());

				info = findViewById(R.id.info);
				titleBar1 = findViewById(R.id.titleBar1);
				titleBar2 = findViewById(R.id.titleBar2);
				soundView = findViewById(R.id.soundView);
				// backViewD = findViewById(R.id.backView1);
				scroll = (HackyScrollView) findViewById(R.id.scroll);
				description = ((TextView) findViewById(R.id.description));
				infoBar = ((TableLayout) findViewById(R.id.infoBar));
				soundButton = (ImageView) findViewById(R.id.soundButton);
				clickBird = bird;

				/*
				 * backViewD.setOnClickListener(new View.OnClickListener() {
				 * 
				 * public void onClick(View v) { onBackPressed(); } });
				 */
				soundView.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						try {
							if (mStartPlaying) {
								mPlayer = new MediaPlayer();
								Uri uri = Uri.parse("android.resource://"
										+ getPackageName() + "/raw/"
										+ clickBird.getSoundName());
								mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
								mPlayer.setDataSource(getApplicationContext(),
										uri);
								mPlayer.prepare();
								mPlayer.start();
								mPlayer.setOnCompletionListener(new OnCompletionListener() {

									@Override
									public void onCompletion(MediaPlayer mp) {
										mp.release();
									}
								});
							} else {
								if (mPlayer != null) {
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
						} catch (IOException e) {
							Log.e("MP", "prepare() failed");
						}
					}
				});
				// *********************************************************************************************************************
				mViewPager = (HackyViewPager) findViewById(R.id.profiles);
				mViewPager.setOffscreenPageLimit(1);
				ProfilePagerAdapter viewPagerAdapter = new ProfilePagerAdapter();
				mViewPager.setAdapter(viewPagerAdapter);
				mViewPager.setOnPageChangeListener(pageChangeListener);
				calcLayoutWH(mViewPager, 0);
				mViewPager.setSaveEnabled(false);
				scroll.setViewPager(mViewPager);
				mViewPager.invalidate();
			}
		} else if (showAllBirdsTabs != null && showAllBirdsTabs) {

			/*
			 * ((TextView) findViewById(R.id.backTV1)).setTypeface(Fonts
			 * .getTfFont_regular());
			 */
			((TextView) findViewById(R.id.soundTV)).setTypeface(Fonts
					.getTfFont_regular());

			info = findViewById(R.id.info);
			titleBar1 = findViewById(R.id.titleBar1);
			titleBar2 = findViewById(R.id.titleBar2);
			soundView = findViewById(R.id.soundView);
			// backViewD = findViewById(R.id.backView1);
			scroll = (HackyScrollView) findViewById(R.id.scroll);
			description = ((TextView) findViewById(R.id.description));
			infoBar = ((TableLayout) findViewById(R.id.infoBar));
			soundButton = (ImageView) findViewById(R.id.soundButton);
			clickBird = bird;

			/*
			 * backViewD.setOnClickListener(new View.OnClickListener() {
			 * 
			 * public void onClick(View v) { onBackPressed(); } });
			 */
			soundView.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						if (mStartPlaying) {
							mPlayer = new MediaPlayer();
							Uri uri = Uri.parse("android.resource://"
									+ getPackageName() + "/raw/"
									+ clickBird.getSoundName());
							mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
							mPlayer.setDataSource(getApplicationContext(), uri);
							mPlayer.prepare();
							mPlayer.start();
							mPlayer.setOnCompletionListener(new OnCompletionListener() {

								@Override
								public void onCompletion(MediaPlayer mp) {
									mp.release();
								}
							});
						} else {
							if (mPlayer != null) {
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
					} catch (IOException e) {
						Log.e("MP", "prepare() failed");
					}
				}
			});
			// *********************************************************************************************************************
			mViewPager = (HackyViewPager) findViewById(R.id.profiles);
			mViewPager.setOffscreenPageLimit(1);
			ProfilePagerAdapter viewPagerAdapter = new ProfilePagerAdapter();
			viewPagerAdapter.notifyDataSetChanged();
			mViewPager.setAdapter(viewPagerAdapter);
			mViewPager.setOnPageChangeListener(pageChangeListener);
			calcLayoutWH(mViewPager, 0);
			mViewPager.setSaveEnabled(false);
			scroll.setViewPager(mViewPager);
			mViewPager.invalidate();

		}
		// ***********************************Bird
		// Detail*********************************

		btnSearchAllBirds.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				int chance = spinnerChance.getSelectedItemPosition();
				int appears = spinnerAppears.getSelectedItemPosition();
				String searchText = etSearchAllBirds.getText().toString()
						.trim();
				birds = Controller.getSelectedBirds(
						SearchResultBirdDetailTabletActivity.this, 5, 5,
						searchText);
				la.setFiller(birds);
				Utils.hideKeyBoard(SearchResultBirdDetailTabletActivity.this,
						etSearchAllBirds);
				la.notifyDataSetChanged();
				mViewPager.getAdapter().notifyDataSetChanged();
				findViewById(R.id.linear_bird_detail).setVisibility(
						View.INVISIBLE);
				containerListView.setVisibility(View.VISIBLE);
			}
		});

		btnSearch.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				int chance = spinnerChance.getSelectedItemPosition();
				int appears = spinnerAppears.getSelectedItemPosition();
				String searchText = etSearch.getText().toString().trim();
				birds = Controller.getSelectedBirds(
						SearchResultBirdDetailTabletActivity.this, ++chance,
						++appears, searchText);
				la.setFiller(birds);
				Utils.hideKeyBoard(SearchResultBirdDetailTabletActivity.this,
						etSearch);
				la.notifyDataSetChanged();
				mViewPager.getAdapter().notifyDataSetChanged();
				findViewById(R.id.linear_bird_detail).setVisibility(
						View.INVISIBLE);
				containerListView.setVisibility(View.VISIBLE);
			}
		});
		scroll.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				v.getParent().requestDisallowInterceptTouchEvent(false);

				return false;
			}
		});

		findViewById(R.id.in_shadow).setOnTouchListener(
				new View.OnTouchListener() {

					@Override
					public boolean onTouch(View v, MotionEvent event) {

						v.getParent().requestDisallowInterceptTouchEvent(false);

						return false;
					}
				});

		((TextView) findViewById(R.id.meer_informative_content))
				.setOnTouchListener(new View.OnTouchListener() {

					@Override
					public boolean onTouch(View v, MotionEvent event) {

						v.getParent().requestDisallowInterceptTouchEvent(true);

						return false;
					}
				});
	}

	private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
		@Override
		public void onPageScrolled(int i, float v, int i2) {
		}

		@Override
		public void onPageSelected(int index) {

			Log.i("Count", mViewPager.getChildCount() + "");
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
	};

	// *****************************************************Bird
	// Detail************************************
	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		findViewById(R.id.linear_bird_detail).setVisibility(View.VISIBLE);
		pos = position;
		// ViewPager vPager = new ViewPager(this);
		// mViewPager.setAdapter(new ProfilePagerAdapter());

		/*
		 * mViewPager.setOffscreenPageLimit(1); ProfilePagerAdapter
		 * viewPagerAdapter = new ProfilePagerAdapter();
		 * mViewPager.setAdapter(viewPagerAdapter);
		 * mViewPager.setOnPageChangeListener(pageChangeListener);
		 * calcLayoutWH(mViewPager, 0); mViewPager.setSaveEnabled(false);
		 * scroll.setViewPager(mViewPager); mViewPager.invalidate();
		 */
		bird = birds.get(position);
		updateBirdData(position);
	}

	// */
	// ++++++++++++++++++++++++++++++++++++++++++++ Bird Detail
	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
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
		// mViewPager.setCurrentItem(pos);
		// new ProfilePagerAdapter().notifyDataSetChanged();
	}

	public void updateBirdData(int index) {

		// Log.i("Count", mViewPager.getChildCount() + "");
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
		mViewPager.setCurrentItem(index);
		createContent();
	}

	private void createContent() {

		TextView name = (TextView) findViewById(R.id.name);
		TextView category = (TextView) findViewById(R.id.category);
		name.setText(bird.getName());

		String[] chances = getResources().getStringArray(R.array.chance);
		String[] appears = getResources().getStringArray(R.array.appears);
		category.setText(chances[Integer.valueOf(bird.getChance()) - 1] + " | "
				+ appears[bird.getAppears().get(0) - 1]);

		name.setTypeface(Fonts.getTfFont_regular());
		category.setTypeface(Fonts.getTfFont_regular());

		TextView voorkomen_title = (TextView) findViewById(R.id.voorkomen_title);
		voorkomen_title.setText("Voorkomen");
		setTypeFace(voorkomen_title, true);

		TextView voorkomen_content = (TextView) findViewById(R.id.voorkomen_content);
		String text = appears[bird.getAppears().get(0) - 1];
		voorkomen_content.setText(text);
		setTypeFace(voorkomen_content, false);

		TextView aantal_title = (TextView) findViewById(R.id.trefkans_title);
		aantal_title.setText("Trefkans");
		setTypeFace(aantal_title, true);

		TextView aantal_content = (TextView) findViewById(R.id.trefkans_content);
		String chance = chances[Integer.valueOf(bird.getChance()) - 1];
		aantal_content.setText(chance);
		setTypeFace(aantal_content, false);

		TextView broedperiode_title = (TextView) findViewById(R.id.grootte_title);
		broedperiode_title.setText("Grootte");
		setTypeFace(broedperiode_title, true);

		TextView broedperiode_content = (TextView) findViewById(R.id.grootte_content);
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
		meer_con.setText(Html.fromHtml(bird.getMeerInfo()));
		setTypeFace(meer_con, false);
		showMore = true;

		/*
		 * meer_con.setMovementMethod(LinkMovementMethod.getInstance());
		 * meer_con.setOnTouchListener(textViewTouchListener);
		 * meer_con.setClickable(true);
		 */
		/*	meer_con.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d("MEER_TAG", "meer_informative_content_text_Displayed");
				System.out.println("meer_informative_content_text_Displayed");
				Toast.makeText(SearchResultBirdDetailTabletActivity.this,
						"CLiked", Toast.LENGTH_SHORT).show();
			}
		});
*/
		// meer_con.setText(Html.fromHtml(bird.getMeerInfo()));
		/*
		 * meer_con.setOnTouchListener(new View.OnTouchListener() {
		 * 
		 * @Override public boolean onTouch(View view, MotionEvent motionEvent)
		 * { meer_con.getParent().requestDisallowInterceptTouchEvent(true);
		 * return false; } });
		 */
		/*
		 * scroll.setOnTouchListener(new View.OnTouchListener() {
		 * 
		 * @Override public boolean onTouch(View view, MotionEvent motionEvent)
		 * { scroll.getParent().requestDisallowInterceptTouchEvent(false);
		 * return false; } });
		 *//*
			 * final View inShadow = findViewById(R.id.in_shadow);
			 * inShadow.setOnTouchListener(new View.OnTouchListener() {
			 * 
			 * @Override public boolean onTouch(View view, MotionEvent
			 * motionEvent) {
			 * inShadow.getParent().requestDisallowInterceptTouchEvent(false);
			 * return false; } });
			 */

		// if(showMore){
		// inShadow.setVisibility(View.VISIBLE);
		// less.setVisibility(View.GONE);
		// }
	}

	private void textClick(){
		Log.d("MEER_TAG", "meer_informative_content_text_Displayed");
		System.out.println("meer_informative_content_text_Displayed");
		Toast.makeText(SearchResultBirdDetailTabletActivity.this,
				"CLiked", Toast.LENGTH_SHORT).show();
	}
	@Override
	protected void onPause() {
		if (mPlayer != null) {
			try {
				mPlayer.pause();
				mPlayer.release();
			} catch (Exception e) {
				// A TI DUMAL IA BUDU SLEDIT' ZA SOSTOIANIEM PLEERA? DA POSHLO
				// ONO NAHUI
				// - IA UJE CHAS KAK DOMA DOLJEN BIT'
			}
			mPlayer = null;
		}
		super.onPause();
	}

	/*
	 * private OnTouchListener textViewTouchListener= new OnTouchListener() {
	 * 
	 * @Override public boolean onTouch(View v, MotionEvent event) { int action
	 * = event.getAction(); switch (action) { case MotionEvent.ACTION_DOWN:
	 * v.getParent().requestDisallowInterceptTouchEvent(true); break;
	 * 
	 * case MotionEvent.ACTION_MOVE:
	 * v.getParent().requestDisallowInterceptTouchEvent(false); break;
	 * 
	 * case MotionEvent.ACTION_UP:
	 * v.getParent().requestDisallowInterceptTouchEvent(false); break; }
	 * 
	 * v.onTouchEvent(event); return true; } };
	 */private LayoutParams calcLayoutWH(View view, int flag) {
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		LayoutParams rlp = (LayoutParams) view.getLayoutParams();
		if (flag == 1)
			rlp.height = (int) (metrics.heightPixels - (210 * metrics.density));
		// rlp.height = LayoutParams.MATCH_PARENT;
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

	private void click(View hidev, View visiblev) {
		hidev.setVisibility(View.GONE);
		visiblev.setVisibility(View.VISIBLE);
		if (ShowFullInfo) {
			info.setVisibility(View.GONE);
			scroll.setScrollingEnabled(false);

			RelativeLayout imagesContainer = (RelativeLayout) findViewById(R.id.relative_container_images);
			imagesContainer.getLayoutParams().height = 200;
			// showBaseHeader(false);
			// hideFooterMenu();
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
		} else {
			info.setVisibility(View.VISIBLE);
			scroll.setScrollingEnabled(true);
			// showBaseHeader(true);
			// showFooterMenu();
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			calcLayoutWH(mViewPager, 2);
		} else {
			calcLayoutWH(mViewPager, ShowFullInfo ? 1 : 0);
		}
		ShowFullInfo = !ShowFullInfo;
	}

	// ***********************************************************************************************************************************
	private class ProfilePagerAdapter extends PagerAdapter {

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			LayoutInflater inflater = LayoutInflater.from(container
					.getContext());
			View view = inflater.inflate(R.layout.viewpager_container, null);

			final ImageView fullScreenIV = (ImageView) view
					.findViewById(R.id.full_screen);
			final ImageView image = (ImageView) view
					.findViewById(R.id.image_small);
			final ImageView image_large = (ImageView) view
					.findViewById(R.id.image_large);

			fullScreenIV.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (v.getTag().toString().equals("small")) {
						click(image, image_large);
						// fullScreenIV.setVisibility(View.VISIBLE);
						v.setTag("large");
					} else {
						click(image_large, image);
						v.setTag("small");
					}
				}
			});

			bird = birds.get(position);
			Uri uri = Uri.parse("android.resource://" + getPackageName()
					+ "/drawable/if" + bird.getImageName());
			image.setImageURI(uri);
			// uri = Uri.parse("android.resource://"+ getPackageName() +
			// "/drawable/if" + bird.getImageName());
			image_large.setImageURI(uri);

			container.addView(view);
			// notifyDataSetChanged();
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

	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++Bird Detail
	// MemberFunctions++++++++++++++++++++++++++++

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (spinnerChance.hasBeenOpened() && hasFocus) {
			spinnerChance.performClosedEvent();
		}
	}

	class CustomAdapter extends ArrayAdapter<String> {
		View v;

		public CustomAdapter(Context context, int resource, String[] objects) {
			super(context, resource, objects);
		}

		public void setView(View view) {
			v = view;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			return createViewFromResource(position, convertView, parent,
					android.R.layout.simple_spinner_dropdown_item);
		}

		private View createViewFromResource(int position, View convertView,
				ViewGroup parent, int resource) {
			View view;
			TextView text;
			view = v;
			text = (TextView) view;
			String item = getItem(position);
			if (item instanceof CharSequence) {
				text.setText((CharSequence) item);
			} else {
				text.setText(item.toString());
			}

			return view;
		}
	}

	private void hideSearchViews() {
		findViewById(R.id.filterContainer).setVisibility(View.GONE);
		spinnerAppears.setVisibility(View.GONE);
		spinnerChance.setVisibility(View.GONE);
		btnSearch.setVisibility(View.GONE);
	}

	private void showBackButton(boolean isShow) {
		if (isShow)
			backView.setVisibility(View.VISIBLE);
		else
			backView.setVisibility(View.GONE);
	}

	private TextWatcher filterTextWatcher = new TextWatcher() {
		@Override
		public void afterTextChanged(Editable s) {
		}

		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			if (la != null)
				la.getFilter().filter(s);
		}
	};
}
