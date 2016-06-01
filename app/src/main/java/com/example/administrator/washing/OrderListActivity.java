package com.example.administrator.washing;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.text.SimpleDateFormat;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/28.
 */
public class OrderListActivity extends AppCompatActivity
{
    String Token = null;

    String[] StartTime = new String[5];
    String[] AfterTime = new String[5];
    String[] Address = new String[5];
    ArrayList <String> StartTimeList = new ArrayList();
    ArrayList <String> AfterTimeList = new ArrayList();
    ArrayList <String> AddressList = new ArrayList();


    FloatingActionButton newOrderButton;

    SwipeRefreshLayout swipeRefreshLayout;
    String order_door_address = null;
    String order_start = null;
    String order_id = null;
    String status = null;

    JSONObject OrderMessage = null;


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_list);

        Intent intent = getIntent();
        Token = intent.getStringExtra("Token");//取得login得到的Token

        long currentTime = System.currentTimeMillis();
        OrderMessage = OrderListServlet.postRequest(Token);
        try
        {
            if(OrderMessage.getJSONArray("orders").length() == 0)
            {

            }
            else
            {
                    JSONArray Orders = OrderMessage.getJSONArray("orders");
                    for(int i = 0;i < Orders.length()&i < 8;i++) {
                        String tmp = null;
                        tmp = Orders.getJSONObject(i).getString("status");
                        if (tmp.equals("1")) {//已经取消的订单
                            String T1 = Orders.getJSONObject(i).getJSONObject("door").getString("start");
                            SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm");
                            StartTimeList.add(formatter.format(new Date(Long.parseLong(T1))));
                            AfterTimeList.add(formatDuringAgo(currentTime - Long.parseLong(T1)));
                            AddressList.add(tmp);
                        }
                        if (tmp.equals("2")) {//已经完成的订单
                            String T2 = Orders.getJSONObject(i).getJSONObject("door").getString("start");
                            SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm");
                            StartTimeList.add(formatter.format(new Date(Long.parseLong(T2))));
                            AfterTimeList.add(formatDuringAgo(currentTime - Long.parseLong(T2)));
                            AddressList.add(tmp);
                        }
                        if (tmp.equals("3")) {//正在进行的订单
                            order_start = Orders.getJSONObject(i).getJSONObject("door").getString("start");
                            order_id = Orders.getJSONObject(i).getString("order_id");
                            order_door_address = Orders.getJSONObject(i).getJSONObject("door").getString("address");
                            long startTime = Long.parseLong(order_start);
                            Date start_time = new Date(startTime);
                            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
                            String st = formatter.format(start_time);
                            String After_time = null;

                            if (startTime - currentTime > 0) {
                                After_time = formatDuring(startTime - currentTime);
                            }
                            StartTimeList.add(st);
                            AfterTimeList.add(After_time);
                            AddressList.add(order_door_address);
                        }
                    }
                     //}

                    mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
                    // use this setting to improve performance if you know that changes
                    // in content do not change the layout size of the RecyclerView
                    mRecyclerView.setHasFixedSize(true);
                    // use a linear layout manager
                    mLayoutManager = new LinearLayoutManager(this);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    // specify an adapter (see also next example)
                    mAdapter = new MyAdapter(StartTimeList, AfterTimeList, AddressList, Token, order_id);
                    mRecyclerView.setAdapter(mAdapter);
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        newOrderButton = (FloatingActionButton)findViewById(R.id.newOrderButton);
        newOrderButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent();
                intent.setClass(OrderListActivity.this,NewOrderActivity.class);
                intent.putExtra("Token",Token);
                OrderListActivity.this.startActivity(intent);
            }
        });

        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                swipeRefreshLayout.setRefreshing(false);
                refresh();
            }
        });
    }

    public static String formatDuring(long mss)
    {
        long days = mss/(1000*60*60*24);
        long hours = (mss%(1000*60*60*24))/(1000*60*60);
        long minutes = (mss%(1000*60*60))/(1000*60);
        if(days > 0)
        {
            return "After " + days + " days" ;
        }
        if(days <= 0 & hours >0)
        {
            return "After " + hours + " hours";
        }
        if(days <= 0 & hours <= 0 & minutes > 0)
        {
            return "After " + minutes + " minutes";
        }
        return "After 0 minutes";
    }

    public static String formatDuringAgo(long mss)
    {
        long days = mss/(1000*60*60*24);
        long hours = (mss%(1000*60*60*24))/(1000*60*60);
        long minutes = (mss%(1000*60*60))/(1000*60);
        if(days > 0)
        {
            return  days + " days Ago" ;
        }
        if(days <= 0 & hours >0)
        {
            return  hours + " hours Ago";
        }
        if(days <= 0 & hours <= 0 & minutes > 0)
        {
            return  minutes + " minutes Ago";
        }
        return "0 minutes Ago";
    }
    public void refresh()
    {
        finish();
        Intent intent = new Intent(OrderListActivity.this,OrderListActivity.class);
        intent.putExtra("Token", Token);
        startActivity(intent);
    }
}
