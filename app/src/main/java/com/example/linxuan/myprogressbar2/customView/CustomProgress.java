package com.example.linxuan.myprogressbar2.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.example.linxuan.myprogressbar2.R;

/**
 * Created by linxuan on 2017/8/29.
 */

public class CustomProgress extends View {
    // 画实心圆的画笔
    private Paint mCirclePaint;
    // 画圆环的画笔
    private Paint mRingPaint;
    // 画字体的画笔
    private Paint mTextPaint;
    // 圆形颜色
    private int mCirclsColor;
    // 圆环颜色
    private int mRingColor;
    // 半径
    private float mRadius;
    // 圆环半径
    private float mRingRadius;
    // 圆环宽度
    private float mStrokeWidth;
    // 字的长度
    private float mTextWidth;
    // 字的高度
    private float mTextHeight;
    // 总进度
    private int mTotalProgress;
    // 当前进度
    private int mProgress;
    // 大圆
    private Paint mBigPatient;
    // 字体颜色
    private int mTextColor;
    //外圆颜色
    private int mBigCircleColor;
    // 圆心x坐标
    private int mXCenter;
    // 圆心y坐标
    private int mYCenter;

    public CustomProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context,attrs);   // 获取自定义的属性
        initVariable(); // 初始化一些变量
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomProgress, 0, 0);
        mRadius = typedArray.getDimension(R.styleable.CustomProgress_radius, 300);
        mStrokeWidth = typedArray.getDimension(R.styleable.CustomProgress_strokeWidth, 20);
        mCirclsColor = typedArray.getColor(R.styleable.CustomProgress_circleColor, Color.BLUE);
        mRingColor = typedArray.getColor(R.styleable.CustomProgress_ringColor, Color.RED);
        mTotalProgress = typedArray.getInt(R.styleable.CustomProgress_totalProgress, 100);
        mTextColor = typedArray.getColor(R.styleable.CustomProgress_textColor, Color.WHITE);
        mBigCircleColor = typedArray.getColor(R.styleable.CustomProgress_bigCircleColor, Color.WHITE);

        typedArray.recycle();//注意这里要释放掉

        mRingRadius = mRadius + mStrokeWidth / 2;
    }

    private void initVariable() {
        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(mCirclsColor);
        mCirclePaint.setStrokeCap(Paint.Cap.ROUND);
        mCirclePaint.setStyle(Paint.Style.FILL);

        mRingPaint = new Paint();
        mRingPaint.setAntiAlias(true);
        mRingPaint.setColor(mRingColor);
        mRingPaint.setStrokeCap(Paint.Cap.ROUND);
        mRingPaint.setStyle(Paint.Style.STROKE);
        mRingPaint.setStrokeWidth(mStrokeWidth);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mRadius / 2);

        mBigPatient = new Paint();
        mBigPatient.setColor(mBigCircleColor);
        mBigPatient.setAntiAlias(true);
        mBigPatient.setStyle(Paint.Style.FILL);

        Paint.FontMetrics fm = mTextPaint.getFontMetrics();
        mTextHeight = (float) Math.ceil(fm.descent - fm.ascent);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mXCenter = getWidth() / 2;
        mYCenter = getHeight() / 2;

        canvas.drawCircle(mXCenter, mYCenter, mRadius + mStrokeWidth, mBigPatient);
        canvas.drawCircle(mXCenter, mYCenter, mRadius, mCirclePaint);

        if (mProgress > 0) {
            RectF oval = new RectF();
            oval.left = (mXCenter - mRingRadius);
            oval.top = (mYCenter - mRingRadius);
            oval.right = mRingRadius * 2 + (mXCenter - mRingRadius);
            oval.bottom = mRingRadius * 2 + (mYCenter - mRingRadius);
            canvas.drawArc(oval, -90,((float)mProgress / mTotalProgress) * 360 , false, mRingPaint);

            String txt = (int) (mProgress * 1.0f / mTotalProgress * 100) + "%";
            mTextWidth = mTextPaint.measureText(txt, 0, txt.length());
            canvas.drawText(txt, mXCenter - mTextWidth / 2, mYCenter + mTextHeight / 4, mTextPaint);
        }
    }

    public void setProgress(int progress){
        mProgress = progress;
        postInvalidate();
    }

    public void setTotalProgress(int totalProgress){
        mTotalProgress = totalProgress;
    }
}
