package cn.edu.shu.scommunity.utils.http;

import java.util.Map;


import android.util.Log;

import cn.edu.shu.scommunity.utils.Constant;

public class ServiceUtil {

	public interface HttpListener {
		void onError();
		void onResult(Object msg);
	}
	/**
	 * 数据交互入口
	 */
	public static void service(final String urlName, final String param,
							   final HttpListener listener) {
		new Thread() {
			public void run() {
				try {
					toService(urlName, param, listener);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	public static boolean toService(String urlName, String param,HttpListener listener) {
		try {
//			String result = Net.doPost(Constant.BASE_URL + urlName, map);
//			String param="";
			String result=HttpRequest.sendPost(Constant.BASE_URL+urlName,param);

			if (result != null & !"".equals(result)) {
				if (listener != null) {
					listener.onResult(result);
				}
				return true;
			} else {
				doError(listener);
			}
		} catch (Exception e) {
			doError(listener);
		}
		return false;
	}

	public static void serviceWeather(final String urlName, final Map map,
			final Map head,final HttpListener listener) {
		/*
		new Thread() {
			public void run() {
				try {
					String result = Net.doGet(urlName, map, head);
					if(result!=null){
						if(listener!=null){
							listener.onResult(result);
						}else{
							doError(listener);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
		*/
	}
	public static void doError(HttpListener listener) {
		if (listener != null) {
			listener.onError();
		}
	}

}