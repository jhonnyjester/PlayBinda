package com.jhony.jester.play.Activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.provider.MediaStore;
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
import com.jhony.jester.play.Utils.FileUtils;
import com.jhony.jester.play.Utils.Preferences;

import de.hdodenhof.circleimageview.CircleImageView;

public class PreferencesSettings extends Preferences  {

    private static final int CAMERA_REQUEST_CODE = 789;
    private static final int GALLERY_REQUEST_CODE = 456;
    private static final String EXTERNAL_DIRECTORY = "PlayBinda/profilePic";
    private final String TAG = "PreferencesSettings";
    LinearLayout cameraLayout, galleryLayout, characterLayout;
    FrameLayout characterFrame;
    BottomSheetDialog dialog;
    CircleImageView playerImage;
    Button signOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences_settings);

        playerImage = findViewById(R.id.user_image_sett);
        signOut = findViewById(R.id.sign_out_sett);

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



}

