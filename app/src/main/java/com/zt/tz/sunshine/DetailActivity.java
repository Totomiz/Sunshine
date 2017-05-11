package com.zt.tz.sunshine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG="zhangtong";

    private String mForcastStr;
    private static final String FORECAST_SHARE_HASHTAG = " #SunshineApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent=getIntent();
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            mForcastStr = intent.getStringExtra(Intent.EXTRA_TEXT);
        }
        if(savedInstanceState ==null){
            getSupportFragmentManager().beginTransaction().add(R.id.container,new PlaceHolderFragment()).commit();
        }
    }


    public static class PlaceHolderFragment extends Fragment{
         private TextView textView;
        private String intentStringExtra;
        private static final String FORECAST_SHARE_HASHTAG = " #SunshineApp";
        public PlaceHolderFragment() {
            setHasOptionsMenu(true);
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            inflater.inflate(R.menu.share_provider,menu);
            MenuItem item = menu.findItem(R.id.menu_item_share);
            ShareActionProvider actionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
            if (actionProvider != null) {
                actionProvider.setShareIntent(creatShareForeCastIntent());
            }else{
                Log.d(TAG, "share intent is null ");
            }
        }

        private Intent creatShareForeCastIntent() {
            Intent intent=new Intent(Intent.ACTION_SEND);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT,intentStringExtra+FORECAST_SHARE_HASHTAG);
            return intent;
        }

        @Nullable
         @Override
         public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
             View inflate = inflater.inflate(R.layout.fragment_detail, container, false);
             textView= (TextView) inflate.findViewById(R.id.tv_detail);
             Intent intent=getActivity().getIntent();
             if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
                 intentStringExtra = intent.getStringExtra(Intent.EXTRA_TEXT);
                 textView.setText(intentStringExtra);
             }

             return inflate;
         }
     }
}
