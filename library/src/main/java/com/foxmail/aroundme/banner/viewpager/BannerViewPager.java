package com.foxmail.aroundme.banner.viewpager;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.foxmail.aroundme.banner.R;
import com.foxmail.aroundme.banner.indicator.Config;
import com.foxmail.aroundme.banner.indicator.IOnItemClickListener;
import com.foxmail.aroundme.banner.indicator.IOnPageChangeListener;
import com.foxmail.aroundme.banner.indicator.IndicatorLocation;
import com.foxmail.aroundme.banner.indicator.IndicatorShape;

import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 * Created by gzl on 2017/1/4.
 */
public class BannerViewPager extends RelativeLayout implements IOnPageChangeListener {

    //ViewPager
    private GBannerViewPager pager;
    //Adapter
    private GBannerPagerAdapter pagerAdapter;
    //ItemView
    private ViewPagerItem viewPagerItem;
    //指示器容器
    private LinearLayout indicatorContainer;

    //保存Listener
    private IOnPageChangeListener iOnPageChangeListener;

    //未选择时指示器绘制
    private Drawable unSelectedDrawable;

    //选择时指示器绘制
    private Drawable selectedDrawable;

    //是否自动播放
    private boolean isAutoPlay = true;

    //默认指示器选择时颜色
    private int selectedIndicatorColor = 0xffff0000;
    //指示器未选择时颜色
    private int unSelectedIndicatorColor = 0x88888888;

    //指示器绘制形状
    private int indicatorShape = IndicatorShape.oval;

    //指示器位置
    private int indicatorPosition = IndicatorLocation.centerBottom;

    //指示器选择时高度
    private int selectedIndicatorHeight = 6;
    //指示器选择时宽度
    private int selectedIndicatorWidth = 6;
    //指示器未选择时高度
    private int unSelectedIndicatorHeight = 6;
    //指示器未选择时宽度
    private int unSelectedIndicatorWidth = 6;

    //自动播放间隔(多久翻页一次)
    private int intervalDuration = 4000;
    //滑动动画播放时间
    private int scrollDuration = 900;

    private int indicatorSpace = 3;

    private int indicatorMargin = 10;
    //当前item
    private int currentPosition;


    public BannerViewPager(Context context) {
        this(context, null);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    /**
     * 初始化attr
     *
     * @param attrs    attrs
     * @param defStyle defStyle
     */

    private void init(AttributeSet attrs, int defStyle) {
        //初始化属性
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.BannerLayoutStyle, defStyle, 0);
        selectedIndicatorColor = array.getColor(R.styleable.BannerLayoutStyle_selectedIndicatorColor, selectedIndicatorColor);
        unSelectedIndicatorColor = array.getColor(R.styleable.BannerLayoutStyle_unSelectedIndicatorColor, unSelectedIndicatorColor);

        indicatorShape = array.getInt(R.styleable.BannerLayoutStyle_indicatorShape, indicatorShape);

        selectedIndicatorHeight = (int) array.getDimension(R.styleable.BannerLayoutStyle_selectedIndicatorHeight, selectedIndicatorHeight);
        selectedIndicatorWidth = (int) array.getDimension(R.styleable.BannerLayoutStyle_selectedIndicatorWidth, selectedIndicatorWidth);
        unSelectedIndicatorHeight = (int) array.getDimension(R.styleable.BannerLayoutStyle_unSelectedIndicatorHeight, unSelectedIndicatorHeight);
        unSelectedIndicatorWidth = (int) array.getDimension(R.styleable.BannerLayoutStyle_unSelectedIndicatorWidth, unSelectedIndicatorWidth);

        indicatorPosition = array.getInt(R.styleable.BannerLayoutStyle_indicatorPosition, indicatorPosition);

        indicatorSpace = (int) array.getDimension(R.styleable.BannerLayoutStyle_indicatorSpace, indicatorSpace);
        indicatorMargin = (int) array.getDimension(R.styleable.BannerLayoutStyle_indicatorMargin, indicatorMargin);
        intervalDuration = array.getInt(R.styleable.BannerLayoutStyle_intervalDuration, intervalDuration);
        scrollDuration = array.getInt(R.styleable.BannerLayoutStyle_scrollDuration, scrollDuration);
        isAutoPlay = array.getBoolean(R.styleable.BannerLayoutStyle_isAutoPlay, isAutoPlay);
        array.recycle();

        //绘制未选中状态图形
        LayerDrawable unSelectedLayerDrawable;
        LayerDrawable selectedLayerDrawable;
        GradientDrawable unSelectedGradientDrawable;
        unSelectedGradientDrawable = new GradientDrawable();

        //绘制选中状态图形
        GradientDrawable selectedGradientDrawable;
        selectedGradientDrawable = new GradientDrawable();
        switch (indicatorShape) {
            case IndicatorShape.rect:
                unSelectedGradientDrawable.setShape(GradientDrawable.RECTANGLE);
                selectedGradientDrawable.setShape(GradientDrawable.RECTANGLE);
                break;
            case IndicatorShape.oval:
                unSelectedGradientDrawable.setShape(GradientDrawable.OVAL);
                selectedGradientDrawable.setShape(GradientDrawable.OVAL);
                break;
        }
        //绘制颜色
        unSelectedGradientDrawable.setColor(unSelectedIndicatorColor);
        //绘制大小
        unSelectedGradientDrawable.setSize(unSelectedIndicatorWidth, unSelectedIndicatorHeight);
        unSelectedLayerDrawable = new LayerDrawable(new Drawable[]{unSelectedGradientDrawable});
        unSelectedDrawable = unSelectedLayerDrawable;

        selectedGradientDrawable.setColor(selectedIndicatorColor);
        selectedGradientDrawable.setSize(selectedIndicatorWidth, selectedIndicatorHeight);
        selectedLayerDrawable = new LayerDrawable(new Drawable[]{selectedGradientDrawable});
        selectedDrawable = selectedLayerDrawable;
    }


