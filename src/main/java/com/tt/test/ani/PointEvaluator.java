package com.tt.test.ani;

import android.animation.TypeEvaluator;
import android.graphics.Point;

/**
 * Created by hoperun on 9/7/16.
 */

public class PointEvaluator implements TypeEvaluator {
    @Override
    public Object evaluate(float v, Object o, Object t1) {
        Point s = (Point) o;
        Point e = (Point) t1;
        int x = (int) (s.x + v * (e.x - s.x));
        int y = (int) (s.y + v * (e.y - s.y));
        return new Point(x, y);
    }
}
