package android_hddl_framework.view;

import com.example.android_hddl_framework.R;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android_hddl_framework.util.DipUtil;
import android_hddl_framework.util.SystemBarTintManager;

public class LoadingDialog extends Dialog{
	public SystemBarTintManager manager;
	public LoadingDialog(Context context) {
		super(context, R.style.alertThrem);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setCancelable(false);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.dialog_layout, null);
		setContentView(v);
		LayoutParams params = getWindow().getAttributes();     
        getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);  
	}

}
