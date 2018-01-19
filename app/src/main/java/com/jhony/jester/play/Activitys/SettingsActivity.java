package com.jhony.jester.play.Activitys;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jhony.jester.play.R;
import com.jhony.jester.play.Utils.Constants;
import com.jhony.jester.play.Utils.DataSingleton;

import de.hdodenhof.circleimageview.CircleImageView;


public class SettingsActivity extends AppCompatActivity {

    ImageButton back, about;
    CircleImageView mUserImage;
    TextView mUserNameSettings, mMusic, mSound, mRotation, mVibration;
    Button signOut;
    Animation spin;
    DataSingleton dataSingleton;
    MaterialDialog.Builder dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        about = findViewById(R.id.about_sett);
        back = findViewById(R.id.back_btn_sett);
        mUserImage = findViewById(R.id.user_image_sett);
        mUserNameSettings = findViewById(R.id.user_name_sett);
        mMusic = findViewById(R.id.music_sett);
        mSound = findViewById(R.id.sound_sett);
        mRotation = findViewById(R.id.rotation_sett);
        mVibration = findViewById(R.id.vibrate_sett);
        signOut = findViewById(R.id.sign_out_sett);

//        spin = AnimationUtils.loadAnimation(this, R.anim.spin);

        initiation();

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingsActivity.this, AboutActivity.class));
            }
        });

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
                if (dataSingleton.isMusic()) {
                    dataSingleton.setMusic(false);
                    mMusic.setTextColor(getResources().getColor(R.color.gray));
                    mMusic.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_no_music), null, null, null);
                } else {
                    dataSingleton.setMusic(true);
                    mMusic.setTextColor(getResources().getColor(R.color.primary));
                    mMusic.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_music_note_black_24dp), null, null, null);
                }
            }
        });

        mRotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (dataSingleton.getGetRotation()) {
                    case Constants.ROTATION:
                        dataSingleton.setGetRotation(Constants.PORTRAIT);
                        mRotation.setText(Constants.PORTRAIT);
                        mRotation.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_portrait_only), null, null, null);
                        break;
                    case Constants.PORTRAIT:
                        dataSingleton.setGetRotation(Constants.LANDSCAPE);
                        mRotation.setText(Constants.LANDSCAPE);
                        mRotation.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_landscape_only), null, null, null);
                        break;
                    case Constants.LANDSCAPE:
                        dataSingleton.setGetRotation(Constants.ROTATION);
                        mRotation.setText(Constants.ROTATION);
                        mRotation.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_screen_rotation_black_24dp), null, null, null);
                        break;
                }
            }
        });

        mSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dataSingleton.isSound()) {
                    dataSingleton.setSound(false);
                    mSound.setTextColor(getResources().getColor(R.color.gray));
                    mSound.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_no_sound), null, null, null);
                } else {
                    dataSingleton.setSound(true);
                    mSound.setTextColor(getResources().getColor(R.color.primary));
                    mSound.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_sound), null, null, null);
                }
            }
        });

        mVibration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dataSingleton.isVibration()) {
                    dataSingleton.setVibration(false);
                    mVibration.setTextColor(getResources().getColor(R.color.gray));
                    mVibration.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_no_vibration), null, null, null);

                } else {
                    dataSingleton.setVibration(true);
                    mVibration.setTextColor(getResources().getColor(R.color.primary));
                    mVibration.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_vibration_black_24dp), null, null, null);
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

    private void initiation() {
        dataSingleton = DataSingleton.getOneInstance();
        mUserNameSettings.setText(dataSingleton.getmUserName());
        if (dataSingleton.isMusic()) {
            mMusic.setTextColor(getResources().getColor(R.color.primary));
            mMusic.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_music_note_black_24dp), null, null, null);
        } else {
            mMusic.setTextColor(getResources().getColor(R.color.gray));
            mMusic.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_no_music), null, null, null);
        }

        if (dataSingleton.isSound()) {
            mSound.setTextColor(getResources().getColor(R.color.primary));
            mSound.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_sound), null, null, null);
        } else {
            mSound.setTextColor(getResources().getColor(R.color.gray));
            mSound.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_no_sound), null, null, null);
        }
        if (dataSingleton.isVibration()) {
            mVibration.setTextColor(getResources().getColor(R.color.primary));
            mVibration.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_vibration_black_24dp), null, null, null);
        } else {
            mVibration.setTextColor(getResources().getColor(R.color.gray));
            mVibration.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_no_vibration), null, null, null);
        }
        switch (dataSingleton.getGetRotation()) {
            case Constants.ROTATION: mRotation.setText(Constants.ROTATION);
                mRotation.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_screen_rotation_black_24dp), null, null, null);
                break;
            case Constants.PORTRAIT:mRotation.setText(Constants.PORTRAIT);
                mRotation.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_portrait_only), null, null, null);
                break;
            case Constants.LANDSCAPE:mRotation.setText(Constants.LANDSCAPE);
                mRotation.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_landscape_only), null, null, null);
                break;
        }
    }

    private void showEditDialog() {
        dialog = new MaterialDialog.Builder(this);
        dialog.title("User Name")
                .titleColor(getResources().getColor(R.color.accent))
                .input(dataSingleton.getmUserName(), null, true, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        if (!TextUtils.isEmpty(input)) {
                            dataSingleton.setmUserName(input.toString());
                            mUserNameSettings.setText(input);
                        }
                    }
                }).show();
    }
}
