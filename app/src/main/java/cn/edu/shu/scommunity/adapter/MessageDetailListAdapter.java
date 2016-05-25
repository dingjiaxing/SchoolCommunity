package cn.edu.shu.scommunity.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import cn.edu.shu.scommunity.R;
import cn.edu.shu.scommunity.activity.MessageDetailActivity;
import cn.edu.shu.scommunity.model.MessageDetail;
import cn.edu.shu.scommunity.view.RoundImageView;

public class MessageDetailListAdapter extends BaseAdapter{
	private Context mContext;
	private List<MessageDetail> list;
	private LayoutInflater inflater;


	public MessageDetailListAdapter() {
		super();
		// TODO Auto-generated constructor stub
	}


	public MessageDetailListAdapter(Context mContext, List<MessageDetail> list) {
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
			holder.rl1= (RelativeLayout) convertView.findViewById(R.id.line1);
			holder.tv_name1=(TextView) convertView.findViewById(R.id.message_item_name1);
			holder.tv_content1=(TextView) convertView.findViewById(R.id.message_item_content1);
			holder.iv1= (RoundImageView) convertView.findViewById(R.id.iv2);
			holder.rl2= (RelativeLayout) convertView.findViewById(R.id.line2);
			holder.tv_name2=(TextView) convertView.findViewById(R.id.message_item_name2);
			holder.tv_content2=(TextView) convertView.findViewById(R.id.message_item_content2);
			holder.iv2= (RoundImageView) convertView.findViewById(R.id.iv2);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		MessageDetail m=new MessageDetail();
		m=list.get(position);
		if(m.getType()==0){
			holder.tv_name1.setText(m.getReceive_name());
			holder.tv_content1.setText(m.getContent());
		}else{
			holder.rl1.setVisibility(View.GONE);
			holder.rl2.setVisibility(View.VISIBLE);
			holder.tv_name2.setText("我");
			holder.tv_content2.setText(m.getContent());
		}

//		ImageLoaderDisplay.displayImage(mContext, Constans.ImageUrl+m.getIMG_URL(), holder.iv);
//		holder.iv.setImageBitmap(PicUtil.drawableToBitamp(mContext.getResources().getDrawable(R.drawable.message_list_img)));

		return convertView;
	}
	class ViewHolder{
		RelativeLayout rl1,rl2;
		RoundImageView iv1,iv2;
		TextView tv_name1,tv_name2;
		TextView tv_content1,tv_content2;
	}

}
