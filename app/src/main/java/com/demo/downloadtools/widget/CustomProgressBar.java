package com.demo.downloadtools.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.demo.downloadtools.R;
import com.demo.downloadtools.utils.ScreenUtils;


public class CustomProgressBar extends ProgressBar {
    private String text="";
    private Paint mPaint;
    private int textSize= ScreenUtils.sp2px(14);
    public CustomProgressBar(Context context) {
        this(context,null);
    }

    public CustomProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initText(context, attrs, defStyle);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mPaint.getTextBounds(this.text, 0, this.text.length(), mRect);
        int x = (getWidth() / 2) - mRect.centerX();
        int y = (getHeight() / 2) - mRect.centerY();
        canvas.drawText(this.text, x, y, this.mPaint);
    }
    Rect mRect;
    //初始化，画笔
    private void initText(Context context, AttributeSet attrs, int defStyleAttr){
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.CustomProgressBar);
        textSize = ta.getDimensionPixelSize(R.styleable.CustomProgressBar_textSize, textSize);
        text = ta.getString(R.styleable.CustomProgressBar_text);
        ta.recycle();
        mRect = new Rect();
        this.mPaint = new Paint();
        this.mPaint.setColor(Color.WHITE);
        this.mPaint.setTextSize(textSize);
        this.mPaint.setAntiAlias(true);
    }

    //设置文字内容
    public void setText(String text){
        this.text = text;
        invalidate();
    }
    //设置文字大小
    public void setTextSize(int textSize){
        this.mPaint.setTextSize(ScreenUtils.sp2px(textSize));
    }
    //设置文字颜色
    public void setTextColor(int textColor){
        this.mPaint.setColor(textColor);
        invalidate();
    }
}