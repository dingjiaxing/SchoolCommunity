package android_hddl_framework.swipeRefreshLayout;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Build.VERSION;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.widget.AbsListView;

@TargetApi(Build.VERSION_CODES.KITKAT)
public class SwipeRefreshLayout extends ViewGroup {
	public static final int LARGE = 0;
	public static final int DEFAULT = 1;
	private static final String LOG_TAG = SwipeRefreshLayout.class
			.getSimpleName();
	private static final int MAX_ALPHA = 255;
	private static final int STARTING_PROGRESS_ALPHA = 76;
	private static final int CIRCLE_DIAMETER = 40;
	private static final int CIRCLE_DIAMETER_LARGE = 56;
	private static final float DECELERATE_INTERPOLATION_FACTOR = 2.0F;
	private static final int INVALID_POINTER = -1;
	private static final float DRAG_RATE = 0.5F;
	private static final float MAX_PROGRESS_ANGLE = 0.8F;
	private static final int SCALE_DOWN_DURATION = 150;
	private static final int ALPHA_ANIMATION_DURATION = 300;
	private static final int ANIMATE_TO_TRIGGER_DURATION = 200;
	private static final int ANIMATE_TO_START_DURATION = 200;
	private static final int CIRCLE_BG_LIGHT = -328966;
	private static final int DEFAULT_CIRCLE_TARGET = 64;
	private View mTarget;
	private OnRefreshListener mListener;
	private boolean mRefreshing = false;
	private int mTouchSlop;
	private float mTotalDragDistance = -1.0F;
	private int mMediumAnimationDuration;
	private int mCurrentTargetOffsetTop;
	private boolean mOriginalOffsetCalculated = false;
	private float mInitialMotionY;
	private float mInitialDownY;
	private boolean mIsBeingDragged;
	private int mActivePointerId = -1;
	private boolean mScale;
	private boolean mReturningToStart;
	private final DecelerateInterpolator mDecelerateInterpolator;
	private static final int[] LAYOUT_ATTRS = { 16842766 };
	private MyCircleImageView mCircleView;
	private int mCircleViewIndex = -1;
	protected int mFrom;
	private float mStartingScale;
	protected int mOriginalOffsetTop;
	private MyMaterialProgressDrawable mProgress;
	private Animation mScaleAnimation;
	private Animation mScaleDownAnimation;
	private Animation mAlphaStartAnimation;
	private Animation mAlphaMaxAnimation;
	private Animation mScaleDownToStartAnimation;
	private float mSpinnerFinalOffset;
	private boolean mNotify;
	private int mCircleWidth;
	private int mCircleHeight;
	private boolean mUsingCustomStart;
	private Animation.AnimationListener mRefreshListener = new Animation.AnimationListener() {
		public void onAnimationStart(Animation animation) {
		}
		
		public void onAnimationRepeat(Animation animation) {
		}
		
		public void onAnimationEnd(Animation animation) {
			if (SwipeRefreshLayout.this.mRefreshing) {
				SwipeRefreshLayout.this.mProgress.setAlpha(255);
				SwipeRefreshLayout.this.mProgress.start();
				if ((SwipeRefreshLayout.this.mNotify)
						&& (SwipeRefreshLayout.this.mListener != null))
					SwipeRefreshLayout.this.mListener.onRefresh();
			} else {
				SwipeRefreshLayout.this.mProgress.stop();
				SwipeRefreshLayout.this.mCircleView.setVisibility(8);
				SwipeRefreshLayout.this.setColorViewAlpha(255);
				
				if (SwipeRefreshLayout.this.mScale)
					SwipeRefreshLayout.this.setAnimationProgress(0.0F);
				else {
					SwipeRefreshLayout.this
							.setTargetOffsetTopAndBottom(
									SwipeRefreshLayout.this.mOriginalOffsetTop
											- SwipeRefreshLayout.this.mCurrentTargetOffsetTop,
									true);
				}
			}
			
			SwipeRefreshLayout.this.mCurrentTargetOffsetTop = SwipeRefreshLayout.this.mCircleView
					.getTop();
		}
	};
	
