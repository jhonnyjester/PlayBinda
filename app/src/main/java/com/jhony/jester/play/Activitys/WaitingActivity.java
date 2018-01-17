package com.jhony.jester.play.Activitys;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.nearby.Nearby;
import com.jhony.jester.play.Adapters.MyRecyclerAdapter;
import com.jhony.jester.play.Connections.EverythingNearby;
import com.jhony.jester.play.R;
import com.jhony.jester.play.Utils.Constants;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.jhony.jester.play.Utils.Constants.HOST;
import static com.jhony.jester.play.Utils.Constants.JOIN;
import static com.jhony.jester.play.Utils.Constants.JOINED;
import static com.jhony.jester.play.Utils.Constants.RECYCLER;
import static com.jhony.jester.play.Utils.Constants.WHICH;
import static com.jhony.jester.play.Utils.DataSingleton.isHost;
import static com.jhony.jester.play.Utils.DataSingleton.isVisible;

public class WaitingActivity extends AppCompatActivity {

    ListViewCompat listViewCompat;
    EditText mPassword;
    CircleImageView mLock;
    ImageButton host_set;
    Button mReady;
    RecyclerView mRecycler;
    MyRecyclerAdapter myRecyclerAdapter;
    Animation slideUp, slideDown, spin;
    ConstraintLayout mHostCL, mChatCL;
    boolean isLocked = false, playerStatus = true;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Nearby.getConnectionsClient(getApplicationContext()).stopDiscovery();
        Nearby.getConnectionsClient(getApplicationContext()).stopAdvertising();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hosting_layout);

        mHostCL = findViewById(R.id.host_set_cl);
        mChatCL = findViewById(R.id.chat_cl);
        mPassword = findViewById(R.id.pass_et);
        mLock = findViewById(R.id.lock);
        host_set = findViewById(R.id.host_settings);
        mReady = findViewById(R.id.start_ready);
        mRecycler = findViewById(R.id.recycler_hosting);
        listViewCompat = findViewById(R.id.lv_game_size);

        slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        slideDown = AnimationUtils.loadAnimation(this, R.anim.slide_down);
        spin = AnimationUtils.loadAnimation(this, R.anim.spin);

        myRecyclerAdapter = new MyRecyclerAdapter(this, RECYCLER);
        mRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecycler.setItemAnimator(new DefaultItemAnimator());
        mRecycler.setAdapter(myRecyclerAdapter);

        switch (getIntent().getStringExtra(WHICH)) {
            case HOST:
            mHostCL.setVisibility(View.GONE);

                break;

            case JOINED:
                host_set.setVisibility(View.INVISIBLE);
                mHostCL.setVisibility(View.GONE);
                listViewCompat.setClickable(false);
                mReady.setText("YES! LET'S GO!");

                break;
            default:
                finish();
        }

        host_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isVisible){
                    host_set.startAnimation(spin);
                    isVisible = false;
                    mHostCL.setVisibility(View.GONE);
                    mChatCL.setVisibility(View.VISIBLE);
                } else {
                    host_set.startAnimation(spin);
                    isVisible = true;
                    mHostCL.setVisibility(View.VISIBLE);
                    mChatCL.setVisibility(View.GONE);
                }
            }
        });

        mReady.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (getIntent().getStringExtra(WHICH)) {
                    case HOST:
                        //check if all are ready and then proceed
                        startActivity(new Intent(WaitingActivity.this, GameActivity.class));
                        break;
                    case JOINED:
                        if (playerStatus) {
                            playerStatus = false;
                            mReady.setText("Wait! I'M not Ready");
                            mReady.setBackgroundColor(getResources().getColor(R.color.gray));
                        } else {
                            playerStatus = true;
                            mReady.setText("Yes! Let's Go!");
                            mReady.setBackground(getResources().getDrawable(R.drawable.button_background));
                        }
                        break;
                }
            }
        });

            /*    mReady.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        if (getIntent().getStringExtra(WHICH) == JOINED) {
                            mReady.setClickable(true);
                            mReady.setText("Yes! Let's Go!");
                        }
                        return true;
                    }
                });*/

        mLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLocked) {
                    isLocked = false;
                    mLock.setImageResource(R.drawable.ic_lock_open);
                    mPassword.startAnimation(slideDown);
                } else {
                    isLocked = true;
                    mPassword.setVisibility(View.VISIBLE);
                    mPassword.startAnimation(slideUp);
                    mLock.setImageResource(R.drawable.ic_lock_close);
                }
            }
        });

        mRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    if (mReady.getVisibility() == View.VISIBLE) {
                        //hide it
                        mReady.setVisibility(View.GONE);
                    }
                } else if (dy < 0) {
                    if (mReady.getVisibility() != View.VISIBLE) {
                        //show it
                        mReady.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

}

