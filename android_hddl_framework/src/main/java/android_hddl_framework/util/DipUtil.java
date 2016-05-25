package android_hddl_framework.util;

import android.content.Context;
import android.util.TypedValue;


/**
 * @author Derrick
 * @date 2014-5-27 下午4:16:27
 */
public class DipUtil {

	/**
	 * dip 转 pixels
	 */
	public static int dipToPixels(float dip) {
		return (int) (dip * Screen.getInstance().density + 0.5f);
	}

	/**
	 * pixels 转 dip
	 */
	public static int pixelsToDip(float pixels) {
		return (int) (pixels / Screen.getInstance().density + 0.5f);
	}

	/**
	 * sp 转 pixels
	 */
	public static int spToPixels(float sp) {
		return (int) (sp * Screen.getInstance().scaledDensity + 0.5f);
	}

	/**
	 * pixels 转 sp
	 */
	public static int pixelsToSp(float pixels) {
		return (int) (pixels / Screen.getInstance().scaledDensity + 0.5f);
	}
	/**
	 * dip转换成px
	 * @param context
	 * @param dipValue
	 * @return
	 */
	public static int dip2px(Context context, int dipValue){
		final float scale = context.getResources().getDisplayMetrics().density; 
        return (int)(dipValue * scale + 0.5f); 
	}
	
	
	public static  int pixelsToDip(Context context,int Pixels) {
		int dip=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, Pixels, context.getResources().getDisplayMetrics());
		return dip;
		}
}
