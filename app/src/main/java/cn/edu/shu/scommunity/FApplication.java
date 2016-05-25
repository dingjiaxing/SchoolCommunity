package cn.edu.shu.scommunity;

import android.app.Application;

import android_hddl_framework.util.SharePreferenceUtils;

/**
 * Created by admin on 2016/4/29.
 */
public class FApplication extends Application {
    private static FApplication mInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        SharePreferenceUtils.init(getApplicationContext());
//        sp.i
    }
    public static FApplication getInstance() {
        if (null == mInstance) {
            mInstance = new FApplication();
        }
        return mInstance;
    }
}
