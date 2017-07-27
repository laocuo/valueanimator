package com.tt.test.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PointFEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;

import com.tt.test.ani.PointInterpolater;

//import com.tt.test.ani.PointEvaluator;
//import com.tt.test.ani.PointInterpolater;

/**
 * Created by hoperun on 9/7/16.
 */

public class Ball extends View {
    private final int RAIDUS = 40;
    private PointF mPoint;
    private Paint mPaint;
    private int mColor = Color.YELLOW;
    public Ball(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPoint = new PointF(RAIDUS,RAIDUS);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        mPaint.setColor(mColor);
        canvas.drawCircle(mPoint.x, mPoint.y, RAIDUS, mPaint);
    }

    public void animateStart() {
        final ValueAnimator ani = ValueAnimator.ofObject(new PointFEvaluator(),
                new PointF(RAIDUS,RAIDUS),
                new PointF(RAIDUS,RAIDUS*20));
        ani.setRepeatCount(ValueAnimator.INFINITE);
//        ani.setDuration(4000);
//        ani.setInterpolator(new BounceInterpolator());
        ani.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                PointF p = (PointF) valueAnimator.getAnimatedValue();
                mPoint.set(p.x, p.y);
                invalidate();
            }
        });
//        ani.start();
        final ValueAnimator ani2 = ValueAnimator.ofArgb(Color.BLUE, Color.YELLOW);
        ani2.setRepeatCount(ValueAnimator.INFINITE);
//        ani2.setDuration(4000);
//        ani2.setInterpolator(new LinearInterpolator());
        ani2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mColor = (int) valueAnimator.getAnimatedValue();
//                invalidate();
            }
        });
//        ani2.start();
        AnimatorSet as = new AnimatorSet();
        as.setDuration(3000);
        as.setInterpolator(new PointInterpolater());
//        as.play(ani2).with(ani);
        as.playTogether(ani, ani2);
        as.start();
    }
}
