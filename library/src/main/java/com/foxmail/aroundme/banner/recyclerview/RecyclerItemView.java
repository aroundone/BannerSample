package com.foxmail.aroundme.banner.recyclerview;

import android.support.annotation.CheckResult;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by gzl on 1/4/17.
 */

public interface RecyclerItemView<VH extends RecyclerView.ViewHolder> {

    /**
     * 代替Adapter实现创建View
     * @param parent ViewGroup
     * @param inflater viewType
     * @return
     */
    @CheckResult
    RecyclerView.ViewHolder onCreateView(LayoutInflater inflater, ViewGroup parent);

    /**
     * 绑定View
     * @param holder ViewHolder
     * @param position viewType
     */
    void onBindView(VH holder, int position, String url);
}
