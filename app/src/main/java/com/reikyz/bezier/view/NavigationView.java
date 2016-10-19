package com.reikyz.bezier.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.reikyz.bezier.R;

/**
 * Created by reikyZ on 2016/10/16.
 */

public class NavigationView extends View {

    final static String TAG = "==NavigationView==";

    private int mWidth, mHeight, mPadding;
    Paint mPaint;

    public NavigationView(Context context) {
        this(context, null);
    }

    public NavigationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(getResources().getColor(R.color.white));
        mPaint.setStrokeWidth(5.0f);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        mPadding = getPaddingBottom();

        pUpStart1 = new Point(mPadding, mHeight / 2);
        pUpEnd1 = new Point(mWidth / 2, mPadding);

        pUpStart2 = new Point(mWidth / 2, mPadding);
        pUpEnd2 = new Point(mWidth - mPadding, mHeight / 2);

        pUpStart3 = new Point(mWidth / 2, mPadding);
        pUpEnd3 = new Point(mWidth / 2, mHeight - mPadding);

        pCloseStart1 = new Point(mPadding / 2 + (mWidth - mPadding * 2) / 4, mPadding / 2 + (mWidth - mPadding * 2) / 4);
        pCloseEnd1 = new Point(mPadding + (mWidth - mPadding * 2) / 4 * 3 + mPadding / 2, mPadding + (mWidth - mPadding * 2) / 4 * 3 + mPadding / 2);

        pCloseStart2 = new Point(mPadding + (mWidth - mPadding * 2) / 4 * 3 + mPadding / 2, mPadding / 2 + (mWidth - mPadding * 2) / 4);
        pCloseEnd2 = new Point(mPadding / 2 + (mWidth - mPadding * 2) / 4, mPadding + (mWidth - mPadding * 2) / 4 * 3 + mPadding / 2);

        pCloseStart3 = new Point(mWidth - mPadding, mHeight / 2);
        pCloseEnd3 = new Point(mPadding, mHeight / 2);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(getResources().getColor(R.color.transparent));

