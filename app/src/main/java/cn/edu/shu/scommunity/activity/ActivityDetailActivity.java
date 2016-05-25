package cn.edu.shu.scommunity.activity;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import android_hddl_framework.util.SharePreferenceUtils;
import android_hddl_framework.util.TLUtil;
import cn.edu.shu.scommunity.R;
import cn.edu.shu.scommunity.model.UserInfo;
import cn.edu.shu.scommunity.utils.Constant;
import cn.edu.shu.scommunity.utils.JackUtil;
import cn.edu.shu.scommunity.utils.PicUtil;
import cn.edu.shu.scommunity.utils.http.HttpUtil;

public class ActivityDetailActivity extends Activity{
	private TextView title,outline;
	private ImageView iv;
	private LinearLayout ll;
	private TextView tv_title,tv_customer;
	TextView content;
	private ScrollView scollview;
	Context context;
	private int activity_id;
	private Button btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context=this;
		setContentView(R.layout.activity_myactivity_detail);
		findById();
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		activity_id=getIntent().getIntExtra("id",0);
		tv_title.setText("活动详情");
		tv_customer.setVisibility(View.GONE);
		Intent intent=getIntent();

//		iv.setImageBitmap(bm);
//		ImageLoaderDisplay.displayImage(this, Constans.ImageUrl+message.getIMG_URL(), iv);
		iv.setImageBitmap(PicUtil.drawableToBitamp(getResources().getDrawable(R.drawable.message_list_img)));
//		content.setText(message.getCONTENT());
		ll.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		scollview.scrollTo(0, 0);
	}

	private void findById() {
		// TODO Auto-generated method stub
		title=(TextView) findViewById(R.id.message_detail_title);
		outline=(TextView) findViewById(R.id.message_detail_outline);
		iv=(ImageView) findViewById(R.id.message_detail_iv);
		content=(TextView) findViewById(R.id.message_detail_content);
		ll=(LinearLayout) findViewById(R.id.lay_back);
		tv_title=(TextView) findViewById(R.id.tv_title);
		tv_customer=(TextView) findViewById(R.id.tv_customer);
		scollview=(ScrollView) findViewById(R.id.scrollview);
		btn= (Button) findViewById(R.id.btn);

		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				JackUtil.showToast(ActivityDetailActivity.this,"报名成功");
				ActivityDetailActivity.this.finish();
			}
		});
	}
	// 登陆返回信息，将数据加入到Sp中
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			try {
				if (msg.what == HttpUtil.SUCCESS) {
					com.alibaba.fastjson.JSONObject json = (com.alibaba.fastjson.JSONObject) msg.obj;
					Log.e("TLLLL", msg.obj.toString());
					if (json.getIntValue("status") == 1) {

						Gson gson = new Gson();
						UserInfo user = gson.fromJson(json.getString("result")
								.toString(), UserInfo.class);
						SharePreferenceUtils.saveSharePerfence("user", user);
						TLUtil.showToast(context, "用户登录成功");
					} else {
						Log.e("TLLLLT", json.getString("message"));
						TLUtil.showToast(context, json.getString("message"));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
	public void queryActivityDetail(){
		com.alibaba.fastjson.JSONObject json = new com.alibaba.fastjson.JSONObject();
		Map<String, Object> map = new HashMap<String, Object>();
		json.put("activity_id", activity_id);
//		json.put("code", password);
		// json.put("password", password);
		try {
			map.put("json", URLEncoder.encode(json.toJSONString(), "utf-8"));
			HttpUtil.sendHttp(ActivityDetailActivity.this, handler, "登陆中...", "",
					Constant.getActivityDetail);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
