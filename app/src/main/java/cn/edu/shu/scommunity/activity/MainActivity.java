package cn.edu.shu.scommunity.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;

import java.util.ArrayList;
import java.util.List;

import android_hddl_framework.util.TLUtil;
import android_hddl_framework.util.ViewPagerScroller;
import android_hddl_framework.view.ProposalFooter;
import android_hddl_framework.view.ProposalTab;
import cn.edu.shu.scommunity.R;
import cn.edu.shu.scommunity.fragment.ActivityFragment;
import cn.edu.shu.scommunity.fragment.InformFragment;
import cn.edu.shu.scommunity.fragment.MessageFragment;
import cn.edu.shu.scommunity.fragment.MyInfoFragment;

public class MainActivity extends BaseActivity  implements ProposalFooter.OnTabChangeListener,
        ViewPager.OnPageChangeListener {
    private ProposalFooter footerLay;
    private ViewPager mViewPager;
    private ViewPagerScroller scroller;
    private List<Fragment> fragments = new ArrayList<Fragment>();
    private FragmentPagerAdapter adapter;
    private ActivityFragment activityFragment;
    private InformFragment informFragment;
    private MessageFragment messageFragment;
    private MyInfoFragment myInfoFragment;
    private Context mContext;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = MainActivity.this;
        init();
        setListener();
    }
    public void setListener() {
        footerLay.setOnTabChangeListener(this);
        footerLay.setClickIndex(0);
        mViewPager.setOnPageChangeListener(this);
    }

    public void init() {
        footerLay = (ProposalFooter) findViewById(R.id.menu_layout);
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
        activityFragment = new ActivityFragment();
        myInfoFragment = new MyInfoFragment();
        informFragment = new InformFragment();
        messageFragment=new MessageFragment();

        fragments.add(activityFragment);

        fragments.add(informFragment);
        fragments.add(messageFragment);
        fragments.add(myInfoFragment);
        scroller = new ViewPagerScroller(mContext);
        scroller.setScrollDuration(400);
        scroller.initViewPagerScroll(mViewPager);

        adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                return fragments.size();
            }

            @Override
            public Fragment getItem(int position) {
                // TODO Auto-generated method stub
                return fragments.get(position);
            }
        };
        mViewPager.setAdapter(adapter);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                footerLay.setClickIndex(0, false);
                break;

            case 1:
                footerLay.setClickIndex(1, false);
                break;
            case 2:
                footerLay.setClickIndex(2, false);
                break;
            case 3:
                footerLay.setClickIndex(3, false);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onTabChange(int index, ProposalTab tab) {
        switch (index) {
            case 0:
                mViewPager.setCurrentItem(0);
                break;
            case 1:
                mViewPager.setCurrentItem(1);
                break;
            case 2:
                mViewPager.setCurrentItem(2);
                break;
            case 3:
                mViewPager.setCurrentItem(3);
                break;
        }
    }
    private long mkeyTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - mkeyTime > 2000) {
                mkeyTime = System.currentTimeMillis();
                TLUtil.showToast(MainActivity.this, "再按一次退出程序");
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