	private final Animation mAnimateToCorrectPosition = new Animation() {
		public void applyTransformation(float interpolatedTime, Transformation t) {
			int targetTop = 0;
			int endTarget = 0;
			if (!SwipeRefreshLayout.this.mUsingCustomStart)
				endTarget = (int) (SwipeRefreshLayout.this.mSpinnerFinalOffset - Math
						.abs(SwipeRefreshLayout.this.mOriginalOffsetTop));
			else {
				endTarget = (int) SwipeRefreshLayout.this.mSpinnerFinalOffset;
			}
			targetTop = SwipeRefreshLayout.this.mFrom
					+ (int) (endTarget - SwipeRefreshLayout.this.mFrom
							* interpolatedTime);
			int offset = targetTop
					- SwipeRefreshLayout.this.mCircleView.getTop();
			SwipeRefreshLayout.this
					.setTargetOffsetTopAndBottom(offset, false);
			SwipeRefreshLayout.this.mProgress
					.setArrowScale(1.0F - interpolatedTime);
		}
	};
	
	private final Animation mAnimateToStartPosition = new Animation() {
		public void applyTransformation(float interpolatedTime, Transformation t) {
			SwipeRefreshLayout.this.moveToStart(interpolatedTime);
		}
	};
	
	private void setColorViewAlpha(int targetAlpha) {
		this.mCircleView.getBackground().setAlpha(targetAlpha);
		this.mProgress.setAlpha(targetAlpha);
	}
	
	public void setProgressViewOffset(boolean scale, int start, int end) {
		this.mScale = scale;
		this.mCircleView.setVisibility(8);
		this.mOriginalOffsetTop = (this.mCurrentTargetOffsetTop = start);
		this.mSpinnerFinalOffset = end;
		this.mUsingCustomStart = true;
		this.mCircleView.invalidate();
	}
	
	public void setProgressViewEndTarget(boolean scale, int end) {
		this.mSpinnerFinalOffset = end;
		this.mScale = scale;
		this.mCircleView.invalidate();
	}
	
	public void setSize(int size) {
		if ((size != 0) && (size != 1)) {
			return;
		}
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		if (size == 0)
			this.mCircleHeight = (this.mCircleWidth = (int) (56.0F * metrics.density));
		else {
			this.mCircleHeight = (this.mCircleWidth = (int) (40.0F * metrics.density));
		}
		
		this.mCircleView.setImageDrawable(null);
		this.mProgress.updateSizes(size);
		this.mCircleView.setImageDrawable(this.mProgress);
	}
	
	public SwipeRefreshLayout(Context context) {
		this(context, null);
	}
	
	public SwipeRefreshLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
		
		this.mMediumAnimationDuration = getResources().getInteger(17694721);
		
		setWillNotDraw(false);
		this.mDecelerateInterpolator = new DecelerateInterpolator(2.0F);
		
		TypedArray a = context.obtainStyledAttributes(attrs, LAYOUT_ATTRS);
		setEnabled(a.getBoolean(0, true));
		a.recycle();
		
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		this.mCircleWidth = (int) (40.0F * metrics.density);
		this.mCircleHeight = (int) (40.0F * metrics.density);
		
		createProgressView();
		ViewCompat.setChildrenDrawingOrderEnabled(this, true);
		
