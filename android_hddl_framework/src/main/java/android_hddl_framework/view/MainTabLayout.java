package android_hddl_framework.view;


import com.example.android_hddl_framework.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.TextView;


public class MainTabLayout extends ProposalTab {

	private ImageView icoImageView;
	private TextView titleTextView;
	private Drawable tabIcoUnSelected;
	private Drawable tabIcoSelect;
	public MainTabLayout(Context context) {
		super(context);
		initView();
		notifyClickChange();
	}
	
	public MainTabLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ProposalTabView);
		tabIcoSelect = a.getDrawable(R.styleable.ProposalTabView_tab_ico_selected);
		tabIcoUnSelected = a.getDrawable(R.styleable.ProposalTabView_tab_ico_unselected);
		a.recycle();
		initView();
		notifyClickChange();
	}

	@Override
	public void isLast(boolean isLast) {
	}
	
	public void initView(){
		inflate(getContext(), R.layout.main_tab, this);
		icoImageView = (ImageView) findViewById(R.id.ico);
		titleTextView = (TextView) findViewById(R.id.title);
	}

	@Override
	public void notifyClickChange() {
		titleTextView.setTextColor(isClick || isTouch ? tabTitleColorSelected : tabTitleColorUnselected);
		titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, tabTitleTextSize);
		if(isClick || isTouch){
			if(tabIcoSelect != null){
				icoImageView.setImageDrawable(tabIcoSelect);
			}
		}else{
			if(tabIcoUnSelected != null){
				icoImageView.setImageDrawable(tabIcoUnSelected);
			}
		}
		if (title != null) {
			titleTextView.setText(title);
		}
	}

}
