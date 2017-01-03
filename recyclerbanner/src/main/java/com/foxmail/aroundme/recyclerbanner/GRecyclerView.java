package com.foxmail.aroundme.recyclerbanner;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by gzl on 1/3/17.
 */

public class GRecyclerView extends RecyclerView{

    private int itemCount;

    public GRecyclerView(Context context) {
        super(context);
        init();
    }

    public GRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {

        startAutoScroll();
    }

    /**
     * 获取当前的Position
     * @return
     */
    private int getVisiblePosition() {
        return ((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);

        itemCount = ((RecyclerBannerAdapter)adapter).getRealCount();
        //无动画直接跳转
        scrollToPosition(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % itemCount);

        Log.d("msg", "itemCount = " + itemCount);
    }


    private void startAutoScroll() {
        Observable.interval(5, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Long>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(Long aLong) {
                smoothScrollToPosition(getVisiblePosition() + 1);
            }
        });
    }

    @Override
    public void smoothScrollToPosition(int position) {
        super.smoothScrollToPosition(position);

        Log.d("msg", "position = " + position);
    }
}
