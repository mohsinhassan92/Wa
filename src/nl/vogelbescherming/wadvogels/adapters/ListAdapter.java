package nl.vogelbescherming.wadvogels.adapters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import nl.vogelbescherming.wadvogels.BaseActivity;
import nl.vogelbescherming.wadvogels.BirdDetailActivity;
import nl.vogelbescherming.wadvogels.R;
import nl.vogelbescherming.wadvogels.SearchResultBirdDetailTabletActivity;
import nl.vogelbescherming.wadvogels.fonts.Fonts;
import nl.vogelbescherming.wadvogels.model.Bird;
import nl.vogelbescherming.wadvogels.util.HackyScrollView;
import nl.vogelbescherming.wadvogels.util.HackyViewPager;
import nl.vogelbescherming.wadvogels.view.Scrollable;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

public class ListAdapter extends BaseAdapter implements Filterable, Scrollable {

	private Context mContext;
	private LayoutInflater mInflater;
	private ArrayList<Bird> mContent;
	private ArrayList<Bird> mFilteredBirds;
	private int mGridItemLayout;

	// Bird Detail Activity
	private Bird birdN;
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
	private ArrayList<Bird> birdsD;
	private HackyScrollView scroll;
	private View info;
	private View titleBar1, titleBar2, soundView, backViewD;

	public ListAdapter(Context cntxt, int gridItemLayout,
			ArrayList<Bird> fillerList) {
		mContext = cntxt;
		mGridItemLayout = gridItemLayout;
		mInflater = LayoutInflater.from(cntxt);
		mContent = fillerList;
		mFilteredBirds = mContent;
	}

	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++Bird Detail Activity+++++++++++++++++++++++++++++
	private void showHeader(boolean show) {
		if (show) {
			titleBar1.setVisibility(View.VISIBLE);
			titleBar2.setVisibility(View.VISIBLE);
			// showBaseHeader(true);
		} else {
			titleBar1.setVisibility(View.GONE);
			titleBar2.setVisibility(View.GONE);
			// showBaseHeader(false);
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

	// +++++++++++++++++++++++++++++++++++++++++++++++Bird Detail Activity++++++++++++++++++++++++++++++

	@Override
	public int getCount() {
		return mFilteredBirds.size();
	}

	@Override
	public Bird getItem(int position) {
		return mFilteredBirds.get(position);
	}

	@Override
	public long getItemId(int position) {
		return getItem(position).getId();
	}

	static class ViewHolder {

		ImageView iv;
		TextView text;
		TextView subtext;
		View itemContainer;
	}

	@Override
	public View getView(final int position, View rootView, ViewGroup parent) {
		ViewHolder viewHolder;

		final Bird bird = getItem(position);

		if (rootView == null) {
			viewHolder = new ViewHolder();
			rootView = mInflater.inflate(mGridItemLayout, null);
			rootView.setTag(viewHolder);

			viewHolder.iv = (ImageView) rootView.findViewById(R.id.image);
			viewHolder.text = (TextView) rootView.findViewById(R.id.text);
			viewHolder.subtext = (TextView) rootView.findViewById(R.id.subtext);
			viewHolder.itemContainer = (View) rootView
					.findViewById(R.id.layoutItem);

		} else
			viewHolder = (ViewHolder) rootView.getTag();

		if (position % 2 == 0) {
			viewHolder.itemContainer.setBackgroundColor(mContext.getResources()
					.getColor(R.color.light_grey_list_item));
		} else {
			viewHolder.itemContainer.setBackgroundColor(mContext.getResources()
					.getColor(R.color.white));
		}

		Log.i("ImageName", "ImageName - " + bird.getImageName());
		Uri uri = Uri.parse("android.resource://" + mContext.getPackageName()
				+ "/drawable/ir" + bird.getImageName());
		viewHolder.iv.setImageURI(uri);
		viewHolder.iv.invalidate();
		viewHolder.text.setText(bird.getName());
		viewHolder.text.setTypeface(Fonts.getTfFont());

		String[] chances = mContext.getResources().getStringArray(
				R.array.chance);
		String[] appears = mContext.getResources().getStringArray(
				R.array.appears);

		String text = appears[bird.getAppears().get(0) - 1] + " | "
				+ chances[Integer.valueOf(bird.getChance()) - 1];

		// viewHolder.subtext.setSelected(true);
		viewHolder.subtext.setText(text);
		viewHolder.subtext.setTypeface(Fonts.getTfFont_italic());
		if (!BaseActivity.isTablet(mContext)) {

			rootView.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {

					Intent i = new Intent(mContext, BirdDetailActivity.class);
					i.putExtra("CurrentBird", bird);
					i.putExtra("Birds", mFilteredBirds);
					i.putExtra("Pos", position);
					i.putExtra("Caller", mContext.toString());
					mContext.startActivity(i);
					/*
					 * }else{ Intent i = new Intent(mContext,
					 * SearchResultBirdDetailTabletActivity.class);
					 * i.putExtra("CurrentBird", bird); i.putExtra("Birds",
					 * mFilteredBirds); i.putExtra("Pos", position);
					 * i.putExtra("Caller", mContext.toString());
					 * mContext.startActivity(i);
					 */
				}

			});
		}
		return rootView;
	}

