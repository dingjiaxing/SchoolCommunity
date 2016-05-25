package cn.edu.shu.scommunity.activity;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.w3c.dom.ls.LSInput;

import java.util.ArrayList;
import java.util.List;

import cn.edu.shu.scommunity.R;
import cn.edu.shu.scommunity.adapter.MessageDetailListAdapter;
import cn.edu.shu.scommunity.model.MessageBean;
import cn.edu.shu.scommunity.model.MessageDetail;
import cn.edu.shu.scommunity.utils.PicUtil;
import cn.edu.shu.scommunity.view.RefreshListener;
import cn.edu.shu.scommunity.view.XListView;

public class MessageDetailActivity extends Activity implements OnClickListener,RefreshListener {

	private TextView tv_title,tv_customer;
	private EditText et;
	private Button btn;
	private XListView listView;
	private List<MessageDetail> list;
	private MessageDetailListAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message_detail);
		findById();
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		
		tv_title.setText("消息详情");
		tv_customer.setVisibility(View.GONE);
//		Intent intent=getIntent();
//		Bundle bundle=intent.getBundleExtra("bundle");
//		MessageBean message=(MessageBean) bundle.get("message");
		list=new ArrayList<>();
		for(int i=0;i<6;i++){
			MessageDetail md=new MessageDetail();
			md.setId(i);
			md.setContent("你做的怎么样了");
			md.setReceive_name("张君怡");
			md.setCreatetime("2016-04-30 22:17");
			if(i%2==0){
				md.setType(0);
			}else{
				md.setType(1);
			}
			list.add(md);
		}
		adapter=new MessageDetailListAdapter(MessageDetailActivity.this,list);
		listView.setAdapter(adapter);
		listView.setOnRefreshListener(this);
	}

	private void findById() {
		// TODO Auto-generated method stub
		tv_title=(TextView) findViewById(R.id.tv_title);
		tv_customer=(TextView) findViewById(R.id.tv_customer);
		et= (EditText) findViewById(R.id.et);
		btn= (Button) findViewById(R.id.btn);
		listView= (XListView) findViewById(R.id.listview);
	}

	@Override
	public void onClick(View v) {

	}

	@Override
	public void onDownPullRefresh() {
		listView.hideHeaderView();
	}

	@Override
	public void onLoadingMore() {
		listView.hideFooterView();
	}
}
