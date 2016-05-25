package android_hddl_framework.model;

import java.util.ArrayList;
import java.util.List;

import android_hddl_framework.util.JsonUtil;
import android_hddl_framework.util.LogUtil;

import com.google.gson.reflect.TypeToken;

public class Message implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String command;

	public List<Var> extra;

	private String detail;

	@Deprecated
	public Message() {
		extra = new ArrayList<Var>();
	}

	public static Message obtain(String command) {
		Message message = new Message();
		message.command = command;
		return message;
	}

	public void setExtra(String name, Object v) {

		boolean haveKey = false;

		for (int i = 0; i < extra.size(); i++) {
			Var p = extra.get(i);
			if (p.getKey().equals(name)) {
				p.setJsonValue(JsonUtil.objetcToJson(v));
				haveKey = true;
				break;
			}
		}
		if (!haveKey) {
			String s = JsonUtil.objetcToJson(v);
			Var param = new Var();
			param.setKey(name);
			param.setJsonValue(s);
			extra.add(param);
		}
	}
	public void setExtra(String name, Object v,boolean bool) {

		boolean haveKey = false;

		for (int i = 0; i < extra.size(); i++) {
			Var p = extra.get(i);
			if (p.getKey().equals(name)) {
				p.setJsonValue(JsonUtil.objetcToJson(v));
				haveKey = true;
				break;
			}
		}
		if (!haveKey) {
			String s = JsonUtil.objectToJson(v, bool);
			Var param = new Var();
			param.setKey(name);
			param.setJsonValue(s);
			extra.add(param);
		}
	}

	public String getExtra(String name) {
		List<Var> varList = extra;
		for (int i = 0; i < varList.size(); i++) {
			Var var = varList.get(i);
			if (var.getKey().equals(name)) {
				return var.getJsonValue();
			}
		}
		return null;
	}

	public Object getExtra(String name, Class<?> classOfT) {
		String json = getExtra(name);
		if (json == null)
			return null;
		if (classOfT != null) {
			LogUtil.d("return decodeJson:" + json);
			return JsonUtil.jsonToObject(json, classOfT);
		}
		LogUtil.d("return json:" + json);
		return json;
	}

	public List<?> getExtraList(String name, TypeToken<?> token) {
		String encodeJson = getExtra(name);
		if (encodeJson == null)
			return null;
		LogUtil.d("return json:" + encodeJson);
		if (token != null) {
			return JsonUtil.jsonToList(encodeJson, token);
		}
		return (List<?>) JsonUtil.jsonToObject(encodeJson, ArrayList.class);
	}

	public String getCommand() {
		return this.command;
	}

	@Deprecated
	public void setCommand(String command) {
		this.command = command;
	}

	public String getDetail() {
		return this.detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

}

