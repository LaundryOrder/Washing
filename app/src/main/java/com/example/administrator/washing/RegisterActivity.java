package com.example.administrator.washing;

import java.util.List;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import java.util.HashMap;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/4/28.
 */
public class RegisterActivity extends AppCompatActivity
{
    Button register;//注册按钮
    EditText email;//电子邮件
    EditText password;//密码
    String email_txt;
    String password_txt;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regester);

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        init();
        register.setOnClickListener(new RegisterOnclick());
    }
    public void init()
    {
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        register = (Button)findViewById(R.id.button_register);
    }

    public class RegisterOnclick implements OnClickListener
    {
        public void onClick(View v)
        {
            String Token = "";
            email_txt = email.getText().toString().trim();//得到邮件
            password_txt = password.getText().toString().trim();//得到密码
            HashMap map = new HashMap();
            map.put("username","test");
            map.put("password","123456");
            Token = RegisterServlet.postRequest(RegisterServlet.RegisterServlet(map));
        }
    }

}

