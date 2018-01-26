package com.jhony.jester.play.Activitys;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.jhony.jester.play.R;
import com.jhony.jester.play.Utils.Preferences;

import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PreferencesSettings extends Preferences {

    private static final int CAMERA_REQUEST_CODE = 789;
    private static final int GALLERY_REQUEST_CODE = 456;
    private static final String EXTERNAL_DIRECTORY = "PlayBinda/profilePic";
    private final String TAG = "PreferencesSettings";
    LinearLayout cameraLayout, galleryLayout, characterLayout;
    FrameLayout cameraFrame, galleryFrame, characterFrame;
    BottomSheetDialog dialog;
    BottomSheetBehavior behavior;
    CircleImageView playerImage;
    Button signOut;
    ListView galleryListView;
    ImageButton editImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences_settings);

        playerImage = findViewById(R.id.user_image_sett);
        signOut = findViewById(R.id.sign_out_sett);
        editImage = findViewById(R.id.image_edit);


        //loading settings fragment
        getFragmentManager().beginTransaction().replace(R.id.content, new MyPreferenceFragment()).commit();

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
                editImage.performClick();
            }
        });

        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open image picker
                openPicker();
            }
        });
    }

    private void openPicker() {

        //open bottom sheets
        View view = getLayoutInflater().inflate(R.layout.bottom_sheets, null);
        //bottomSheetsLayout = view.findViewById(R.id.bottom_sheet);

        galleryListView = view.findViewById(R.id.gallery_listview);

        cameraLayout = view.findViewById(R.id.camera_layout);
        galleryLayout = view.findViewById(R.id.gallery_layout);
        characterLayout = view.findViewById(R.id.characters_layout);

        cameraFrame = view.findViewById(R.id.camera_frame);
        galleryFrame = view.findViewById(R.id.gallery_frame);
        characterFrame = view.findViewById(R.id.characters_frame);

        dialog = new BottomSheetDialog(this);
        dialog.setContentView(view);
        dialog.show();

        cameraLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraFrame.setVisibility(View.VISIBLE);
                galleryFrame.setVisibility(View.GONE);
                characterFrame.setVisibility(View.GONE);

            }
        });

        galleryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraFrame.setVisibility(View.GONE);
                galleryFrame.setVisibility(View.VISIBLE);
                characterFrame.setVisibility(View.GONE);
                Intent intent = new Intent();
                intent.setType("image");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                List<ResolveInfo> launchables = getPackageManager().queryIntentActivities(intent, 0);
                Collections.sort(launchables, new ResolveInfo.DisplayNameComparator(getPackageManager()));
                ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), R.layout.players_single_item, R.id.user_name_players, launchables);
                galleryListView.setAdapter(arrayAdapter);


//                startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_REQUEST_CODE);
            }
        });

        characterLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraFrame.setVisibility(View.GONE);
                galleryFrame.setVisibility(View.GONE);
                characterFrame.setVisibility(View.VISIBLE);

            }
        });



    /*    Intent intent = new Intent();
        intent.setType("image*//*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_REQUEST_CODE);*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST_CODE) {
            Log.d(TAG, "onActivityResult: " + data.getData());
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public static class MyPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }
    }
}

