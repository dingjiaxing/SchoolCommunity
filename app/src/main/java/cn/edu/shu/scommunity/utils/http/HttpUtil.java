package cn.edu.shu.scommunity.utils.http;

import java.util.Map;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android_hddl_framework.util.DialogUtil;
import android_hddl_framework.util.TLUtil;
import cn.edu.shu.scommunity.FApplication;
import cn.edu.shu.scommunity.utils.Constant;

public class HttpUtil {
	public static final int SUCCESS = 1000000;
	public static final int ERROR = 1000001;
	private static RequestQueue mRequestQueue = null;
	public static RequestQueue getRequestQueue(Context context) {
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(context);
			return mRequestQueue;
		}
		return mRequestQueue;
	}

	public static synchronized void sendHttp(Context context,
			final Handler handler, String msg, String parm, String urlname) {
		DialogUtil.show(context, msg);
		ServiceUtil.service(urlname, parm, new ServiceUtil.HttpListener() {
			@Override
			public void onError() {
				DialogUtil.dismiss();
				Message message = handler.obtainMessage(ERROR);
				handler.sendMessage(message);
				TLUtil.showToast(FApplication.getInstance()
						.getApplicationContext(), "网络异常!");
			}

			@Override
			public void onResult(Object msg) {
				DialogUtil.dismiss();
				TLUtil.printLog(msg);
				parseString(msg.toString(), handler);
			}

		});
	}
	public static synchronized void sendHttp(Context context,
			final Handler handler, String parm, String urlname) {
		ServiceUtil.service(urlname, parm, new ServiceUtil.HttpListener() {
			@Override
			public void onError() {
				DialogUtil.dismiss();
				Message message = handler.obtainMessage(ERROR);
				handler.sendMessage(message);
				TLUtil.showToast(FApplication.getInstance()
						.getApplicationContext(), "网络异常!");
			}

			@Override
			public void onResult(Object msg) {
				DialogUtil.dismiss();
				TLUtil.printLog(msg);
				parseString(msg.toString(), handler);
			}

		});
	}
	public static synchronized void getWeather(Context context,
			final Handler handler, final Map map, final Map head, String urlname) {
		ServiceUtil.serviceWeather(urlname, map, head, new ServiceUtil.HttpListener() {
			
			@Override
			public void onResult(Object msg) {
				// TODO Auto-generated method stub
				TLUtil.printLog(msg);
				parseString(msg.toString(), handler);
			}
			
			@Override
			public void onError() {
				// TODO Auto-generated method stub
				Message message = handler.obtainMessage(ERROR);
				handler.sendMessage(message);
			}
		});
	}
	
	public static synchronized void sendHttpNoDialog(Context context, final Handler handler,
			String msg, final Map parm, String urlname) {
		StringRequest sr = new StringRequest(Request.Method.POST,
				Constant.BASE_URL + urlname, new Response.Listener() {

					@Override
					public void onResponse(Object obj) {
						TLUtil.printLog(obj);
						parseString(obj.toString(), handler);
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						Message message = handler.obtainMessage(ERROR);
						handler.sendMessage(message);
						TLUtil.showToast(FApplication.getInstance()
								.getApplicationContext(), "网络异常!");
						DialogUtil.dismiss();
					}
				}) {

			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				return parm;
			}
			@Override
			public byte[] getBody() throws AuthFailureError {
				// TODO Auto-generated method stub
				byte[] bytes = super.getBody();
				if(bytes != null){
					String str = new String(bytes);
					System.out.println(str);
				}
				return bytes;
			}

		};
		mRequestQueue.add(sr);
	}

	private static void parseString(String responseContent, Handler handler) {
		DialogUtil.dismiss();
		try {
//			JSONObject json = JSON
//					.parseObject(responseContent);
			Gson gson=new Gson();
			JsonObject jsonObject=gson.fromJson(responseContent,JsonObject.class);

			Message msg = handler.obtainMessage();
			msg.what = SUCCESS;
			msg.obj = jsonObject;
			Bundle bundle=new Bundle();
			bundle.putString("str",responseContent);
			msg.setData(bundle);
			handler.sendMessage(msg);
		} catch (Exception e) {
			DialogUtil.dismiss();
			Message msg = handler.obtainMessage();
			msg.what = ERROR;
			handler.sendMessage(msg);
		}

	}

}
