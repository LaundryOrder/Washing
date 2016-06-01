package com.example.administrator.washing;
import android.support.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
/**
 * Created by Administrator on 2016/5/7.
 */
public class OrderListCancelServlet
{
    public static JSONObject CancelOrderList(String Token,String order_id)
    {
        JSONObject ResMessage = null;
        int ResCode = 0;

        try
        {

            URL url = new URL("http://bj.cn.atarss.com:8233/order/"+order_id);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("DELETE");
            con.setReadTimeout(5000);
            String token = "Token "+Token;
            con.setRequestProperty("Authorization",token);
            con.connect();
            ResCode = con.getResponseCode();
            String RsLine = "";
            String RsString = "";
            java.io.InputStream is = con.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            if(ResCode == 200)
            {
                while ((RsLine = reader.readLine()) != null)
                {
                    if (RsLine.length() > 0)
                    {
                        RsString = RsString + RsLine.trim();
                    }
                }
                ResMessage = new JSONObject(RsString);
            }
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return ResMessage;
    }
}
