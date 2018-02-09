package com.foxmail.aroundme.banner.recyclerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.foxmail.aroundme.banner.indicator.IOnPageChangeListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by gzl on 1/3/17.
 */

public class GRecyclerView extends RecyclerView{

    private int itemCount;

    private boolean isCanScroll;

    private int intervalDuration = 3000;

    private List<IOnPageChangeListener> onPageChangeListeners;

    private Timer timer = new Timer();

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

                        int position = getVisiblePosition() % itemCount;

                        //移到下一个
                        smoothScrollToPosition(getVisiblePosition() + 1);

                        if(position == itemCount) {
                            postListener(0);
                            return;
                        }
                        postListener((position + 1));
                    }
                    break;
            }
        }
    };

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
        onPageChangeListeners = new ArrayList<>();
    }

    private void beginAutoScroll() {
        timer.schedule(timerTask, intervalDuration, intervalDuration);
    }

    private void postListener(int position) {
        if (onPageChangeListeners == null) {
            return;
        }

        for (IOnPageChangeListener listener : onPageChangeListeners) {
            listener.onPageChangeListener(position);
        }
    }

    /**
     * 设置可以自动滚动
     */
    public void setIsCanScroll(boolean setIsCanScroll) {
        if(setIsCanScroll) {
            this.isCanScroll = true;
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

    public void setOnPageChangeListener(IOnPageChangeListener iOnPageChangeListener) {
        if (onPageChangeListeners != null) {
            onPageChangeListeners.add(iOnPageChangeListener);
        }
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
