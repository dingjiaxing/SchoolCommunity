package android_hddl_framework.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class SlideScrollView extends ScrollView{
	
	private int height;
	private int halfHeight;
	private int mScreenHeight;
	private LinearLayout wrapper;
	private ViewGroup viewGroup1;
	private ViewGroup viewGroup2;

	public SlideScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		mScreenHeight=getScreenHeight(getContext());
		height=getHeight();
		halfHeight=height/2;
		wrapper=(LinearLayout) this.getChildAt(0);
		viewGroup1=(ViewGroup) wrapper.getChildAt(0);
		viewGroup2=(ViewGroup) wrapper.getChildAt(1);
		viewGroup1.getLayoutParams().height=height;
		viewGroup2.getLayoutParams().height=height;
	}
	
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);
		if(changed){
			this.scrollTo(0, 0);
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		int action=ev.getAction();
		switch (action) {
//		case MotionEvent.ACTION_UP:
//			int scrollY=getScrollY();
//			if(scrollY>halfHeight){
//				this.smoothScrollTo(0, height);
//				
//			}else {
//				this.smoothScrollTo(0, 0);
//			}
//			
//			break;
		}
		return super.onTouchEvent(ev);
	}
	
	public static int getScreenHeight(Context context)
	{
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.heightPixels;
	}
}
