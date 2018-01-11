package com.jhony.jester.play.Activitys;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jhony.jester.play.R;
import com.jhony.jester.play.Utils.Constants;
import com.jhony.jester.play.Utils.DataSingleton;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.jhony.jester.play.Utils.DataSingleton.mUserName;

public class SettingsActivity extends AppCompatActivity {

    ImageButton back;
    CircleImageView mUserImage;
    TextView mUserNameSettings, mMusic, mSound, mRotation, mVibration;
    Button signOut;
    MaterialDialog.Builder dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        back = findViewById(R.id.back_btn_sett);
        mUserImage = findViewById(R.id.user_image_sett);
        mUserNameSettings = findViewById(R.id.user_name_sett);
        mMusic = findViewById(R.id.music_sett);
        mSound = findViewById(R.id.sound_sett);
        mRotation = findViewById(R.id.rotation_sett);
        mVibration = findViewById(R.id.vibrate_sett);
        signOut = findViewById(R.id.sign_out_sett);

        mUserNameSettings.setText(mUserName);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        mUserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Image Picker
            }
        });

        mUserNameSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditDialog();
            }
        });

        mMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DataSingleton.isMusic) {
                    DataSingleton.isMusic = false;
                    mMusic.setTextColor(getResources().getColor(R.color.gray));
                    mMusic.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_no_sound), null);
                } else {
                    DataSingleton.isMusic = true;
                    mMusic.setTextColor(getResources().getColor(R.color.primary));
                    mMusic.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_music_note_black_24dp), null);
                }
            }
        });

        mRotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (DataSingleton.getRotation) {
                    case Constants.ROTATION:
                        DataSingleton.getRotation = Constants.PORTRAIT;
                        mRotation.setText(Constants.PORTRAIT);
                        mRotation.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_portrait_only), null, null, null);
                        break;
                    case Constants.PORTRAIT:
                        DataSingleton.getRotation = Constants.LANDSCAPE;
                        mRotation.setText(Constants.LANDSCAPE);
                        mRotation.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_landscape_only), null, null, null);
                        break;
                    case Constants.LANDSCAPE:
                        DataSingleton.getRotation = Constants.ROTATION;
                        mRotation.setText(Constants.ROTATION);
                        mRotation.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_screen_rotation_black_24dp), null, null, null);
                        break;
                }
            }
        });

        mSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DataSingleton.isSound) {
                    DataSingleton.isSound = false;
                    mSound.setTextColor(getResources().getColor(R.color.gray));
                    mSound.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_no_sound), null);
                } else {
                    DataSingleton.isSound = true;
                    mSound.setTextColor(getResources().getColor(R.color.primary));
                    mSound.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_sound), null);
                }
            }
        });

        mVibration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DataSingleton.isVibration) {
                    DataSingleton.isVibration = false;
                    mVibration.setTextColor(getResources().getColor(R.color.gray));
                    mVibration.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_no_sound), null, null, null);

                } else {
                    DataSingleton.isVibration = true;
                    mVibration.setTextColor(getResources().getColor(R.color.primary));
                    mVibration.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_vibration_black_24dp),null, null,  null);
                }
            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //signout firebase
            }
        });


    }

    private void showEditDialog() {
        dialog = new MaterialDialog.Builder(this);
        dialog.title("User Name")
                .titleColor(getResources().getColor(R.color.accent))
                .input(mUserName, null, true, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        if (!TextUtils.isEmpty(input)) {
                            mUserName = input.toString();
                            mUserNameSettings.setText(input);
                        }
                    }
                }).show();
    }
}
