package cn.edu.shu.scommunity.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import cn.edu.shu.scommunity.R;
import cn.edu.shu.scommunity.activity.ActivityDetailActivity;
import cn.edu.shu.scommunity.model.MyActivity;
import cn.edu.shu.scommunity.utils.Constant;
import cn.edu.shu.scommunity.utils.ImageLoaderDisplay;
import cn.edu.shu.scommunity.utils.PicUtil;

public class ActivityListAdapter extends BaseAdapter{
	private Context mContext;
	private List<MyActivity> list;
	private LayoutInflater inflater;


	public ActivityListAdapter() {
		super();
		// TODO Auto-generated constructor stub
	}


	public ActivityListAdapter(Context mContext, List<MyActivity> list) {
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
			convertView=inflater.inflate(R.layout.myactivity_list_item, null);
			holder.ll=(LinearLayout) convertView.findViewById(R.id.main);
//			holder.outline=(TextView) convertView.findViewById(R.id.message_item_outline);
			holder.title=(TextView) convertView.findViewById(R.id.message_item_title);
			holder.iv=(ImageView) convertView.findViewById(R.id.iv);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		MyActivity m=new MyActivity();
		m=list.get(position);
		holder.title.setText(m.getTitle());
//		holder.outline.setText(m.get);
		ImageLoaderDisplay.displayImage(mContext, Constant.IMG_URL+m.getUrl(), holder.iv);
//		holder.iv.setImageBitmap(PicUtil.drawableToBitamp(mContext.getResources().getDrawable(R.drawable.message_list_img)));
		holder.ll.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				TLUtil.showToast(mContext, "点击了："+position);
//				Bundle bundle=new Bundle();
//				bundle.putSerializable("messageId", list.get(position).getId());
//				mContext.startActivity(new Intent(mContext,ActivityDetailActivity.class).putExtra("id", list.get(position).getId()));
				mContext.startActivity(new Intent(mContext,ActivityDetailActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
			}
		});
		return convertView;
	}
	class ViewHolder{
		LinearLayout ll;
		TextView title;
		TextView outline;
		ImageView iv;
	}

}
