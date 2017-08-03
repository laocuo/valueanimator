package com.tt.test.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

public class BottleView extends View {
    private final String TAG = "BottleView";
    private final int BOTTLE_W = 200;
    private final int BOTTLE_H = 300;
    // color
    private static final int DEFAULT_BOTTLE_COLOR = 0XFFCEFCFF;
    private static final int DEFAULT_WATER_COLOR = 0XFF41EDFA;

    /*
     * 0-90 90:full, 0:half
     */
    private int jiaodu = 20;

    /*
     * bottle rectangle
     */
    private Rect mRect;

    private Paint mPaint, mWaterPaint;
    private Path mPath, mWaterPath;
    private RectF mBottleBottomF, mWaterRectF;
    private int bottleR = BOTTLE_W/2, pingkouzhijin = BOTTLE_W/5, waveDistance = BOTTLE_W/10;
    private int viewW, viewH, bottleW, bottleH, waterW, waterH, startX = 0, startY = 0;
    private int bottleColor, waterColor;
    private float strokeWidth = BOTTLE_W/20;
    private float scale_water_bottle = 0.9f;

    private Point start, middle, end;
    private boolean isInit = false;

    public BottleView(Context context) {
        this(context, null);
    }

    public BottleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BottleView, defStyleAttr, 0);
        bottleColor = ta.getColor(R.styleable.BottleView_bottle_color, DEFAULT_BOTTLE_COLOR);
        waterColor = ta.getColor(R.styleable.BottleView_water_color, DEFAULT_WATER_COLOR);
        ta.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width, height;
        int width_mode = MeasureSpec.getMode(widthMeasureSpec);
        int width_size = MeasureSpec.getSize(widthMeasureSpec);
        int height_mode = MeasureSpec.getMode(heightMeasureSpec);
        int height_size = MeasureSpec.getSize(heightMeasureSpec);
        switch (width_mode) {
            case MeasureSpec.EXACTLY:
                width = width_size;
                break;
            default:
                width = dipToPx(getContext(), BOTTLE_W);
                break;
        }
        switch (height_mode) {
            case MeasureSpec.EXACTLY:
                height = height_size;
                break;
            default:
                height = dipToPx(getContext(), BOTTLE_H);
                break;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        float default_scale = (float)BOTTLE_H / (float)BOTTLE_W;
        float current_scale = (float)h / (float)w;
        viewW = w;
        viewH = h;
        bottleW = w;
        bottleH = h;
        if (current_scale > default_scale) {
            bottleH = (int) (w * default_scale);
        } else {
            bottleW = (int) (h / default_scale);
        }

        //reduce stroke width
        bottleW = bottleW * 20 / 21;
        bottleH = (int) (bottleW * default_scale);
        init();
    }

    private void init() {
        initRects();
        initPaints();
        initBottlePath();
        initWaterParams();
        updateWaterPath(0);
    }

    private void initRects() {
        bottleR = bottleW/2;
        pingkouzhijin = bottleW/5;
        waveDistance = bottleW/10;
        strokeWidth = bottleW/20;
        mRect = new Rect((viewW - bottleW)/2,(viewH - bottleH)/2,(viewW - bottleW)/2+bottleW,(viewH - bottleH)/2+bottleH);
        mBottleBottomF = new RectF(0,0,bottleR*2,bottleR*2);
        mWaterRectF = new RectF(0,0,bottleR*2*scale_water_bottle, bottleR*2*scale_water_bottle);
    }

    private void initPaints() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(strokeWidth);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setColor(bottleColor);
        CornerPathEffect patheffect = new CornerPathEffect(strokeWidth);
        mPaint.setPathEffect(patheffect);

        mWaterPaint = new Paint();
        mWaterPaint.setStyle(Paint.Style.FILL);
        mWaterPaint.setAntiAlias(true);
        mWaterPaint.setColor(waterColor);
    }

    private void initBottlePath() {
        mPath = new Path();
        mWaterPath = new Path();
        startX = (viewW - pingkouzhijin)/2;
        startY = (int) (strokeWidth/2 + (viewH - bottleH)/2) + 1;
        mPath.moveTo(startX,startY);
        int x = bottleW / 20;
        mPath.rQuadTo(-x,0,-x,x);
        mPath.rLineTo(x,x);
        mPath.rLineTo(0,bottleW/2);
        mBottleBottomF.offsetTo(viewW/2 - bottleR, startY + x + x + bottleW/2);
        mPath.arcTo(mBottleBottomF, 252, -117, false);
        mPath.rLineTo((float) (bottleR*1.414/2), 0);

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
    }

    private void initWaterParams() {
        if (!isInit) {
            isInit = true;
            int gap = (int) ((mBottleBottomF.height() - mWaterRectF.height()) / 2);
            int x = bottleW / 20;
            int waterStartY = startY + x + x + bottleW/2 + gap;
            mWaterRectF.offsetTo(viewW / 2 - mWaterRectF.width() / 2, waterStartY);

            double hudu = jiaodu * Math.PI / 180;
            int waterR = (int) (bottleR * scale_water_bottle);
            waterH = (int) (Math.sin(hudu) * waterR);
            waterW = (int) (Math.cos(hudu) * waterR);
            start = new Point(viewW / 2 + waterW, waterStartY + waterR - waterH);
            middle = new Point(viewW / 2, waterStartY + waterR - waterH);
            end = new Point(viewW / 2 - waterW, waterStartY + waterR - waterH);
        }
    }

    private void updateWaterPath(float scale) {
        mWaterPath.reset();
        mWaterPath.arcTo(mWaterRectF, 180 + jiaodu, -45 - jiaodu);
        mWaterPath.arcTo(mWaterRectF, 45, -45 - jiaodu, false);
        float waveH = 0;
        float waveV = waveDistance * (1 - (float)jiaodu/90f) * scale;
        mWaterPath.quadTo(middle.x + waterW/2 + waveH,
                middle.y + waveV,
                middle.x, middle.y);
        mWaterPath.quadTo(end.x + waterW/2 + waveH,
                end.y - waveV,
                end.x, end.y);
        mWaterPath.close();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isInit) {
            canvas.drawPath(mPath, mPaint);
            canvas.drawPath(mWaterPath, mWaterPaint);
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

    private int dipToPx(Context context, int dip) {
        return (int) (dip * getScreenDensity(context) + 0.5f);
    }

    private float getScreenDensity(Context context) {
        try {
            DisplayMetrics dm = new DisplayMetrics();
            ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
                    .getMetrics(dm);
            return dm.density;
        } catch (Exception e) {
            return DisplayMetrics.DENSITY_DEFAULT;
        }
    }
}

