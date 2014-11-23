
package com.cire.gridimagesearch;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class PrefActivity extends PreferenceActivity implements OnPreferenceChangeListener {
    private SharedPreferences sharedPreferences;
    private Preference imgszPref;
    private Preference imgcolorPref;
    private Preference imgtypePref;
    private Preference sitesearchPref;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        imgszPref = (Preference)findPreference("imgsz");
        imgcolorPref = (Preference)findPreference("imgcolor");
        imgtypePref = (Preference)findPreference("imgtype");
        sitesearchPref = (Preference)findPreference("as_sitesearch");

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        // setup setOnPreferenceChangeListener
        imgszPref.setOnPreferenceChangeListener(this);
        imgcolorPref.setOnPreferenceChangeListener(this);
        imgtypePref.setOnPreferenceChangeListener(this);
        sitesearchPref.setOnPreferenceChangeListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String imgsz = sharedPreferences.getString("imgsz", "");

        if (imgsz.length() != 0) {
            imgszPref.setSummary(imgsz);
        } else {
            imgszPref.setSummary("any");
        }

        String imgcolor = sharedPreferences.getString("imgcolor", "");
        if (imgcolor.length() != 0) {
            imgcolorPref.setSummary(imgcolor);
        } else {
            imgcolorPref.setSummary("any");
        }

        String imgtype = sharedPreferences.getString("imgtype", "");
        if (imgtype.length() != 0) {
            imgtypePref.setSummary(imgtype);
        } else {
            imgtypePref.setSummary("any");
        }

        String sitesearchSetting = sharedPreferences.getString("as_sitesearch", "");
        if (sitesearchSetting.length() != 0) {
            sitesearchPref.setSummary(sitesearchSetting);
        } else {
            sitesearchPref.setSummary("any");
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String summary = "";
        if (newValue != null && String.valueOf(newValue).length() != 0) {
            summary = String.valueOf(newValue);
        } else {
            summary = "any";
        }
        ((Preference)findPreference(preference.getKey())).setSummary(summary);

        return true;
    }
}
