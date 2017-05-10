package com.zt.tz.sunshine;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

/**
 * Created by zhangtong on 2017-05-10 11:58
 * QQ:xxxxxxxx
 */

public class SettingActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference_general);
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_location_key)));
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_units_key)));
    }

    private void bindPreferenceSummaryToValue(Preference preference){
        preference.setOnPreferenceChangeListener(this);
        onPreferenceChange(preference, PreferenceManager.getDefaultSharedPreferences(preference.getContext()).getString(preference.getKey(),""));

    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String value=newValue.toString();
        if(preference instanceof ListPreference){
            ListPreference listPreference= (ListPreference) preference;
            int indexOfValue = listPreference.findIndexOfValue(value);
            if(indexOfValue>=0){
                preference.setSummary(listPreference.getEntries()[indexOfValue]);
            }
        }else {
            preference.setSummary(value);
        }
        return true;
    }
}
