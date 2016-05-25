package cn.edu.shu.scommunity.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.TypeInfo;

import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.security.KeyRep;
import java.util.ArrayList;
import java.util.List;

import android_hddl_framework.util.SharePreferenceUtils;
import android_hddl_framework.util.TLUtil;
import cn.edu.shu.scommunity.R;
import cn.edu.shu.scommunity.activity.LoginActivity;
import cn.edu.shu.scommunity.activity.MainActivity;
import cn.edu.shu.scommunity.adapter.ActivityListAdapter;
import cn.edu.shu.scommunity.model.MyActivity;
import cn.edu.shu.scommunity.model.UserInfo;
import cn.edu.shu.scommunity.utils.Constant;
import cn.edu.shu.scommunity.utils.http.HttpUtil;
import cn.edu.shu.scommunity.view.RefreshListener;
import cn.edu.shu.scommunity.view.XListView;

/**
 * Created by admin on 2016/4/14.
 */
public class ActivityFragment extends Fragment implements RefreshListener {
    private View view;
    Activity mActivity;
    LinearLayout lay_back;
    TextView tv_title,tv_customer;
    XListView listView;
    ActivityListAdapter adapter;
    List<MyActivity> list;
    private int sumCount=0;
    private UserInfo user;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_activity,null);
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
        user=SharePreferenceUtils.getObject("user",UserInfo.class);
        list=new ArrayList<MyActivity>();

//        for(int i=0;i<8;i++){
//            MyActivity myActivity=new MyActivity();
//            myActivity.setId(i);
//            myActivity.setTitle( i+".文学经典的当代之用：莎士比亚个案的启示");
//            myActivity.setUpdatetime("");
//            myActivity.setUrl("");
//            list.add(myActivity);
//        }

        adapter=new ActivityListAdapter(getActivity().getApplicationContext(),list);
        listView.setAdapter(adapter);
        listView.setOnRefreshListener(this);
        getActivityList();
//        sendInitList();

    }

    @Override
    public void onDownPullRefresh() {
        getActivityList();


    }

    @Override
    public void onLoadingMore() {

        getActivityListMore();
    }
    // 登陆返回信息，将数据加入到Sp中
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            listView.hideHeaderView();
            try {
                if (msg.what == HttpUtil.SUCCESS) {
                    Gson gson = new Gson();
//                    JsonObject json= (JsonObject) gson.fromJson(msg.getData().getString("str"),JsonObject.class);
                    JsonObject json= (JsonObject) msg.obj;
                    if (json.get("result").getAsInt() == 1) {
                        String u=json.get("activity").toString();
                        Type type=new TypeToken<List<MyActivity>>(){}.getType();
                        list = gson.fromJson(u, type);
                        adapter=new ActivityListAdapter(getActivity().getApplicationContext(),list);
                        listView.setAdapter(adapter);
//                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    } else {
                        Log.e("TLLLLT", json.get("info").getAsString());
                        TLUtil.showToast(mActivity, json.get("info").getAsString());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    public void getActivityList(){
        try {
            String param="token="+ URLEncoder.encode(user.getToken(),"utf-8")+"&type1="+URLEncoder.encode("1","utf-8")
                    +"&type2="+URLEncoder.encode("0","utf-8")+"&type="+URLEncoder.encode("0","utf-8")
                    +"&activity_id="+URLEncoder.encode("0","utf-8");
            HttpUtil.sendHttp(getActivity(), handler, "登陆中...",param ,
                    Constant.getActivityList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // 登陆返回信息，将数据加入到Sp中
    private Handler handlerMore = new Handler() {
        public void handleMessage(Message msg) {
            listView.hideFooterView();
            try {
                if (msg.what == HttpUtil.SUCCESS) {
                    Gson gson = new Gson();
//                    JsonObject json= (JsonObject) gson.fromJson(msg.getData().getString("str"),JsonObject.class);
                    JsonObject json= (JsonObject) msg.obj;
                    if (json.get("result").getAsInt() == 1) {
                        String u=json.get("activity").toString();
                        Type type=new TypeToken<List<MyActivity>>(){}.getType();
                        List<MyActivity> list2 = gson.fromJson(u, type);
                        list.addAll(list2);
                        adapter=new ActivityListAdapter(getActivity().getApplicationContext(),list);
//                        listView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
//                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    } else {
                        Log.e("TLLLLT", json.get("info").getAsString());
                        TLUtil.showToast(mActivity, json.get("info").getAsString());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    public void getActivityListMore(){
        try {
            String id="";
            if(list.size()>0){
                id=list.get(list.size()-1).getId();
            }
            String param="token="+ URLEncoder.encode(user.getToken(),"utf-8")+"&type1="+URLEncoder.encode("2","utf-8")
                    +"&type2="+URLEncoder.encode("0","utf-8")+"&type="+URLEncoder.encode("0","utf-8")
                    +"&activity_id="+URLEncoder.encode(id,"utf-8");
            HttpUtil.sendHttp(getActivity(), handlerMore, "登陆中...",param ,
                    Constant.getActivityList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
