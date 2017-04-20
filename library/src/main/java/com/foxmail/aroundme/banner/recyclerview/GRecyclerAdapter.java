package com.foxmail.aroundme.banner.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.foxmail.aroundme.banner.indicator.IOnItemClickListener;

import java.util.List;

/**
 * Created by gzl on 1/3/17.
 *
 */

public class GRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;

    private List<String> urls;

    private RecyclerItemView recyclerItemView;

    private int currentPosition = 0;

    private IOnItemClickListener iOnItemClickListener;

    public GRecyclerAdapter(Context context, List<String> urls) {
        this.context = context;
        this.urls = urls;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = null;

        if(recyclerItemView != null) {

            inflater= LayoutInflater.from(parent.getContext());
        }

        return recyclerItemView.onCreateView(inflater, parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(urls == null || urls.get(position % urls.size()) == null) {
            return;
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(iOnItemClickListener != null) {
                    iOnItemClickListener.OnClickListener(position);
                }
            }
        });

        currentPosition = position;
        currentPosition %= urls.size();

        recyclerItemView.onBindView(holder, position, urls.get(currentPosition));
    }

    @Override
    public int getItemCount() {
        return urls == null ? 0 : urls.size() < 2 ? urls.size() : Integer.MAX_VALUE;

    }

    /**
     * @return 实际数据大小
     */
    public int getRealCount() {
        return urls.size();
    }

    public void setRecyclerItemView (RecyclerItemView recyclerItemView) {
        this.recyclerItemView = recyclerItemView;
    }

    public void setIOnItemClickListener(IOnItemClickListener iOnItemClickListener) {
        this.iOnItemClickListener = iOnItemClickListener;
    }

}
