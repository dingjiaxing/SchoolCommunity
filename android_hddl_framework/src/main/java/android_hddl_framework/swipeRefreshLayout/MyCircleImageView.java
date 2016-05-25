package android_hddl_framework.swipeRefreshLayout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.view.animation.Animation;
import android.widget.ImageView;

public class MyCircleImageView extends ImageView {
	private static final int KEY_SHADOW_COLOR = 503316480;
	private static final int FILL_SHADOW_COLOR = 1023410176;
	private static final float X_OFFSET = 0.0F;
	private static final float Y_OFFSET = 1.75F;
	private static final float SHADOW_RADIUS = 3.5F;
	private static final int SHADOW_ELEVATION = 4;
	private Animation.AnimationListener mListener;
	private int mShadowRadius;
	
	public MyCircleImageView(Context context, int color, float radius) {
		super(context);
		float density = getContext().getResources().getDisplayMetrics().density;
		int diameter = (int) (radius * density * 2.0F);
		int shadowYOffset = (int) (density * 1.75F);
		int shadowXOffset = (int) (density * 0.0F);
		
		this.mShadowRadius = (int) (density * 3.5F);
		ShapeDrawable circle;
		if (elevationSupported()) {
			circle = new ShapeDrawable(new OvalShape());
			ViewCompat.setElevation(this, 4.0F * density);
		} else {
			OvalShape oval = new OvalShadow(this.mShadowRadius, diameter);
			circle = new ShapeDrawable(oval);
			ViewCompat.setLayerType(this, 1, circle.getPaint());
			circle.getPaint().setShadowLayer(this.mShadowRadius, shadowXOffset,
					shadowYOffset, 503316480);
			
			int padding = this.mShadowRadius;
			
			setPadding(padding, padding, padding, padding);
		}
		circle.getPaint().setColor(color);
		setBackgroundDrawable(circle);
	}
	
	private boolean elevationSupported() {
		return Build.VERSION.SDK_INT >= 21;
	}
	
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		if (!elevationSupported())
			setMeasuredDimension(getMeasuredWidth() + this.mShadowRadius * 2,
					getMeasuredHeight() + this.mShadowRadius * 2);
	}
	
	public void setAnimationListener(Animation.AnimationListener listener) {
		this.mListener = listener;
	}
	
	public void onAnimationStart() {
		super.onAnimationStart();
		if (this.mListener != null)
			this.mListener.onAnimationStart(getAnimation());
	}
	
	public void onAnimationEnd() {
		super.onAnimationEnd();
		if (this.mListener != null)
			this.mListener.onAnimationEnd(getAnimation());
	}
	
	public void setBackgroundColorRes(int colorRes) {
		setBackgroundColor(getContext().getResources().getColor(colorRes));
	}
	
	public void setBackgroundColor(int color) {
		if ((getBackground() instanceof ShapeDrawable))
			((ShapeDrawable) getBackground()).getPaint().setColor(color);
	}
	
	private class OvalShadow extends OvalShape {
		private RadialGradient mRadialGradient;
		private Paint mShadowPaint;
		private int mCircleDiameter;
		
		public OvalShadow(int shadowRadius, int circleDiameter) {
			this.mShadowPaint = new Paint();
			MyCircleImageView.this.mShadowRadius = shadowRadius;
			this.mCircleDiameter = circleDiameter;
			this.mRadialGradient = new RadialGradient(this.mCircleDiameter / 2,
					this.mCircleDiameter / 2,
					MyCircleImageView.this.mShadowRadius, new int[] { 1023410176,
							0 }, null, Shader.TileMode.CLAMP);
			
			this.mShadowPaint.setShader(this.mRadialGradient);
		}
		
		public void draw(Canvas canvas, Paint paint) {
			int viewWidth = MyCircleImageView.this.getWidth();
			int viewHeight = MyCircleImageView.this.getHeight();
			canvas.drawCircle(viewWidth / 2, viewHeight / 2,
					this.mCircleDiameter / 2
							+ MyCircleImageView.this.mShadowRadius,
					this.mShadowPaint);
			
			canvas.drawCircle(viewWidth / 2, viewHeight / 2,
					this.mCircleDiameter / 2, paint);
		}
	}
}
