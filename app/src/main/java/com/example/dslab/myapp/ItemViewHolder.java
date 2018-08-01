package com.example.dslab.myapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemViewHolder extends RecyclerView.ViewHolder {
    ImageView image;
    TextView name;
    TextView date;
    public ItemViewHolder(View itemView) {
        super(itemView);
        image=itemView.findViewById(R.id.image1);
        name=itemView.findViewById(R.id.text1);
        date=itemView.findViewById(R.id.text2);
    }
}
