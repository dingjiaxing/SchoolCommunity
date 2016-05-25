package android_hddl_framework.model;

public class ReturnInfo {
	Message msg;
	String returnCode;
	String returnDesc;

	public ReturnInfo(String returnCode, String returnDesc, Message msg) {
		this.returnCode = returnCode;
		this.returnDesc = returnDesc;
		this.msg = msg;
	}

	public Message getMsg() {
		return msg;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public String getReturnDesc() {
		return returnDesc;
	}

}
