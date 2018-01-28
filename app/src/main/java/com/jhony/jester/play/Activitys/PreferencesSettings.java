package com.jhony.jester.play.Activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jhony.jester.play.Fragments.MyPreferenceFragment;
import com.jhony.jester.play.R;
import com.jhony.jester.play.Utils.Constants;
import com.jhony.jester.play.Utils.DataSingleton;
import com.jhony.jester.play.Utils.FileUtils;
import com.jhony.jester.play.Utils.Preferences;

import de.hdodenhof.circleimageview.CircleImageView;

public class PreferencesSettings extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener  {

    private static final int CAMERA_REQUEST_CODE = 789;
    private static final int GALLERY_REQUEST_CODE = 456;
    private static final String EXTERNAL_DIRECTORY = "PlayBinda/profilePic";
    private final String TAG = "PreferencesSettings";
    LinearLayout cameraLayout, galleryLayout, characterLayout;
    FrameLayout characterFrame;
    BottomSheetDialog dialog;
    CircleImageView playerImage;
    Button signOut;
    DataSingleton dataSingleton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences_settings);

        playerImage = findViewById(R.id.user_image_sett);
        signOut = findViewById(R.id.sign_out_sett);
        addPreferencesFromResource(R.xml.preferences);
        //loading settings fragment
//        getFragmentManager().beginTransaction().replace(R.id.content, new MyPreferenceFragment()).commit();

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //temporary or permanent signout
                //sign out from firebase
            }
        });

        playerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPicker();
            }
        });

    }

    private void openPicker() {

        //open bottom sheets
        View view = getLayoutInflater().inflate(R.layout.bottom_sheets, null);
        //bottomSheetsLayout = view.findViewById(R.id.bottom_sheet);

        cameraLayout = view.findViewById(R.id.camera_layout);
        galleryLayout = view.findViewById(R.id.gallery_layout);
        characterLayout = view.findViewById(R.id.characters_layout);

        characterFrame = view.findViewById(R.id.characters_frame);

        dialog = new BottomSheetDialog(this);
        dialog.setContentView(view);
        dialog.show();

        cameraLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                characterFrame.setVisibility(View.GONE);
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
                }
            }
        });

        galleryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                characterFrame.setVisibility(View.GONE);
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, GALLERY_REQUEST_CODE);
            }
        });

        characterLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                characterFrame.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        dialog.cancel();
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == CAMERA_REQUEST_CODE) {
                Log.d(TAG, "onActivityResult: Camera     " + data.getData());
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) (extras != null ? extras.get("data") : null);
                if (imageBitmap != null) {
                    playerImage.setImageBitmap(imageBitmap);
                } else Toast.makeText(this, "Couldn't Capture Image", Toast.LENGTH_SHORT).show();
            }
            if (requestCode == GALLERY_REQUEST_CODE) {
                Log.d(TAG, "onActivityResult: Gallery      " + data.getData());
                Uri selectedImageUri = data.getData();
                String selectedImagePath = FileUtils.getPath(getApplicationContext(), selectedImageUri);
                playerImage.setImageURI(Uri.parse(selectedImagePath));
            }
        } else Log.d(TAG, "onActivityResult: NULL");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    public static class MyPreferenceFragment extends PreferenceFragment{

        private static final String TAG = "MyPreferenceFragment";

        Preference namePref, musicPref, rotationPref, soundPref, vibrationPref, aboutPref, feedbackPref;


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

