package com.zt.tz.sunshine.bean;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.zt.tz.sunshine.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by zhangtong on 2017-05-09 20:59
 * QQ:xxxxxxxx
 */

public class WeatherListBean {
    private static final String TAG = "zhangtong";
    private String list = "list";
    private String weather = "weather";
    private String temp = "temp";
    private String max = "max";
    private String min = "min";
    private String main_description = "main";
    private JSONObject jsonObject;
    private JSONArray weatherArray;
    private Context context;

    public WeatherListBean(String jsonStr,Context context) throws JSONException {
        this.jsonObject = new JSONObject(jsonStr);
        this.weatherArray = jsonObject.getJSONArray(list);
        this.context=context;
    }

    public String getDesciption(int index) throws JSONException {
        JSONObject dayForecast = weatherArray.getJSONObject(index);
        // description is in a child array called "weather", which is 1 element long.
        JSONObject weatherObject = dayForecast.getJSONArray(weather).getJSONObject(0);
        String description = weatherObject.getString(main_description);
        return description;
    }

    /**
     * Prepare the weather high/lows for presentation.
     */
    private String formatHighLows(double high, double low) {
        // For presentation, assume the user doesn't care about tenths of a degree.
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(context);
        String unitType = preferences.getString(context.getString(R.string.pref_units_key), context.getString(R.string.pref_units_metric));
        if (unitType.equals(context.getString(R.string.pref_units_imperial))) {
             high = (high * 1.8) + 32;
            low = (low * 1.8) + 32;
             }
        long roundedHigh = Math.round(high);
        long roundedLow = Math.round(low);
        String highLowStr = roundedHigh + "/" + roundedLow;
        return highLowStr;
    }

    private String getReadableDateString(long time) {
        // Because the API returns a unix timestamp (measured in seconds),
        // it must be converted to milliseconds in order to be converted to valid date.
        SimpleDateFormat shortenedDateFormat = new SimpleDateFormat("EEE MMM dd");
        String format = shortenedDateFormat.format(time);
        return format;
    }


    public String[] getWeatherDataFromJson(int numDays) throws JSONException {
        String[] resultStrs = new String[numDays];
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR,-1);
        for (int i = 0; i < numDays; i++) {
            calendar.add(Calendar.DAY_OF_YEAR,1);
            String day=getReadableDateString(calendar.getTimeInMillis());
            resultStrs[i] = day + " - " + getDesciption(i) + " - " + getWeatherTemp(i);
        }
        return resultStrs;
    }

    public String getWeatherTemp(int index) throws JSONException {
        JSONObject dayForecast = weatherArray.getJSONObject(index);
        JSONObject temperatureObject = dayForecast.getJSONObject(temp);
        double high = temperatureObject.getDouble(max);
        double low = temperatureObject.getDouble(min);
        return formatHighLows(high, low);
    }
}
