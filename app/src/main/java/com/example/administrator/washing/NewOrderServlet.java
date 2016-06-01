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
/**
 * Created by Administrator on 2016/5/27.
 */
public class NewOrderServlet
{
    public static int postRequest(JSONObject json,String Token)
    {
        JSONObject ResMessage = null;
        int ResCode = 0;
        try
        {
            URL url = new URL("http://bj.cn.atarss.com:8233/order");
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            byte [] js = json.toString().getBytes();
            con.setRequestMethod("POST");
            con.setReadTimeout(5000);
            String token = "Token "+Token;
            con.setRequestProperty("Authorization",token);
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
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return ResCode;
    }
}
