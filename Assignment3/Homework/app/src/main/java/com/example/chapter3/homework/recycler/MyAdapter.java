package com.example.chapter3.homework.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chapter3.homework.R;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    private List<Messeges> mDataset = new ArrayList<>();
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView tvimage;
        TextView tvname;
        TextView tvtime;
        TextView tvmessage;
        private View contentView;
        public MyViewHolder(View v) {
            super(v);
            contentView = v;
            tvimage = v.findViewById(R.id.tvimage);
            tvtime = v.findViewById(R.id.tvtime);
            tvname = v.findViewById(R.id.tvname);
            tvmessage = v.findViewById(R.id.tvmessage);
        }
        public void onBind(int position, Messeges data) {
            tvtime.setText(data.time);
            tvname.setText(data.name);
            tvmessage.setText(data.message);
            tvimage.setImageResource(data.image);
        }
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.onBind(position, mDataset.get(position));
    }


    public MyAdapter(List<Messeges> myDataset) {
        mDataset.addAll(myDataset);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false));
    }

}

