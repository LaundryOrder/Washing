package com.example.administrator.washing;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Administrator on 2016/4/28.
 */
public class NewOrderActivity extends AppCompatActivity
{
    JSONObject NewOrder = new JSONObject();
    JSONObject ResMessage = new JSONObject();
    //ArrayList<String> door = new ArrayList<String>();
    JSONObject door = new JSONObject();
    String Token = null;
    CheckBox DoorService;
    TextView PhoneNumber;
    TextView Address;
    TextView Earliest;
    TextView AppointmentTime;
    ImageButton OrderButton;
    String Door_Service;
    String Phone_Number;
    String address;
    Date time;
    String Time;
    TextInputLayout Phone_txt;
    TextInputLayout Address_txt;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_order);

        Intent intent = getIntent();
        Token = intent.getStringExtra("Token");//取得login得到的Token
        DoorService = (CheckBox)findViewById(R.id.door_service);
        PhoneNumber = (TextView)findViewById(R.id.phone_number);
        Earliest = (TextView)findViewById(R.id.earliest_time);
        AppointmentTime = (TextView)findViewById(R.id.appointment_time);
        Address = (TextView)findViewById(R.id.address);
        OrderButton = (ImageButton)findViewById(R.id.order_button);
        Phone_txt = (TextInputLayout)findViewById(R.id.Phone_txt);
        Address_txt = (TextInputLayout)findViewById(R.id.Address_txt);
        long currentTime = System.currentTimeMillis();
        ResMessage = NewOrderCreateServlet.postRequest(Token);
        try
        {
            time = new Date(Long.parseLong(ResMessage.getString("time")));
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Time = formatter.format(time);
        String Time1 = formatter.format(new Date(currentTime));
        Earliest.setText(Time);
        AppointmentTime.setText(Time);
        init();
    }
    public void init()
    {
        OrderButton.setOnClickListener(new Order());
        DoorService.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                {
                    PhoneNumber.setVisibility(View.VISIBLE);
                    Address.setVisibility(View.VISIBLE);
                    Phone_txt.setVisibility(View.VISIBLE);
                    Address_txt.setVisibility(View.VISIBLE);
                }
            }
        });

        DoorService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(!((CheckBox) v).isChecked())
                {
                    PhoneNumber.setVisibility(View.INVISIBLE);
                    Address.setVisibility(View.INVISIBLE);
                    Phone_txt.setVisibility(View.INVISIBLE);
                    Address_txt.setVisibility(View.INVISIBLE);
                }
            }
        });
    }


    private class Order implements View.OnClickListener
    {
        public void onClick(View v)
        {
            if(DoorService.isChecked())
            {
                //Phone_Number = PhoneNumber.getText().toString();
                //address = Address.getText().toString();
                Phone_Number = "18888776991";
                address = "NanJing Road";
                try
                {
                    door.put("phone",Phone_Number);
                    door.put("address",address);
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

                try
                {
                    NewOrder.put("door",door);
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
            int ResCode = NewOrderServlet.postRequest(NewOrder,Token);
            if(ResCode == 200)
            {
                Intent intent = new Intent();
                intent.setClass(NewOrderActivity.this,OrderListActivity.class);
                intent.putExtra("Token",Token);
                NewOrderActivity.this.startActivity(intent);
            }
        }
    }
}
