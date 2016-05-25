package android_hddl_framework.util;


import android.content.Context;
import android_hddl_framework.view.LoadingDialog;

public class DialogUtil {
	private static LoadingDialog dialog;

	public static void show(final Context context, final String msg) {

		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
		dialog = new LoadingDialog(context);
		// dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		dialog.setCancelable(true);
		dialog.setTitle(msg);
		dialog.show();
	}

	public static void dismiss() {

		if (dialog != null) {
			dialog.dismiss();
		}
	}

}
