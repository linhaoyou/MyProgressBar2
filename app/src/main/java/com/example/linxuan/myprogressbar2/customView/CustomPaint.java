package com.example.linxuan.myprogressbar2.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.linxuan.myprogressbar2.R;

/**
 * Created by linxuan on 2017/8/30.
 */

public class CustomPaint extends View {
    private int mBoardBackground;   //写字板背景颜色
    private int mPaintColor;        //画笔颜色
    private int mPaintWidth;        //画笔宽度

    private Paint mPaint;
    private Path mPath;

    public CustomPaint(Context context) {
        this(context,null);
    }

    public CustomPaint(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomPaint(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomProgress);
        mBoardBackground = typedArray.getColor(R.styleable.CustomProgress_bigCircleColor, Color.WHITE);
        mPaintColor = typedArray.getColor(R.styleable.CustomProgress_circleColor, Color.BLACK);
        mPaintWidth = typedArray.getDimensionPixelSize(R.styleable.CustomProgress_strokeWidth,15);

        typedArray.recycle();

        mPaint = new Paint();
        mPath = new Path();

        setBackgroundColor(mBoardBackground);
        mPaint.setColor(mPaintColor);
        mPaint.setStrokeWidth(mPaintWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                mPath.lineTo(touchX,touchY);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        invalidate();
        return true;
    }
}
