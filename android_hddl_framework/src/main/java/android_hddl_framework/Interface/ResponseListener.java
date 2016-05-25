package android_hddl_framework.Interface;

import android_hddl_framework.model.ReturnInfo;

public interface ResponseListener {
	public void responeSuccess(int id, String code,ReturnInfo info);
	public void responseFial(int id, int errorNo, String strMsg);
}
