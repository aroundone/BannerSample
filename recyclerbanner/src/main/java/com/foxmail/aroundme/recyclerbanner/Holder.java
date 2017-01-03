package com.foxmail.aroundme.recyclerbanner;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by gzl on 1/3/17.
 */

public class Holder extends RecyclerView.ViewHolder{

    public ImageView imageView;

    public Holder(View itemView) {
        super(itemView);
        imageView = (ImageView)itemView.findViewById(R.id.image_view);
    }
}
