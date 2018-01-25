package com.jhony.jester.play.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.DragAndDropPermissions;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.connection.ConnectionsClient;
import com.google.android.gms.nearby.connection.Payload;
import com.jhony.jester.play.Adapters.MyRecyclerAdapter;
import com.jhony.jester.play.R;
import com.jhony.jester.play.Utils.DataSingleton;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.jhony.jester.play.Utils.Constants.HOST;
import static com.jhony.jester.play.Utils.Constants.JOINED;
import static com.jhony.jester.play.Utils.Constants.RECYCLER;


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
    ConnectionsClient connectionsClient;
    DataSingleton dataSingleton;
    JSONObject statusPayload;
    boolean isLocked = false, playerStatus = true;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        connectionsClient.stopDiscovery();
        connectionsClient.stopAdvertising();
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hosting_layout);

        statusPayload = new JSONObject();
        dataSingleton = DataSingleton.getOneInstance();

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
        connectionsClient = Nearby.getConnectionsClient(this);
        switch (getIntent().getIntExtra(getResString(R.string.which), 0)) {
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
                if (dataSingleton.isVisible()) {
                    host_set.startAnimation(spin);
                    dataSingleton.setVisible(false);
                    mHostCL.setVisibility(View.GONE);
                    mChatCL.setVisibility(View.VISIBLE);
                } else {
                    host_set.startAnimation(spin);
                    dataSingleton.setVisible(true);
                    mHostCL.setVisibility(View.VISIBLE);
                    mChatCL.setVisibility(View.GONE);
                }
            }
        });

        mReady.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (getIntent().getIntExtra(getResString(R.string.which), 0)) {
                    case HOST:
                        //check if all are ready and then proceed
                        startActivity(new Intent(WaitingActivity.this, GameActivity.class));
                        break;
                    case JOINED:
                        if (playerStatus) {
                            playerStatus = false;
                            mReady.setText("Wait! I'M not Ready");
                            mReady.setBackgroundColor(getResources().getColor(R.color.gray));

                            try {
                                statusPayload.put(getResString(R.string.payload_id), 5);
                                statusPayload.put(getResString(R.string.user_id), dataSingleton.getMyId());
                                statusPayload.put(getResString(R.string.status), false);
                                connectionsClient.sendPayload(dataSingleton.getHostEndPoint(),
                                        Payload.fromBytes(statusPayload.toString().getBytes()));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            playerStatus = true;
                            mReady.setText("Yes! Let's Go!");
                            mReady.setBackground(getResources().getDrawable(R.drawable.button_background));

                            try {
                                statusPayload.put(getResString(R.string.user_id), dataSingleton.getMyId());
                                statusPayload.put(getResString(R.string.payload_id), 5);
                                statusPayload.put(getResString(R.string.status), true);
                                connectionsClient.sendPayload(dataSingleton.getHostEndPoint(),
                                        Payload.fromBytes(statusPayload.toString().getBytes()));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
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

    private String getResString(int id) {
        return getResources().getString(id);
    }

}

