package com.foxmail.aroundme.banner.recyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.foxmail.aroundme.banner.indicator.IOnPageChangeListener;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by gzl on 1/3/17.
 */

public class GRecyclerView extends RecyclerView{

    private int itemCount;

    private boolean isCanScroll;
    //设置全局防止重复订阅
    private Subscription subscription;

    private IOnPageChangeListener iOnPageChangeListener;

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
        beginAutoScroll();
    }

    private void beginAutoScroll() {
        //取消上次订阅
        if(subscription != null) {
            subscription.unsubscribe();
        }

        subscription = Observable.interval(4, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Long>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(Long aLong) {
                if(isCanScroll) {

                    int position = getVisiblePosition() % itemCount;

                    //移到下一个
                    smoothScrollToPosition(getVisiblePosition() + 1);

                    if(position == itemCount) {
                        iOnPageChangeListener.onPageChangeListener(0);
                        return;
                    }
                    iOnPageChangeListener.onPageChangeListener((position + 1));
                }
            }
        });
    }

    /**
     * 设置可以自动滚动
     */
    public void setIsCanScroll(boolean setIsCanScroll) {
        if(setIsCanScroll) {
            this.isCanScroll = true;
            beginAutoScroll();
        } else {
            this.isCanScroll = false;
        }
    }

    /**
     * 获取当前的Position
     * @return position
     */
    private int getVisiblePosition() {
        return ((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
    }

    /**
     * 设置实际的Item数量
     * @param adapter　adapter
     */
    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);

        itemCount = ((GRecyclerAdapter)adapter).getRealCount();
        //无动画直接跳转
        scrollToPosition(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % itemCount);
    }

    public void setiOnPageChangeListener(IOnPageChangeListener iOnPageChangeListener) {
        this.iOnPageChangeListener = iOnPageChangeListener;
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setIsCanScroll(true);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setIsCanScroll(false);
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        if(visibility == View.GONE){
            // 停止轮播
            setIsCanScroll(false);
        }else if(visibility == View.VISIBLE){
            // 开始轮播
            setIsCanScroll(true);
        }
        super.onWindowVisibilityChanged(visibility);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN :

                setIsCanScroll(false);
                break;

            case MotionEvent.ACTION_UP:

                setIsCanScroll(true);
                break;

            default:

                break;

        }
        return super.dispatchTouchEvent(ev);
    }
}
