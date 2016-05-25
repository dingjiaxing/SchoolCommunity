package android_hddl_framework.view;

import com.example.android_hddl_framework.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android_hddl_framework.util.DipUtil;



public class Switch extends LinearLayout{

	private boolean yes_no = false;
	
	OnClickListener l;
	onClickChangeListener changeListener;
	
	public Switch(Context context){
		this(context,null);
		setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	public Switch(Context context, AttributeSet as) {
		super(context,as);
		this.setLayoutParams(new LayoutParams(DipUtil.dipToPixels(60),DipUtil.dipToPixels(30)));
		this.setBackgroundResource(R.drawable.ios7_switch_off);
		setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	public void setOnClickListener(OnClickListener l)
	{
		this.l = l;
		
		super.setOnClickListener(new Click());
	}
	
	public class Click implements OnClickListener
	{
		public Click()
		{
		}

		public void onClick(View v)
		{

			setYesNo(!yes_no);
			
			if (l != null)
				l.onClick(Switch.this);
			if(changeListener!=null)
				changeListener.onClickListener(yes_no);
		}
	}
	
	public void setYesNo(Boolean value){
		this.yes_no = value;
		if(value){
			this.setBackgroundResource(R.drawable.ios7_switch_on);
		}else{
			this.setBackgroundResource(R.drawable.ios7_switch_off);	
		}
	}
	
	public boolean getYesNo(){
		return yes_no;
	}
	
	public interface onClickChangeListener{
		public void onClickListener(boolean yes_no);
	}


	public void setChangeListener(onClickChangeListener changeListener) {
		this.changeListener = changeListener;
	}
	
}
