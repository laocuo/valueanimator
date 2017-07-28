package com.tt.test.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.tt.test.ani.WaveInterpolater;


public class BottleView extends View {
    private Rect mRect;
    private Paint mPaint, mWaterPaint, mPointPaint;
    private Path mPath, mWaterPath;
    private RectF mRectF, mWaterRectF;
    private int bottleR = 200, startX = 0, startY = 100, pingkouzhijin = 80;
    private int bottleW = bottleR * 2, bottleH = bottleR * 2 + 0;
    private int w,h,waterW, waterH;
    private float strokeWidth = 20f;
    private float scale_water_bottle = 0.92f;
    private int jiaodu = 10, waveDistance = 40;
    private Point start, middle, end;
    private boolean isInit = false;

    public BottleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(strokeWidth);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setColor(Color.GRAY);
        CornerPathEffect patheffect = new CornerPathEffect(strokeWidth);
        mPaint.setPathEffect(patheffect);

        mWaterPaint = new Paint();
        mWaterPaint.setStyle(Paint.Style.FILL);
        mWaterPaint.setAntiAlias(true);
        mWaterPaint.setColor(Color.BLUE);

        mPointPaint = new Paint();
        mPointPaint.setStyle(Paint.Style.STROKE);
        mPointPaint.setStrokeWidth(4);
        mPointPaint.setAntiAlias(true);
        mPointPaint.setColor(Color.RED);

        mRect = new Rect();
        mRectF = new RectF(0,0,bottleW,bottleH);
        mWaterRectF = new RectF(0,0,bottleW*scale_water_bottle, bottleH*scale_water_bottle);

        mPath = new Path();
        mWaterPath = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRect.set(0,0,w,h);
        this.w = w;
        this.h = h;
        init();
    }

    private void init() {
        initBottlePath();
        initWaterParams();
        updateWaterPath(0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        canvas.drawPath(mPath, mPaint);
        if (isInit) {
            canvas.drawPath(mWaterPath, mWaterPaint);
//            drawPoint(canvas, start);
//            drawPoint(canvas, middle);
//            drawPoint(canvas, end);
        }
    }

    private void drawPoint(Canvas canvas, Point p) {
        if (p != null) {
            canvas.drawPoint(p.x, p.y, mPointPaint);
        }
    }

    public void performAnim() {
        ValueAnimator anim = ValueAnimator.ofFloat(0, 1.0f);
        anim.setRepeatCount(ValueAnimator.INFINITE);
        anim.setInterpolator(new WaveInterpolater());
        anim.setDuration(2000);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float scale = animation.getAnimatedFraction();
                if (isInit) {
                    updateWaterPath(scale);
                }
            }
        });
        anim.start();
    }

    private void initBottlePath() {
        startX = (w - pingkouzhijin)/2;
        mPath.moveTo(startX,startY);
        mPath.rQuadTo(-20,0,-20,20);
        mPath.rLineTo(20,20);
        mPath.rLineTo(0,200);
        mRectF.offsetTo(w/2 - bottleW/2, startY + 240);
        mPath.arcTo(mRectF, 252, -117, false);
        mPath.rLineTo(160, 0);

        Path mirrorPath = new Path();
        mirrorPath.addPath(mPath);

        Matrix matrix = new Matrix();
        Camera camera = new Camera();
        camera.save();
        camera.rotateY(180);
        camera.getMatrix(matrix);
        camera.restore();
        matrix.preTranslate(-mRect.centerX(), -mRect.centerY());
        matrix.postTranslate(mRect.centerX(), mRect.centerY());
        mPath.addPath(mirrorPath, matrix);

//        RectF mBottleRectF = new RectF();
//        mPath.computeBounds(mBottleRectF, false);
    }

    private void initWaterParams() {
        if (!isInit) {
            isInit = true;
            int gap = (int) ((mRectF.height() - mWaterRectF.height()) / 2);
            int waterStartY = startY + 240 + gap;
            mWaterRectF.offsetTo(w / 2 - mWaterRectF.width() / 2, waterStartY);

            double hudu = jiaodu * Math.PI / 180;
            int waterR = (int) (bottleR * scale_water_bottle);
            waterH = (int) (Math.sin(hudu) * waterR);
            waterW = (int) (Math.cos(hudu) * waterR);
            start = new Point(w / 2 + waterW, waterStartY + waterR - waterH);
            middle = new Point(w / 2, waterStartY + waterR - waterH);
            end = new Point(w / 2 - waterW, waterStartY + waterR - waterH);
        }
    }

    private void updateWaterPath(float scale) {
        mWaterPath.reset();
        mWaterPath.arcTo(mWaterRectF, 180 + jiaodu, -45 - jiaodu);
        mWaterPath.arcTo(mWaterRectF, 45, -45 - jiaodu, false);
        mWaterPath.quadTo(middle.x + waterW/2, middle.y + waveDistance * scale, middle.x, middle.y);
        mWaterPath.quadTo(end.x + waterW/2, end.y - waveDistance * scale, end.x, end.y);
        mWaterPath.close();
        invalidate();
    }
}
