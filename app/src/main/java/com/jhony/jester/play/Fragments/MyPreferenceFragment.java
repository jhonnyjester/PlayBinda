package com.jhony.jester.play.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;

import com.jhony.jester.play.R;
import com.jhony.jester.play.Utils.Constants;
import com.jhony.jester.play.Utils.DataSingleton;

/**
 * Created by JAR on 27-01-2018.
 */

public class MyPreferenceFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = "MyPreferenceFragment";

    Preference namePref, musicPref, rotationPref, soundPref, vibrationPref, aboutPref, feedbackPref;
    DataSingleton dataSingleton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        namePref = findPreference("name_key");
        musicPref = findPreference("music_key");
        soundPref = findPreference("sound_key");
        vibrationPref = findPreference("vib_key");
        rotationPref = findPreference("rotation_key");
        aboutPref = findPreference("about_preference");
        feedbackPref = findPreference("feedback_preference");

       /* FOR INITIAL SETUP
       -----------NOT REQUIRED NOW--------------

       SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String namePrefValue = preferences.getString("name_key", "Name not found");
        boolean musicPrefValue = preferences.getBoolean("music_key", false);
        boolean soundPrefValue = preferences.getBoolean("sound_key", false);
        boolean vibPrefValue = preferences.getBoolean("vib_key", false);
        boolean rotationPrefValue = preferences.getBoolean("rotation_key", false);*/
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference anyPreference = findPreference(key);
        Log.d(TAG, "onSharedPreferenceChanged: " + key);
        switch (key) {
            case Constants.SP_NAME_KEY:
                anyPreference.setSummary(sharedPreferences.getString(key, " "));
                dataSingleton.setmUserName(sharedPreferences.getString(key, dataSingleton.getmUserName()));
                break;
            case Constants.SP_MUSIC_KEY:
                if (sharedPreferences.getBoolean(key, false)) {
                    anyPreference.setSummary("Music is ON");
                    putOn(1);
                } else {
                    anyPreference.setSummary("Music is OFF");
                    putOff(1);
                }
                break;
            case Constants.SP_SOUND_KEY:
                if (sharedPreferences.getBoolean(key, false)) {
                    anyPreference.setSummary("Sound is ON");
                    putOn(2);
                } else {
                    anyPreference.setSummary("Sound is OFF");
                    putOff(2);
                }
                break;
            case Constants.SP_VIB_KEY:
                if (sharedPreferences.getBoolean(key, false)) {
                    anyPreference.setSummary("Vibration is ON");
                    putOn(3);
                } else {
                    anyPreference.setSummary("Vibration is OFF");
                    putOff(3);
                }
                break;
            case Constants.SP_ROTATION_KEY:
                if (sharedPreferences.getBoolean(key, false)) {
                    anyPreference.setSummary("Rotation is ON");
                    putOn(4);
                } else {
                    anyPreference.setSummary("Rotation is OFF");
                    putOff(4);
                }
                break;
        }
    }

    private void putOn(int whichSwitch) {
        switch (whichSwitch) {
            case 1: //switch on the music
                break;
            case 2://switch on the sound
                break;
            case 3://switch on the vibration
                break;
            case 4://switch rotation
                break;
        }
    }

    private void putOff(int whichSwitch) {
        switch (whichSwitch) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
        }
    }
}

