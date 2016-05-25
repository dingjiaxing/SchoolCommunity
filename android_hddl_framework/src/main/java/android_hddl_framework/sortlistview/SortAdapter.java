package android_hddl_framework.sortlistview;

import java.util.List;

import com.example.android_hddl_framework.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

public class SortAdapter extends BaseAdapter implements SectionIndexer{
	private List<SortModel> list=null;
	private Context mContext;
	
	public SortAdapter(Context mContext,List<SortModel> list){
		this.mContext=mContext;
		this.list=list;
	}
	
	public void updateListView(List<SortModel> list){
		this.list=list;
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder=null;
		final SortModel mContent=list.get(position);
		if(convertView==null){
			holder=new ViewHolder();
			convertView=LayoutInflater.from(mContext).inflate(R.layout.sort_item, null);
			holder.tv_title=(TextView) convertView.findViewById(R.id.title);
			holder.tv_letter=(TextView) convertView.findViewById(R.id.catalog);
			holder.view=convertView.findViewById(R.id.view);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		//根据position获取分类的首字母的char asii值
		int section=getSectionForPosition(position);
		
		if(position==getPositionForSection(section)){
			holder.tv_letter.setVisibility(View.VISIBLE);
			holder.view.setVisibility(View.VISIBLE);
			holder.tv_letter.setText(mContent.getSortLetters());
		}else{
			holder.tv_letter.setVisibility(View.GONE);
			holder.view.setVisibility(View.GONE);
		}
		
		holder.tv_title.setText(this.list.get(position).getName());
		return convertView;
	}
	
	final static class ViewHolder{
		TextView tv_letter;
		TextView tv_title;
		View view;
	}
	
	@Override
	public Object[] getSections() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getPositionForSection(int sectionIndex) {
		// TODO Auto-generated method stub
		for(int i=0;i<getCount();i++){
			String sortStr=list.get(i).getSortLetters();
			char firstChar=sortStr.toUpperCase().charAt(0);
			if(firstChar==sectionIndex){
				return i;
			}
		}
		
		return -1;
	}
	
	
	/**
	 * 根据listView的当前位置获取分类的首字母的char ascii值
	 */
	@Override
	public int getSectionForPosition(int position) {
		// TODO Auto-generated method stub
		return list.get(position).getSortLetters().charAt(0);
	}
	
}
