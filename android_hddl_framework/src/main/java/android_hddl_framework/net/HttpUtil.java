package android_hddl_framework.net;

import android.content.Context;
import android.os.Handler;
import android_hddl_framework.Interface.ResponseListener;
import android_hddl_framework.model.Message;
import android_hddl_framework.model.ReturnInfo;
import android_hddl_framework.util.DialogUtil;
import android_hddl_framework.util.TLUtil;

public class HttpUtil {
	public static final int ERROR = 1000001;
	
	public static synchronized void sendHttp(final Context context,Message message,int id,final Handler handler){
		DialogUtil.show(context,"");
		ResponseDates response=new ResponseDates(new ResponseListener() {
			
			@Override
			public void responseFial(int id, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				TLUtil.showToast(context, "请检查网络");
				DialogUtil.dismiss();
			}
			
			@Override
			public void responeSuccess(int id, String code, ReturnInfo info) {
				// TODO Auto-generated method stub
				detailHandler(id, handler, info);
				DialogUtil.dismiss();
			}
		}, id, message);
	}
	
	public static synchronized void sendHttpNoDialog(final Context context,Message message,int id,final Handler handler){
		ResponseDates response=new ResponseDates(new ResponseListener() {
			
			@Override
			public void responseFial(int id, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				TLUtil.showToast(context, "请检查网络");
			}
			
			@Override
			public void responeSuccess(int id, String code, ReturnInfo info) {
				// TODO Auto-generated method stub
				detailHandler(id, handler, info);
			}
		}, id, message);
	}
	private static void detailHandler(int id,Handler handler,ReturnInfo info){
		android.os.Message msg=handler.obtainMessage();
		if("101".equals(info.getReturnCode())){
			msg.what=id;
			if(info.getMsg()==null)
				msg.what=ERROR;
		}else if ("102".equals(info.getReturnCode())) {
			msg.what=ERROR;
		}
		msg.obj=info.getMsg()!=null?info.getMsg():null;
		handler.sendMessage(msg);
	}
}
