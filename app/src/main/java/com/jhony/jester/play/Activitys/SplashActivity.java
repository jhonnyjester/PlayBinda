package com.jhony.jester.play.Activitys;

import android.content.Intent;
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
import com.bumptech.glide.Glide;
import com.jhony.jester.play.Connections.EverythingNearby;
import com.jhony.jester.play.Model.AllPlayers;
import com.jhony.jester.play.Model.GsonModel;
import com.jhony.jester.play.Model.PlayerInfo;
import com.jhony.jester.play.R;
import com.jhony.jester.play.Utils.Constants;
import com.jhony.jester.play.Utils.DataSingleton;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.jhony.jester.play.Utils.Constants.HOST;

public class SplashActivity extends AppCompatActivity {

    TextView userName, currentProgress, levelUpTarget, hostGame, joinGame, userLevel;
    ProgressBar progressBar;
    ImageButton settings;
    EverythingNearby everythingNearby;
    CircleImageView userImage, logo;
    Animation up, down, left, right, spin;
    Intent intent;
    DataSingleton dataSingleton;
    MaterialDialog.Builder dialog;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        dataSingleton = DataSingleton.getOneInstance();

        userName = findViewById(R.id.user_name);
        currentProgress = findViewById(R.id.current_progress_tv);
        levelUpTarget = findViewById(R.id.lvl_up_target);
        hostGame = findViewById(R.id.host_tv);
        joinGame = findViewById(R.id.join_tv);
        userLevel = findViewById(R.id.exp_tv);
        settings = findViewById(R.id.settings_btn);
        userImage = findViewById(R.id.user_image);
        logo = findViewById(R.id.circleImageView);
        progressBar = findViewById(R.id.progressBar);

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
                // TODO: 25-01-2018 open a dialog for game size and password
                intent.putExtra(getResString(R.string.which), HOST);
                dataSingleton.setHost(true);
                everythingNearby = new EverythingNearby(getApplicationContext(), Constants.HOST);
                // TODO: 25-01-2018 generate unique id and store it in datasingleton.myId
                dataSingleton.getAllPlayer().add(0,
                        new AllPlayers(
                                new PlayerInfo(dataSingleton.getmUserName(),
                                        //add unique id
                                        //also add image
                                        dataSingleton.getmUserDesc(),
                                        dataSingleton.getmUserLevel())));
                startActivity(intent);
            }
        });

        joinGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataSingleton.setHost(false);
                startActivity(new Intent(SplashActivity.this, JoinActivity.class));
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), PreferencesSettings.class));

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
        dialog.title("Player Name")
                .titleColor(getResources().getColor(R.color.accent))
                .input(dataSingleton.getmUserName(), null, true, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        if (!TextUtils.isEmpty(input)) {
                            dataSingleton.setmUserName(input.toString());
                            userName.setText(input);
                        }
                    }
                }).show();
    }

    public void setValues() {
        userName.setText(dataSingleton.getmUserName());
        currentProgress.setText(String.valueOf(dataSingleton.getmCurrentProgress()));
        levelUpTarget.setText(String.valueOf(dataSingleton.getmLevelUpTarget()));
        progressBar.setProgress(75);
        userLevel.setText(String.valueOf(dataSingleton.getmUserLevel()));
        Glide.with(getApplicationContext()).load(R.drawable.vector).into(userImage);
//        logo.setImageResource();
    }

    private String getResString(int id) {
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

