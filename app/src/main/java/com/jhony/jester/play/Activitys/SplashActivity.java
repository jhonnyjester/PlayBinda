package com.jhony.jester.play.Activitys;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jhony.jester.play.Adapters.CustomDialog;
import com.jhony.jester.play.R;
import com.jhony.jester.play.Utils.Constants;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.jhony.jester.play.Utils.Constants.HOST;
import static com.jhony.jester.play.Utils.Constants.JOIN;
import static com.jhony.jester.play.Utils.Constants.WHICH;
import static com.jhony.jester.play.Utils.DataSingleton.mCurrentProgress;
import static com.jhony.jester.play.Utils.DataSingleton.mLevelUpTarget;
import static com.jhony.jester.play.Utils.DataSingleton.mUserImage;
import static com.jhony.jester.play.Utils.DataSingleton.mUserLevel;
import static com.jhony.jester.play.Utils.DataSingleton.mUserName;


public class SplashActivity extends AppCompatActivity {

    TextView userName, currentProgress, levelUpTarget, hostGame, joinGame, userLevel;
    ProgressBar progressBar;
    ImageButton settings;
    CircleImageView userImage, logo;
    Animation up, down, left, right;
    Intent intent;
    public static final String TAG = "Splash Activity";

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

        userName.setText(mUserName);
        userImage.setImageResource(R.drawable.vector);
//        logo.setImageResource();
        currentProgress.setText(String.valueOf(mCurrentProgress));
        levelUpTarget.setText(String.valueOf(mLevelUpTarget));
        progressBar.setProgress(75);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
        userLevel.setText(String.valueOf(mUserLevel));

        intent = new Intent(getApplicationContext(), ConnectPlayersActivity.class);

        hostGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra(WHICH, HOST);
                startActivity(intent);
            }
        });

        joinGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra(WHICH, JOIN);
                startActivity(intent);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
            }
        });

    }

/*    private void openDialog(String which) {
//         CustomDialog.newInstance(getApplicationContext(), which).show(getSupportFragmentManager(), "hostOrJoin");
        Dialog dialog;
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(hosting.getWidth(), hosting.getHeight());
//        dialog = new Dialog(new ContextThemeWrapper(this, R.style.DialogSlideAnimation));
//        dialog.setContentView(R.layout.hosting_layout);
//        dialog.create();
//        dialog.show();
        getWindow().setGravity(Gravity.BOTTOM);
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

