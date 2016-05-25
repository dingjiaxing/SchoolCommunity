package android_hddl_framework.swipeRefreshLayout;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.FillType;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.support.annotation.NonNull;
//import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

public class MyMaterialProgressDrawable extends Drawable
  implements Animatable
{
  private static final Interpolator LINEAR_INTERPOLATOR = new LinearInterpolator();
  private static final Interpolator MATERIAL_INTERPOLATOR = new FastOutSlowInInterpolator();
  private static final float FULL_ROTATION = 1080.0F;
  static final int LARGE = 0;
  static final int DEFAULT = 1;
  private static final int CIRCLE_DIAMETER = 40;
  private static final float CENTER_RADIUS = 8.75F;
  private static final float STROKE_WIDTH = 2.5F;
  private static final int CIRCLE_DIAMETER_LARGE = 56;
  private static final float CENTER_RADIUS_LARGE = 12.5F;
  private static final float STROKE_WIDTH_LARGE = 3.0F;
  private final int[] COLORS = { -16777216 };
  private static final float COLOR_START_DELAY_OFFSET = 0.75F;
  private static final float END_TRIM_START_DELAY_OFFSET = 0.5F;
  private static final float START_TRIM_DURATION_OFFSET = 0.5F;
  private static final int ANIMATION_DURATION = 1332;
  private static final float NUM_POINTS = 5.0F;
  private final ArrayList<Animation> mAnimators = new ArrayList();
  private final Ring mRing;
  private float mRotation;
  private static final int ARROW_WIDTH = 10;
  private static final int ARROW_HEIGHT = 5;
  private static final float ARROW_OFFSET_ANGLE = 5.0F;
  private static final int ARROW_WIDTH_LARGE = 12;
  private static final int ARROW_HEIGHT_LARGE = 6;
  private static final float MAX_PROGRESS_ARC = 0.8F;
  private Resources mResources;
  private View mParent;
  private Animation mAnimation;
  private float mRotationCount;
  private double mWidth;
  private double mHeight;
  boolean mFinishing;
  private final Drawable.Callback mCallback = new Drawable.Callback()
  {
    public void invalidateDrawable(Drawable d) {
      MyMaterialProgressDrawable.this.invalidateSelf();
    }

    public void scheduleDrawable(Drawable d, Runnable what, long when)
    {
      MyMaterialProgressDrawable.this.scheduleSelf(what, when);
    }

    public void unscheduleDrawable(Drawable d, Runnable what)
    {
      MyMaterialProgressDrawable.this.unscheduleSelf(what);
    }
  };

  public MyMaterialProgressDrawable(Context context, View parent)
  {
    this.mParent = parent;
    this.mResources = context.getResources();

    this.mRing = new Ring(this.mCallback);
    this.mRing.setColors(this.COLORS);

    updateSizes(1);
    setupAnimators();
  }

  private void setSizeParameters(double progressCircleWidth, double progressCircleHeight, double centerRadius, double strokeWidth, float arrowWidth, float arrowHeight)
  {
    Ring ring = this.mRing;
    DisplayMetrics metrics = this.mResources.getDisplayMetrics();
    float screenDensity = metrics.density;

    this.mWidth = (progressCircleWidth * screenDensity);
    this.mHeight = (progressCircleHeight * screenDensity);
    ring.setStrokeWidth((float)strokeWidth * screenDensity);
    ring.setCenterRadius(centerRadius * screenDensity);
    ring.setColorIndex(0);
    ring.setArrowDimensions(arrowWidth * screenDensity, arrowHeight * screenDensity);
    ring.setInsets((int)this.mWidth, (int)this.mHeight);
  }

  public void updateSizes(@ProgressDrawableSize int size)
  {
    if (size == 0) {
      setSizeParameters(56.0D, 56.0D, 12.5D, 3.0D, 12.0F, 6.0F);
    }
    else
      setSizeParameters(40.0D, 40.0D, 8.75D, 2.5D, 10.0F, 5.0F);
  }

  public void showArrow(boolean show)
  {
    this.mRing.setShowArrow(show);
  }

  public void setArrowScale(float scale)
  {
    this.mRing.setArrowScale(scale);
  }

  public void setStartEndTrim(float startAngle, float endAngle)
  {
    this.mRing.setStartTrim(startAngle);
    this.mRing.setEndTrim(endAngle);
  }

  public void setProgressRotation(float rotation)
  {
    this.mRing.setRotation(rotation);
  }

  public void setBackgroundColor(int color)
  {
    this.mRing.setBackgroundColor(color);
  }

  public void setColorSchemeColors(int[] colors)
  {
    this.mRing.setColors(colors);
    this.mRing.setColorIndex(0);
  }

  public int getIntrinsicHeight()
  {
    return (int)this.mHeight;
  }

  public int getIntrinsicWidth()
  {
    return (int)this.mWidth;
  }

  public void draw(Canvas c)
  {
    Rect bounds = getBounds();
    int saveCount = c.save();
    c.rotate(this.mRotation, bounds.exactCenterX(), bounds.exactCenterY());
    this.mRing.draw(c, bounds);
    c.restoreToCount(saveCount);
  }

  public void setAlpha(int alpha)
  {
    this.mRing.setAlpha(alpha);
  }

  public int getAlpha() {
    return this.mRing.getAlpha();
  }

  public void setColorFilter(ColorFilter colorFilter)
  {
    this.mRing.setColorFilter(colorFilter);
  }

  void setRotation(float rotation)
  {
    this.mRotation = rotation;
    invalidateSelf();
  }

  private float getRotation()
  {
    return this.mRotation;
  }

  public int getOpacity()
  {
    return -3;
  }

  public boolean isRunning()
  {
    ArrayList animators = this.mAnimators;
    int N = animators.size();
    for (int i = 0; i < N; i++) {
      Animation animator = (Animation)animators.get(i);
      if ((animator.hasStarted()) && (!animator.hasEnded())) {
        return true;
      }
    }
    return false;
  }

  public void start()
  {
    this.mAnimation.reset();
    this.mRing.storeOriginals();

    if (this.mRing.getEndTrim() != this.mRing.getStartTrim()) {
      this.mFinishing = true;
      this.mAnimation.setDuration(666L);
      this.mParent.startAnimation(this.mAnimation);
    } else {
      this.mRing.setColorIndex(0);
      this.mRing.resetOriginals();
      this.mAnimation.setDuration(1332L);
      this.mParent.startAnimation(this.mAnimation);
    }
  }

  public void stop()
  {
    this.mParent.clearAnimation();
    setRotation(0.0F);
    this.mRing.setShowArrow(false);
    this.mRing.setColorIndex(0);
    this.mRing.resetOriginals();
  }

  private float getMinProgressArc(Ring ring) {
    return (float)Math.toRadians(ring.getStrokeWidth() / (6.283185307179586D * ring.getCenterRadius()));
  }

  private int evaluateColorChange(float fraction, int startValue, int endValue)
  {
    int startInt = Integer.valueOf(startValue).intValue();
    int startA = startInt >> 24 & 0xFF;
    int startR = startInt >> 16 & 0xFF;
    int startG = startInt >> 8 & 0xFF;
    int startB = startInt & 0xFF;

    int endInt = Integer.valueOf(endValue).intValue();
    int endA = endInt >> 24 & 0xFF;
    int endR = endInt >> 16 & 0xFF;
    int endG = endInt >> 8 & 0xFF;
    int endB = endInt & 0xFF;

    return startA + (int)(fraction * endA - startA) << 24 | startR + (int)(fraction * endR - startR) << 16 | startG + (int)(fraction * endG - startG) << 8 | startB + (int)(fraction * endB - startB);
  }

  private void updateRingColor(float interpolatedTime, Ring ring)
  {
    if (interpolatedTime > 0.75F)
    {
      ring.setColor(evaluateColorChange((interpolatedTime - 0.75F) / 0.25F, ring.getStartingColor(), ring.getNextColor()));
    }
  }

  private void applyFinishTranslation(float interpolatedTime, Ring ring)
  {
    updateRingColor(interpolatedTime, ring);
    float targetRotation = (float)(Math.floor(ring.getStartingRotation() / 0.8F) + 1.0D);

    float minProgressArc = getMinProgressArc(ring);
    float startTrim = ring.getStartingStartTrim() + (ring.getStartingEndTrim() - minProgressArc - ring.getStartingStartTrim()) * interpolatedTime;

    ring.setStartTrim(startTrim);
    ring.setEndTrim(ring.getStartingEndTrim());
    float rotation = ring.getStartingRotation() + (targetRotation - ring.getStartingRotation()) * interpolatedTime;

    ring.setRotation(rotation);
  }

  private void setupAnimators() {
    final Ring ring = this.mRing;
    Animation animation = new Animation()
    {
      public void applyTransformation(float interpolatedTime, Transformation t) {
        if (MyMaterialProgressDrawable.this.mFinishing) {
          MyMaterialProgressDrawable.this.applyFinishTranslation(interpolatedTime, ring);
        }
        else
        {
          float minProgressArc = MyMaterialProgressDrawable.this.getMinProgressArc(ring);
          float startingEndTrim = ring.getStartingEndTrim();
          float startingTrim = ring.getStartingStartTrim();
          float startingRotation = ring.getStartingRotation();

          MyMaterialProgressDrawable.this.updateRingColor(interpolatedTime, ring);

          if (interpolatedTime <= 0.5F)
          {
            float scaledTime = interpolatedTime / 0.5F;

            float startTrim = startingTrim + (0.8F - minProgressArc) * MyMaterialProgressDrawable.MATERIAL_INTERPOLATOR.getInterpolation(scaledTime);

            ring.setStartTrim(startTrim);
          }

          if (interpolatedTime > 0.5F)
          {
            float minArc = 0.8F - minProgressArc;
            float scaledTime = (interpolatedTime - 0.5F) / 0.5F;

            float endTrim = startingEndTrim + minArc * MyMaterialProgressDrawable.MATERIAL_INTERPOLATOR.getInterpolation(scaledTime);

            ring.setEndTrim(endTrim);
          }

          float rotation = startingRotation + 0.25F * interpolatedTime;
          ring.setRotation(rotation);

          float groupRotation = 216.0F * interpolatedTime + 1080.0F * (MyMaterialProgressDrawable.this.mRotationCount / 5.0F);

          MyMaterialProgressDrawable.this.setRotation(groupRotation);
        }
      }
    };
    animation.setRepeatCount(-1);
    animation.setRepeatMode(1);
    animation.setInterpolator(LINEAR_INTERPOLATOR);
    animation.setAnimationListener(new Animation.AnimationListener()
    {
      public void onAnimationStart(Animation animation)
      {
        MyMaterialProgressDrawable.this.mRotationCount = 0.0F;
      }

      public void onAnimationEnd(Animation animation)
      {
      }

      public void onAnimationRepeat(Animation animation)
      {
        ring.storeOriginals();
        ring.goToNextColor();
        ring.setStartTrim(ring.getEndTrim());
        if (MyMaterialProgressDrawable.this.mFinishing)
        {
          MyMaterialProgressDrawable.this.mFinishing = false;
          animation.setDuration(1332L);
          ring.setShowArrow(false);
        } else {
          MyMaterialProgressDrawable.this.mRotationCount = ((MyMaterialProgressDrawable.this.mRotationCount + 1.0F) % 5.0F);
        }
      }
    });
    this.mAnimation = animation;
  }

  private static class Ring {
    private final RectF mTempBounds = new RectF();
    private final Paint mPaint = new Paint();
    private final Paint mArrowPaint = new Paint();
    private final Drawable.Callback mCallback;
    private float mStartTrim = 0.0F;
    private float mEndTrim = 0.0F;
    private float mRotation = 0.0F;
    private float mStrokeWidth = 5.0F;
    private float mStrokeInset = 2.5F;
    private int[] mColors;
    private int mColorIndex;
    private float mStartingStartTrim;
    private float mStartingEndTrim;
    private float mStartingRotation;
    private boolean mShowArrow;
    private Path mArrow;
    private float mArrowScale;
    private double mRingCenterRadius;
    private int mArrowWidth;
    private int mArrowHeight;
    private int mAlpha;
    private final Paint mCirclePaint = new Paint(1);
    private int mBackgroundColor;
    private int mCurrentColor;

    public Ring(Drawable.Callback callback) {
      this.mCallback = callback;

      this.mPaint.setStrokeCap(Paint.Cap.SQUARE);
      this.mPaint.setAntiAlias(true);
      this.mPaint.setStyle(Paint.Style.STROKE);

      this.mArrowPaint.setStyle(Paint.Style.FILL);
      this.mArrowPaint.setAntiAlias(true);
    }

    public void setBackgroundColor(int color) {
      this.mBackgroundColor = color;
    }

    public void setArrowDimensions(float width, float height)
    {
      this.mArrowWidth = (int)width;
      this.mArrowHeight = (int)height;
    }

    public void draw(Canvas c, Rect bounds)
    {
      RectF arcBounds = this.mTempBounds;
      arcBounds.set(bounds);
      arcBounds.inset(this.mStrokeInset, this.mStrokeInset);

      float startAngle = (this.mStartTrim + this.mRotation) * 360.0F;
      float endAngle = (this.mEndTrim + this.mRotation) * 360.0F;
      float sweepAngle = endAngle - startAngle;

      this.mPaint.setColor(this.mCurrentColor);
      c.drawArc(arcBounds, startAngle, sweepAngle, false, this.mPaint);

      drawTriangle(c, startAngle, sweepAngle, bounds);

      if (this.mAlpha < 255) {
        this.mCirclePaint.setColor(this.mBackgroundColor);
        this.mCirclePaint.setAlpha(255 - this.mAlpha);
        c.drawCircle(bounds.exactCenterX(), bounds.exactCenterY(), bounds.width() / 2, this.mCirclePaint);
      }
    }

    private void drawTriangle(Canvas c, float startAngle, float sweepAngle, Rect bounds)
    {
      if (this.mShowArrow) {
        if (this.mArrow == null) {
          this.mArrow = new Path();
          this.mArrow.setFillType(Path.FillType.EVEN_ODD);
        } else {
          this.mArrow.reset();
        }

        float inset = (int)this.mStrokeInset / 2 * this.mArrowScale;
        float x = (float)(this.mRingCenterRadius * Math.cos(0.0D) + bounds.exactCenterX());
        float y = (float)(this.mRingCenterRadius * Math.sin(0.0D) + bounds.exactCenterY());

        this.mArrow.moveTo(0.0F, 0.0F);
        this.mArrow.lineTo(this.mArrowWidth * this.mArrowScale, 0.0F);
        this.mArrow.lineTo(this.mArrowWidth * this.mArrowScale / 2.0F, this.mArrowHeight * this.mArrowScale);

        this.mArrow.offset(x - inset, y);
        this.mArrow.close();

        this.mArrowPaint.setColor(this.mCurrentColor);
        c.rotate(startAngle + sweepAngle - 5.0F, bounds.exactCenterX(), bounds.exactCenterY());

        c.drawPath(this.mArrow, this.mArrowPaint);
      }
    }

    public void setColors(@NonNull int[] colors)
    {
      this.mColors = colors;

      setColorIndex(0);
    }

    public void setColor(int color)
    {
      this.mCurrentColor = color;
    }

    public void setColorIndex(int index)
    {
      this.mColorIndex = index;
      this.mCurrentColor = this.mColors[this.mColorIndex];
    }

    public int getNextColor()
    {
      return this.mColors[getNextColorIndex()];
    }

    private int getNextColorIndex() {
      return (this.mColorIndex + 1) % this.mColors.length;
    }

    public void goToNextColor()
    {
      setColorIndex(getNextColorIndex());
    }

    public void setColorFilter(ColorFilter filter) {
      this.mPaint.setColorFilter(filter);
      invalidateSelf();
    }

    public void setAlpha(int alpha)
    {
      this.mAlpha = alpha;
    }

    public int getAlpha()
    {
      return this.mAlpha;
    }

    public void setStrokeWidth(float strokeWidth)
    {
      this.mStrokeWidth = strokeWidth;
      this.mPaint.setStrokeWidth(strokeWidth);
      invalidateSelf();
    }

    public float getStrokeWidth()
    {
      return this.mStrokeWidth;
    }

    public void setStartTrim(float startTrim)
    {
      this.mStartTrim = startTrim;
      invalidateSelf();
    }

    public float getStartTrim()
    {
      return this.mStartTrim;
    }

    public float getStartingStartTrim() {
      return this.mStartingStartTrim;
    }

    public float getStartingEndTrim() {
      return this.mStartingEndTrim;
    }

    public int getStartingColor() {
      return this.mColors[this.mColorIndex];
    }

    public void setEndTrim(float endTrim)
    {
      this.mEndTrim = endTrim;
      invalidateSelf();
    }

    public float getEndTrim()
    {
      return this.mEndTrim;
    }

    public void setRotation(float rotation)
    {
      this.mRotation = rotation;
      invalidateSelf();
    }

    public float getRotation()
    {
      return this.mRotation;
    }

    public void setInsets(int width, int height) {
      float minEdge = Math.min(width, height);
      float insets;
      if ((this.mRingCenterRadius <= 0.0D) || (minEdge < 0.0F))
        insets = (float)Math.ceil(this.mStrokeWidth / 2.0F);
      else {
        insets = (float)(minEdge / 2.0F - this.mRingCenterRadius);
      }
      this.mStrokeInset = insets;
    }

    public float getInsets()
    {
      return this.mStrokeInset;
    }

    public void setCenterRadius(double centerRadius)
    {
      this.mRingCenterRadius = centerRadius;
    }

    public double getCenterRadius() {
      return this.mRingCenterRadius;
    }

    public void setShowArrow(boolean show)
    {
      if (this.mShowArrow != show) {
        this.mShowArrow = show;
        invalidateSelf();
      }
    }

    public void setArrowScale(float scale)
    {
      if (scale != this.mArrowScale) {
        this.mArrowScale = scale;
        invalidateSelf();
      }
    }

    public float getStartingRotation()
    {
      return this.mStartingRotation;
    }

    public void storeOriginals()
    {
      this.mStartingStartTrim = this.mStartTrim;
      this.mStartingEndTrim = this.mEndTrim;
      this.mStartingRotation = this.mRotation;
    }

    public void resetOriginals()
    {
      this.mStartingStartTrim = 0.0F;
      this.mStartingEndTrim = 0.0F;
      this.mStartingRotation = 0.0F;
      setStartTrim(0.0F);
      setEndTrim(0.0F);
      setRotation(0.0F);
    }

    private void invalidateSelf() {
      this.mCallback.invalidateDrawable(null);
    }
  }

  @Retention(RetentionPolicy.CLASS)
  public static @interface ProgressDrawableSize
  {
  }
}