package com.robert.bethub.Adapter;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.robert.bethub.Model.membershipData;
import com.robert.bethub.R;
import com.robert.bethub.View.ContentActivity;
import com.robert.bethub.View.MembershipActivity;

import java.util.ArrayList;

public class MembershipAdapter extends RecyclerView.Adapter<MembershipAdapter.MyViewHolder> {

    private ArrayList<membershipData> dataSet;

    public static Integer[] drawableArray = {R.drawable.targetracing, R.drawable.greyhound2, R.drawable.earlybird,
            R.drawable.gatespeed};


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        ImageView imageViewIcon;
        Button readMore;
        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewName = itemView.findViewById(R.id.textViewName);
            this.imageViewIcon = itemView.findViewById(R.id.imageView);
            this.readMore = itemView.findViewById(R.id.readmoreButton);

        }
    }

    public MembershipAdapter(ArrayList<membershipData> data) {
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.membership_cards_layout, parent, false);

        view.setOnClickListener(MembershipActivity.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView textViewName = holder.textViewName;
        ImageView imageView = holder.imageViewIcon;
        Button readmoreButton = holder.readMore;
        int imageArrayNumber = MembershipActivity.numberDrawable;
        textViewName.setText(dataSet.get(listPosition).getTitle());
        imageView.setImageResource(drawableArray[imageArrayNumber]);
        readmoreButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int position = listPosition;
                //Log.d("tag1",""+position);
                String contentText = dataSet.get(position).getContent();
                Intent intent = new Intent(v.getContext(),ContentActivity.class);
                intent.putExtra("content",contentText);
                v.getContext().startActivity(intent);
                /// button click event
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}