	@Override
	public Filter getFilter() {
		return new BirdFilter();
	}

	private class BirdFilter extends Filter {
		@Override
		protected FilterResults performFiltering(CharSequence constrants) {
			String s = constrants.toString().toLowerCase();

			final List<Bird> filtered = new LinkedList<Bird>();

			for (Bird bird : mContent) {
				if (bird.getName().toLowerCase().contains(s)) {
					filtered.add(bird);
				}
			}

			FilterResults results = new FilterResults();
			results.count = filtered.size();
			results.values = filtered;
			return results;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {
			mFilteredBirds = new ArrayList<Bird>((List<Bird>) results.values);
			notifyDataSetInvalidated();
		}
	}

	public void setFiller(ArrayList<Bird> filler) {
		mContent = filler;
		mFilteredBirds = mContent;
	}

	public ArrayList<Bird> getFiller() {
		return mFilteredBirds;
	}

	@Override
	public String getIndicatorForPosition(int childposition, int groupposition) {
		return mFilteredBirds.get(childposition).getIndicator();
	}

	@Override
	public int getScrollPosition(int childposition, int groupposition) {
		return childposition;
	}

	// ++++++++++++++++++++++++++++++++++++++++++++BirdDetail+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	public void onConfigurationChanged(Configuration newConfig) {
		// super.onConfigurationChanged(newConfig);
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			showHeader(false);
			calcLayoutWH(mViewPager, 2);
		} else {
			showHeader(true);
			calcLayoutWH(mViewPager, 1);
		}
	}

	/*
	 * @Override protected void onResume() { super.onResume(); int t =
	 * birdsD.indexOf(bird); if (t != -1) pos = t;
	 * mViewPager.setCurrentItem(pos); }
	 */private void createContent() {

		TextView name = (TextView) ((Activity) mContext)
				.findViewById(R.id.name);
		TextView category = (TextView) ((Activity) mContext)
				.findViewById(R.id.category);
		name.setText(birdN.getName());

		String[] chances = mContext.getResources().getStringArray(
				R.array.chance);
		String[] appears = mContext.getResources().getStringArray(
				R.array.appears);
		category.setText(chances[Integer.valueOf(birdN.getChance()) - 1]
				+ " | " + appears[birdN.getAppears().get(0) - 1]);

		name.setTypeface(Fonts.getTfFont_regular());
		category.setTypeface(Fonts.getTfFont_regular());

		TextView voorkomen_title = (TextView) ((Activity) mContext)
				.findViewById(R.id.voorkomen_title);
		voorkomen_title.setText("Voorkomen");
		setTypeFace(voorkomen_title, true);

		TextView voorkomen_content = (TextView) ((Activity) mContext)
				.findViewById(R.id.voorkomen_content);
		String text = appears[birdN.getAppears().get(0) - 1];
		voorkomen_content.setText(text);
		setTypeFace(voorkomen_content, false);

		TextView aantal_title = (TextView) ((Activity) mContext)
				.findViewById(R.id.trefkans_title);
		aantal_title.setText("Trefkans");
		setTypeFace(aantal_title, true);

		TextView aantal_content = (TextView) ((Activity) mContext)
				.findViewById(R.id.trefkans_content);
		String chance = chances[Integer.valueOf(birdN.getChance()) - 1];
		aantal_content.setText(chance);
		setTypeFace(aantal_content, false);

		TextView broedperiode_title = (TextView) ((Activity) mContext)
				.findViewById(R.id.grootte_title);
		broedperiode_title.setText("Grootte");
		setTypeFace(broedperiode_title, true);

		TextView broedperiode_content = (TextView) ((Activity) mContext)
				.findViewById(R.id.grootte_content);
		broedperiode_content.setText(birdN.getGrootte());
		setTypeFace(broedperiode_content, false);

		TextView description = (TextView) ((Activity) mContext)
				.findViewById(R.id.description);
		description.setText(birdN.getDescription());
		setTypeFace(description, false);

		TextView snavel_title = (TextView) ((Activity) mContext)
				.findViewById(R.id.snavel_title);
		setTypeFace(snavel_title, true);

		TextView snavel_con = (TextView) ((Activity) mContext)
				.findViewById(R.id.snavel_content);
		snavel_con.setText(birdN.getSnavel());
		setTypeFace(snavel_con, false);

		TextView poten_title = (TextView) ((Activity) mContext)
				.findViewById(R.id.poten_title);
		setTypeFace(poten_title, true);

		TextView poten_con = (TextView) ((Activity) mContext)
				.findViewById(R.id.poten_content);
		poten_con.setText(birdN.getPoten());
		setTypeFace(poten_con, false);

		TextView opvallende_title = (TextView) ((Activity) mContext)
				.findViewById(R.id.opvallende_kenmerken_title);
		setTypeFace(opvallende_title, true);

		TextView opvallende_con = (TextView) ((Activity) mContext)
				.findViewById(R.id.opvallende_kenmerken_content);
		opvallende_con.setText(birdN.getOpvallende());
		setTypeFace(opvallende_con, false);

		TextView gedrag_title = (TextView) ((Activity) mContext)
				.findViewById(R.id.gedrag_title);
		setTypeFace(gedrag_title, true);

		TextView gedrag_con = (TextView) ((Activity) mContext)
				.findViewById(R.id.gedrag_content);
		gedrag_con.setText(birdN.getGedrag());
		setTypeFace(gedrag_con, false);

		TextView voedsel_title = (TextView) ((Activity) mContext)
				.findViewById(R.id.voedsel_title);
		setTypeFace(voedsel_title, true);

		TextView voedsel_con = (TextView) ((Activity) mContext)
				.findViewById(R.id.voedsel_content);
		voedsel_con.setText(birdN.getVoedsel());
		setTypeFace(voedsel_con, false);

		TextView leefgebied_title = (TextView) ((Activity) mContext)
				.findViewById(R.id.leefgebied_title);
		setTypeFace(leefgebied_title, true);

		TextView leefgebied_con = (TextView) ((Activity) mContext)
				.findViewById(R.id.leefgebied_content);
		leefgebied_con.setText(birdN.getLeefgebied());
		setTypeFace(leefgebied_con, false);

		TextView verspreiding_title = (TextView) ((Activity) mContext)
				.findViewById(R.id.verspreiding_in_europa_title);
		setTypeFace(verspreiding_title, true);

		TextView verspreiding_con = (TextView) ((Activity) mContext)
				.findViewById(R.id.verspreiding_in_europa_content);
		verspreiding_con.setText(birdN.getVerspreiding());
		setTypeFace(verspreiding_con, false);

		TextView engelse_title = (TextView) ((Activity) mContext)
				.findViewById(R.id.engelse_naam_title);
		setTypeFace(engelse_title, true);

		TextView engelse_con = (TextView) ((Activity) mContext)
				.findViewById(R.id.engelse_naam_content);
		engelse_con.setText(birdN.getEngelse());
		setTypeFace(engelse_con, false);

		TextView latin_title = (TextView) ((Activity) mContext)
				.findViewById(R.id.latijnse_naam_title);
		setTypeFace(latin_title, true);

		TextView latin_con = (TextView) ((Activity) mContext)
				.findViewById(R.id.latijnse_naam_content);
		latin_con.setText(birdN.getLatijnse());
		setTypeFace(latin_con, false);

		TextView meer_title = (TextView) ((Activity) mContext)
				.findViewById(R.id.meer_informative_title);
		setTypeFace(meer_title, true);

		TextView meer_con = (TextView) ((Activity) mContext)
				.findViewById(R.id.meer_informative_content);
		// meer_con.setMovementMethod(LinkMovementMethod.getInstance());
		meer_con.setText(Html.fromHtml(birdN.getMeerInfo()));
		// meer_con.setText(bird.getMeerInfo());

		setTypeFace(meer_con, false);

		final View inShadow = ((Activity) mContext)
				.findViewById(R.id.in_shadow);
		showMore = true;
		// if(showMore){
		// inShadow.setVisibility(View.VISIBLE);
		// less.setVisibility(View.GONE);
		// }

	}

	/*
	 * @Override protected void onPause() { if (mPlayer != null) { try {
	 * mPlayer.pause(); mPlayer.release(); } catch (Exception e) { // A TI DUMAL
	 * IA BUDU SLEDIT' ZA SOSTOIANIEM PLEERA? DA POSHLO // ONO NAHUI // - IA UJE
	 * CHAS KAK DOMA DOLJEN BIT' } mPlayer = null; } super.onPause(); }
	 */private LayoutParams calcLayoutWH(View view, int flag) {
		DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
		LayoutParams rlp = (LayoutParams) view.getLayoutParams();
		if (flag == 1)
			rlp.height = (int) (metrics.heightPixels - (155 * metrics.density));
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
			// showBaseHeader(false);
			// hideFooterMenu();
			((Activity) mContext)
					.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
		} else {
			info.setVisibility(View.VISIBLE);
			scroll.setScrollingEnabled(true);
			// showBaseHeader(true);
			// showFooterMenu();
			((Activity) mContext)
					.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}
		if (mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
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
					if (fullScreenIV.getTag().toString().equals("small")) {
						click(image, image_large);
						v.setTag("large");
					} else {
						click(image_large, image);
						v.setTag("small");
					}
				}
			});

			birdN = mFilteredBirds.get(position);
			Uri uri = Uri.parse("android.resource://"
					+ mContext.getPackageName() + "/drawable/if"
					+ birdN.getImageName());
			image.setImageURI(uri);
			// uri = Uri.parse("android.resource://"+ getPackageName() +
			// "/drawable/if" + bird.getImageName());
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
			return mFilteredBirds.size();
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

}
