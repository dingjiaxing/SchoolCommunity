package cn.edu.shu.scommunity.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.util.Log;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

public class JackUtil {
	public static final String TAG="JackUtil";
	/**
	 * 隐藏软键盘
	 */
	public static void hideKeyboard(Activity activity) {
		if (activity.getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
			if (activity.getCurrentFocus() != null){
				 InputMethodManager manager;
				 manager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
				manager.hideSoftInputFromWindow(activity.getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}
				
		}
	}
	/** 
	* 根据手机的分辨率从 dp 的单位 转成为 px(像素) 
	*/ 
	public static int dip2px(Context context, float dpValue) { 
		final float scale = context.getResources().getDisplayMetrics().density; 
		return (int) (dpValue * scale + 0.5f); 
	} 

	/** 
	* 根据手机的分辨率从 px(像素) 的单位 转成为 dp 
	*/ 
	public static int px2dip(Context context, float pxValue) { 
		final float scale = context.getResources().getDisplayMetrics().density; 
		return (int) (pxValue / scale + 0.5f); 
	} 
	/**
	 * toast一个字符串
	 */
	public static void showToast(Context context, String str) {
		Toast.makeText(context, str, Toast.LENGTH_LONG).show();
	}
	/**
	 * 在logcat打印一个对象的信息
	 */
	public static void printLog(Object obj) {
		Log.i("OUTPUT", obj.toString());
	}
	/**
	 * Json转对象
	 */
	public static Object jsonToObject(String json, Class<?> classOfT) {
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		return gson.fromJson(json, classOfT);
	}

	/**
	 * Json转对象
	 */
	public static Object jsonToObject(String json, Type type) {
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		return gson.fromJson(json, type);
	}

	/**
	 * json解析回ArrayList,参数为new TypeToken<ArrayList<T>>() {},必须加泛型
	 */
	public static List<?> jsonToList(String json, TypeToken<?> token) {
		return (List<?>) jsonToObject(json, token.getType());
	}

	/**
	 * 对象转Json
	 */
	public static String objetcToJson(Object object) {
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		return gson.toJson(object);
	}
	/**
	 * 获取列表
	 */
	public static List getlist(String key,String jsonString){
		List list=new ArrayList();
		try {
			JSONObject jsonObject=new JSONObject(jsonString);
			JSONArray array=jsonObject.getJSONArray(key);
			for(int i=0;i<array.length();i++){
				String msg=array.getString(i);
				list.add(msg);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 将图片用base64编码为字符串 
	 */
	public static String imgToBase64(String imgPath) {
		Bitmap bitmap = null;
		if (imgPath != null && imgPath.length() > 0) {
			bitmap = readBitmap(imgPath);
		}
		ByteArrayOutputStream out = null;
		try {
			out = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

			out.flush();
			out.close();

			byte[] imgBytes = out.toByteArray();

			Log.e("打印",(Base64.encodeToString(imgBytes, Base64.DEFAULT)));
			return Base64.encodeToString(imgBytes, Base64.DEFAULT);
			
		} catch (Exception e) {
			return null;
		} finally {
			try {
				out.flush();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * 将图片转化为bitmap
	 */
	private static Bitmap readBitmap(String imgPath) {
		try {
			return BitmapFactory.decodeFile(imgPath);
		} catch (Exception e) {
			return null;
		}
	}
	/**
	 * 将drawable转化为bitmap
	 * @param drawable
	 * @return
	 */
	public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(
                                        drawable.getIntrinsicWidth(),
                                        drawable.getIntrinsicHeight(),
                                        drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                                        : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        //canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
	}
	/**
	 * 将 bitmap 转化为 drawable
	 * @param bitmap
	 * @return
	 */
	public static Drawable bitmapToDrawable(Bitmap bitmap){
		Drawable drawable =new BitmapDrawable(bitmap);
		return drawable;
	}
	/**
	 * 检测网络是否可用
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetWorkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}

		return false;
	}

	/**
	 * 检测Sdcard是否存在
	 * 
	 * @return
	 */
	public static boolean isExitsSdcard() {
		if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
			return true;
		else
			return false;
	}
	
    /**
     * 通过字符串id获取字符串
     * @param context
     * @param resId
     * @return
     */
    static String getString(Context context, int resId){
        return context.getResources().getString(resId);
    }
	
	/**
	 * 获取栈顶的activity的名称
	 * @param context
	 * @return
	 */
	public static String getTopActivity(Context context) {
		ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);

		if (runningTaskInfos != null)
			return runningTaskInfos.get(0).topActivity.getClassName();
		else
			return "";
	}
}
