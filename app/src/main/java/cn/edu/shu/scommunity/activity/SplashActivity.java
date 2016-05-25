package cn.edu.shu.scommunity.activity;

import android.app.Activity;
import android.app.LauncherActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import cn.edu.shu.scommunity.R;

/**
 * Created by admin on 2016/4/14.
 */
public class SplashActivity extends Activity {
    private ImageView iv1,iv2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        iv1=(ImageView) findViewById(R.id.launcher_iv_1);
        iv2=(ImageView) findViewById(R.id.launcher_iv_2);
        //为艺术字图片设置一个透明度渐变的动画，让人看起来不那么突兀
        AlphaAnimation aa=new AlphaAnimation(0.3f, 1.0f);
        aa.setDuration(1200);
        iv1.startAnimation(aa);
        iv2.startAnimation(aa);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("isChat", false);
                startActivity(intent);
                finish();
            }
        }, 1 * 1500);
    }
}
