package android_hddl_framework.util;

import java.util.Set;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * SharePreference工具类 
 * 使用之前应先inital 
 * 将inital放到application中，
 * 程序中直接使用即可
 * 
 * @author 
 * 
 */
public class SharePreferenceUtils {

	public static final int DEFAULT_VALUE = 1001;
	public static final String USER_INFO_KEY = "user_info";
	private static SharePreferenceUtils sharePreferenceUtils;
	private SharedPreferences mSharedPreferences;

	private SharePreferenceUtils() {
	}

	public static void init(Context context) {
		if (context == null) {
			return;
		}
		if (sharePreferenceUtils == null) {
			sharePreferenceUtils = new SharePreferenceUtils();
			sharePreferenceUtils.mSharedPreferences = context
					.getSharedPreferences(context.getPackageName(),
							Context.MODE_PRIVATE);
		}

	}

	public static void putString(String key, String value) {
		Editor editor = sharePreferenceUtils.mSharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static void putInt(String key, int value) {
		Editor editor = sharePreferenceUtils.mSharedPreferences.edit();
		editor.putInt(key, value);
		editor.commit();
	}

	public static void putFloat(String key, float value) {
		Editor editor = sharePreferenceUtils.mSharedPreferences.edit();
		editor.putFloat(key, value);
		editor.commit();
	}

	public static void putBoolean(String key, boolean value) {
		Editor editor = sharePreferenceUtils.mSharedPreferences.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	public static void putLong(String key, long value) {
		Editor editor = sharePreferenceUtils.mSharedPreferences.edit();
		editor.putLong(key, value);
		editor.commit();
	}

	public static void putStringSet(String key, Set value) {
		Editor editor = sharePreferenceUtils.mSharedPreferences.edit();
		editor.putStringSet(key, value);
		editor.commit();
	}

	public static void putObject(String key, Object value) {
		if (value == null) {
			return;
		}
		Editor editor = sharePreferenceUtils.mSharedPreferences.edit();
		editor.putString(key, JsonUtil.objetcToJson(value));
		editor.commit();
	}

	public static int getInt(String key) {
		return sharePreferenceUtils.mSharedPreferences.getInt(key,
				DEFAULT_VALUE);
	}

	public static String getString(String key) {
		return sharePreferenceUtils.mSharedPreferences.getString(key, null);
	}

	public static long getLong(String key) {
		return sharePreferenceUtils.mSharedPreferences.getLong(key,
				DEFAULT_VALUE);
	}

	public static float getFloat(String key) {
		return sharePreferenceUtils.mSharedPreferences.getFloat(key,
				DEFAULT_VALUE);
	}

	public static Set<String> getStringSet(String key) {
		return sharePreferenceUtils.mSharedPreferences.getStringSet(key, null);
	}

	public static <T> T getObject(String key, Class<T> clazz) {
		String jsonValue = sharePreferenceUtils.mSharedPreferences.getString(
				key, null);
		if (jsonValue == null || "".equals(jsonValue)) {
			return null;
		}
		return (T) JsonUtil.jsonToObject(jsonValue, clazz);
	}

	public static void remove(String key) {
		Editor editor = sharePreferenceUtils.mSharedPreferences.edit();
		editor.remove(key);
		editor.commit();
	}

	public static void logout() {
		remove(USER_INFO_KEY);
	}

	public static SharedPreferences getSharePreference() {
		return sharePreferenceUtils.mSharedPreferences;
	}
	private static void checkInit() throws Exception {
		if (sharePreferenceUtils == null
				|| sharePreferenceUtils.mSharedPreferences == null) {
			throw new Exception("SharePreferenceUtils未初始化，请先初始化！");
		}
	}
	/**
	 * 存储对象
	 * @param key
	 * @param object
	 */
	public static void saveSharePerfence(String key,Object object){
		putObject(key, object);
	}
	public static <T> Object getSharePerfence(String key,Class<T> clazz){
		return getObject(key, clazz);
	}
	

}

