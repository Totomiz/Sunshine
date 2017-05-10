package com.zt.tz.sunshine.api;

import android.net.Uri;
import android.util.Log;

/**
 * Created by zhangtong on 2017-05-09 15:09
 * QQ:xxxxxxxx
 */

public class WeatherApi {
    private static final String TAG = "zhangtong";
    private static final String apiKey = "2ffe8a08857e3c924029fa0f1cb73763";
    public String baseUrl = "http://api.openweathermap.org/data/2.5/forecast/daily?q=94043&mode=json&units=metric&cnt=7";
    public String apiKey2 = "&APPID=" + apiKey;
    private static final String FORECAST_BASE_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?";
    private static final String QUERY_PARAM = "q";
    private static final String FORMAT_PARAM = "mode";
    private static final String UNITS_PARAM = "units";
    private static final String DAYS_PARAM = "cnt";
    private static final String APPID_PARAM = "APPID";
    private static String format = "json";
    private static String units = "metric";
    //一次获取的天数天气
    public static int numDays = 7;


    public static String getStringUrl(int query_parm) {
        Uri.Builder builder = Uri.parse(FORECAST_BASE_URL).buildUpon();
        builder.appendQueryParameter(QUERY_PARAM, Integer.toString(query_parm));
        builder.appendQueryParameter(FORMAT_PARAM, format);
        builder.appendQueryParameter(UNITS_PARAM, units);
        builder.appendQueryParameter(DAYS_PARAM, Integer.toString(numDays));
        builder.appendQueryParameter(APPID_PARAM, apiKey);
        String urlStr = builder.build().toString();
        Log.d(TAG, "build Url: " + urlStr);
        return urlStr;
    }

    public static String getStringUrl(String query_parm) {
        Uri.Builder builder = Uri.parse(FORECAST_BASE_URL).buildUpon();
        builder.appendQueryParameter(QUERY_PARAM, query_parm);
        builder.appendQueryParameter(FORMAT_PARAM, format);
        builder.appendQueryParameter(UNITS_PARAM, units);
        builder.appendQueryParameter(DAYS_PARAM, Integer.toString(numDays));
        builder.appendQueryParameter(APPID_PARAM, apiKey);
        String urlStr = builder.build().toString();
        Log.d(TAG, "build Url: " + urlStr);
        return urlStr;
    }
}
