package android_hddl_framework.net;

import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpConfig;
import org.kymjs.kjframe.http.HttpParams;

import android_hddl_framework.Interface.ResponseListener;
import android_hddl_framework.model.Message;
import android_hddl_framework.net.model.ReturnMessage;
import android_hddl_framework.util.ConstansUrl;
import android_hddl_framework.util.JsonUtil;
import android_hddl_framework.util.TLUtil;

/** interface Response
 * 定义请求数据通用模板 后面所有的接口请求走通用类 返回值通过接口返回activity
 * */
public class ResponseDates {
	public  Message message;
	public ResponseListener response;
	public  int id;

	public ResponseDates(ResponseListener response,int id, Message message) {
		this.message = message;
		this.id = id;
		this.response=response;
		rePost(id, message, response);
	}

	public  void re(final int id, Message message,final ResponseListener response) {
		HttpConfig config=new HttpConfig();
		config.TIMEOUT=10000;
		KJHttp kjHttp = new KJHttp(config);
		HttpParams params = new HttpParams();
		String json = JsonUtil.objetcToJson(message);
		params.putJsonParams(json);
		kjHttp.get("http://www.baidu.com", new HttpCallBack() {
			@Override
			public void onSuccess(String info) {
				Message message;
				message = (Message) JsonUtil.jsonToObject(info, Message.class);
				if (message.getDetail() != null) {
					String[] detail = message.getDetail().split(":");
					if (detail.length > 1)
						if (response != null)
							response.responeSuccess(id, ReturnMessage.SERVICE_SUCCESS,
									ReturnMessage.getReturnInfo(message));
				} else {
					if (response != null)
						response.responeSuccess(id, ReturnMessage.SERVICE_SUCCESS,
								 ReturnMessage.getReturnInfo(message));
				}

			}

			@Override
			public void onFailure(int errorNo, String strMsg) {
				super.onFailure(errorNo, strMsg);
				if (response != null)
					response.responseFial(id,errorNo, strMsg);
			}
		});
	}
	public void rePost(final int id, Message message,final ResponseListener response) {
		HttpConfig config=new HttpConfig();
		config.cacheTime=0;
		config.TIMEOUT=10000;
		KJHttp kjHttp = new KJHttp(config);
		HttpParams params = new HttpParams();
		String json = JsonUtil.objetcToJson(message);
//		json=json.replace("\\\"", "");
		TLUtil.printLog(json);
		params.put("jsonString", json);
		kjHttp.post(ConstansUrl.Url,params, new HttpCallBack() {
			@Override
			public void onSuccess(String info) {
				TLUtil.printLog(info);
				Message message;
				message = (Message) JsonUtil.jsonToObject(info, Message.class);
				if (message.getDetail() != null) {
					String[] detail = message.getDetail().split(":");
					if (detail.length > 1)
						if (response != null)
							response.responeSuccess(id, ReturnMessage.SERVICE_SUCCESS, 
									ReturnMessage.getReturnInfo(message));
						else if (response != null)
							response.responeSuccess(id, ReturnMessage.SERVICE_SUCCESS,
								
									ReturnMessage.getReturnInfo(message));
				} else {
					if (response != null)
						response.responeSuccess(id, ReturnMessage.SERVICE_SUCCESS,
								 ReturnMessage.getReturnInfo(message));
				}

			}

			@Override
			public void onFailure(int errorNo, String strMsg) {
				super.onFailure(errorNo, strMsg);
				if (response != null)
					response.responseFial(id,errorNo, strMsg);
			}
		});
	}
	public void setResponse(ResponseListener response) {
		this.response = response;
	}

}
