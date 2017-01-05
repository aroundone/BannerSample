package com.foxmail.aroundme.bannertest.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.foxmail.aroundme.bannertest.R;

/**
 * Created by gzl on 1/3/17.
 *
 */

public class Holder extends RecyclerView.ViewHolder {

    public ImageView imageView;
    public TextView textView;

    public Holder(View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.image_view_recycler);
        textView = (TextView) itemView.findViewById(R.id.text);
    }
}
