package com.suggee.edustudent.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.suggee.edustudent.R;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao1987@gmail.com
 * Date:    16/8/2
 * Description:
 * 角标控件
 */
public class LabelView extends View {

    Paint mTextPaint;
    int mTextColor;
    float mTextSize;
    float mTextHeight;
    float mTextWidth;
    int mTextStyle;

    float mTopPadding;
    float mBottomPadding;
    float mCenterPadding;


    Paint mTrianglePaint;
    int mBackGroundColor;

    float mDegrees;

    String mText = "Top";

    int width;
    int height;

    public static final int DEGREES_LEFT = -45;
    public static final int DEGREES_RIGHT = 45;

    public LabelView(Context context) {
        this(context, null);
    }

    public LabelView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LabelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.LabelView);

        mTopPadding = ta.getDimension(R.styleable.LabelView_labelTopPadding, dp2px(3));
        mBottomPadding = ta.getDimension(R.styleable.LabelView_labelBottomPadding, dp2px(3));

        mBackGroundColor = ta.getColor(R.styleable.LabelView_backgroundColor, Color.parseColor("#66000000"));
        mTextColor = ta.getColor(R.styleable.LabelView_textColor, Color.WHITE);

        mTextSize = ta.getDimension(R.styleable.LabelView_textSize, sp2px(8));

        mText = ta.getString(R.styleable.LabelView_text);

        mTextStyle = ta.getInt(R.styleable.LabelView_textStyle, 0);

        mDegrees = ta.getInt(R.styleable.LabelView_direction, 45);

        ta.recycle();

        initTextPaint();
        initTrianglePaint();

        resetTextStatus();
    }

    public void setText(String text) {
        mText = text;
        resetTextStatus();
        invalidate();
    }

    public void setBackGroundColor(int color) {
        mTrianglePaint.setColor(color);
        invalidate();
    }

    private void initTextPaint() {
        //初始化绘制修饰文本的画笔
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setTextSize(mTextSize);
        if (mTextStyle == 1) {
            mTextPaint.setTypeface(Typeface.SANS_SERIF);
        } else if (mTextStyle == 2) {
            mTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
        }
    }

    private void initTrianglePaint() {
        //初始化绘制三角形背景的画笔
        mTrianglePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTrianglePaint.setColor(mBackGroundColor);
    }

    private void resetTextStatus() {
        // 测量文字高度
        Rect rectText = new Rect();
        mTextPaint.getTextBounds(mText, 0, mText.length(), rectText);
        mTextWidth = rectText.width();
        mTextHeight = rectText.height();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        canvas.save();

        //位移和旋转canvas
        canvas.translate(0, (float) ((height * Math.sqrt(2)) - height));
        if (mDegrees == DEGREES_LEFT) {
            canvas.rotate(mDegrees, 0, height);
        } else if (mDegrees == DEGREES_RIGHT) {
            canvas.rotate(mDegrees, width, height);
        }

        //绘制三角形背景
        Path path = new Path();
        path.moveTo(0, height);
        path.lineTo(width / 2, 0);
        path.lineTo(width, height);
        path.close();
        canvas.drawPath(path, mTrianglePaint);
        //绘制修饰文本
        canvas.drawText(mText, (width) / 2, mTopPadding + mTextHeight, mTextPaint);

        canvas.restore();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        height = (int) (mTopPadding + mBottomPadding + mTextHeight);
        width = 2 * height;
        //控件的真正高度，勾股定理...
        int realHeight = (int) (height * Math.sqrt(2));
        setMeasuredDimension(width, realHeight);
    }

    public int dp2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public float sp2px(float spValue) {
        final float scale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return spValue * scale;
    }
}
