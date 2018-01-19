package com.jhony.jester.play.Activitys;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jhony.jester.play.Connections.EverythingNearby;
import com.jhony.jester.play.Model.AllPlayers;
import com.jhony.jester.play.Model.PlayerInfo;
import com.jhony.jester.play.R;
import com.jhony.jester.play.Utils.Constants;
import com.jhony.jester.play.Utils.DataSingleton;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.jhony.jester.play.Utils.Constants.HOST;
import static com.jhony.jester.play.Utils.DataSingleton.allPlayer;
import static com.jhony.jester.play.Utils.DataSingleton.isHost;
import static com.jhony.jester.play.Utils.DataSingleton.mCurrentProgress;
import static com.jhony.jester.play.Utils.DataSingleton.mLevelUpTarget;
import static com.jhony.jester.play.Utils.DataSingleton.mUserLevel;
import static com.jhony.jester.play.Utils.DataSingleton.mUserName;


public class SplashActivity extends AppCompatActivity{

    public static final String TAG = "Splash Activity";
    TextView userName, currentProgress, levelUpTarget, hostGame, joinGame, userLevel;
    ProgressBar progressBar;
    ImageButton settings;
    EverythingNearby everythingNearby;
    CircleImageView userImage, logo;
    Animation up, down, left, right, spin;
    Intent intent;
    MaterialDialog.Builder dialog;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        userName = (TextView) findViewById(R.id.user_name);
        currentProgress = (TextView) findViewById(R.id.current_progress_tv);
        levelUpTarget = (TextView) findViewById(R.id.lvl_up_target);
        hostGame = (TextView) findViewById(R.id.host_tv);
        joinGame = (TextView) findViewById(R.id.join_tv);
        userLevel = (TextView) findViewById(R.id.exp_tv);
        settings = (ImageButton) findViewById(R.id.settings_btn);
        userImage = (CircleImageView) findViewById(R.id.user_image);
        logo = (CircleImageView) findViewById(R.id.circleImageView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

//        up = new AnimationUtils().loadAnimation(getApplicationContext(), R.anim.slide_up);
//        down = new AnimationUtils().loadAnimation(getApplicationContext(), R.anim.slide_down);
//        right = new AnimationUtils().loadAnimation(getApplicationContext(), R.anim.right);
//        left = new AnimationUtils().loadAnimation(getApplicationContext(), R.anim.left);

//        spin = AnimationUtils.loadAnimation(this, R.anim.spin);

        setValues();

        userName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showEditDialog();
                return true;
            }
        });

        intent = new Intent(getApplicationContext(), WaitingActivity.class);

        hostGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra(getResString(R.string.which), HOST);
                isHost = true;
                everythingNearby = new EverythingNearby(getApplicationContext(), Constants.HOST);
                allPlayer.add(0,
                        new AllPlayers(
                                new PlayerInfo(DataSingleton.mUserName,
                                        //also add image
                                        DataSingleton.mUserDesc,
                                        DataSingleton.mUserLevel)));
                startActivity(intent);
            }
        });

        joinGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isHost = false;
                everythingNearby = new EverythingNearby(getApplicationContext(), Constants.JOIN);
                startActivity(new Intent(SplashActivity.this, JoinActivity.class));
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        setValues();
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
                            userName.setText(input);
                        }
                    }
                }).show();
    }

    public void setValues() {
        userName.setText(mUserName);
        currentProgress.setText(String.valueOf(mCurrentProgress));
        levelUpTarget.setText(String.valueOf(mLevelUpTarget));
        progressBar.setProgress(75);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
        userLevel.setText(String.valueOf(mUserLevel));
        userImage.setImageResource(R.drawable.vector);
//        logo.setImageResource();
    }

    private String getResString(int id){
        return getResources().getString(id);
    }

 /*   private void openDialog(String which) {
         CustomDialog.newInstance(getApplicationContext(), which).show(getSupportFragmentManager(), "hostOrJoin");
        Dialog dialog;

        dialog.create();
        dialog.show();

    }*/

   /* private void invertVisibility(String which) {
        switch (which) {
            case HOST:
                if (hosting.getVisibility() == View.VISIBLE) {
                    hosting.setVisibility(View.GONE);
                    joining.setVisibility(View.GONE);
                } else {
                    hosting.setVisibility(View.VISIBLE);
                    joining.setVisibility(View.GONE);
                }
                break;

            case JOIN:
                if (joining.getVisibility() == View.VISIBLE) {
                    hosting.setVisibility(View.GONE);
                    joining.setVisibility(View.GONE);
                } else {
                    hosting.setVisibility(View.GONE);
                    joining.setVisibility(View.VISIBLE);
                }
        }
    }*/

   /* public void openBottomSheet (View v) {
        View view = getLayoutInflater ().inflate (R.layout.hosting_layout, null);

        final Dialog mBottomSheetDialog = new Dialog (SplashActivity.this,
                R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView (view);
        mBottomSheetDialog.setCancelable (true);
        mBottomSheetDialog.getWindow ().setLayout (LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow ().setGravity (Gravity.BOTTOM);
        mBottomSheetDialog.show ();

    }*/
}

