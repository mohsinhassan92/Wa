package nl.vogelbescherming.wadvogels.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import nl.vogelbescherming.wadvogels.GrootteActivity;
import nl.vogelbescherming.wadvogels.SilhuetteActivity;
import nl.vogelbescherming.wadvogels.SnavelActivity;

public class Bird implements Serializable{
	private final int COLOR_LENGTH = 4;
	private final int SIZE_LENGTH = 2;
	private final int APPEARS_LENGTH = 2;
	private final int SILHUETTE_LENGTH = 3;
	private final int BEAK_LENGTH = 3;
	
	
	//private static this this;
	//private Integer silhouette;
	private List<Integer> colors;
	private List<Integer> sizes;
	private List<Integer> silhouette;
	private List<Integer> beak;
	//private Integer beak;
	private boolean isShowMessage;
	private String name;
	private int id;
	private static String message;// = "Geef deze vogel in april, mei en juni aub extra ruimte!";
	//-------------------------------------------------------------------------------------------------------
	private List<Integer> appears;
	

	private String description;
	private String grootte;
	private String snavel;
	private String poten;
	private String opvallende;
	private String gedrag;
	private String voedsel;
	private String leefgebied;
	private String verspreiding;
	private String engelse;
	private String latijnse;
	private String meerInfo;
	private int sort; 
	
	private int chance;
	
	public void setSort(int sort) {
		this.sort = sort;
	}
	public int getSort() {
		return sort;
	}
	public void setChance(String chance) {
		this.chance = Integer.valueOf(chance);
	}
	public Integer getChance() {
		return chance;
	}
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getGrootte() {
		return grootte;
	}

	public void setGrootte(String grootte) {
		this.grootte = grootte;
	}

	public String getSnavel() {
		return snavel;
	}

	public void setSnavel(String snavel) {
		this.snavel = snavel;
	}

	public String getPoten() {
		return poten;
	}

	public void setPoten(String poten) {
		this.poten = poten;
	}

	public String getOpvallende() {
		return opvallende;
	}

	public void setOpvallende(String opvallende) {
		this.opvallende = opvallende;
	}

	public String getGedrag() {
		return gedrag;
	}

	public void setGedrag(String gedrag) {
		this.gedrag = gedrag;
	}

	public String getVoedsel() {
		return voedsel;
	}

	public void setVoedsel(String voedsel) {
		this.voedsel = voedsel;
	}

	public String getLeefgebied() {
		return leefgebied;
	}

	public void setLeefgebied(String leefgebied) {
		this.leefgebied = leefgebied;
	}

	public String getVerspreiding() {
		return verspreiding;
	}

	public void setVerspreiding(String verspreiding) {
		this.verspreiding = verspreiding;
	}

	public String getEngelse() {
		return engelse;
	}

	public void setEngelse(String engelse) {
		this.engelse = engelse;
	}

	public String getLatijnse() {
		return latijnse;
	}

	public void setLatijnse(String latijnse) {
		this.latijnse = latijnse;
	}

	public String getMeerInfo() {
		return meerInfo;
	}

	public void setMeerInfo(String meerInfo) {
		this.meerInfo = meerInfo;
	}
	//-------------------------------------------------------------------------------------------------------
	public String getMessage() {
		if (this.isShowMessage)
			return message;
		return null;
	}

	public void setShowMessage(boolean isShowMessage) {
		this.isShowMessage = isShowMessage;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Bird(){
		isShowMessage = false;
	}

//	public Integer getSilhouette() {
//		return this.silhouette;
//	}
	public List<Integer> getAppears() {
		return appears;
	}

	public void addAppears(Integer appear){
		if (this.appears == null)
			this.appears = new ArrayList<Integer>(APPEARS_LENGTH);
		this.appears.add(appear);
	}

	public List<Integer> getBeak() {
		return beak;
	}

	public void addBeak(Integer beak){
		if (this.beak == null)
			this.beak = new ArrayList<Integer>(BEAK_LENGTH);
		if (this.beak.size() >= SnavelActivity.MAX_NUMBER_SELECTED_ITEMS){
			this.beak.clear();
		}
		this.beak.add(beak);
	}

    public void parseBeak(Integer beak){
        if (this.beak == null)
            this.beak = new ArrayList<Integer>(BEAK_LENGTH);
        this.beak.add(beak);
    }
	
	public List<Integer> getSilhouette() {
		return silhouette;
	}

	public void addSilhuette(Integer silhuette){
		if (this.silhouette == null){
			silhouette = new ArrayList<Integer>(SILHUETTE_LENGTH);
			//Log.d("HAI001","HAI001 "+silhouette);
		}
		if (this.silhouette.size() >= SilhuetteActivity.MAX_NUMBER_SELECTED_ITEMS){
			this.silhouette.clear();
			//Log.d("HAI002","HAI002");
		}
		this.silhouette.add(silhuette);
		//Log.d("HAI003","HAI003 "+silhuette);
	}

    public void parseSilhuette(Integer silhuette){
        if (this.silhouette == null)
            silhouette = new ArrayList<Integer>(SILHUETTE_LENGTH);
        this.silhouette.add(silhuette);
    }
	
	public List<Integer> getSilhuette() {
		return this.silhouette;
	}
	
//	public void setSilhouette(Integer silhouette) {
//		this.silhouette = silhouette;
//	}

	
	public List<Integer> getColors() {
		return this.colors;
	}

	public void AddColor(Integer color) {
		if (this.colors == null)
			colors = new ArrayList<Integer>(COLOR_LENGTH);
		this.colors.add(color);
	}

	public List<Integer> getSizes() {
		return this.sizes;
	}

	public void AddSize(Integer size) {
		if (this.sizes == null)
			sizes = new ArrayList<Integer>(SIZE_LENGTH);
		if (this.sizes.size() >= GrootteActivity.MAX_NUMBER_SELECTED_ITEMS){
			this.sizes.clear();
		}
		this.sizes.add(size);
	}

    public void parseSize(Integer size) {
        if (this.sizes == null)
            sizes = new ArrayList<Integer>(SIZE_LENGTH);
        this.sizes.add(size);
    }

//	public Integer getBeak() {
//		return this.beak;
//	}

//	public void setBeak(Integer beak) {
//		this.beak = beak;
//	}

	public boolean isShowMessage() {
		return this.isShowMessage;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getImageName(){
		return createName()/* + ".png"*/;
	}
	public String getSoundName(){
		return "s" + createName()/* + ".mp3"*/;
	}

	private String createName() {
		String name =String.valueOf(((1000) + getId())).substring(1)/* + "_" + getName()*/;
		return name; 
	}

	public void setColor(List<Integer> colors) {
		this.colors = colors;
	}
	public void setSize(List<Integer> sizes) {
		this.sizes = sizes;
	}

//	public String getLatinName() {
//		return latinName;
//	}
//
//	public void setLatinName(String latinName) {
//		this.latinName = latinName;
//	}

    public void clearSilhuette() {
        silhouette = new ArrayList<Integer>(SILHUETTE_LENGTH);
    }

    public void clearBeak() {
        beak = new ArrayList<Integer>(BEAK_LENGTH);
    }

    public void clearColors() {
        colors = new ArrayList<Integer>(COLOR_LENGTH);
    }

    public void clearSizes() {
        sizes = new ArrayList<Integer>(SIZE_LENGTH);
    }
}