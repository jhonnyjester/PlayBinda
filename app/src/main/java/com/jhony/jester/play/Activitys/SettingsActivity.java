package com.jhony.jester.play.Activitys;

import android.content.Intent;
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

import static com.jhony.jester.play.Utils.DataSingleton.getRotation;
import static com.jhony.jester.play.Utils.DataSingleton.isMusic;
import static com.jhony.jester.play.Utils.DataSingleton.isSound;
import static com.jhony.jester.play.Utils.DataSingleton.isVibration;
import static com.jhony.jester.play.Utils.DataSingleton.mUserName;

public class SettingsActivity extends AppCompatActivity {

    ImageButton back, about;
    CircleImageView mUserImage;
    TextView mUserNameSettings, mMusic, mSound, mRotation, mVibration;
    Button signOut;
    Animation spin;
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
                if (DataSingleton.isMusic) {
                    DataSingleton.isMusic = false;
                    mMusic.setTextColor(getResources().getColor(R.color.gray));
                    mMusic.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_no_music), null, null, null);
                } else {
                    DataSingleton.isMusic = true;
                    mMusic.setTextColor(getResources().getColor(R.color.primary));
                    mMusic.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_music_note_black_24dp), null, null, null);
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
                    mSound.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_no_sound), null, null, null);
                } else {
                    DataSingleton.isSound = true;
                    mSound.setTextColor(getResources().getColor(R.color.primary));
                    mSound.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_sound), null, null, null);
                }
            }
        });

        mVibration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DataSingleton.isVibration) {
                    DataSingleton.isVibration = false;
                    mVibration.setTextColor(getResources().getColor(R.color.gray));
                    mVibration.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_no_vibration), null, null, null);

                } else {
                    DataSingleton.isVibration = true;
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
        mUserNameSettings.setText(mUserName);
        if (isMusic) {
            mMusic.setTextColor(getResources().getColor(R.color.primary));
            mMusic.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_music_note_black_24dp), null, null, null);
        } else {
            mMusic.setTextColor(getResources().getColor(R.color.gray));
            mMusic.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_no_music), null, null, null);
        }

        if (isSound) {
            mSound.setTextColor(getResources().getColor(R.color.primary));
            mSound.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_sound), null, null, null);
        } else {
            mSound.setTextColor(getResources().getColor(R.color.gray));
            mSound.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_no_sound), null, null, null);
        }
        if (isVibration) {
            mVibration.setTextColor(getResources().getColor(R.color.primary));
            mVibration.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_vibration_black_24dp), null, null, null);
        } else {
            mVibration.setTextColor(getResources().getColor(R.color.gray));
            mVibration.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_no_vibration), null, null, null);
        }
        switch (getRotation) {
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
