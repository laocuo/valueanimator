package com.tt.test.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

import com.tt.test.R;

/**
 * Created by hoperun on 10/12/16.
 */

public class XfermodeView extends View {
    private Paint mCpaint, mRpaint;
    private Canvas mSrcCanvas;
    private Bitmap mSrcBitmap, mDestBitmap;
    private final int mRadius = 100;
    private int mSrcW,mSrcH;
    private PorterDuffXfermode mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SCREEN);
    public XfermodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mCpaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCpaint.setColor(Color.GREEN);
        mRpaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRpaint.setColor(Color.BLUE);
        mDestBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.xxx);
        mSrcW = mDestBitmap.getWidth();
        mSrcH = mDestBitmap.getHeight();
        mSrcBitmap = Bitmap.createBitmap(mRadius+mSrcW,mRadius+mSrcH, Bitmap.Config.ARGB_8888);
        mSrcCanvas = new Canvas(mSrcBitmap);
//        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mSrcCanvas.drawCircle(mSrcW, mSrcH, mRadius, mCpaint);
        int layer = canvas.saveLayer(0,0,mRadius+mSrcW,mRadius+mSrcH,null,Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(mDestBitmap, 0, 0, mRpaint);
        mRpaint.setXfermode(mXfermode);
        canvas.drawBitmap(mSrcBitmap, 0, 0, mRpaint);
        mRpaint.setXfermode(null);
        canvas.restoreToCount(layer);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        setMeasuredDimension(mRadius*2, mRadius*2);
    }
}
