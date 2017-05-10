package com.zt.tz.sunshine.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by zhangtong on 2017-05-06 21:04
 * QQ:xxxxxxxx
 */

public class HttpUtils {
    public Handler mHandler;

    public HttpUtils() {
    }

    public HttpUtils(Context context) {
        mHandler = new Handler(Looper.getMainLooper());
    }

    public void getString(IResult<String> iResult, String urlStr) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        StringBuffer strBuff = new StringBuffer();
        try {
            String jsonStr = null;
            URL url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = reader.readLine()) != null) {
                strBuff.append(line + "\n");
            }
            if (strBuff.length() == 0) {
                jsonStr = null;
            }
            jsonStr = strBuff.toString();
            if (iResult != null) {
                iResult.onSuccess(jsonStr);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            CloseUtils.closeIO(reader);
        }
    }

    public String getString(String urlStr) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        String jsonStr = null;
        try {
            StringBuffer strBuff = new StringBuffer();

            URL url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = reader.readLine()) != null) {
                strBuff.append(line + "\n");
            }
            if (strBuff.length() == 0) {
                jsonStr = null;
            }
            jsonStr = strBuff.toString();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            CloseUtils.closeIO(reader);
        }
        return jsonStr;
    }
}
