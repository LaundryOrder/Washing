package com.example.administrator.washing;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/5/8.
 */
public class CancelOrder  extends RecyclerView.Adapter<CancelOrder.ViewHolder>
{
    public TextView mTextView;
    private String[] StartTime;

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView mTextView;
        private String[] StartTime;
        public ViewHolder(View v)
        {
            super(v);
            mTextView =(TextView)itemView.findViewById(R.id.Cancel_time);
        }
    }

    public CancelOrder(String[] startTime)
    {
        StartTime = startTime;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cancel_order_list, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        holder.mTextView.setText(StartTime[position]);
    }

    @Override
    public int getItemCount()
    {
        return 0;
    }
}
