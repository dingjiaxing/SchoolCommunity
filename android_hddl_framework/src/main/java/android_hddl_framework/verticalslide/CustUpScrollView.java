package android_hddl_framework.verticalslide;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class CustUpScrollView extends ScrollView {
	boolean allowDragTop = true; // 如果是true，则允许拖动至底部的下一页
	float downY = 0;
	boolean needConsumeTouch = true; // 是否需要承包touch事件，needConsumeTouch一旦被定性，则不会更改
	private ScrollListener scrollListener;
	private boolean oneTime=true;
	private Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			oneTime=true;
		}
	};
	
	public CustUpScrollView(Context arg0) {
		this(arg0, null);
	}

	public CustUpScrollView(Context arg0, AttributeSet arg1) {
		this(arg0, arg1, 0);
	}

	public CustUpScrollView(Context arg0, AttributeSet arg1, int arg2) {
		super(arg0, arg1, arg2);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			downY = ev.getRawY();
			needConsumeTouch = true; // 默认情况下，listView内部的滚动优先，默认情况下由该listView去消费touch事件
			allowDragTop = isAtTop();
		} else if (ev.getAction() == MotionEvent.ACTION_MOVE) {
			if (!needConsumeTouch) {
				// 在最顶端且向上拉了，则这个touch事件交给父类去处理
				getParent().requestDisallowInterceptTouchEvent(false);
				return false;
			} else if (allowDragTop) {
				// needConsumeTouch尚未被定性，此处给其定性
				// 允许拖动到底部的下一页，而且又向上拖动了，就将touch事件交给父view
				if (ev.getRawY() - downY > 2) {
					// flag设置，由父类去消费
					needConsumeTouch = false;
					getParent().requestDisallowInterceptTouchEvent(false);
					return false;
				}
			}
		}

		// 通知父view是否要处理touch事件
		getParent().requestDisallowInterceptTouchEvent(needConsumeTouch);
		return super.dispatchTouchEvent(ev);
	}

	/**
	 * 判断listView是否在顶部
	 * 
	 * @return 是否在顶部
	 */
	private boolean isAtTop() {
		return getScrollY() == 0;
	}
	
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		// TODO Auto-generated method stub
		if (t + getHeight() >= computeVerticalScrollRange()) {
			if(scrollListener!=null)
				if(oneTime){
					oneTime=false;
					scrollListener.scrollToButtom();
					handler.postDelayed(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							oneTime=true;
						}
					}, 1000);
				}
		}
		super.onScrollChanged(l, t, oldl, oldt);
	}
	
	public interface ScrollListener{
		public void scrollToButtom();
	}

	public void setScrollListener(ScrollListener scrollListener) {
		this.scrollListener = scrollListener;
	}
	
	
}
