package com.tt.test.ani;

import android.view.animation.Interpolator;

/**
 * -1 1 -1.
 */

public class WaveInterpolater implements Interpolator {
    @Override
    public float getInterpolation(float input) {
        if (input > 0.5) {
            return (1 - input)*4 - 1;
        } else {
            return input*4 - 1;
        }
    }
}
