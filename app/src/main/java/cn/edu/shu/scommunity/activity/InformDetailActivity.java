package cn.edu.shu.scommunity.activity;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import cn.edu.shu.scommunity.R;
import cn.edu.shu.scommunity.model.InformBean;
import cn.edu.shu.scommunity.utils.PicUtil;

public class InformDetailActivity extends Activity{
	private TextView title,outline;
	private ImageView iv;
	private LinearLayout ll;
	private TextView tv_title,tv_customer;
	TextView content;
	private ScrollView scollview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_information_detail);
		findById();
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		
		tv_title.setText("通知详情");
		tv_customer.setVisibility(View.GONE);
		Intent intent=getIntent();
		int id=getIntent().getIntExtra("id",0);
//		Bundle bundle=intent.getBundleExtra("bundle");
//		InformBean message=(InformBean) bundle.get("message");
//		title.setText(message.getTITLE());
//		outline.setText(message.getOUT_LINE());
////		iv.setImageBitmap(bm);
////		ImageLoaderDisplay.displayImage(this, Constans.ImageUrl+message.getIMG_URL(), iv);
//		iv.setImageBitmap(PicUtil.drawableToBitamp(getResources().getDrawable(R.drawable.message_list_img)));
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
	}
}
