package com.gaojun.hasee.popbgview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 高俊 on 2017/8/17.
 */

public class PopBgView extends View {
    private Paint mPaint;

    //背景颜色
    private int roundRectBgColor;
    //圆角度数
    private float roundRectDegree;
    //三角型方位
    private int triangleLocation;
    private int width;
    private int height;
    private int triangleSideLength;
    private static final int TOP = 0;
    private static final int RIGHT = 1;
    private static final int BOTTOM = 2;//默认位置
    private static final int LEFT = 3;


    public PopBgView(Context context) {
        this(context, null, 0);
    }

    public PopBgView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PopBgView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取初始化设置
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PopBgView);
        roundRectBgColor = typedArray.getColor(R.styleable.PopBgView_round_rect_bg_color, Color.WHITE);
        roundRectDegree = typedArray.getDimension(R.styleable.PopBgView_round_rect_degree, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()));
        triangleLocation = typedArray.getInt(R.styleable.PopBgView_triangle_location, BOTTOM);//默认在底部
        triangleSideLength=(int)typedArray.getDimension(R.styleable.PopBgView_triangle_side_length,30);
        typedArray.recycle();

        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(roundRectBgColor);
    }

    //自适应布局
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        //自适应的情况下默认宽200dp 高100dp
        int resultWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics());
        int resultHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());

        if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT && getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(resultWidth, resultHeight);
        } else if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(resultWidth, sizeHeight);
        } else if (getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(sizeWidth, resultHeight);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画圆角矩形
        RectF rectF = new RectF(triangleSideLength, triangleSideLength, width - triangleSideLength, height - triangleSideLength);
        canvas.drawRoundRect(rectF, roundRectDegree, roundRectDegree, mPaint);
        //画三角型
        drawTriangle(triangleLocation, canvas);
    }

    private void drawTriangle(int orientation, Canvas canvas) {
        Path pathTriangle = new Path();
        switch (orientation) {
            case TOP:
                pathTriangle.moveTo(width / 2 - triangleSideLength, triangleSideLength);
                pathTriangle.lineTo(width / 2, 0);
                pathTriangle.lineTo(width / 2 + triangleSideLength, triangleSideLength);
                pathTriangle.close();
                break;

            case RIGHT:
                pathTriangle.moveTo(width - triangleSideLength, height / 2 - triangleSideLength);
                pathTriangle.lineTo(width, height / 2);
                pathTriangle.lineTo(width - triangleSideLength, height / 2 + triangleSideLength);
                pathTriangle.close();

                break;

            case BOTTOM:
                pathTriangle.moveTo(width / 2 - triangleSideLength, height - triangleSideLength);
                pathTriangle.lineTo(width / 2, height);
                pathTriangle.lineTo(width / 2 + triangleSideLength, height - triangleSideLength);
                pathTriangle.close();
                break;

            case LEFT:
                pathTriangle.moveTo(triangleSideLength, height / 2 - triangleSideLength);
                pathTriangle.lineTo(0, height / 2);
                pathTriangle.lineTo(triangleSideLength, height / 2 + triangleSideLength);
                pathTriangle.close();
                break;
        }
        canvas.drawPath(pathTriangle, mPaint);
    }

    public int getRoundRectBgColor() {
        return roundRectBgColor;
    }

    public void setRoundRectBgColor(int roundRectBgColor) {
        this.roundRectBgColor = roundRectBgColor;
    }

    public float getRoundRectDegree() {
        return roundRectDegree;
    }

    public void setRoundRectDegree(float roundRectDegree) {
        this.roundRectDegree = roundRectDegree;
    }

    public int getTriangleLocation() {
        return triangleLocation;
    }

    public void setTriangleLocation(int triangleLocation) {
        this.triangleLocation = triangleLocation;
    }
}
