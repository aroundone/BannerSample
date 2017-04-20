package com.foxmail.aroundme.banner.indicator;

import android.support.annotation.IntDef;
import android.webkit.WebSettings;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by gzl on 1/3/17.
 */

public class IndicatorShape {
    @IntDef({rect, oval})

    @Retention(RetentionPolicy.SOURCE)

    public @interface Shapes {
    }

    public static final int rect = 0;
    public static final int oval = 1;

}
