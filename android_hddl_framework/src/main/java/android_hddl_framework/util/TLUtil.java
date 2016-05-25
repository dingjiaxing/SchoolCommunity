package android_hddl_framework.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class TLUtil {
	public static void showToast(Context context, String str) {
		Toast.makeText(context, str, Toast.LENGTH_LONG).show();
	}

	public static void printLog(Object obj) {
		Log.i("OUTPUT", obj.toString());
	}

}
