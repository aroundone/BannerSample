package com.foxmail.aroundme.banner.viewpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.foxmail.aroundme.banner.indicator.Config;
import com.foxmail.aroundme.banner.indicator.IOnItemClickListener;
import com.foxmail.aroundme.banner.indicator.IOnPageChangeListener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by gzl on 12/30/16.
 *
 */

public class GBannerViewPager extends ViewPager{

    //能否滚动，当点击时不能滚动
    private boolean isCanScroll = true;

    private DurationScroller scroller;

    private int currentIndex = 0;

    private List<IOnPageChangeListener> listeners = new ArrayList<>();

    private Context context;

    private int intervalDuration = 3000;

    private IOnItemClickListener iOnItemClickListener;


    //设置全局防止重复订阅
    private Subscription subscription;

    public GBannerViewPager(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public GBannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentIndex = position;
                if(Config.itemCount != 0) {
                    notification(position % Config.itemCount);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        initViewPagerScroll();
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        super.setAdapter(adapter);
        int firstPosition = Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % Config.itemCount;
        setCurrentItem(firstPosition);
    }

    public void setIOnPageChangeListener(IOnPageChangeListener iOnPageChangeListener) {
        this.listeners.add(iOnPageChangeListener);
        int firstPosition = Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % Config.itemCount;
        setCurrentItem(firstPosition);
    }

    private void startAutoScroll() {

        //取消上次订阅
        if(subscription != null) {
            subscription.unsubscribe();
        }

        // TODO: 1/9/17 会产生两个计时器
        subscription = Observable.interval(intervalDuration, TimeUnit.MILLISECONDS)
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
                    setCurrentItem(currentIndex + 1);
                }
            }
        });
    }

    /**
     * @param position
     */
    private void notification(int position) {
        for(IOnPageChangeListener iOnPageChangeListener : listeners) {
            iOnPageChangeListener.onPageChangeListener(position);
        }
    }

    /**
     * 设置可以自动滚动
     */
    public void setIsCanScroll(boolean setIsCanScroll) {
            this.isCanScroll = setIsCanScroll;
            startAutoScroll();
    }

    public void setScrollDuration(int scrollDuration) {
        if (scroller != null) {
            scroller.setScrollDuration(scrollDuration);
        }
    }
    public void setItemOnClickListener(IOnItemClickListener listener) {
        this.iOnItemClickListener = listener;
    }

    public void setIntervalDuration(int duration) {
        this.intervalDuration = duration;
    }

    /**
     * 设置滚动速度
     */
    private void initViewPagerScroll() {
        try {
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            scroller = new DurationScroller(this.getContext());
            mScroller.set(this, scroller);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
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
