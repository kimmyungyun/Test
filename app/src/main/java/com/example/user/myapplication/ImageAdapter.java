package com.example.user.myapplication;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ImageItem> items;

    public ImageAdapter(ArrayList<ImageItem> arrayList){
        this.items = arrayList;
    }
    @Override
    //여기서 무슨 타입으로 보여줄 것인지 리턴.
    //사용 할 거면 수정.
    public int getItemViewType(int position){
        return 0;
    }
    @Override
    public int getItemCount(){
        return items.size();
    }
    @Override
    //viewType 에 따라 받아오는 Layout 이 다름. 여기서 layout 은 개별적으로 만든 것.
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.imageitem,parent,false);
        return new ImageItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ImageItemViewHolder) {
            ((ImageItemViewHolder)holder).imageView.setImageDrawable(items.get(position).getIcon());
        }
    }

    static class ImageItemViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        //generate constructor here
        ImageItemViewHolder(View itemview){
            super(itemview);
            imageView = (ImageView) itemview.findViewById(R.id.ImageView);
        }
    }
}
