package com.example.administrator.washing;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/3.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>
{
    private String[] StartTime = new String[5];
    private String[] AfterTime = new String[5];
    private String[] Address = new String[5];
    private ArrayList <String> StartTimeList = new ArrayList();
    private ArrayList <String> AfterTimeList = new ArrayList();
    private ArrayList <String> AddressList = new ArrayList();
    String Token = null;
    String Order_id = null;

    JSONObject CancelMessage = null;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        // each data item is just a string in this case
        public TextView mTextView;
        public TextView after_time;
        public TextView address;
        public ImageView mImageView;
        public CardView mCardView;
        Button DoorService;
        Button Cancel;

        public ViewHolder(View v)
        {
            super(v);
            mTextView =(TextView)itemView.findViewById(R.id.appointment_time);
            after_time = (TextView)itemView.findViewById(R.id.after_time);
            address = (TextView)itemView.findViewById(R.id.addressText);
            DoorService = (Button)itemView.findViewById(R.id.door_service);
            Cancel = (Button)itemView.findViewById(R.id.cancel);
            mImageView = (ImageView)itemView.findViewById(R.id.order_icon);
            mCardView = (CardView)itemView.findViewById(R.id.card_view);
            //newOrderButton = (FloatingActionButton)itemView.findViewById(R.id.newOrderButton);
        }
    }
    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList<String>startTimeList,ArrayList<String>afterTimeList,ArrayList<String>addressList,String token,String order_id)
    {
        StartTimeList = (ArrayList<String>) startTimeList.clone();
        AfterTimeList = (ArrayList<String>) afterTimeList.clone();
        AddressList = (ArrayList<String>) addressList.clone();
        Token = token;
        Order_id = order_id;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position)
    {
        holder.mTextView.setText(StartTimeList.get(position));
        holder.after_time.setText(AfterTimeList.get(position));
        if(AddressList.get(position).equals("1"))
        {
            holder.address.setText("Canceled");
            holder.mImageView.setBackgroundResource(R.drawable.ic_close_black_48px);
            holder.Cancel.setVisibility(View.GONE);
            holder.DoorService.setVisibility(View.GONE);
        }
        else if(AddressList.get(position).equals("2"))
        {
            holder.address.setText("Completed");
            holder.mImageView.setBackgroundResource(R.drawable.ic_check_black_48px);
            holder.Cancel.setVisibility(View.GONE);
            holder.DoorService.setVisibility(View.GONE);
        }
        else
        {
            holder.address.setText(AddressList.get(position));
            holder.Cancel.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    String cancelStr = null;
                    CancelMessage = OrderListCancelServlet.CancelOrderList(Token, Order_id);
                    try
                    {
                        cancelStr = CancelMessage.getString("success");
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                    if (cancelStr.equals("1"))
                    {
                        holder.address.setText ("Canceled");
                        AddressList.set(position,"Canceled");
                        holder.after_time.setText("0 minutes Ago");
                        AfterTimeList.set(position,"0 minutes Ago");
                        holder.mImageView.setBackgroundResource(R.drawable.ic_close_black_48px);
                        holder.Cancel.setVisibility(View.GONE);
                        holder.DoorService.setVisibility(View.GONE);
                        MyAdapter.this.notifyDataSetChanged();
                    }
                }
            });
        }
    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount()
    {
        return StartTimeList.size();
    }

}
