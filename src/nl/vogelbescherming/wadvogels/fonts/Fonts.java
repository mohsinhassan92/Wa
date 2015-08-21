package nl.vogelbescherming.wadvogels.fonts;

import android.content.Context;
import android.graphics.Typeface;

public class Fonts{
	private static Typeface tfFont;
	private static Typeface tfFont_italic;
	private static Typeface tfFont_book;
	private static Typeface tfFont_bold;
	private static Typeface tfFont_condensed_bold;
	private static Typeface tfFont_medium;
	private static Typeface tfFont_regular;
	private static Typeface tfFont_opentype;
	
	
	public void getFonts(Context context){
		tfFont = Typeface.createFromAsset(context.getAssets(), "fonts/Futura.ttc");
		tfFont_book = Typeface.createFromAsset(context.getAssets(), "fonts/Futura_Book.ttf");
		tfFont_italic = Typeface.createFromAsset(context.getAssets(), "fonts/Futura_italic.ttf");
		tfFont_bold = Typeface.createFromAsset(context.getAssets(), "fonts/Futura_Bold.ttf");
		tfFont_medium = Typeface.createFromAsset(context.getAssets(), "fonts/Futura_medium.ttf");
		tfFont_condensed_bold = Typeface.createFromAsset(context.getAssets(), "fonts/Futura_con_bold.ttf");
		tfFont_regular = Typeface.createFromAsset(context.getAssets(), "fonts/Museo500-Regular.otf");
		tfFont_opentype = Typeface.createFromAsset(context.getAssets(), "fonts/ufonts.com_museo-700-opentype.otf");
		
		
	}
	
	public static Typeface getTfFont() {
		return tfFont;
	}
	public static final Typeface getTfFont_italic() {
		return tfFont_italic;
	}
	public static final Typeface getTfFont_book() {
		return tfFont_book;
	}
	public static final Typeface getTfFont_bold() {
		return tfFont_bold;
	}
	public static final Typeface getTfFont_condensed_bold() {
		return tfFont_condensed_bold;
	}
	public static final Typeface getTfFont_medium() {
		return tfFont_medium;
	}
	public static final Typeface getTfFont_regular() {
		return tfFont_regular;
	}
	public static final Typeface getTfFont_opentype() {
		return tfFont_opentype;
	}	
}