    /**
     * 获取ViewPager
     *
     * @return viewpager
     */
    public ViewPager getPager() {
        if (pager != null) {
            return pager;
        }
        return null;
    }

    /**
     * 添加任意View视图
     * <p>
     * 先初始化ViewPager再初始化Indicator，因为Indicator个数是由ViewPager个数决定的
     */
    public void setUrls(List<String> urls) {


        Config.itemCount = urls.size();

        initViewPager(urls);

        initIndicator();
    }

    private void initViewPager(List<String> urls) {
        //初始化pager
        pager = new GBannerViewPager(getContext());
        //添加viewpager到SliderLayout
        addView(pager);

        pagerAdapter = new GBannerPagerAdapter(getContext(), urls);
        if (viewPagerItem != null) {
            pagerAdapter.setViewPagerItem(viewPagerItem);
        } else {
            throw new RuntimeException("ViewPagerItem is null");
        }
        pager.setIntervalDuration(intervalDuration);
        pager.setScrollDuration(scrollDuration);

        pager.setIOnPageChangeListener(this);
        if(iOnPageChangeListener != null) {
            //防止因为实现顺序不同导致Listener第一次无法监听
            pager.setIOnPageChangeListener(iOnPageChangeListener);
        }

        pager.setAdapter(pagerAdapter);

        pager.setIsCanScroll(isAutoPlay);

    }

    private void initIndicator() {
        //初始化indicatorContainer
        indicatorContainer = new LinearLayout(getContext());
        indicatorContainer.setGravity(Gravity.CENTER_VERTICAL);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        //设置展示区域
        switch (indicatorPosition) {
            case IndicatorLocation.centerBottom:
                params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                break;
            case IndicatorLocation.centerTop:
                params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                break;
            case IndicatorLocation.leftBottom:
                params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                break;
            case IndicatorLocation.leftTop:
                params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                break;
            case IndicatorLocation.rightBottom:
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                break;
            case IndicatorLocation.rightTop:
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                break;
        }
        //设置margin
        params.setMargins(indicatorMargin, indicatorMargin, indicatorMargin, indicatorMargin);
        //添加指示器容器布局到SliderLayout
        addView(indicatorContainer, params);

        //初始化指示器，并添加到指示器容器布局
        for (int i = 0; i < Config.itemCount; i++) {
            ImageView indicator = new ImageView(getContext());
            indicator.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            indicator.setPadding(indicatorSpace, indicatorSpace, indicatorSpace, indicatorSpace);
            indicator.setImageDrawable(unSelectedDrawable);
            indicatorContainer.addView(indicator);
        }
    }

    /**
     * 切换指示器状态
     *
     * @param currentPosition 当前位置
     */
    private void switchIndicator(int currentPosition) {
        for (int i = 0; i < indicatorContainer.getChildCount(); i++) {
            ((ImageView) indicatorContainer.getChildAt(i)).setImageDrawable(i == currentPosition ? selectedDrawable : unSelectedDrawable);
        }
    }

    @Override
    public void onPageChangeListener(int position) {
        switchIndicator(position);
    }

    public void setOnPageChangeListener(@NonNull IOnPageChangeListener iOnPageChangeListener) {
        this.iOnPageChangeListener = iOnPageChangeListener;
        if (pager != null) {
            pager.setIOnPageChangeListener(iOnPageChangeListener);
        }
    }

    public void setOnItemClickListener(IOnItemClickListener itemClickListener) {
        if (pagerAdapter != null) {
            pagerAdapter.setItemOnClickListener(itemClickListener);
        }
    }

    public void setViewPagerItem(ViewPagerItem viewPagerItem) {
        this.viewPagerItem = viewPagerItem;
    }

    /**
     * 恢复数据
     *
     * @param state 　state
     */
    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        currentPosition = savedState.currentPosition;
        requestLayout();
    }

    /**
     * 保存数据
     *
     * @return data
     */
    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.currentPosition = currentPosition;
        return savedState;
    }


    private static class SavedState extends BaseSavedState {
        int currentPosition;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            currentPosition = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(currentPosition);
        }

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

}


