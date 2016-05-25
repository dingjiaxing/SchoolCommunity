package android_hddl_framework.view;



import com.example.android_hddl_framework.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

public abstract class ProposalTab extends RelativeLayout implements OnClickListener{
	
	public int tabBackgroundUnselected = R.color.proposal_tab_background_unselected;
	public int tabBackgroundSelected;
	public Drawable tabBackgroundUnselectedDrawable;
	public Drawable tabBackgroundSelectedDrawable;
	public int tabTitleColorUnselected = android.R.color.white;
	public int tabTitleColorSelected = R.color.proposal_tab_tell_line_selected;
	public float tabTitleTextSize = 18;
	public boolean touchEable = true;
	
	public float getTabTitleTextSize() {
		return tabTitleTextSize;
	}

	public void setTabTitleTextSize(float tabTitleTextSize) {
		this.tabTitleTextSize = tabTitleTextSize;
	}

	public Object code;
	
	public Object getCode() {
		return code;
	}

	public void setCode(Object code) {
		this.code = code;
	}

	public boolean isClick = false;
	
	public boolean isTouch = false;
	public String title = null;

	public OnTabClickListener onClickListener;
	public ProposalFooter.OnTabClickListener onTabClickListener;

	public ProposalTab(Context context){
		super(context);
		setOnClickListener(this);
	}
	
	public ProposalTab(Context context, AttributeSet attrs) {
		super(context, attrs);
		int colorId;
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ProposalTabView);
		tabBackgroundSelectedDrawable = a.getDrawable(R.styleable.ProposalTabView_tab_background_selected);
		tabBackgroundUnselectedDrawable = a.getDrawable(R.styleable.ProposalTabView_tab_background_unselected);
		if(tabBackgroundSelectedDrawable == null){
			colorId = a.getResourceId(R.styleable.ProposalTabView_tab_background_selected, -1);
			if(colorId != -1){
				tabBackgroundSelectedDrawable = getResources().getDrawable(colorId);
			}
		}
		if(tabBackgroundSelectedDrawable == null){
			tabBackgroundSelected = a.getColor(R.styleable.ProposalTabView_tab_background_selected, -1);
			if (tabBackgroundSelected == -1) {
				colorId = a.getResourceId(R.styleable.ProposalTabView_tab_background_selected, R.color.proposal_tab_background_selected);
				tabBackgroundSelected = getResources().getColor(colorId);
			}
		}
		if(tabBackgroundUnselectedDrawable == null){
			colorId = a.getResourceId(R.styleable.ProposalTabView_tab_background_unselected, -1);
			if(colorId != -1){
				tabBackgroundUnselectedDrawable = getResources().getDrawable(colorId);
			}
		}
		if(tabBackgroundUnselectedDrawable == null){
			tabBackgroundUnselected = a.getColor(R.styleable.ProposalTabView_tab_background_unselected, -1);
			if (tabBackgroundUnselected == -1) {
				colorId = a.getResourceId(R.styleable.ProposalTabView_tab_background_unselected, R.color.proposal_tab_background_unselected);
				tabBackgroundUnselected = getResources().getColor(colorId);
			}
		}
		
