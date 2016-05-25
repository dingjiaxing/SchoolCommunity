package android_hddl_framework.verticalslide;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

public class CustDownScrollView extends ScrollView {
	boolean allowDragBottom = true; // 如果是true，则允许拖动至底部的下一页
	float downY = 0;
	boolean needConsumeTouch = true; // 是否需要承包touch事件，needConsumeTouch一旦被定性，则不会更改
	int maxScroll = -1; // 最大滑动距离
	int height;
	View view;

	public CustDownScrollView(Context arg0) {
		this(arg0, null);
	}

	public CustDownScrollView(Context arg0, AttributeSet arg1) {
		this(arg0, arg1, 0);
	}

	public CustDownScrollView(Context arg0, AttributeSet arg1, int arg2) {
		super(arg0, arg1, arg2);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			downY = ev.getRawY();
			needConsumeTouch = true; // 默认情况下，scrollView内部的滚动优先，默认情况下由该ScrollView去消费touch事件

			if (maxScroll > 0
					&& getScrollY() + getMeasuredHeight() >= maxScroll - 2) {
				// 允许向上拖动底部的下一页
				allowDragBottom = true;
			} else {
				// 不允许向上拖动底部的下一页
				allowDragBottom = true;
			}
		} else if (ev.getAction() == MotionEvent.ACTION_MOVE) {
			if (!needConsumeTouch) {
				// 在最顶端且向上拉了，则这个touch事件交给父类去处理
				getParent().requestDisallowInterceptTouchEvent(false);
				return false;
			} else if (allowDragBottom) {
				// needConsumeTouch尚未被定性，此处给其定性
				// 允许拖动到底部的下一页，而且又向上拖动了，就将touch事件交给父view
				if (downY - ev.getRawY() > 20) {
					// flag设置，由父类去消费
					needConsumeTouch = false;
					getParent().requestDisallowInterceptTouchEvent(false);
					Log.e("tag", needConsumeTouch+"-------------"+downY+"-----"+ev.getRawY()+"------"+(downY-ev.getRawY()));
					return false;
				}
			}
		}

		// 通知父view是否要处理touch事件
		getParent().requestDisallowInterceptTouchEvent(needConsumeTouch);
		return super.dispatchTouchEvent(ev);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		height=getHeight();
		view=this.getChildAt(0);
		view.getLayoutParams().height=height;
		
		
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		if (maxScroll < 0) {
			maxScroll = computeVerticalScrollRange();
			Log.e("tag", maxScroll+"------------");
		}

		super.onScrollChanged(l, t, oldl, oldt);
	}
}
