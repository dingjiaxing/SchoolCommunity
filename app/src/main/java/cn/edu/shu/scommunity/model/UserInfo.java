package cn.edu.shu.scommunity.model;

import java.io.Serializable;
public class UserInfo implements Serializable {
	private String sid;	//学号
	private String name;	//姓名
	private String email;	//邮箱
	private String phone;	//手机号
	private String grade;	//年级
	private String classid;	//班级
	private String pwd;	//班级
	private String address;		//现住址
	private String token;		//令牌
	private String description;		//简介
	private String type;	//类型
	private String state;
	private String del;

	public UserInfo() {
	}

	public UserInfo(String sid, String name, String email, String phone, String grade, String classid, String pwd, String address, String token, String description, String type, String state, String del) {
		this.sid = sid;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.grade = grade;
		this.classid = classid;
		this.pwd = pwd;
		this.address = address;
		this.token = token;
		this.description = description;
		this.type = type;
		this.state = state;
		this.del = del;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getClassid() {
		return classid;
	}

	public void setClassid(String classid) {
		this.classid = classid;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDel() {
		return del;
	}

	public void setDel(String del) {
		this.del = del;
	}
}
