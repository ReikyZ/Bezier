package com.reikyz.bezier.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.reikyz.bezier.R;

/**
 * Created by reikyZ on 2016/10/15.
 */

public class BezierActionBar extends View {

    final static String TAG = "==BezierActionBar==";

    Paint mPaint;

    float x1, x2, x3;
    float y1, y2, y3;

    public BezierActionBar(Context context) {
        this(context, null);

    }

    public BezierActionBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BezierActionBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        x1 = 200;
        y1 = 50;// IMPORTANT
        x2 = 500;
        y2 = 50;// IMPORTANT
        x3 = getMeasuredWidth();
        y3 = 50;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(getResources().getColor(R.color.transparent));

        Path bezier = new Path();
        bezier.moveTo(0, 50);

        bezier.cubicTo(x1, y1, x2, y2, x3, y3);

        bezier.lineTo(getMeasuredWidth(), getMeasuredHeight());
        bezier.lineTo(0, getMeasuredHeight());
        bezier.lineTo(0, 50);
        bezier.close();

        mPaint.setColor(getResources().getColor(R.color.blue));
        canvas.drawPath(bezier, mPaint);

    }


    public void startAnimate() {
        if (animStat == 0) {
            animStat = -1;
            new AnimationThread().start();
        }
    }

    int animStat = 0;

    class AnimationThread extends Thread {
        @Override
        public void run() {
            while (y1 < 250 || y2 > 0) {
                if (y1 < 250) {
                    x1 += 5;
                    y1 += 8f;
                }
                if (y2 > 0) {
                    x2 += 7;
                    y2 -= 5.5f;
                }
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                postInvalidate();
                if (y1 >= 250 && y2 <= 0) animStat = 1;
            }
            if (animStat == 1) {
                while (y1 != 50 || y2 != 50) {
                    if (y1 != 50) {
                        x1 -= 5;
                        y1 -= 8f;
                    }
                    if (y2 != 50) {
                        x2 -= 7;
                        y2 += 5.5f;
                    }
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    postInvalidate();
                    if (y1 == 50 && y2 == 50) animStat = 2;
                }
            }

            if (animStat == 2) {
                while (y1 > 0 || y2 < 250) {
                    if (y1 > 0) {
                        x1 -= 5;
                        y1 -= 7f;
                    }
                    if (y2 < 250) {
                        x2 -= 3;
                        y2 += 6.5f;
                    }
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    postInvalidate();
                    if (y1 <= 0 && y2 >= 250) animStat = 3;
                }
            }

            if (animStat == 3) {
                while (y1 != 50 || y2 != 50) {
                    if (y1 != 50) {
                        x1 += 5;
                        y1 += 7f;
                    }
                    if (y2 != 50) {
                        x2 += 3;
                        y2 -= 6.5f;
                    }
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    postInvalidate();
                    if (y1 == 50 && y2 == 50) animStat = 0;
                }
            }

        }
    }
}