        if (currentStatus == STATUS_UP) {
            canvas.drawLine(pUpStart1.x, pUpStart1.y, pUpEnd1.x, pUpEnd1.y, mPaint);
            canvas.drawLine(pUpStart2.x, pUpStart2.y, pUpEnd2.x, pUpEnd2.y, mPaint);
            canvas.drawLine(pUpStart3.x, pUpStart3.y, pUpEnd3.x, pUpEnd3.y, mPaint);
        } else if (currentStatus == STATUS_CLOSE) {
            mPaint.setAlpha(255);
            canvas.drawLine(pCloseStart1.x, pCloseStart1.y, pCloseEnd1.x, pCloseEnd1.y, mPaint);
            canvas.drawLine(pCloseStart2.x, pCloseStart2.y, pCloseEnd2.x, pCloseEnd2.y, mPaint);
            mPaint.setAlpha(0);
            canvas.drawLine(pCloseStart3.x, pCloseStart3.y, pCloseEnd3.x, pCloseEnd3.y, mPaint);
        } else if (currentStatus == STATUS_TO_CLOSE) {
            mPaint.setAlpha(255);
            canvas.drawLine(
                    pUpStart1.x + (pCloseStart1.x - pUpStart1.x) / 30 * offset,
                    pUpStart1.y + (pCloseStart1.y - pUpStart1.y) / 30 * offset,
                    pUpEnd1.x + (pCloseEnd1.x - pUpEnd1.x) / 30 * offset,
                    pUpEnd1.y + (pCloseEnd1.y - pUpEnd1.y) / 30 * offset,
                    mPaint);
            canvas.drawLine(
                    pUpStart2.x + (pCloseStart2.x - pUpStart2.x) / 30 * offset,
                    pUpStart2.y + (pCloseStart2.y - pUpStart2.y) / 30 * offset,
                    pUpEnd2.x + (pCloseEnd2.x - pUpEnd2.x) / 30 * offset,
                    pUpEnd2.y + (pCloseEnd2.y - pUpEnd2.y) / 30 * offset,
                    mPaint);
            mPaint.setAlpha((30 - offset) / 30 * 255);
            canvas.drawLine(
                    pUpStart3.x + (pCloseStart3.x - pUpStart3.x) / 30 * offset,
                    pUpStart3.y + (pCloseStart3.y - pUpStart3.y) / 30 * offset,
                    pUpEnd3.x + (pCloseEnd3.x - pUpEnd3.x) / 30 * offset,
                    pUpEnd3.y + (pCloseEnd3.y - pUpEnd3.y) / 30 * offset,
                    mPaint);
        } else if (currentStatus == STATUS_TO_UP) {
            mPaint.setAlpha(255);
            canvas.drawLine(
                    pCloseStart1.x + (pUpEnd1.x - pCloseStart1.x) / 30 * offset,
                    pCloseStart1.y + (pUpEnd1.y - pCloseStart1.y) / 30 * offset,
                    pCloseEnd1.x + (pUpStart1.x - pCloseEnd1.x) / 30 * offset,
                    pCloseEnd1.y + (pUpStart1.y - pCloseEnd1.y) / 30 * offset,
                    mPaint);
            canvas.drawLine(
                    pCloseStart2.x + (pUpEnd2.x - pCloseStart2.x) / 30 * offset,
                    pCloseStart2.y + (pUpEnd2.y - pCloseStart2.y) / 30 * offset,
                    pCloseEnd2.x + (pUpStart2.x - pCloseEnd2.x) / 30 * offset,
                    pCloseEnd2.y + (pUpStart2.y - pCloseEnd2.y) / 30 * offset,
                    mPaint);
            mPaint.setAlpha(255);
            canvas.drawLine(
                    pCloseStart3.x + (pUpEnd3.x - pCloseStart3.x) / 30 * offset,
                    pCloseStart3.y + (pUpEnd3.y - pCloseStart3.y) / 30 * offset,
                    pCloseEnd3.x + (pUpStart3.x - pCloseEnd3.x) / 30 * offset,
                    pCloseEnd3.y + (pUpStart3.y - pCloseEnd3.y) / 30 * offset,
                    mPaint);
        }
    }

    Point pUpStart1, pUpEnd1, pUpStart2, pUpEnd2, pUpStart3, pUpEnd3;
    Point pCloseStart1, pCloseEnd1, pCloseStart2, pCloseEnd2, pCloseStart3, pCloseEnd3;


    final static int STATUS_UP = 0x00;
    final static int STATUS_CLOSE = 0x01;
    final static int STATUS_TO_UP = 0x02;
    final static int STATUS_TO_CLOSE = 0x03;

    int currentStatus = STATUS_UP;
    int offset = 0;

    public void changeStatus(boolean open) {
        if (open) {
            currentStatus = STATUS_UP;
        } else {
            currentStatus = STATUS_CLOSE;
        }
        if (currentStatus == STATUS_UP) {
            Log.e(TAG, "STATUS_TO_CLOSE");
            currentStatus = STATUS_TO_CLOSE;
            new AnimationThread().start();
        } else if (currentStatus == STATUS_CLOSE) {
            Log.e(TAG, "STATUS_TO_UP");
            currentStatus = STATUS_TO_UP;
            new AnimationThread().start();
        }
    }

    class AnimationThread extends Thread {
        @Override
        public void run() {
            for (int i = 1; i <= 30; i++) {
                offset = i;
                try {
                    Thread.sleep(15);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                postInvalidate();
            }
            if (currentStatus == STATUS_TO_CLOSE) {
                currentStatus = STATUS_CLOSE;
            } else if (currentStatus == STATUS_TO_UP) {
                currentStatus = STATUS_UP;
            }
        }
    }

    class Point {
        float x, y;

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }
}
