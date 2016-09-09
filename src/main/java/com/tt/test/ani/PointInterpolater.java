package com.tt.test.ani;

import android.view.animation.Interpolator;

/**
 * Created by hoperun on 9/7/16.
 */

public class PointInterpolater implements Interpolator {
    @Override
    public float getInterpolation(float v) {
        if (v > 0.5) {
            return (1 - v)*2;
        } else {
            return v*2;
        }
    }
}
