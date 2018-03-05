package com.foxmail.aroundme.banner.viewpager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.foxmail.aroundme.banner.indicator.Config;
import com.foxmail.aroundme.banner.indicator.IOnItemClickListener;
import com.foxmail.aroundme.banner.indicator.IOnPageChangeListener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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

    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            Message msg=new Message();
            msg.what=1;
            if (handler != null) {
                handler.sendMessage(msg);
            }
        }
    };

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch(msg.what){
                case 1:
                    if(isCanScroll) {
                        setCurrentItem(currentIndex + 1);
                    }
                    break;
            }
        }
    };


    private Timer timer;

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

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setIsCanScroll(true);
        beginAutoScroll();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setIsCanScroll(false);
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == VISIBLE) {
            setIsCanScroll(true);
        } else if (visibility == GONE) {
            setIsCanScroll(false);
        }
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
                    postListener(position % Config.itemCount);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        initViewPagerScroll();
    }

    /**
     * @param position
     */
    private void postListener(int position) {
        for(IOnPageChangeListener iOnPageChangeListener : listeners) {
            iOnPageChangeListener.onPageChangeListener(position);
        }
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

    private void beginAutoScroll() {
        if (timer == null) {
            timer = new Timer();
            timer.schedule(timerTask, intervalDuration, intervalDuration);
        }
    }


    /**
     * 设置可以自动滚动
     */
    public void setIsCanScroll(boolean setIsCanScroll) {
            this.isCanScroll = setIsCanScroll;
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
