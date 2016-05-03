package com.example.administrator.washing;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
/**
 * Created by Administrator on 2016/5/1.
 */
public class LoginServlet
{
    public static JSONObject LoginServlet(HashMap map)
    {
        JSONObject json = new JSONObject(map);
        json.toString();
        return json;
    }
    public static String postRequest(JSONObject json)
    {
        JSONObject ResMessage = null;
        String Token = null;
        int ResCode = 0;
        try
        {
            URL url = new URL("http://bj.cn.atarss.com:8233/login");
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            byte [] js = json.toString().getBytes();
            con.setRequestMethod("POST");
            con.setReadTimeout(5000);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Content-Length",String.valueOf(js.length));
            OutputStream outStream = con.getOutputStream();
            outStream.write(js);
            outStream.close();
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

                try
                {
                    ResMessage = new JSONObject(RsString);
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
                try
                {
                    Token = ResMessage.getString("token");
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
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
        return Token;
    }
}
