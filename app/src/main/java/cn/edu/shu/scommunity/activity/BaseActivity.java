package cn.edu.shu.scommunity.activity;

import java.io.File;
import java.io.IOException;
import java.util.List;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

public class BaseActivity extends FragmentActivity {
	private InputMethodManager imm;
	private FragmentTransaction transaction;
	private String currentTime;
	public static  String IMAGE_UNSPECIFIED = "image/*";
	public static  String CACHE_DIR = "/fhj/" + "images/";

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	}

	public void hideInputFromwindow() {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm.isActive() && getCurrentFocus() != null) {
			if (getCurrentFocus().getWindowToken() != null) {
				imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}
	}

	public void toPage(Class<?> c) {
		hideInputFromwindow();
		Intent intent = new Intent(this, c);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}

	public void toPage(Class<?> c, Bundle bundle) {
		hideInputFromwindow();
		Intent intent = new Intent(this, c);
		intent.putExtras(bundle);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}

	public void replace(int resId, Fragment page) {
		transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(resId, page);
		transaction.commit();
	}

	public void replace(int resId, Fragment page, String tag) {
		transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(resId, page, tag);
		transaction.commit();
	}

	public void addFragment(int resId, Fragment page) {
		transaction = getSupportFragmentManager().beginTransaction();
		transaction.add(resId, page);
		transaction.commit();
	}

	public void addFragment(int resId, Fragment page, String tag) {
		transaction = getSupportFragmentManager().beginTransaction();
		transaction.add(resId, page, tag);
		transaction.commit();
	}

	public void hideFragment(Fragment page) {
		transaction = getSupportFragmentManager().beginTransaction();
		transaction.hide(page);
		transaction.commit();
	}

	public void showFragment(Fragment page) {
		transaction = getSupportFragmentManager().beginTransaction();
		transaction.show(page);
		transaction.commit();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		FragmentManager manager = getSupportFragmentManager();
		List<Fragment> list = manager.getFragments();
		if (list != null && !list.isEmpty()) {
			final int count = list.size();
			for (int i = 0; i < count; i++) {
				Fragment fragment = list.get(i);
				if (fragment != null && fragment.isVisible()) {
					fragment.onActivityResult(requestCode, resultCode, data);
				}
			}
		}
	}
	// 获取图片的路径
			public File getTempHeadFile() {
				File f = null;
				File head = null;
				if (android.os.Environment.getExternalStorageState().equals(
						android.os.Environment.MEDIA_MOUNTED))
					f = new File(android.os.Environment.getExternalStorageDirectory(),
							CACHE_DIR);
				else
					f = getCacheDir();

				if (!f.exists())
					f.mkdirs();
				else {
					if (f.isFile()) {
						f.deleteOnExit();
						f.mkdirs();
					}
				}
				head = new File(f, currentTime + "comment.jpg");
				//head = new File(f,   "comment.jpg");
				if (!head.exists()) {
					try {
						head.createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				f = null;
				return head;
			}
}
