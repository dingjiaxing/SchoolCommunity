package android_hddl_framework.view;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class ProposalFooter extends LinearLayout {
	
	private ProposalTab currTab;
	private int preIndex;
	
	private OnTabChangeListener listener;
	
	public void setOnTabChangeListener(OnTabChangeListener listener){
		this.listener = listener;
		init();
	}
	
	public OnTabChangeListener getOnTabChangeListener(){
		return listener;
	}
	
	public OnTabClickListener getOnTabClickListener() {
		return onTabClickListener;
	}

	public ProposalFooter(Context context){
		super(context);
	}

	public ProposalTab getCurrTab() {
		return currTab;
	}

	public ProposalFooter(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	private OnTabClickListener onTabClickListener = new OnTabClickListener() {
		
		@Override
		public void onTabClick(ProposalTab tab) {
			ProposalTab temp = currTab;
			currTab = tab;
			final int count = getChildCount();
			int index = 0;
			for(int i = 0; i < count; i++){
				ProposalTab tab1 = (ProposalTab) getChildAt(i);
				if(tab1 != tab){
					tab1.setClick(false);
				}else{
					index = i;
				}
				if(tab1 == temp){
					preIndex = i;
				}
			}
			if(listener != null){
				listener.onTabChange(index, tab);
			}
		}
	};

	public void init(){
		int count = getChildCount();
		for(int i = 0; i < count; i++){
			ProposalTab tab = (ProposalTab) getChildAt(i);
			if(tab.getOnTabClickListener() == null){
				tab.setOnTabClickListener(onTabClickListener);
			}
			if(i != count -1){
				tab.isLast(false);
			}else{
				tab.isLast(true);
			}
		}
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		init();
	}
	
	interface OnTabClickListener{
		public void onTabClick(ProposalTab tab);
	}
	
	public interface OnTabChangeListener{
		public void onTabChange(int index, ProposalTab tab);
	}
	
	public void setClickIndex(int index){
		setClickIndex(index, true);
	}
	
	public void setClickIndex(int index, boolean isChange){
		int count = getChildCount();
		for(int i = 0; i < count; i++){
			ProposalTab tab = (ProposalTab) getChildAt(i);
			if(i == index){
				tab.setClick(true, isChange);
			}else{
				tab.setClick(false);
			}
		}
	}
	
	public int getPreIndex() {
		return preIndex;
	}

	public void setPreIndex(int preIndex) {
		this.preIndex = preIndex;
	}
	
	public void addTab(ProposalTab tab, int leftMargin, int topMargin, int rightMargin, int bottomMargin){
		LinearLayout.LayoutParams lp = new LayoutParams(0, LayoutParams.MATCH_PARENT, 1);
		lp.leftMargin = leftMargin;
		lp.rightMargin = rightMargin;
		lp.topMargin = topMargin;
		lp.bottomMargin = bottomMargin;
		addView(tab, lp);
		init();
	}
	
	public void addTab(ProposalTab tab, int leftMargin, int topMargin, int rightMargin, int bottomMargin, int index){
		LinearLayout.LayoutParams lp = new LayoutParams(0, LayoutParams.MATCH_PARENT, 1);
		lp.leftMargin = leftMargin;
		lp.rightMargin = rightMargin;
		lp.topMargin = topMargin;
		lp.bottomMargin = bottomMargin;
		addView(tab, index, lp);
		init();
	}
	
	public void addTab(ProposalTab tab, LayoutParams lp, int index){
		addView(tab, index, lp);
		init();
	}
	
	public void addTab(ProposalTab tab, LayoutParams lp){
		addView(tab, lp);
		init();
	}
	
	public List<ProposalTab> getTabList(){
		final int count = getChildCount();
		List<ProposalTab> tabList = new ArrayList<ProposalTab>();
		for(int i = 0; i < count; i++){
			ProposalTab tab = (ProposalTab) getChildAt(i);
			tabList.add(tab);
		}
		return tabList;
	}
	
	public ProposalTab getTabAt(int i){
		return (ProposalTab) getChildAt(i);
	}
}
