package com.foxmail.aroundme.banner.viewpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.foxmail.aroundme.banner.indicator.IOnItemClickListener;

import java.util.List;

/**
 * Created by gzl on 12/30/16.
 *
 */

public class GBannerPagerAdapter extends PagerAdapter {

    private Context context;

    private List<String> list;

    private IOnItemClickListener iOnItemClickListener;

    private int currentPosition = 0;

    //展示View
    private ViewPagerItem viewPagerItem;

    public GBannerPagerAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        //减１再求余防止下标混乱
        position -= 1;

        position %= list.size();

        if (position < 0) {
            position = list.size() + position;
        }
        currentPosition = position;

        View view = viewPagerItem.instantiateItem(container, list.get(position), position);

        if(view == null) {
            throw new RuntimeException("ViewPager item view is null");
        }

        //返回正确下标
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(iOnItemClickListener != null) {
                    iOnItemClickListener.OnClickListener(currentPosition);
                }
            }
        });

        ViewParent viewParent = view.getParent();
        if (viewParent != null) {
            ViewGroup parent = (ViewGroup) viewParent;
            parent.removeView(view);
        }
        container.addView(view);
        return view;
    }


    @Override
    public int getCount() {
        return list == null ? 0 : list.size() < 2 ? list.size() : Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //container.removeView((View) object);
    }

    public void setItemOnClickListener(IOnItemClickListener listener) {
        this.iOnItemClickListener = listener;
    }

    public void setViewPagerItem (ViewPagerItem viewPagerItem) {
        this.viewPagerItem = viewPagerItem;
    }

    /**
     * @return 实际数据大小
     */
    public int getRealCount() {
        return list.size();
    }
}
