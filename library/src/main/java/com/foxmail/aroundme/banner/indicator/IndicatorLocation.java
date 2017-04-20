package com.foxmail.aroundme.banner.indicator;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by gzl on 1/3/17.
 */

public class IndicatorLocation {

    @IntDef({centerBottom, rightBottom,
            leftBottom, centerTop, rightTop, leftTop})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Location {
    }

    public static final int centerBottom = 0;
    public static final int rightBottom = 1;
    public static final int leftBottom = 2;
    public static final int centerTop = 3;
    public static final int rightTop = 4;
    public static final int leftTop = 5;

}
