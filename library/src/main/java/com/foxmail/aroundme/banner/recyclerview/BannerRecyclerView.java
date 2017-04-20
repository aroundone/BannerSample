package com.foxmail.aroundme.banner.recyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.foxmail.aroundme.banner.R;
import com.foxmail.aroundme.banner.indicator.IOnItemClickListener;
import com.foxmail.aroundme.banner.indicator.IndicatorLocation;
import com.foxmail.aroundme.banner.indicator.IndicatorShape;
import com.foxmail.aroundme.banner.indicator.IOnPageChangeListener;

import java.util.List;

/**
 * Created by gzl on 1/4/17.
 *
 */

public class BannerRecyclerView extends LinearLayout{

    private GRecyclerView recyclerView;

    private RecyclerItemView recyclerItemView;

    private GRecyclerAdapter adapter;

    //指示器容器
    private LinearLayout indicatorContainer;

    //未选择时指示器绘制
    private Drawable unSelectedDrawable;

    //选择时指示器绘制
    private Drawable selectedDrawable;

    //是否自动播放
    private boolean isAutoPlay = true;

    //item数目
    private int itemCount;

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

    public BannerRecyclerView(Context context) {
        this(context, null);
    }

    public BannerRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

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


    public void setUrls(List<String> urls) {

        initRecyclerView(urls);

        initIndicator();

    }

    private void initRecyclerView(List<String> urls) {

        RelativeLayout.LayoutParams params = new RelativeLayout
                .LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        recyclerView = new GRecyclerView(getContext());
        ScrollSpeedLinearLayoutManger layoutManager = new ScrollSpeedLinearLayoutManger(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        new FixLinearSnapHelper().attachToRecyclerView(recyclerView);
        addView(recyclerView, params);
        adapter = new GRecyclerAdapter(getContext(), urls);
        adapter.setRecyclerItemView(recyclerItemView);
        recyclerView.setAdapter(adapter);

    }

    private void initIndicator() {
        //初始化indicatorContainer
        indicatorContainer = new LinearLayout(getContext());
        indicatorContainer.setGravity(Gravity.CENTER_VERTICAL);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

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
        for (int i = 0; i < itemCount; i++) {
            ImageView indicator = new ImageView(getContext());
            indicator.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            indicator.setPadding(indicatorSpace, indicatorSpace, indicatorSpace, indicatorSpace);
            indicator.setImageDrawable(unSelectedDrawable);
            indicatorContainer.addView(indicator);
        }
        //指到第一个位置
        switchIndicator(0);
    }

    /**
     * 每个Item的实体类
     * @param recyclerItemView
     */
    public void setRecyclerItemView(RecyclerItemView recyclerItemView) {
        this.recyclerItemView = recyclerItemView;
    }

    /**
     * 设置页数翻滚事件
     * @param iOnPageChangeListener　翻滚事件
     */
    public void setIOnPageChangeListener(IOnPageChangeListener iOnPageChangeListener) {
        if(recyclerView != null) {
            recyclerView.setiOnPageChangeListener(iOnPageChangeListener);
        }
    }

    /**
     *
     */
    public void setOnItemClickListener(IOnItemClickListener itemClickListener) {
        if(adapter != null) {
            adapter.setIOnItemClickListener(itemClickListener);
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
}
