package com.zt.tz.sunshine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.zt.tz.sunshine.api.WeatherApi;
import com.zt.tz.sunshine.bean.WeatherListBean;
import com.zt.tz.sunshine.utils.HttpUtils;

import org.json.JSONException;

import java.lang.ref.WeakReference;

/**
 * Created by zhangtong on 2017-05-07 16:59
 * QQ:xxxxxxxx
 */

public class ForeCastFragment extends Fragment {
    private static final String TAG = "zhangtong";
    private TextView textView;
    private ArrayAdapter<String> adapter;
    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_listview, container, false);
        listView = (ListView) inflate.findViewById(R.id.list_view);
        textView = (TextView) inflate.findViewById(R.id.tv_show_info);
//        String[] data = {
//                "Mon 6/23 - Sunny - 31/17",
//                "Tue 6/24 - Foggy - 21/8",
//                "Wed 6/25 - Cloudy - 22/17",
//                "Thurs 6/26 - Rainy - 18/11",
//                "Fri 6/27 - Foggy - 21/10",
//                "Sat 6/28 - TRAPPED IN WEATHERSTATION - 23/18",
//                "Sun 6/29 - Sunny - 20/7"
//        };
//        Log.d(TAG, "onCreateView: ");
//        List<String> datas = new ArrayList<String>(Arrays.asList(data));
//        adapter = new ArrayAdapter<String>(inflate.getContext(), R.layout.fragment_item, R.id.content, datas);
//        listView.setAdapter(adapter);
        setHasOptionsMenu(true);//让fragment 中的菜单选项生效，否者不会显示出菜单选项
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, adapter.getItem(position));
                startActivity(intent);
            }
        });
        return inflate;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.forecast_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_refresh) {
            updateWeather();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateWeather(){
        FetchWeatherTask task = new FetchWeatherTask(textView);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String location = preferences.getString(getString(R.string.pref_location_key),
                getString(R.string.pref_location_default));
        task.execute(WeatherApi.getStringUrl(location));
    }

    @Override
    public void onStart() {
        super.onStart();
        updateWeather();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FetchWeatherTask(textView).execute(WeatherApi.getStringUrl("94043"));
            }
        });
    }

    private class FetchWeatherTask extends AsyncTask<String, Void, String[]> {
        private WeakReference<TextView> textViewWeakReference;

        public FetchWeatherTask(TextView textView) {
            this.textViewWeakReference = new WeakReference<TextView>(textView);
        }

        @Override
        protected String[] doInBackground(String... params) {
            HttpUtils utils = new HttpUtils();
            String resultJson = utils.getString(params[0]);
            Log.d(TAG, "doInBackground: " + resultJson);
            try {
                WeatherListBean weather = new WeatherListBean(resultJson,getActivity());
                return weather.getWeatherDataFromJson(WeatherApi.numDays);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String[] s) {
            super.onPostExecute(s);
            if (textViewWeakReference.get() != null) {
                textViewWeakReference.get().setText(s.toString());
            }
            adapter = new ArrayAdapter<String>(listView.getContext(), R.layout.fragment_item, R.id.content, s);
            listView.setAdapter(adapter);
        }
    }
}
