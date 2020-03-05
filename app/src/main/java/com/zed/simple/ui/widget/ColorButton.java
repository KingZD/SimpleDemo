package com.zed.simple.ui.widget;


import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatButton;

import com.zed.simple.util.UiUtils;

public class ColorButton extends AppCompatButton {
    Paint mPaint;
    //按钮大小
    int mWidth, mHeight;
    //圆角大小
    int roundCorner = 0;
    //阴影大小
    int shadowLayer = 0;
    //阴影起始坐标
    int shadowLayerX, shadowLayerY;
    //阴影默认色
    int shadowColor = Color.BLACK;
    //发光大小
    int maskFilter = 0;
    //发光默认色
    int maskColor = Color.BLACK;
    //模拟按钮点击缩放效果
    int clickScaleSize = 0;

    public ColorButton(Context context) {
        super(context);
        init();
    }

    public ColorButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ColorButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    /**
     * 初始化一些杂七杂八的
     */
    private void init() {
        roundCorner = UiUtils.dip2px(getResources(), 100);
        clickScaleSize = UiUtils.dip2px(getResources(), 5);
        //去掉背景
        setBackgroundResource(android.R.color.transparent);
        initPaint();
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        if (mPaint == null)
            mPaint = new Paint();
        mPaint.reset();
        //抗锯齿
        mPaint.setAntiAlias(true);
        //防抖动
        mPaint.setDither(true);
        //对图片进行处理
        mPaint.setFilterBitmap(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        addColor(canvas);
        super.onDraw(canvas);
    }

    private void addColor(Canvas canvas) {
        initPaint();
        //如果阴影和发光同时有，计算最大的那个区域，避免被显示范围切断
        int shadowMaskSpace = Math.max(shadowLayer, maskFilter);
        float x0 = shadowMaskSpace + shadowLayerX;
        float y0 = shadowMaskSpace + shadowLayerY;
        float x1 = mWidth - shadowMaskSpace - shadowLayerX;
        float y1 = mHeight - shadowMaskSpace - shadowLayerY;

        Path mPath = new Path();
        //判断边 与 roundCorner大小 取最小值
        int mNewRoundCorner = Math.min(roundCorner, Math.min(mHeight, mWidth));
//        if (mNewRoundCorner == mWidth||mNewRoundCorner == mHeight)
//            mNewRoundCorner = mNewRoundCorner / 2;
        //绘制圆形
        mPath.addRoundRect(new RectF(x0,
                        y0,
                        x1,
                        y1),
                new float[]{mNewRoundCorner, mNewRoundCorner, mNewRoundCorner, mNewRoundCorner, mNewRoundCorner, mNewRoundCorner, mNewRoundCorner, mNewRoundCorner},
                Path.Direction.CW);
        if (shadowLayer > 0)
            mPaint.setShadowLayer(shadowLayer, shadowLayerX, shadowLayerY, shadowColor);
        if (maskFilter > 0) {
            mPaint.setColor(maskColor);
            mPaint.setMaskFilter(new BlurMaskFilter(maskFilter, BlurMaskFilter.Blur.NORMAL));
        }
        //绘制阴影或者发光效果
        if (shadowLayer > 0 || maskFilter > 0) {
            canvas.drawPath(mPath, mPaint);
        }
        //清空阴影在绘制，否则会和渐变色混合
        mPaint.clearShadowLayer();
        LinearGradient linearGradient = new LinearGradient(
                x0,
                y0,
                x1,
                y1,
                Color.RED,
                Color.BLUE,
                Shader.TileMode.CLAMP);
        mPaint.setShader(linearGradient);
        //渐变色 点击
        canvas.drawPath(mPath, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                shadowLayer = shadowLayer + clickScaleSize;
                postInvalidate();
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                shadowLayer = shadowLayer - clickScaleSize;
                postInvalidate();
        }
        return true;
    }
}
