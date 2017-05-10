package com.zt.tz.sunshine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.zt.tz.sunshine.utils.HttpUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class MainActivity extends AppCompatActivity {
    private static final String TAG="zhangtong";
    private TextView tv;
    private String  url="http://samples.openweathermap.org/data/2.5/weather?q=London&appid=b1b15e88fa797225412429c1c50c122a1";
    private String  url2="http://samples.openweathermap.org/data/2.5/weather?q=London&appid=b1b15e88fa797225412429c1c50c122a1";
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Object obj = msg.obj;
            tv.setText((String)obj);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv= (TextView) findViewById(R.id.tv_show_info);
        getSupportFragmentManager().beginTransaction().add(R.id.container,new ForeCastFragment()).commit();

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        HttpUtils httpUtils=new HttpUtils();
                        String string = httpUtils.getString(url);
                        Log.d("zhangtong", "onCreate: "+string);
                        Message msg=new Message();
                        msg.obj=string;
                        mHandler.sendMessage(msg);
                    }
                }).start();

            }
        });


//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                HttpUtils httpUtils=new HttpUtils();
//                String result = httpUtils.getString(url);
//                Log.d("zhangtong", "onCreate: "+result);
//                tv.setText(result);
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.setting){
            startActivity(new Intent(MainActivity.this,SettingsActivity.class));
            return true;
        }
        if(item.getItemId()==R.id.item){
            startActivity(new Intent(MainActivity.this,SettingActivity.class));
            return true;
        }

        if(item.getItemId()==R.id.action_map){
            SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);
            String location = preferences.getString(getString(R.string.pref_location_key), getString(R.string.pref_location_default));
            Uri uri=Uri.parse("geo:0,0?").buildUpon().appendQueryParameter("q",location).build();
            Intent intent=new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            Log.d(TAG, "onOptionsItemSelected: "+uri.toString());
            intent.setData(uri);
            if(intent.resolveActivity(getPackageManager())!=null){
                startActivity(intent);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class PlaceHolderFragment extends Fragment{
        private static final String TAG = "ZHANGTONG";

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View inflate = inflater.inflate(R.layout.fragment_listview, container,false);
            ListView viewById = (ListView) inflate.findViewById(R.id.list_view);
            String[] data={
                    "Mon 6/23 - Sunny - 31/17",
                    "Tue 6/24 - Foggy - 21/8",
                    "Wed 6/25 - Cloudy - 22/17",
                    "Thurs 6/26 - Rainy - 18/11",
                    "Fri 6/27 - Foggy - 21/10",
                    "Sat 6/28 - TRAPPED IN WEATHERSTATION - 23/18",
                    "Sun 6/29 - Sunny - 20/7"
            };
            Log.d(TAG, "onCreateView: ");
            List<String> datas=new ArrayList<String>(Arrays.asList(data));
            ArrayAdapter<String> adapter=new ArrayAdapter<String>(inflate.getContext(),R.layout.fragment_item,R.id.content,datas);
            viewById.setAdapter(adapter);
            return inflate;
        }
    }
}
