package cn.edu.shu.scommunity.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.edu.shu.scommunity.R;
import cn.edu.shu.scommunity.activity.MessageDetailActivity;
import cn.edu.shu.scommunity.model.Message;
import cn.edu.shu.scommunity.utils.PicUtil;
import cn.edu.shu.scommunity.view.RoundImageView;

public class MessageListAdapter extends BaseAdapter{
	private Context mContext;
	private List<Message> list;
	private LayoutInflater inflater;
	

	public MessageListAdapter() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public MessageListAdapter(Context mContext, List<Message> list) {
		super();
		this.mContext = mContext;
		this.list = list;
		inflater=LayoutInflater.from(mContext);
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder=null;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=inflater.inflate(R.layout.message_list_item, null);
			holder.rl= (RelativeLayout) convertView.findViewById(R.id.main);
			holder.tv_name=(TextView) convertView.findViewById(R.id.message_item_name1);
			holder.tv_content=(TextView) convertView.findViewById(R.id.message_item_content1);
			holder.iv= (RoundImageView) convertView.findViewById(R.id.iv1);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		Message m=new Message();
		m=list.get(position);
		holder.tv_name.setText(m.getReceive_name());
		holder.tv_content.setText(m.getContent());
//		ImageLoaderDisplay.displayImage(mContext, Constans.ImageUrl+m.getIMG_URL(), holder.iv);
//		holder.iv.setImageBitmap(PicUtil.drawableToBitamp(mContext.getResources().getDrawable(R.drawable.message_list_img)));
		holder.rl.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				TLUtil.showToast(mContext, "点击了："+position);
//				Bundle bundle=new Bundle();
//				bundle.putSerializable("message", list.get(position));
				mContext.startActivity(new Intent(mContext,MessageDetailActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
			}
		});
		return convertView;
	}
	class ViewHolder{
		RelativeLayout rl;
		RoundImageView iv;
		TextView tv_name;
		TextView tv_content;
	}

}
