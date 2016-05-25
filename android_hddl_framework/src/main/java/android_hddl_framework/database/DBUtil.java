package android_hddl_framework.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.os.Environment;
import android_hddl_framework.util.LogUtil;

public class DBUtil {
	public static void moveDBFile(Context context, String filePath, String name) {
		File f = new File(Environment.getDataDirectory() + "/data/" + context.getPackageName() + "/databases/");
		LogUtil.d(f.getAbsolutePath());
		if (!f.exists())
			f.mkdirs();
		InputStream input = null;
		OutputStream output = null;
		try {
			if (filePath.startsWith("assets:")) {
				input = context.getAssets().open(filePath.replaceFirst("assets:", "").trim());
			} else {
				input = new FileInputStream(new File(filePath));
			}
			output = new FileOutputStream(context.getDatabasePath(name));
			int len = 0;
			byte[] buffer = new byte[1024];
			while ((len = input.read(buffer)) > 0) {
				output.write(buffer, 0, len);
			}
		} catch (Exception e) {
			LogUtil.w(e);
		} finally {
		}
	}
}