		tabTitleColorSelected = a.getColor(R.styleable.ProposalTabView_tab_title_color_selected, -1);
		if (tabTitleColorSelected == -1) {
			colorId = a.getResourceId(R.styleable.ProposalTabView_tab_title_color_selected, R.color.proposal_tab_tell_line_selected);
			tabTitleColorSelected = getResources().getColor(colorId);
		}
		tabTitleColorUnselected = a.getColor(R.styleable.ProposalTabView_tab_title_color_unselected, -1);
		if (tabTitleColorUnselected == -1) {
			colorId = a.getResourceId(R.styleable.ProposalTabView_tab_title_color_unselected, android.R.color.white);
			tabTitleColorUnselected = getResources().getColor(colorId);
		}
		int strId = a.getResourceId(R.styleable.ProposalTabView_tab_title, -1);
		if (strId != -1) {
			title = getResources().getString(strId);
		} else {
			title = a.getString(R.styleable.ProposalTabView_tab_title);
		}
		tabTitleTextSize = a.getDimensionPixelSize(R.styleable.ProposalTabView_tab_title_text_size, 18);
		a.recycle();
		setOnClickListener(this);
	}
	
	public interface OnTabClickListener {
		public void onClick();
	}
	
	public abstract void isLast(boolean isLast);
	
	public ProposalFooter.OnTabClickListener getOnTabClickListener() {
		return onTabClickListener;
	}

	public void setOnTabClickListener(
			ProposalFooter.OnTabClickListener onTabClickListener) {
		this.onTabClickListener = onTabClickListener;
	}
	
	public int getTabBackgroundUnselected() {
		return tabBackgroundUnselected;
	}

	public void setTabBackgroundUnselected(int tabBackgroundUnselected) {
		this.tabBackgroundUnselected = tabBackgroundUnselected;
		tabBackgroundUnselectedDrawable = null;
		notifyClickChange();
	}

	public int getTabBackgroundSelected() {
		return tabBackgroundSelected;
	}

	public void setTabBackgroundSelected(int tabBackgroundSelected) {
		this.tabBackgroundSelected = tabBackgroundSelected;
		tabBackgroundSelectedDrawable = null;
		notifyClickChange();
	}
	
	public Drawable getTabBackgroundUnselectedDrawable() {
		return tabBackgroundUnselectedDrawable;
	}

	public void setTabBackgroundUnselectedDrawable(
			Drawable tabBackgroundUnselectedDrawable) {
		this.tabBackgroundUnselectedDrawable = tabBackgroundUnselectedDrawable;
	}
	
	public void setTabBackgroundUnselectedDrawable(int drawId){
		this.tabBackgroundUnselectedDrawable = getResources().getDrawable(drawId);
		notifyClickChange();
	}

	public Drawable getTabBackgroundSelectedDrawable() {
		return tabBackgroundSelectedDrawable;
	}

	public void setTabBackgroundSelectedDrawable(
			Drawable tabBackgroundSelectedDrawable) {
		this.tabBackgroundSelectedDrawable = tabBackgroundSelectedDrawable;
		notifyClickChange();
	}
	
	public void setTabBackgroundSelectedDrawable(int drawId){
		this.tabBackgroundSelectedDrawable = getResources().getDrawable(drawId);
		notifyClickChange();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
		notifyClickChange();
	}

	public OnTabClickListener getClickListener() {
		return onClickListener;
	}

	public void setClickListener(OnTabClickListener onClickListener) {
		this.onClickListener = onClickListener;
	}

	public int getTabTitleColorUnselected() {
		return tabTitleColorUnselected;
	}

	public void setTabTitleColorUnselected(int tabTitleColorUnselected) {
		this.tabTitleColorUnselected = tabTitleColorUnselected;
		notifyClickChange();
	}

	public int getTabTitleColorSelected() {
		return tabTitleColorSelected;
	}

	public void setTabTitleColorSelected(int tabTitleColorSelected) {
		this.tabTitleColorSelected = tabTitleColorSelected;
		notifyClickChange();
	}
	
	@Override
	public void onClick(View v) {
		if (!isClick) {
			isClick = true;
			notifyClickChange();
			if(onTabClickListener != null){
				onTabClickListener.onTabClick(this);
			}
			if (onClickListener != null) {
				onClickListener.onClick();
			}
		}
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		boolean flag = super.onTouchEvent(event);
		if(touchEable){
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				isTouch = true;
				notifyClickChange();
				break;
			case MotionEvent.ACTION_UP:
				isTouch = false;
				notifyClickChange();
				break;
			}
		}
		return flag;
	}
	
	public boolean isClick() {
		return isClick;
	}

	public void setClick(boolean isClick, boolean isChange) {
		if(isClick && isChange){
			onClick(this);
		}else{
			this.isClick = isClick;
			notifyClickChange();
		}
	}
	
	public void setClick(boolean isClick) {
		setClick(isClick, true);
	}
	
	public abstract void notifyClickChange();
}
