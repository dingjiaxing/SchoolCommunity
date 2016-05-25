package android_hddl_framework.net.model;

import android_hddl_framework.model.Message;
import android_hddl_framework.model.ReturnInfo;

public class ReturnMessage {
	public static final String SERVICE_ERROR = "102"; // 调用异常
	public static final String SERVICE_SUCCESS = "101"; // 调用正常
//	public static final String DECODE_ERROR = "201"; // 解码失败
//	public static final String NO_SUCH_RULE = "303"; // 无此权限
//	public static final String NO_SUCH_USER = "401"; // 无效用户
//	public static final String DB_ERROR = "501"; // 数据库错误
//	public static final String CHANEL_ERROR = "601"; // 渠道错误
//	public static final String SEARCH_FAILURE = "801"; // 查询失败
//	public static final String SEARCH_SUCCESS = "802"; // 查询成功
//	public static final String SYSTEM_ERROR = "000"; // 系统错误
//	public static final String NET_ERROR = "001"; // 网络调用异常

	public static ReturnInfo getReturnInfo(Message msg) {
		String[] detail = msg.getDetail().split(":");
		if (detail[0].equals(SERVICE_SUCCESS)) {
			msg.setDetail(detail[1]);
			return new ReturnInfo(SERVICE_SUCCESS, "调用正常", msg);
		} else if (detail[0].equals(SERVICE_ERROR)) {
			msg.setDetail(detail[1]);
			return new ReturnInfo(SERVICE_ERROR, "调用异常", msg);
		} else {
			return new ReturnInfo(SERVICE_ERROR, "服务器调用异常", msg);
		}
	}

}