		this.mSpinnerFinalOffset = (64.0F * metrics.density);
		this.mTotalDragDistance = this.mSpinnerFinalOffset;
	}
	
	protected int getChildDrawingOrder(int childCount, int i) {
		if (this.mCircleViewIndex < 0)
			return i;
		if (i == childCount - 1) {
			return this.mCircleViewIndex;
		}
		if (i >= this.mCircleViewIndex) {
			return i + 1;
		}
		
		return i;
	}
	
	private void createProgressView() {
		this.mCircleView = new MyCircleImageView(getContext(), -328966, 20.0F);
		this.mProgress = new MyMaterialProgressDrawable(getContext(), this);
		this.mProgress.setBackgroundColor(-328966);
		this.mCircleView.setImageDrawable(this.mProgress);
		this.mCircleView.setVisibility(8);
		addView(this.mCircleView);
	}
	
	public void setOnRefreshListener(OnRefreshListener listener) {
		this.mListener = listener;
	}
	
	private boolean isAlphaUsedForScale() {
		return Build.VERSION.SDK_INT < 11;
	}
	
	public void setRefreshing(boolean refreshing) {
		if ((refreshing) && (this.mRefreshing != refreshing)) {
			this.mRefreshing = refreshing;
			int endTarget = 0;
			if (!this.mUsingCustomStart)
				endTarget = (int) (this.mSpinnerFinalOffset + this.mOriginalOffsetTop);
			else {
				endTarget = (int) this.mSpinnerFinalOffset;
			}
			setTargetOffsetTopAndBottom(endTarget
					- this.mCurrentTargetOffsetTop, true);
			
			this.mNotify = false;
			startScaleUpAnimation(this.mRefreshListener);
		} else {
			setRefreshing(refreshing, false);
		}
	}
	
	private void startScaleUpAnimation(Animation.AnimationListener listener) {
		this.mCircleView.setVisibility(0);
		if (Build.VERSION.SDK_INT >= 11) {
			this.mProgress.setAlpha(255);
		}
		this.mScaleAnimation = new Animation() {
			public void applyTransformation(float interpolatedTime,
					Transformation t) {
				SwipeRefreshLayout.this
						.setAnimationProgress(interpolatedTime);
			}
		};
		this.mScaleAnimation.setDuration(this.mMediumAnimationDuration);
		if (listener != null) {
			this.mCircleView.setAnimationListener(listener);
		}
		this.mCircleView.clearAnimation();
		this.mCircleView.startAnimation(this.mScaleAnimation);
	}
	
	private void setAnimationProgress(float progress) {
		if (isAlphaUsedForScale()) {
			setColorViewAlpha((int) (progress * 255.0F));
		} else {
			ViewCompat.setScaleX(this.mCircleView, progress);
			ViewCompat.setScaleY(this.mCircleView, progress);
		}
	}
	
	public void setRefreshing(boolean refreshing, boolean notify) {
		if (this.mRefreshing != refreshing) {
			this.mNotify = notify;
			ensureTarget();
			this.mRefreshing = refreshing;
			if (this.mRefreshing) {
				animateOffsetToCorrectPosition(this.mCurrentTargetOffsetTop,
						this.mRefreshListener);
			} else {
				startScaleDownAnimation(this.mRefreshListener);
			}
		}
	}
	
	public void setRefreshEntry() {
		this.mNotify = true;
		ensureTarget();
		this.mRefreshing = true;
		if (this.mRefreshing) {
			this.mCircleView.setAnimationListener(this.mRefreshListener);
			this.mCircleView.clearAnimation();
			this.mCircleView.startAnimation(this.mAnimateToCorrectPosition);
		} else {
			startScaleDownAnimation(this.mRefreshListener);
		}
	}
	
	private void startScaleDownAnimation(Animation.AnimationListener listener) {
		this.mScaleDownAnimation = new Animation() {
			public void applyTransformation(float interpolatedTime,
					Transformation t) {
				SwipeRefreshLayout.this
						.setAnimationProgress(1.0F - interpolatedTime);
			}
		};
		this.mScaleDownAnimation.setDuration(150L);
		this.mCircleView.setAnimationListener(listener);
		this.mCircleView.clearAnimation();
		this.mCircleView.startAnimation(this.mScaleDownAnimation);
	}
	
	private void startProgressAlphaStartAnimation() {
		this.mAlphaStartAnimation = startAlphaAnimation(
				this.mProgress.getAlpha(), 76);
	}
	
	private void startProgressAlphaMaxAnimation() {
		this.mAlphaMaxAnimation = startAlphaAnimation(
				this.mProgress.getAlpha(), 255);
	}
	
	private Animation startAlphaAnimation(final int startingAlpha,
			final int endingAlpha) {
		if ((this.mScale) && (isAlphaUsedForScale())) {
			return null;
		}
		Animation alpha = new Animation() {
			public void applyTransformation(float interpolatedTime,
					Transformation t) {
				SwipeRefreshLayout.this.mProgress
						.setAlpha((int) (startingAlpha + endingAlpha - startingAlpha
								* interpolatedTime));
			}
		};
		alpha.setDuration(300L);
		
		this.mCircleView.setAnimationListener(null);
		this.mCircleView.clearAnimation();
		this.mCircleView.startAnimation(alpha);
		return alpha;
	}
	
	@Deprecated
	public void setProgressBackgroundColor(int colorRes) {
		setProgressBackgroundColorSchemeResource(colorRes);
	}
	
	public void setProgressBackgroundColorSchemeResource(int colorRes) {
		setProgressBackgroundColorSchemeColor(getResources().getColor(colorRes));
	}
	
	public void setProgressBackgroundColorSchemeColor(int color) {
		this.mCircleView.setBackgroundColor(color);
		this.mProgress.setBackgroundColor(color);
	}
	
	@Deprecated
	public void setColorScheme(int[] colors) {
		setColorSchemeResources(colors);
	}
	
	public void setColorSchemeResources(int[] colorResIds) {
		Resources res = getResources();
		int[] colorRes = new int[colorResIds.length];
		for (int i = 0; i < colorResIds.length; i++) {
			colorRes[i] = res.getColor(colorResIds[i]);
		}
		setColorSchemeColors(colorRes);
	}
	
	public void setColorSchemeColors(int[] colors) {
		ensureTarget();
		this.mProgress.setColorSchemeColors(colors);
	}
	
	public boolean isRefreshing() {
		return this.mRefreshing;
	}
	
	private void ensureTarget() {
		if (this.mTarget == null)
			for (int i = 0; i < getChildCount(); i++) {
				View child = getChildAt(i);
				if (!child.equals(this.mCircleView)) {
					this.mTarget = child;
					break;
				}
			}
	}
	
	public void setDistanceToTriggerSync(int distance) {
		this.mTotalDragDistance = distance;
	}
	
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		int width = getMeasuredWidth();
		int height = getMeasuredHeight();
		if (getChildCount() == 0) {
			return;
		}
		if (this.mTarget == null) {
			ensureTarget();
		}
		if (this.mTarget == null) {
			return;
		}
		View child = this.mTarget;
		int childLeft = getPaddingLeft();
		int childTop = getPaddingTop();
		int childWidth = width - getPaddingLeft() - getPaddingRight();
		int childHeight = height - getPaddingTop() - getPaddingBottom();
		child.layout(childLeft, childTop, childLeft + childWidth, childTop
				+ childHeight);
		int circleWidth = this.mCircleView.getMeasuredWidth();
		int circleHeight = this.mCircleView.getMeasuredHeight();
		this.mCircleView.layout(width / 2 - circleWidth / 2,
				this.mCurrentTargetOffsetTop, width / 2 + circleWidth / 2,
				this.mCurrentTargetOffsetTop + circleHeight);
	}
	
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		if (this.mTarget == null) {
			ensureTarget();
		}
		if (this.mTarget == null) {
			return;
		}
		this.mTarget.measure(
				View.MeasureSpec.makeMeasureSpec(getMeasuredWidth()
						- getPaddingLeft() - getPaddingRight(), 1073741824),
				View.MeasureSpec.makeMeasureSpec(getMeasuredHeight()
						- getPaddingTop() - getPaddingBottom(), 1073741824));
		
		this.mCircleView
				.measure(View.MeasureSpec.makeMeasureSpec(this.mCircleWidth,
						1073741824), View.MeasureSpec.makeMeasureSpec(
						this.mCircleHeight, 1073741824));
		
		if ((!this.mUsingCustomStart) && (!this.mOriginalOffsetCalculated)) {
			this.mOriginalOffsetCalculated = true;
			this.mCurrentTargetOffsetTop = (this.mOriginalOffsetTop = -this.mCircleView
					.getMeasuredHeight());
		}
		this.mCircleViewIndex = -1;
		
		for (int index = 0; index < getChildCount(); index++)
			if (getChildAt(index) == this.mCircleView) {
				this.mCircleViewIndex = index;
				break;
			}
	}
	
	public int getProgressCircleDiameter() {
		return this.mCircleView != null ? this.mCircleView.getMeasuredHeight()
				: 0;
	}
	
	public boolean canChildScrollUp() {
		if (Build.VERSION.SDK_INT < 14) {
			if ((this.mTarget instanceof AbsListView)) {
				AbsListView absListView = (AbsListView) this.mTarget;
				return (absListView.getChildCount() > 0)
						&& ((absListView.getFirstVisiblePosition() > 0) || (absListView
								.getChildAt(0).getTop() < absListView
								.getPaddingTop()));
			}
			
			return (ViewCompat.canScrollVertically(this.mTarget, -1))
					|| (this.mTarget.getScrollY() > 0);
		}
		
		return ViewCompat.canScrollVertically(this.mTarget, -1);
	}
	
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		ensureTarget();
		
		int action = MotionEventCompat.getActionMasked(ev);
		
		if ((this.mReturningToStart) && (action == 0)) {
			this.mReturningToStart = false;
		}
		
		if ((!isEnabled()) || (this.mReturningToStart) || (canChildScrollUp())
				|| (this.mRefreshing)) {
			return false;
		}
		
		switch (action) {
			case 0:
				setTargetOffsetTopAndBottom(this.mOriginalOffsetTop
						- this.mCircleView.getTop(), true);
				this.mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
				this.mIsBeingDragged = false;
				float initialDownY = getMotionEventY(ev, this.mActivePointerId);
				if (initialDownY == -1.0F) {
					return false;
				}
				this.mInitialDownY = initialDownY;
				break;
			case 2:
				if (this.mActivePointerId == -1) {
					Log.e(LOG_TAG,
							"Got ACTION_MOVE event but don't have an active pointer id.");
					return false;
				}
				
				float y = getMotionEventY(ev, this.mActivePointerId);
				if (y == -1.0F) {
					return false;
				}
				float yDiff = y - this.mInitialDownY;
				if ((yDiff > this.mTouchSlop) && (!this.mIsBeingDragged)) {
					this.mInitialMotionY = (this.mInitialDownY + this.mTouchSlop);
					this.mIsBeingDragged = true;
					this.mProgress.setAlpha(76);
				}
				break;
			case 6:
				onSecondaryPointerUp(ev);
				break;
			case 1:
			case 3:
				this.mIsBeingDragged = false;
				this.mActivePointerId = -1;
			case 4:
			case 5:
		}
		return this.mIsBeingDragged;
	}
	
	private float getMotionEventY(MotionEvent ev, int activePointerId) {
		int index = MotionEventCompat.findPointerIndex(ev, activePointerId);
		if (index < 0) {
			return -1.0F;
		}
		return MotionEventCompat.getY(ev, index);
	}
	
	public void requestDisallowInterceptTouchEvent(boolean b) {
	}
	
	private boolean isAnimationRunning(Animation animation) {
		return (animation != null) && (animation.hasStarted())
				&& (!animation.hasEnded());
	}
	
	public boolean onTouchEvent(MotionEvent ev) {
		int action = MotionEventCompat.getActionMasked(ev);
		
		if ((this.mReturningToStart) && (action == 0)) {
			this.mReturningToStart = false;
		}
		
		if ((!isEnabled()) || (this.mReturningToStart) || (canChildScrollUp())) {
			return false;
		}
		
		switch (action) {
			case 0:
				this.mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
				this.mIsBeingDragged = false;
				break;
			case 2:
				int pointerIndex = MotionEventCompat.findPointerIndex(ev,
						this.mActivePointerId);
				if (pointerIndex < 0) {
					Log.e(LOG_TAG,
							"Got ACTION_MOVE event but have an invalid active pointer id.");
					return false;
				}
				
				float y = MotionEventCompat.getY(ev, pointerIndex);
				float overscrollTop = (y - this.mInitialMotionY) * 0.5F;
				if (this.mIsBeingDragged) {
					this.mProgress.showArrow(true);
					float originalDragPercent = overscrollTop
							/ this.mTotalDragDistance;
					if (originalDragPercent < 0.0F) {
						return false;
					}
					float dragPercent = Math.min(1.0F,
							Math.abs(originalDragPercent));
					float adjustedPercent = (float) Math.max(
							dragPercent - 0.4D, 0.0D) * 5.0F / 3.0F;
					float extraOS = Math.abs(overscrollTop)
							- this.mTotalDragDistance;
					float slingshotDist = this.mUsingCustomStart ? this.mSpinnerFinalOffset
							- this.mOriginalOffsetTop
							: this.mSpinnerFinalOffset;
					
					float tensionSlingshotPercent = Math.max(0.0F,
							Math.min(extraOS, slingshotDist * 2.0F)
									/ slingshotDist);
					
					float tensionPercent = (float) (tensionSlingshotPercent / 4.0F - Math
							.pow(tensionSlingshotPercent / 4.0F, 2.0D)) * 2.0F;
					
					float extraMove = slingshotDist * tensionPercent * 2.0F;
					
					int targetY = this.mOriginalOffsetTop
							+ (int) (slingshotDist * dragPercent + extraMove);
					
					if (this.mCircleView.getVisibility() != 0) {
						this.mCircleView.setVisibility(0);
					}
					if (!this.mScale) {
						ViewCompat.setScaleX(this.mCircleView, 1.0F);
						ViewCompat.setScaleY(this.mCircleView, 1.0F);
					}
					if (overscrollTop < this.mTotalDragDistance) {
						if (this.mScale) {
							setAnimationProgress(overscrollTop
									/ this.mTotalDragDistance);
						}
						if ((this.mProgress.getAlpha() > 76)
								&& (!isAnimationRunning(this.mAlphaStartAnimation))) {
							startProgressAlphaStartAnimation();
						}
						float strokeStart = adjustedPercent * 0.8F;
						this.mProgress.setStartEndTrim(0.0F,
								Math.min(0.8F, strokeStart));
						this.mProgress.setArrowScale(Math.min(1.0F,
								adjustedPercent));
					} else if ((this.mProgress.getAlpha() < 255)
							&& (!isAnimationRunning(this.mAlphaMaxAnimation))) {
						startProgressAlphaMaxAnimation();
					}
					
					float rotation = (-0.25F + 0.4F * adjustedPercent + tensionPercent * 2.0F) * 0.5F;
					this.mProgress.setProgressRotation(rotation);
					setTargetOffsetTopAndBottom(targetY
							- this.mCurrentTargetOffsetTop, true);
				}
				break;
			case 5:
				int index = MotionEventCompat.getActionIndex(ev);
				this.mActivePointerId = MotionEventCompat.getPointerId(ev,
						index);
				break;
			case 6:
				onSecondaryPointerUp(ev);
				break;
			case 1:
			case 3:
				if (this.mActivePointerId == -1) {
					if (action == 1) {
						Log.e(LOG_TAG,
								"Got ACTION_UP event but don't have an active pointer id.");
					}
					return false;
				}
				int pointerIndex_1 = MotionEventCompat.findPointerIndex(ev,
						this.mActivePointerId);
				float y_1 = MotionEventCompat.getY(ev, pointerIndex_1);
				float overscrollTop_1 = (y_1 - this.mInitialMotionY) * 0.5F;
				this.mIsBeingDragged = false;
				if (overscrollTop_1 > this.mTotalDragDistance) {
					setRefreshing(true, true);
				} else {
					this.mRefreshing = false;
					this.mProgress.setStartEndTrim(0.0F, 0.0F);
					Animation.AnimationListener listener = null;
					if (!this.mScale)
						listener = new Animation.AnimationListener() {
							public void onAnimationStart(Animation animation) {
							}
							
							public void onAnimationEnd(Animation animation) {
								if (!SwipeRefreshLayout.this.mScale)
									SwipeRefreshLayout.this
											.startScaleDownAnimation(null);
							}
							
							public void onAnimationRepeat(Animation animation) {
							}
						};
					animateOffsetToStartPosition(this.mCurrentTargetOffsetTop,
							listener);
					this.mProgress.showArrow(false);
				}
				this.mActivePointerId = -1;
				return false;
			case 4:
		}
		
		return true;
	}
	
	private void animateOffsetToCorrectPosition(int from,
			Animation.AnimationListener listener) {
		this.mFrom = from;
		this.mAnimateToCorrectPosition.reset();
		this.mAnimateToCorrectPosition.setDuration(200L);
		this.mAnimateToCorrectPosition
				.setInterpolator(this.mDecelerateInterpolator);
		if (listener != null) {
			this.mCircleView.setAnimationListener(listener);
		}
		this.mCircleView.clearAnimation();
		this.mCircleView.startAnimation(this.mAnimateToCorrectPosition);
	}
	
	private void animateOffsetToStartPosition(int from,
			Animation.AnimationListener listener) {
		if (this.mScale) {
			startScaleDownReturnToStartAnimation(from, listener);
		} else {
			this.mFrom = from;
			this.mAnimateToStartPosition.reset();
			this.mAnimateToStartPosition.setDuration(200L);
			this.mAnimateToStartPosition
					.setInterpolator(this.mDecelerateInterpolator);
			if (listener != null) {
				this.mCircleView.setAnimationListener(listener);
			}
			this.mCircleView.clearAnimation();
			this.mCircleView.startAnimation(this.mAnimateToStartPosition);
		}
	}
	
	private void moveToStart(float interpolatedTime) {
		int targetTop = 0;
		targetTop = this.mFrom
				+ (int) (this.mOriginalOffsetTop - this.mFrom
						* interpolatedTime);
		int offset = targetTop - this.mCircleView.getTop();
		setTargetOffsetTopAndBottom(offset, false);
	}
	
	private void startScaleDownReturnToStartAnimation(int from,
			Animation.AnimationListener listener) {
		this.mFrom = from;
		if (isAlphaUsedForScale())
			this.mStartingScale = this.mProgress.getAlpha();
		else {
			this.mStartingScale = ViewCompat.getScaleX(this.mCircleView);
		}
		this.mScaleDownToStartAnimation = new Animation() {
			public void applyTransformation(float interpolatedTime,
					Transformation t) {
				float targetScale = SwipeRefreshLayout.this.mStartingScale
						+ -SwipeRefreshLayout.this.mStartingScale
						* interpolatedTime;
				SwipeRefreshLayout.this.setAnimationProgress(targetScale);
				SwipeRefreshLayout.this.moveToStart(interpolatedTime);
			}
		};
		this.mScaleDownToStartAnimation.setDuration(150L);
		if (listener != null) {
			this.mCircleView.setAnimationListener(listener);
		}
		this.mCircleView.clearAnimation();
		this.mCircleView.startAnimation(this.mScaleDownToStartAnimation);
	}
	
	private void setTargetOffsetTopAndBottom(int offset, boolean requiresUpdate) {
		this.mCircleView.bringToFront();
		this.mCircleView.offsetTopAndBottom(offset);
		this.mCurrentTargetOffsetTop = this.mCircleView.getTop();
		if ((requiresUpdate) && (Build.VERSION.SDK_INT < 11))
			invalidate();
	}
	
	private void onSecondaryPointerUp(MotionEvent ev) {
		int pointerIndex = MotionEventCompat.getActionIndex(ev);
		int pointerId = MotionEventCompat.getPointerId(ev, pointerIndex);
		if (pointerId == this.mActivePointerId) {
			int newPointerIndex = pointerIndex == 0 ? 1 : 0;
			this.mActivePointerId = MotionEventCompat.getPointerId(ev,
					newPointerIndex);
		}
	}
	
	// public void setOnRefreshing(){
	// setRefreshing(true, true);
	// }
	
	public static abstract interface OnRefreshListener {
		public abstract void onRefresh();
	}
}