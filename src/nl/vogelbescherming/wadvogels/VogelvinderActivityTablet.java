package nl.vogelbescherming.wadvogels;

import java.util.ArrayList;
import java.util.List;

import nl.vogelbescherming.wadvogels.fonts.Fonts;
import nl.vogelbescherming.wadvogels.view.CircleImageView;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class VogelvinderActivityTablet extends Activity {

	// ---------------------Attributes
	private List<Integer> positionGrootte = new ArrayList<Integer>();
	private int positionSilhoute = -1;
	private int positionSnavel = -1;
	private List<Integer> positionKleur = new ArrayList<Integer>();

	private int TABLE_ITEM_NUMBER_GROOTTE = 4;
	private int TABLE_ITEM_NUMBER_SILHOUTE = 9;
	private int TABLE_ITEM_NUMBER_SNAVEL = 9;
	private int TABLE_ITEM_NUMBER_KLEUR = 9;

	private List<ViewHelperTab> viewHelperGrootte = new ArrayList<ViewHelperTab>();
	private List<ViewHelperTab> viewHelperSilhoute = new ArrayList<ViewHelperTab>();
	private List<ViewHelperTab> viewHelperSnavel = new ArrayList<ViewHelperTab>();
	private List<ViewHelperTab> viewHelperKleur = new ArrayList<ViewHelperTab>();

	private List<View> viewListGrootte = new ArrayList<View>();
	private List<View> viewListGrootteSelection = new ArrayList<View>();
	private List<View> viewListSilhoute = new ArrayList<View>();
	private List<View> viewListSnavel = new ArrayList<View>();
	private List<View> viewListKleur = new ArrayList<View>();
	private List<View> viewListKleurSelection = new ArrayList<View>();

	private List<Drawable> listGrootte = new ArrayList<Drawable>();
	private List<Drawable> listGrootteActive = new ArrayList<Drawable>();
	private List<Drawable> listGrootteInActive = new ArrayList<Drawable>();
	private List<Drawable> listSilhoute = new ArrayList<Drawable>();
	private List<Drawable> listSilhouteActive = new ArrayList<Drawable>();
	private List<Drawable> listSilhouteInActive = new ArrayList<Drawable>();
	private List<Drawable> listSnavel = new ArrayList<Drawable>();
	private List<Drawable> listSnavelActive = new ArrayList<Drawable>();
	private List<Drawable> listSnavelInActive = new ArrayList<Drawable>();
	private List<Drawable> listKleur = new ArrayList<Drawable>();
	private List<Drawable> listKleurActive = new ArrayList<Drawable>();

	// ----------------------Methods

	// ********************Grootte Methods******************
	private List<Drawable> createListGrootte() {
		List<Drawable> temp = new ArrayList<Drawable>(TABLE_ITEM_NUMBER_GROOTTE);
		temp.add(getResources().getDrawable(R.drawable.black_bird_4));
		temp.add(getResources().getDrawable(R.drawable.bird_on_bt_2));
		temp.add(getResources().getDrawable(R.drawable.bird_on_bt_3));
		temp.add(getResources().getDrawable(R.drawable.bird_on_bt_4));
		return temp;
	}

	private List<Drawable> createListGrootteActive() {
		List<Drawable> temp = new ArrayList<Drawable>(TABLE_ITEM_NUMBER_GROOTTE);
		temp.add(getResources().getDrawable(R.drawable.black_bird_4_active));
		temp.add(getResources().getDrawable(R.drawable.bird_on_bt_2_active));
		temp.add(getResources().getDrawable(R.drawable.bird_on_bt_3_active));
		temp.add(getResources().getDrawable(R.drawable.bird_on_bt_4_active));
		return temp;
	}

	private List<Drawable> createListGrootteInActive() {
		List<Drawable> temp = new ArrayList<Drawable>(TABLE_ITEM_NUMBER_GROOTTE);
		temp.add(getResources().getDrawable(R.drawable.bird_on_bt_1_inactive));
		temp.add(getResources().getDrawable(R.drawable.bird_on_bt_2_inactive));
		temp.add(getResources().getDrawable(R.drawable.bird_on_bt_3_inactive));
		temp.add(getResources().getDrawable(R.drawable.bird_on_bt_4_inactive));
		return temp;
	}

	// ********************Silhoute Methods******************

	private List<Drawable> createListSilhoute() {
		List<Drawable> temp = new ArrayList<Drawable>(
				TABLE_ITEM_NUMBER_SILHOUTE);
		temp.add(getResources().getDrawable(R.drawable.black_bird_1));
		temp.add(getResources().getDrawable(R.drawable.black_bird_2));
		temp.add(getResources().getDrawable(R.drawable.black_bird_3));
		temp.add(getResources().getDrawable(R.drawable.black_bird_4));
		temp.add(getResources().getDrawable(R.drawable.black_bird_5));
		temp.add(getResources().getDrawable(R.drawable.black_bird_6));
		temp.add(getResources().getDrawable(R.drawable.black_bird_7));
		temp.add(getResources().getDrawable(R.drawable.black_bird_8));
		temp.add(getResources().getDrawable(R.drawable.black_bird_9));
		return temp;
	}

	private List<Drawable> createListSilhouteActive() {
		List<Drawable> temp = new ArrayList<Drawable>(
				TABLE_ITEM_NUMBER_SILHOUTE);
		temp.add(getResources().getDrawable(R.drawable.black_bird_1_active));
		temp.add(getResources().getDrawable(R.drawable.black_bird_2_active));
		temp.add(getResources().getDrawable(R.drawable.black_bird_3_active));
		temp.add(getResources().getDrawable(R.drawable.black_bird_4_active));
		temp.add(getResources().getDrawable(R.drawable.black_bird_5_active));
		temp.add(getResources().getDrawable(R.drawable.black_bird_6_active));
		temp.add(getResources().getDrawable(R.drawable.black_bird_7_active));
		temp.add(getResources().getDrawable(R.drawable.black_bird_8_active));
		temp.add(getResources().getDrawable(R.drawable.black_bird_9_active));
		return temp;
	}

	private List<Drawable> createListSilhouteInActive() {
		List<Drawable> temp = new ArrayList<Drawable>(
				TABLE_ITEM_NUMBER_SILHOUTE);
		temp.add(getResources().getDrawable(R.drawable.black_bird_1_inactive));
		temp.add(getResources().getDrawable(R.drawable.black_bird_2_inactive));
		temp.add(getResources().getDrawable(R.drawable.black_bird_3_inactive));
		temp.add(getResources().getDrawable(R.drawable.black_bird_4_inactive));
		temp.add(getResources().getDrawable(R.drawable.black_bird_5_inactive));
		temp.add(getResources().getDrawable(R.drawable.black_bird_6_inactive));
		temp.add(getResources().getDrawable(R.drawable.black_bird_7_inactive));
		temp.add(getResources().getDrawable(R.drawable.black_bird_8_inactive));
		temp.add(getResources().getDrawable(R.drawable.black_bird_9_inactive));
		return temp;
	}

	// ********************Snavel Methods******************

	private List<Drawable> createListSnavel() {
		List<Drawable> temp = new ArrayList<Drawable>(TABLE_ITEM_NUMBER_SNAVEL);
		temp.add(getResources().getDrawable(R.drawable.nose1));
		temp.add(getResources().getDrawable(R.drawable.nose2));
		temp.add(getResources().getDrawable(R.drawable.nose3));
		temp.add(getResources().getDrawable(R.drawable.nose4));
		temp.add(getResources().getDrawable(R.drawable.nose5));
		temp.add(getResources().getDrawable(R.drawable.nose6));
		temp.add(getResources().getDrawable(R.drawable.nose7));
		temp.add(getResources().getDrawable(R.drawable.nose8));
		temp.add(getResources().getDrawable(R.drawable.nose9));
		return temp;
	}

	private List<Drawable> createListSnavelActive() {
		List<Drawable> temp = new ArrayList<Drawable>(TABLE_ITEM_NUMBER_SNAVEL);
		temp.add(getResources().getDrawable(R.drawable.nose1_active));
		temp.add(getResources().getDrawable(R.drawable.nose2_active));
		temp.add(getResources().getDrawable(R.drawable.nose3_active));
		temp.add(getResources().getDrawable(R.drawable.nose4_active));
		temp.add(getResources().getDrawable(R.drawable.nose5_active));
		temp.add(getResources().getDrawable(R.drawable.nose6_active));
		temp.add(getResources().getDrawable(R.drawable.nose7_active));
		temp.add(getResources().getDrawable(R.drawable.nose8_active));
		temp.add(getResources().getDrawable(R.drawable.nose9_active));
		return temp;
	}

	private List<Drawable> createListSnavelInActive() {
		List<Drawable> temp = new ArrayList<Drawable>(TABLE_ITEM_NUMBER_SNAVEL);
		temp.add(getResources().getDrawable(R.drawable.nose1_inactive));
		temp.add(getResources().getDrawable(R.drawable.nose2_inactive));
		temp.add(getResources().getDrawable(R.drawable.nose3_inactive));
		temp.add(getResources().getDrawable(R.drawable.nose4_inactive));
		temp.add(getResources().getDrawable(R.drawable.nose5_inactive));
		temp.add(getResources().getDrawable(R.drawable.nose6_inactive));
		temp.add(getResources().getDrawable(R.drawable.nose7_inactive));
		temp.add(getResources().getDrawable(R.drawable.nose8_inactive));
		temp.add(getResources().getDrawable(R.drawable.nose9_inactive));
		return temp;
	}

	// ********************Kleur Methods******************
	private List<Drawable> createListKleur() {
		List<Drawable> temp = new ArrayList<Drawable>(TABLE_ITEM_NUMBER_KLEUR);
		temp.add(getResources().getDrawable(R.drawable.color_1_black));
		temp.add(getResources().getDrawable(R.drawable.color_2_white));
		temp.add(getResources().getDrawable(R.drawable.color_3_grey));
		temp.add(getResources().getDrawable(R.drawable.color_4_yellow));
		temp.add(getResources().getDrawable(R.drawable.color_5_orange));
		temp.add(getResources().getDrawable(R.drawable.color_6_red));
		temp.add(getResources().getDrawable(R.drawable.color_7_greyishblue));
		temp.add(getResources().getDrawable(R.drawable.color_8_green));
		temp.add(getResources().getDrawable(R.drawable.color_9_brown));
		return temp;
	}

	private List<Drawable> createListKleurActive() {
		List<Drawable> temp = new ArrayList<Drawable>(TABLE_ITEM_NUMBER_KLEUR);
		temp.add(getResources().getDrawable(R.drawable.color_1_black_active));
		temp.add(getResources().getDrawable(R.drawable.color_2_white_active));
		temp.add(getResources().getDrawable(R.drawable.color_3_grey_active));
		temp.add(getResources().getDrawable(R.drawable.color_4_yellow_active));
		temp.add(getResources().getDrawable(R.drawable.color_5_orange_active));
		temp.add(getResources().getDrawable(R.drawable.color_6_red_active));
		temp.add(getResources().getDrawable(
				R.drawable.color_7_greyishblue_active));
		temp.add(getResources().getDrawable(R.drawable.color_8_green_active));
		temp.add(getResources().getDrawable(R.drawable.color_9_brown_active));
		return temp;
	}

	// ------------------------OnCreate
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.base_tab);
		findViewById(R.id.relative_footer).setVisibility(View.GONE);
		findViewById(R.id.container_vogelvinder_tab)
				.setVisibility(View.VISIBLE);
		listGrootte = createListGrootte();
		listGrootteActive = createListGrootteActive();
		listGrootteInActive = createListGrootteInActive();

		listSilhoute = createListSilhoute();
		listSilhouteActive = createListSilhouteActive();
		listSilhouteInActive = createListSilhouteInActive();

		listSnavel = createListSnavel();
		listSnavelActive = createListSnavelActive();
		listSnavelInActive = createListSnavelInActive();

		listKleur = createListKleur();
		listKleurActive = createListKleurActive();

		setTextGrooteSilhouteSnavelkleur();
		invisible_silhoute_snavel_kleur();
		// Binding of Grootte Listener
		setGrooteListener();
		// Binding of Silhoute Listener
		setSilhouteListener();
		// Binding of Snavel Listener
		setSnavelListener();
		// Binding of Kleur Listener
		setKleurListener();

		setSelectieWissenListener();
	}
	
	private void setSelectieWissenListener(){
		View view_btn=findViewById(R.id.button_vorige);
		view_btn.setOnClickListener(selectieWissenListener);
	
	}
	
	private OnClickListener selectieWissenListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
		
			for(int counter=0;counter<viewHelperGrootte.size();counter++)
			{
				viewHelperGrootte.get(counter).setSelected(false);
			}
			viewListGrootteSelection.clear();
			positionGrootte.clear();
			
			for(int counter=0;counter<viewHelperSilhoute.size();counter++)
			{
				viewHelperSilhoute.get(counter).setSelected(false);
			}
			positionSilhoute=-1;
			
			for(int counter=0;counter<viewHelperSnavel.size();counter++)
			{
				viewHelperSnavel.get(counter).setSelected(false);
			}
			positionSnavel=-1;
			
			for(int counter=0;counter<viewHelperKleur.size();counter++)
			{
				viewHelperKleur.get(counter).setSelected(false);
			}
			viewListKleurSelection.clear();
			positionKleur.clear();
			
			for(int counter=0;counter<viewListGrootte.size();counter++)
			{
				unSelectGrootteAll(viewListGrootte.get(counter), viewHelperGrootte.get(counter), counter);
			}
			
			for(int counter=0;counter<viewListSilhoute.size();counter++)
			{
				unSelectSilhouteAll(viewListSilhoute.get(counter), viewHelperSilhoute.get(counter), counter);
			}
			for(int counter=0;counter<viewListSnavel.size();counter++)
			{
				unSelectSnavelAll(viewListSnavel.get(counter), viewHelperSnavel.get(counter), counter);
			}
			for(int counter=0;counter<viewListKleur.size();counter++)
			{
				unSelectKleurAll(viewListKleur.get(counter), viewHelperKleur.get(counter), counter);
			}
			findViewById(R.id.container_vogelvinder_tab)
			.setVisibility(View.VISIBLE);
			setTextGrooteSilhouteSnavelkleur();
			visible_grootte();
			
		//	visible_silhoute();
		//	visible_snavel();
		//	visible_kleur();

			invisible_silhoute_snavel_kleur();
			findViewById(R.id.button_vorige).setBackgroundColor(
					getResources().getColor(R.color.inactive_button_color));	
		}
	};

	
	

	//*/
	private OnClickListener grootteListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			int position = (Integer) v.getTag();
			Boolean flag = false;
			View iv = v;
			View iv_temp;
			ViewHelperTab viewhelpGrootte = ((ViewHelperTab) viewHelperGrootte
					.get(position));
			if (viewhelpGrootte.getSelected() == false) {
				visible_silhoute();
				for (int i = 0; i < viewHelperGrootte.size(); i++) {
					if (viewHelperGrootte.get(i).getSelected() == true) {
						flag = true;
					}
				}
				if (flag == false) {
					viewListGrootteSelection.clear();
					positionGrootte.clear();
				}
				if (viewListGrootteSelection.size() < 2) {
					viewListGrootteSelection.add(v);
					positionGrootte.add(position);
				} else if (viewListGrootteSelection.size() >= 2
						&& viewListGrootteSelection != null
						&& (viewListGrootteSelection.get(0) != v && viewListGrootteSelection
								.get(1) != v)) {
					View view = viewListGrootteSelection.get(0);
					ViewHelperTab viewhelpGrootteTemp = viewHelperGrootte
							.get((Integer) view.getTag());
					view.setBackgroundResource(R.drawable.cell);

					viewhelpGrootteTemp.getImageView().setImageDrawable(
							listGrootte.get(positionGrootte.get(0)));
					TextView text = (TextView) viewhelpGrootteTemp
							.getTextView();
					text.setTextColor(getResources().getColor(
							R.color.inactive_button_color));
					viewhelpGrootteTemp.setSelected(true);
					positionGrootte.remove(0);
					viewListGrootteSelection.remove(0);
					positionGrootte.add(position);
					viewListGrootteSelection.add(v);
				}

				for (int i = 0; i < viewHelperGrootte.size(); i++) {
					unSelectGrootteAll(viewListGrootte.get(i),
							viewHelperGrootte.get(i), i);
				}

				for (int i = 0; i < viewListGrootteSelection.size(); i++) {
					selectGrootte(viewListGrootteSelection.get(i), i);
				}
				findViewById(R.id.button_vorige).setBackgroundColor(getResources().getColor(R.color.active_button_color));
			} else {
				unSelectGrootte(iv, position);
		/*		if (positionGrootte.size() < 1) {
					findViewById(R.id.container_items_silhoute).setVisibility(
							View.INVISIBLE);
					findViewById(R.id.container_items_snavel).setVisibility(
							View.INVISIBLE);
					findViewById(R.id.container_items_kleur).setVisibility(
							View.INVISIBLE);
				}*/
			}
		}
	};
	// */
	private OnClickListener silhouteListener = new OnClickListener() {
		@Override
		public void onClick(View v) {

			int position = (Integer) v.getTag();
			View iv = v;
			View iv_temp;
			ViewHelperTab viewhelpSilhoute = ((ViewHelperTab) viewHelperSilhoute
					.get(position));
			if (viewhelpSilhoute.getSelected() == false) {
				for (int i = 0; i < viewListSilhoute.size(); i++) {
					iv_temp = viewListSilhoute.get(i);
					iv_temp.setBackgroundResource(R.drawable.cell);
					iv_temp.setBackgroundColor(getResources().getColor(
							R.color.light_grey_list_item));
					viewHelperSilhoute.get(i).getImageView()
							.setImageDrawable(listSilhoute.get(i));
					TextView text = (TextView) viewHelperSilhoute.get(i)
							.getTextView();
					text.setTextColor(getResources().getColor(
							R.color.inactive_button_color));
					viewHelperSilhoute.get(i).setSelected(false);
				}
				iv.setBackgroundResource(R.drawable.cell_select);
				viewhelpSilhoute.getImageView().setImageDrawable(
						listSilhouteActive.get(position));
				TextView text = (TextView) viewhelpSilhoute.getTextView();
				text.setTextColor(Color.WHITE);
				viewhelpSilhoute.setSelected(true);
				positionSilhoute = position;
				visible_snavel();
			} else {
				unSelectSilhoute(v, position);
			}
		}
	};
	private OnClickListener snavelListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			int position = (Integer) v.getTag();
			View iv = v;
			View iv_temp;
			ViewHelperTab viewhelpSnavel = ((ViewHelperTab) viewHelperSnavel
					.get(position));
			if (viewhelpSnavel.getSelected() == false) {
				for (int i = 0; i < viewListSnavel.size(); i++) {
					iv_temp = viewListSnavel.get(i);
					iv_temp.setBackgroundResource(R.drawable.cell);
					viewHelperSnavel.get(i).getImageView()
							.setImageDrawable(listSnavel.get(i));
					TextView text = (TextView) viewHelperSnavel.get(i)
							.getTextView();
					text.setTextColor(getResources().getColor(
							R.color.inactive_button_color));
					viewHelperSnavel.get(i).setSelected(false);
				}
				iv.setBackgroundResource(R.drawable.cell_select);
				viewhelpSnavel.getImageView().setImageDrawable(
						listSnavelActive.get(position));
				TextView text = (TextView) viewhelpSnavel.getTextView();
				text.setTextColor(Color.WHITE);
				viewhelpSnavel.setSelected(true);
				positionSnavel = position;
				visible_kleur();

			} else {
				unSelectSnavel(v, position);
			}
		}
	};
	private OnClickListener kleurListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			int position = (Integer) v.getTag();
			Boolean flag = false;
			View iv = v;
			View iv_temp;
			ViewHelperTab viewhelpKleur = ((ViewHelperTab) viewHelperKleur
					.get(position));
			if (viewhelpKleur.getSelected() == false) {
				

				for (int i = 0; i < viewHelperKleur.size(); i++) {
					if (viewHelperKleur.get(i).getSelected() == true) {
						flag = true;
					}
				}
				if (flag == false) {
					viewListKleurSelection.clear();
					positionKleur.clear();
				}
				if (viewListKleurSelection.size() < 3) {
					viewListKleurSelection.add(v);
					positionKleur.add(position);
				} else if (viewListKleurSelection.size() >= 3
						&& viewListKleurSelection != null
						&& (viewListKleurSelection.get(0) != v
						&& viewListKleurSelection.get(1) != v 
						&& viewListKleurSelection.get(2) != v)) {
					View view = viewListKleurSelection.get(0);
					ViewHelperTab viewhelpKleurTemp = viewHelperKleur
							.get((Integer) view.getTag());
					view.setBackgroundColor(getResources().getColor(R.color.light_grey_list_item));

					viewhelpKleurTemp.getImageView().setImageDrawable(
							listKleur.get(positionKleur.get(0)));
					viewhelpKleurTemp.setSelected(true);
					positionKleur.remove(0);
					viewListKleurSelection.remove(0);
					positionKleur.add(position);
					viewListKleurSelection.add(v);
				}

				for (int i = 0; i < viewHelperKleur.size(); i++) {
					unSelectKleurAll(viewListKleur.get(i),
							viewHelperKleur.get(i), i);
				}

				for (int i = 0; i < viewListKleurSelection.size(); i++) {
					selectKleur(viewListKleurSelection.get(i), i);
				}
			} else {
				unSelectKleur(iv, position);
			}
		}
	};

	//*/
	private void setGrooteListener() {
		View item1Grootte = findViewById(R.id.relative_item1_grootte);
		item1Grootte.setOnClickListener(grootteListener);
		item1Grootte.setTag(0);
		ViewHelperTab viewHelperTab1 = new ViewHelperTab();
		viewHelperTab1.setImageView((ImageView) item1Grootte
				.findViewById(R.id.imgvw_groottebird_1));
		viewHelperTab1.setTextView((TextView) item1Grootte
				.findViewById(R.id.txtvw_groottebird_1));
		viewHelperTab1.setSelected(false);
		viewHelperGrootte.add(viewHelperTab1);
		viewListGrootte.add(item1Grootte);

		View item2Grootte = findViewById(R.id.relative_item2_grootte);
		item2Grootte.setOnClickListener(grootteListener);
		item2Grootte.setTag(1);
		ViewHelperTab viewHelperTab2 = new ViewHelperTab();
		viewHelperTab2.setImageView((ImageView) item2Grootte
				.findViewById(R.id.imgvw_groottebird_2));
		viewHelperTab2.setTextView((TextView) item2Grootte
				.findViewById(R.id.txtvw_groottebird_2));
		viewHelperTab2.setSelected(false);
		viewHelperGrootte.add(viewHelperTab2);
		viewListGrootte.add(item2Grootte);

		View item3Grootte = findViewById(R.id.relative_item3_grootte);
		item3Grootte.setOnClickListener(grootteListener);
		item3Grootte.setTag(2);
		ViewHelperTab viewHelperTab3 = new ViewHelperTab();
		viewHelperTab3.setImageView((ImageView) item3Grootte
				.findViewById(R.id.imgvw_groottebird_3));
		viewHelperTab3.setTextView((TextView) item3Grootte
				.findViewById(R.id.txtvw_groottebird_3));
		viewHelperTab3.setSelected(false);
		viewHelperGrootte.add(viewHelperTab3);
		viewListGrootte.add(item3Grootte);

		View item4Grootte = findViewById(R.id.relative_item4_grootte);
		item4Grootte.setOnClickListener(grootteListener);
		item4Grootte.setTag(3);
		ViewHelperTab viewHelperTab4 = new ViewHelperTab();
		viewHelperTab4.setImageView((ImageView) item4Grootte
				.findViewById(R.id.imgvw_groottebird_4));
		viewHelperTab4.setTextView((TextView) item4Grootte
				.findViewById(R.id.txtvw_groottebird_4));
		viewHelperTab4.setSelected(false);
		viewHelperGrootte.add(viewHelperTab4);
		viewListGrootte.add(item4Grootte);
	}

	// */

	private void setSilhouteListener() {
		View item1Silhoute = findViewById(R.id.relative_item1_silhoute);
		item1Silhoute.setOnClickListener(silhouteListener);
		item1Silhoute.setTag(0);
		ViewHelperTab viewHelperTab1 = new ViewHelperTab();
		viewHelperTab1.setImageView((ImageView) item1Silhoute
				.findViewById(R.id.imgvw_silhoutebird_1));
		viewHelperTab1.setTextView((TextView) item1Silhoute
				.findViewById(R.id.txtvw_silhoutebird_1));
		viewHelperTab1.setSelected(false);
		viewHelperSilhoute.add(viewHelperTab1);
		viewListSilhoute.add(item1Silhoute);

		View item2Silhoute = findViewById(R.id.relative_item2_silhoute);
		item2Silhoute.setOnClickListener(silhouteListener);
		item2Silhoute.setTag(1);
		ViewHelperTab viewHelperTab2 = new ViewHelperTab();
		viewHelperTab2.setImageView((ImageView) item2Silhoute
				.findViewById(R.id.imgvw_silhoutebird_2));
		viewHelperTab2.setTextView((TextView) item2Silhoute
				.findViewById(R.id.txtvw_silhoutebird_2));
		viewHelperTab2.setSelected(false);
		viewHelperSilhoute.add(viewHelperTab2);
		viewListSilhoute.add(item2Silhoute);

		View item3Silhoute = findViewById(R.id.relative_item3_silhoute);
		item3Silhoute.setOnClickListener(silhouteListener);
		item3Silhoute.setTag(2);
		ViewHelperTab viewHelperTab3 = new ViewHelperTab();
		viewHelperTab3.setImageView((ImageView) item3Silhoute
				.findViewById(R.id.imgvw_silhoutebird_3));
		viewHelperTab3.setTextView((TextView) item3Silhoute
				.findViewById(R.id.txtvw_silhoutebird_3));
		viewHelperTab3.setSelected(false);
		viewHelperSilhoute.add(viewHelperTab3);
		viewListSilhoute.add(item3Silhoute);

		View item4Silhoute = findViewById(R.id.relative_item4_silhoute);
		item4Silhoute.setOnClickListener(silhouteListener);
		item4Silhoute.setTag(3);
		ViewHelperTab viewHelperTab4 = new ViewHelperTab();
		viewHelperTab4.setImageView((ImageView) item4Silhoute
				.findViewById(R.id.imgvw_silhoutebird_4));
		viewHelperTab4.setTextView((TextView) item4Silhoute
				.findViewById(R.id.txtvw_silhoutebird_4));
		viewHelperTab4.setSelected(false);
		viewHelperSilhoute.add(viewHelperTab4);
		viewListSilhoute.add(item4Silhoute);

		View item5Silhoute = findViewById(R.id.relative_item5_silhoute);
		item5Silhoute.setOnClickListener(silhouteListener);
		item5Silhoute.setTag(4);
		ViewHelperTab viewHelperTab5 = new ViewHelperTab();
		viewHelperTab5.setImageView((ImageView) item5Silhoute
				.findViewById(R.id.imgvw_silhoutebird_5));
		viewHelperTab5.setTextView((TextView) item5Silhoute
				.findViewById(R.id.txtvw_silhoutebird_5));
		viewHelperTab5.setSelected(false);
		viewHelperSilhoute.add(viewHelperTab5);
		viewListSilhoute.add(item5Silhoute);

		View item6Silhoute = findViewById(R.id.relative_item6_silhoute);
		item6Silhoute.setOnClickListener(silhouteListener);
		item6Silhoute.setTag(5);
		ViewHelperTab viewHelperTab6 = new ViewHelperTab();
		viewHelperTab6.setImageView((ImageView) item6Silhoute
				.findViewById(R.id.imgvw_silhoutebird_6));
		viewHelperTab6.setTextView((TextView) item6Silhoute
				.findViewById(R.id.txtvw_silhoutebird_6));
		viewHelperTab6.setSelected(false);
		viewHelperSilhoute.add(viewHelperTab6);
		viewListSilhoute.add(item6Silhoute);

		View item7Silhoute = findViewById(R.id.relative_item7_silhoute);
		item7Silhoute.setOnClickListener(silhouteListener);
		item7Silhoute.setTag(6);
		ViewHelperTab viewHelperTab7 = new ViewHelperTab();
		viewHelperTab7.setImageView((ImageView) item7Silhoute
				.findViewById(R.id.imgvw_silhoutebird_7));
		viewHelperTab7.setTextView((TextView) item7Silhoute
				.findViewById(R.id.txtvw_silhoutebird_7));
		viewHelperTab7.setSelected(false);
		viewHelperSilhoute.add(viewHelperTab7);
		viewListSilhoute.add(item7Silhoute);

		View item8Silhoute = findViewById(R.id.relative_item8_silhoute);
		item8Silhoute.setOnClickListener(silhouteListener);
		item8Silhoute.setTag(7);
		ViewHelperTab viewHelperTab8 = new ViewHelperTab();
		viewHelperTab8.setImageView((ImageView) item8Silhoute
				.findViewById(R.id.imgvw_silhoutebird_8));
		viewHelperTab8.setTextView((TextView) item8Silhoute
				.findViewById(R.id.txtvw_silhoutebird_8));
		viewHelperTab8.setSelected(false);
		viewHelperSilhoute.add(viewHelperTab8);
		viewListSilhoute.add(item8Silhoute);

		View item9Silhoute = findViewById(R.id.relative_item9_silhoute);
		item9Silhoute.setOnClickListener(silhouteListener);
		item9Silhoute.setTag(8);
		ViewHelperTab viewHelperTab9 = new ViewHelperTab();
		viewHelperTab9.setImageView((ImageView) item9Silhoute
				.findViewById(R.id.imgvw_silhoutebird_9));
		viewHelperTab9.setTextView((TextView) item9Silhoute
				.findViewById(R.id.txtvw_silhoutebird_9));
		viewHelperTab9.setSelected(false);
		viewHelperSilhoute.add(viewHelperTab9);
		viewListSilhoute.add(item9Silhoute);

	}

	private void setSnavelListener() {

		View item1Snavel = findViewById(R.id.relative_item1_snavel);
		item1Snavel.setOnClickListener(snavelListener);
		item1Snavel.setTag(0);
		ViewHelperTab viewHelperTab1 = new ViewHelperTab();
		viewHelperTab1.setImageView((ImageView) item1Snavel
				.findViewById(R.id.imgvw_snavelbird_1));
		viewHelperTab1.setTextView((TextView) item1Snavel
				.findViewById(R.id.txtvw_snavelbird_1));
		viewHelperTab1.setSelected(false);
		viewHelperSnavel.add(viewHelperTab1);
		viewListSnavel.add(item1Snavel);

		View item2Snavel = findViewById(R.id.relative_item2_snavel);
		item2Snavel.setOnClickListener(snavelListener);
		item2Snavel.setTag(1);
		ViewHelperTab viewHelperTab2 = new ViewHelperTab();
		viewHelperTab2.setImageView((ImageView) item2Snavel
				.findViewById(R.id.imgvw_snavelbird_2));
		viewHelperTab2.setTextView((TextView) item2Snavel
				.findViewById(R.id.txtvw_snavelbird_2));
		viewHelperTab2.setSelected(false);
		viewHelperSnavel.add(viewHelperTab2);
		viewListSnavel.add(item2Snavel);

		View item3Snavel = findViewById(R.id.relative_item3_snavel);
		item3Snavel.setOnClickListener(snavelListener);
		item3Snavel.setTag(2);
		ViewHelperTab viewHelperTab3 = new ViewHelperTab();
		viewHelperTab3.setImageView((ImageView) item3Snavel
				.findViewById(R.id.imgvw_snavelbird_3));
		viewHelperTab3.setTextView((TextView) item3Snavel
				.findViewById(R.id.txtvw_snavelbird_3));
		viewHelperTab3.setSelected(false);
		viewHelperSnavel.add(viewHelperTab3);
		viewListSnavel.add(item3Snavel);

		View item4Snavel = findViewById(R.id.relative_item4_snavel);
		item4Snavel.setOnClickListener(snavelListener);
		item4Snavel.setTag(3);
		ViewHelperTab viewHelperTab4 = new ViewHelperTab();
		viewHelperTab4.setImageView((ImageView) item4Snavel
				.findViewById(R.id.imgvw_snavelbird_4));
		viewHelperTab4.setTextView((TextView) item4Snavel
				.findViewById(R.id.txtvw_snavelbird_4));
		viewHelperTab4.setSelected(false);
		viewHelperSnavel.add(viewHelperTab4);
		viewListSnavel.add(item4Snavel);

		View item5Snavel = findViewById(R.id.relative_item5_snavel);
		item5Snavel.setOnClickListener(snavelListener);
		item5Snavel.setTag(4);
		ViewHelperTab viewHelperTab5 = new ViewHelperTab();
		viewHelperTab5.setImageView((ImageView) item5Snavel
				.findViewById(R.id.imgvw_snavelbird_5));
		viewHelperTab5.setTextView((TextView) item5Snavel
				.findViewById(R.id.txtvw_snavelbird_5));
		viewHelperTab5.setSelected(false);
		viewHelperSnavel.add(viewHelperTab5);
		viewListSnavel.add(item5Snavel);

		View item6Snavel = findViewById(R.id.relative_item6_snavel);
		item6Snavel.setOnClickListener(snavelListener);
		item6Snavel.setTag(5);
		ViewHelperTab viewHelperTab6 = new ViewHelperTab();
		viewHelperTab6.setImageView((ImageView) item6Snavel
				.findViewById(R.id.imgvw_snavelbird_6));
		viewHelperTab6.setTextView((TextView) item6Snavel
				.findViewById(R.id.txtvw_snavelbird_6));
		viewHelperTab6.setSelected(false);
		viewHelperSnavel.add(viewHelperTab6);
		viewListSnavel.add(item6Snavel);

		View item7Snavel = findViewById(R.id.relative_item7_snavel);
		item7Snavel.setOnClickListener(snavelListener);
		item7Snavel.setTag(6);
		ViewHelperTab viewHelperTab7 = new ViewHelperTab();
		viewHelperTab7.setImageView((ImageView) item7Snavel
				.findViewById(R.id.imgvw_snavelbird_7));
		viewHelperTab7.setTextView((TextView) item7Snavel
				.findViewById(R.id.txtvw_snavelbird_7));
		viewHelperTab7.setSelected(false);
		viewHelperSnavel.add(viewHelperTab7);
		viewListSnavel.add(item7Snavel);

		View item8Snavel = findViewById(R.id.relative_item8_snavel);
		item8Snavel.setOnClickListener(snavelListener);
		item8Snavel.setTag(7);
		ViewHelperTab viewHelperTab8 = new ViewHelperTab();
		viewHelperTab8.setImageView((ImageView) item8Snavel
				.findViewById(R.id.imgvw_snavelbird_8));
		viewHelperTab8.setTextView((TextView) item8Snavel
				.findViewById(R.id.txtvw_snavelbird_8));
		viewHelperTab8.setSelected(false);
		viewHelperSnavel.add(viewHelperTab8);
		viewListSnavel.add(item8Snavel);

		View item9Snavel = findViewById(R.id.relative_item9_snavel);
		item9Snavel.setOnClickListener(snavelListener);
		item9Snavel.setTag(8);
		ViewHelperTab viewHelperTab9 = new ViewHelperTab();
		viewHelperTab9.setImageView((ImageView) item9Snavel
				.findViewById(R.id.imgvw_snavelbird_9));
		viewHelperTab9.setTextView((TextView) item9Snavel
				.findViewById(R.id.txtvw_snavelbird_9));
		viewHelperTab9.setSelected(false);
		viewHelperSnavel.add(viewHelperTab9);
		viewListSnavel.add(item9Snavel);

	}

	private void setKleurListener() {
		View item1Kleur = findViewById(R.id.relative_item1_kleur);
		item1Kleur.setOnClickListener(kleurListener);
		item1Kleur.setTag(0);
		ViewHelperTab viewHelperTab1 = new ViewHelperTab();
		viewHelperTab1.setImageView((ImageView) item1Kleur
				.findViewById(R.id.imgvw_kleurbird_1));
		viewHelperTab1.setSelected(false);
		viewHelperKleur.add(viewHelperTab1);
		viewListKleur.add(item1Kleur);

		View item2Kleur = findViewById(R.id.relative_item2_kleur);
		item2Kleur.setOnClickListener(kleurListener);
		item2Kleur.setTag(1);
		ViewHelperTab viewHelperTab2 = new ViewHelperTab();
		viewHelperTab2.setImageView((ImageView) item2Kleur
				.findViewById(R.id.imgvw_kleurbird_2));
		viewHelperTab2.setSelected(false);
		viewHelperKleur.add(viewHelperTab2);
		viewListKleur.add(item2Kleur);

		View item3Kleur = findViewById(R.id.relative_item3_kleur);
		item3Kleur.setOnClickListener(kleurListener);
		item3Kleur.setTag(2);
		ViewHelperTab viewHelperTab3 = new ViewHelperTab();
		viewHelperTab3.setImageView((ImageView) item3Kleur
				.findViewById(R.id.imgvw_kleurbird_3));
		viewHelperTab3.setSelected(false);
		viewHelperKleur.add(viewHelperTab3);
		viewListKleur.add(item3Kleur);

		View item4Kleur = findViewById(R.id.relative_item4_kleur);
		item4Kleur.setOnClickListener(kleurListener);
		item4Kleur.setTag(3);
		ViewHelperTab viewHelperTab4 = new ViewHelperTab();
		viewHelperTab4.setImageView((ImageView) item4Kleur
				.findViewById(R.id.imgvw_kleurbird_4));
		viewHelperTab4.setSelected(false);
		viewHelperKleur.add(viewHelperTab4);
		viewListKleur.add(item4Kleur);

		View item5Kleur = findViewById(R.id.relative_item5_kleur);
		item5Kleur.setOnClickListener(kleurListener);
		item5Kleur.setTag(4);
		ViewHelperTab viewHelperTab5 = new ViewHelperTab();
		viewHelperTab5.setImageView((ImageView) item5Kleur
				.findViewById(R.id.imgvw_kleurbird_5));
		viewHelperTab5.setSelected(false);
		viewHelperKleur.add(viewHelperTab5);
		viewListKleur.add(item5Kleur);

		View item6Kleur = findViewById(R.id.relative_item6_kleur);
		item6Kleur.setOnClickListener(kleurListener);
		item6Kleur.setTag(5);
		ViewHelperTab viewHelperTab6 = new ViewHelperTab();
		viewHelperTab6.setImageView((ImageView) item6Kleur
				.findViewById(R.id.imgvw_kleurbird_6));
		viewHelperTab6.setSelected(false);
		viewHelperKleur.add(viewHelperTab6);
		viewListKleur.add(item6Kleur);

		View item7Kleur = findViewById(R.id.relative_item7_kleur);
		item7Kleur.setOnClickListener(kleurListener);
		item7Kleur.setTag(6);
		ViewHelperTab viewHelperTab7 = new ViewHelperTab();
		viewHelperTab7.setImageView((ImageView) item7Kleur
				.findViewById(R.id.imgvw_kleurbird_7));
		viewHelperTab7.setSelected(false);
		viewHelperKleur.add(viewHelperTab7);
		viewListKleur.add(item7Kleur);

		View item8Kleur = findViewById(R.id.relative_item8_kleur);
		item8Kleur.setOnClickListener(kleurListener);
		item8Kleur.setTag(7);
		ViewHelperTab viewHelperTab8 = new ViewHelperTab();
		viewHelperTab8.setImageView((ImageView) item8Kleur
				.findViewById(R.id.imgvw_kleurbird_8));
		viewHelperTab8.setSelected(false);
		viewHelperKleur.add(viewHelperTab8);
		viewListKleur.add(item8Kleur);

		View item9Kleur = findViewById(R.id.relative_item9_kleur);
		item9Kleur.setOnClickListener(kleurListener);
		item9Kleur.setTag(8);
		ViewHelperTab viewHelperTab9 = new ViewHelperTab();
		viewHelperTab9.setImageView((ImageView) item9Kleur
				.findViewById(R.id.imgvw_kleurbird_9));
		viewHelperTab9.setSelected(false);
		viewHelperKleur.add(viewHelperTab9);
		viewListKleur.add(item9Kleur);
	}

	private void setTextGrooteSilhouteSnavelkleur() {
		TextView text_grootte = (TextView) findViewById(R.id.text_grootte_tab);
		text_grootte.setText(Html.fromHtml(getResources().getString(
				R.string.sub_header_grootte_tab)));
		text_grootte.setTypeface(Fonts.getTfFont_regular());

		TextView text_silhoute = (TextView) findViewById(R.id.text_silhoute_tab);
		text_silhoute.setText(Html.fromHtml(getResources().getString(
				R.string.sub_header_silhuette_tab)));
		text_silhoute.setTypeface(Fonts.getTfFont_regular());

		TextView text_snavel = (TextView) findViewById(R.id.text_snavel_tab);
		text_snavel.setText(Html.fromHtml(getResources().getString(
				R.string.sub_header_snavel_tab)));
		text_snavel.setTypeface(Fonts.getTfFont_regular());

		TextView text_kleur = (TextView) findViewById(R.id.text_kleur_tab);
		text_kleur.setText(Html.fromHtml(getResources().getString(
				R.string.sub_header_kleur_tab)));
		text_kleur.setTypeface(Fonts.getTfFont_regular());
	}

	private void unSelectSilhoute(View iv, int position) {
		ViewHelperTab viewhelpSilhoute = ((ViewHelperTab) viewHelperSilhoute
				.get(position));
		iv.setBackgroundResource(R.drawable.cell);
		iv.setBackgroundColor(getResources().getColor(
				R.color.light_grey_list_item));
		viewhelpSilhoute.getImageView().setImageDrawable(
				listSilhoute.get(position));
		TextView text = (TextView) viewhelpSilhoute.getTextView();
		text.setTextColor(getResources()
				.getColor(R.color.inactive_button_color));
		viewhelpSilhoute.setSelected(false);
/*		findViewById(R.id.container_items_snavel).setVisibility(View.INVISIBLE);
		findViewById(R.id.container_items_kleur).setVisibility(View.INVISIBLE);*/
	}

	private void unSelectSnavel(View iv, int position) {
		ViewHelperTab viewhelpSnavel = ((ViewHelperTab) viewHelperSnavel
				.get(position));
		iv.setBackgroundResource(R.drawable.cell);
		viewhelpSnavel.getImageView()
				.setImageDrawable(listSnavel.get(position));
		TextView text = (TextView) viewhelpSnavel.getTextView();
		text.setTextColor(getResources()
				.getColor(R.color.inactive_button_color));
		viewhelpSnavel.setSelected(false);
/*		findViewById(R.id.container_items_kleur).setVisibility(View.INVISIBLE);*/
	}

	private void selectGrootte(View iv_temp2, int position) {
		ViewHelperTab viewHelpTemp = viewHelperGrootte.get((Integer) iv_temp2
				.getTag());
		iv_temp2.setBackgroundResource(R.drawable.cell_select);
		viewHelpTemp.getImageView().setImageDrawable(
				listGrootteActive.get(positionGrootte.get(position)));
		TextView text = (TextView) viewHelpTemp.getTextView();
		text.setTextColor(Color.WHITE);
		viewHelpTemp.setSelected(true);
	}

	private void unSelectGrootteAll(View viewTemp, ViewHelperTab viewHelpTemp,
			int position) {
		viewTemp.setBackgroundResource(R.drawable.cell);
		viewHelpTemp.getImageView().setImageDrawable(listGrootte.get(position));
		TextView text = (TextView) viewHelpTemp.getTextView();
		text.setTextColor(getResources().getColor(
				R.color.inactive_button_color));
		viewHelpTemp.setSelected(false);
	}
	private void unSelectSilhouteAll(View viewTemp, ViewHelperTab viewHelpTemp,
			int position) {
		viewTemp.setBackgroundColor(getResources().getColor(R.color.light_grey_list_item));
		viewHelpTemp.getImageView().setImageDrawable(listSilhoute.get(position));
		TextView text = (TextView) viewHelpTemp.getTextView();
		text.setTextColor(this.getResources().getColor(
				R.color.inactive_button_color));
		viewHelpTemp.setSelected(false);
	}
	private void unSelectSnavelAll(View viewTemp, ViewHelperTab viewHelpTemp,
			int position) {
		viewTemp.setBackgroundResource(R.drawable.cell);
		viewHelpTemp.getImageView().setImageDrawable(listSnavel.get(position));
		TextView text = (TextView) viewHelpTemp.getTextView();
		text.setTextColor(this.getResources().getColor(
				R.color.inactive_button_color));
		viewHelpTemp.setSelected(false);
	}

	private void unSelectGrootte(View iv, int position) {

		ViewHelperTab viewHelpTemp = viewHelperGrootte.get((Integer) iv
				.getTag());
		iv.setBackgroundResource(R.drawable.cell);
		viewHelpTemp.getImageView().setImageDrawable(listGrootte.get(position));
		TextView text = (TextView) viewHelpTemp.getTextView();
		text.setTextColor(getResources()
				.getColor(R.color.inactive_button_color));
		viewHelpTemp.setSelected(false);
		for (int counter = 0; counter < positionGrootte.size(); counter++) {
			if (positionGrootte.get(counter) == position) {
				positionGrootte.remove(counter);
				viewListGrootteSelection.remove(counter);
			}
		}

	} //

	
	private void selectKleur(View iv_temp2, int position) {
		ViewHelperTab viewHelpTemp = viewHelperKleur.get((Integer) iv_temp2
				.getTag());
		iv_temp2.setBackgroundColor(getResources().getColor(R.color.light_grey_list_item));
		viewHelpTemp.getImageView().setImageDrawable(
				listKleurActive.get(positionKleur.get(position)));
		viewHelpTemp.setSelected(true);
	}

	private void unSelectKleurAll(View viewTemp, ViewHelperTab viewHelpTemp,int position) {	
		viewTemp.setBackgroundColor(getResources().getColor(R.color.light_grey_list_item));
		viewHelpTemp.getImageView().setImageDrawable(listKleur.get(position));
		viewHelpTemp.setSelected(false);
	}

	private void unSelectKleur(View iv, int position) {

		ViewHelperTab viewHelpTemp = viewHelperKleur.get((Integer) iv
				.getTag());
		iv.setBackgroundColor(getResources().getColor(R.color.light_grey_list_item));
		viewHelpTemp.getImageView().setImageDrawable(listKleur.get(position));
		viewHelpTemp.setSelected(false);
		for (int counter = 0; counter < positionKleur.size(); counter++) {
			if (positionKleur.get(counter) == position) {
				positionKleur.remove(counter);
				viewListKleurSelection.remove(counter);
			}
		}

	} //

	public void invisible_silhoute_snavel_kleur() {
		View view_silhouteText = findViewById(R.id.container_text_silhoute);
		view_silhouteText.setVisibility(View.INVISIBLE);

		View view_silhouteItems = findViewById(R.id.container_items_silhoute);
		view_silhouteItems.setVisibility(View.INVISIBLE);

		View view_snavelText = findViewById(R.id.container_text_snavel);
		view_snavelText.setVisibility(View.INVISIBLE);

		View view_snavelItems = findViewById(R.id.container_items_snavel);
		view_snavelItems.setVisibility(View.INVISIBLE);

		View view_kluerText = findViewById(R.id.container_text_kleur);
		view_kluerText.setVisibility(View.INVISIBLE);

		View view_kleurItems = findViewById(R.id.container_items_kleur);
		view_kleurItems.setVisibility(View.INVISIBLE);

	}

	public void invisible_snavel_kleur() {
		View view_snavelText = findViewById(R.id.container_text_snavel);
		view_snavelText.setVisibility(View.INVISIBLE);

		View view_snavelItems = findViewById(R.id.container_items_snavel);
		view_snavelItems.setVisibility(View.INVISIBLE);

		View view_kluerText = findViewById(R.id.container_text_kleur);
		view_kluerText.setVisibility(View.INVISIBLE);

		View view_kleurItems = findViewById(R.id.container_items_kleur);
		view_kleurItems.setVisibility(View.INVISIBLE);

	}

	public void invisible_kleur() {
		View view_kluerText = findViewById(R.id.container_text_kleur);
		view_kluerText.setVisibility(View.INVISIBLE);

		View view_kleurItems = findViewById(R.id.container_items_kleur);
		view_kleurItems.setVisibility(View.INVISIBLE);

	}

	public void visible_kleur() {
		View view_kluerText = findViewById(R.id.container_text_kleur);
		view_kluerText.setVisibility(View.VISIBLE);

		View view_kleurItems = findViewById(R.id.container_items_kleur);
		view_kleurItems.setVisibility(View.VISIBLE);

	}

	public void visible_silhoute() {
		View view_silhouteText = findViewById(R.id.container_text_silhoute);
		view_silhouteText.setVisibility(View.VISIBLE);

		View view_silhouteItems = findViewById(R.id.container_items_silhoute);
		view_silhouteItems.setVisibility(View.VISIBLE);

	}

	public void visible_snavel() {
		View view_snavelText = findViewById(R.id.container_text_snavel);
		view_snavelText.setVisibility(View.VISIBLE);

		View view_snavelItems = findViewById(R.id.container_items_snavel);
		view_snavelItems.setVisibility(View.VISIBLE);

	}

	public void visible_grootte() {
		View view_grootteText = findViewById(R.id.container_text_grootte);
		view_grootteText.setVisibility(View.VISIBLE);

		View view_grootteItems = findViewById(R.id.container_items_grootte);
		view_grootteItems.setVisibility(View.VISIBLE);

	}

}
