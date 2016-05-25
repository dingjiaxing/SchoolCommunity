package cn.edu.shu.scommunity.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import android_hddl_framework.util.SharePreferenceUtils;
import android_hddl_framework.util.TLUtil;
import cn.edu.shu.scommunity.R;
import cn.edu.shu.scommunity.model.UserInfo;
import cn.edu.shu.scommunity.utils.Constant;
import cn.edu.shu.scommunity.utils.http.HttpRequest;
import cn.edu.shu.scommunity.utils.http.HttpUtil;

/**
 * Created by admin on 2016/4/14.
 */
public class LoginActivity extends Activity implements View.OnClickListener{
    Context context;
    Button btn;
    private EditText edit_username,edit_userpwd;
    @Override
    public void onCreate(Bundle savedInstanceStatee) {
        super.onCreate(savedInstanceStatee);
        context=this;
        setContentView(R.layout.activity_login);
        btn= (Button) findViewById(R.id.btn_submit);
        edit_username= (EditText) findViewById(R.id.edit_username);
        edit_userpwd= (EditText) findViewById(R.id.edit_userpwd);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_submit:
                if(check()){
                    login();
                }
//                startActivity(new Intent(LoginActivity.this,MainActivity.class));
        }
    }
    // 登陆返回信息，将数据加入到Sp中
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            try {
                if (msg.what == HttpUtil.SUCCESS) {
                    Gson gson = new Gson();
//                    JsonObject json= (JsonObject) gson.fromJson(msg.getData().getString("str"),JsonObject.class);
                    JsonObject json= (JsonObject) msg.obj;
                    if (json.get("result").getAsInt() == 1) {
                        String u=json.get("user").toString();
                        UserInfo user = gson.fromJson(u, UserInfo.class);
                        SharePreferenceUtils.saveSharePerfence("user", user);
                        TLUtil.showToast(context, "用户登录成功");
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    } else {
                        Log.e("TLLLLT", json.get("info").getAsString());
                        TLUtil.showToast(context, json.get("info").getAsString());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    public void login(){
        final String id=edit_username.getText().toString().trim();
        final String pwd= edit_userpwd.getText().toString().trim();
        try {
            String param="id="+URLEncoder.encode(id,"utf-8")+"&pwd="+URLEncoder.encode(pwd,"utf-8")+"&token=\"\"";
            HttpUtil.sendHttp(LoginActivity.this, handler, "登陆中...",param ,
                    Constant.login);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private Boolean check(){
        if(edit_username.getText().toString().trim().equals("")){
            TLUtil.showToast(context,"用户名不能为空");
            return false;
        }
        if(edit_userpwd.getText().toString().trim().equals("")){
            TLUtil.showToast(context,"密码不能为空");
            return false;
        }
        return true;
    }
}
