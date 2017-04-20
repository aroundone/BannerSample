package com.foxmail.aroundme.banner.viewpager;

import android.support.annotation.CheckResult;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by gzl on 1/4/17.
 *
 */

public interface ViewPagerItem {
    @CheckResult
    View instantiateItem(ViewGroup parent, String url, int position);
}
