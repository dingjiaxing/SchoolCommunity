package cn.edu.shu.scommunity.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.edu.shu.scommunity.R;
import cn.edu.shu.scommunity.model.Message;
import cn.edu.shu.scommunity.adapter.MessageListAdapter;
import cn.edu.shu.scommunity.view.RefreshListener;
import cn.edu.shu.scommunity.view.XListView;

/**
 * Created by admin on 2016/4/14.
 */
public class MessageFragment extends Fragment implements RefreshListener {
    private View view;
    Activity mActivity;
    LinearLayout lay_back;
    TextView tv_title,tv_customer;
    XListView listView;
    MessageListAdapter adapter;
    List<Message> list;
    private int sumCount=0;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_message,null);
        mActivity=getActivity();
        findById();
        init();
        return view;
    }
    private void findById() {
        // TODO Auto-generated method stub
        listView=(XListView) view.findViewById(R.id.listview);
    }
    private void init() {
        // TODO Auto-generated method stub
        list=new ArrayList<Message>();

		for(int i=0;i<10;i++){
			Message message=new Message();
            message.setId(i);
            message.setReceive_name("张君怡");
            message.setContent("你做的怎么样了？");
			list.add(message);
		}

        adapter=new MessageListAdapter(mActivity,list);
        listView.setAdapter(adapter);
        listView.setOnRefreshListener(this);
//        sendInitList();

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
