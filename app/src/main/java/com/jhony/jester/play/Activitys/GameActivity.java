package com.jhony.jester.play.Activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.connection.ConnectionsClient;
import com.google.android.gms.nearby.connection.Payload;
import com.jhony.jester.play.Adapters.MyRecyclerAdapter;
import com.jhony.jester.play.Connections.AnalysePayload;
import com.jhony.jester.play.Interfaces.BindaItemClickListener;
import com.jhony.jester.play.Interfaces.SplitListener;
import com.jhony.jester.play.Model.GsonModel;
import com.jhony.jester.play.R;
import com.jhony.jester.play.Utils.DataSingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Collections;

import static com.jhony.jester.play.Utils.Constants.GRID;


public class GameActivity extends AppCompatActivity implements BindaItemClickListener, SplitListener {

    TextView
            number,
            playerName;
    TextView[] binda = new TextView[5];
    Animation bounce, spin;
    RecyclerView mRecycler;
    DataSingleton dataSingleton;
    MyRecyclerAdapter myRecyclerAdapter;
    ConnectionsClient connectionsClient;

    int playerSize;
    int turnCount = -1;
    int pos;
    int gameSize;
    int[] rowCount, columnCount;
    int d1Count = 0, d2Count = 0;
    int column;
    int bindaCount = 0;
    AnalysePayload analysePayload;
    JSONObject numPayload;
    JSONObject turnPayLoad;
    JSONObject pickerPayload;
    JSONObject winnerPayload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        dataSingleton = DataSingleton.getOneInstance();

        gameSize = dataSingleton.getGameSize();
        columnCount = new int[gameSize];
        rowCount = new int[gameSize];

        turnPayLoad = new JSONObject();
        numPayload = new JSONObject();
        winnerPayload = new JSONObject();
        pickerPayload = new JSONObject();

        dataSingleton.setDidWin(false);
        playerSize = dataSingleton.getAllPlayer().size();
        connectionsClient = Nearby.getConnectionsClient(this);
        bounce = AnimationUtils.loadAnimation(this, R.anim.bounce);
        spin = AnimationUtils.loadAnimation(this, R.anim.spin);

        mRecycler = findViewById(R.id.grid_recycler_view);
        binda[0] = findViewById(R.id.b);
        binda[1] = findViewById(R.id.i);
        binda[2] = findViewById(R.id.n);
        binda[3] = findViewById(R.id.d);
        binda[4] = findViewById(R.id.a);
        number = findViewById(R.id.number);


        dataSingleton.getNumbers().clear();
        dataSingleton.getIsTicked().clear();

        for (int i = 0; i < gameSize; i++) {
            rowCount[i] = 0;
            columnCount[i] = 0;
        }

        for (int i = 0; i < (gameSize * gameSize); i++) {
            dataSingleton.getNumbers().add(i + 1);
            dataSingleton.getIsTicked().add(false);
        }

        Collections.shuffle(dataSingleton.getNumbers());

        myRecyclerAdapter = new MyRecyclerAdapter(this, GRID);
        mRecycler.setLayoutManager(new GridLayoutManager(getApplicationContext(), gameSize));
        mRecycler.setItemAnimator(new DefaultItemAnimator());
        mRecycler.setAdapter(myRecyclerAdapter);
        myRecyclerAdapter.setListener(this);
        analysePayload.setSplitListener(this);

        if (dataSingleton.isHost()) {
            worryAboutTurns();
        }

    }

    private void worryAboutTurns() {
        turnCount++;
        if (turnCount == playerSize) turnCount = 0;
        if (turnCount == playerSize - 1) dataSingleton.setMyTurn(true);
        else {
            try {
                turnPayLoad.put(getResString(R.string.payload_id), 3);
                turnPayLoad.put(getResString(R.string.user_name), dataSingleton.getAllPlayer().get(turnCount).getPlayerInfo().getmUserName());
                turnPayLoad.put(getResString(R.string.user_id), dataSingleton.getAllPlayer().get(turnCount).getUniqueId());
                playerName.setText(dataSingleton.getAllPlayer().get(turnCount).getPlayerInfo().getmUserName());
                connectionsClient.sendPayload(dataSingleton.getEndPoints(),
                        Payload.fromBytes(turnPayLoad.toString().getBytes()));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void cardClciked(int pos) {
        column = pos % gameSize;
        columnCount[column]++;

        for (int r = 0; r < gameSize; r++) {

            if (columnCount[r] == gameSize) {
                columnCount[r] = 0;
                itsBinda();
            }

            if ((gameSize * r) + column == pos) {
                rowCount[r]++;
                if (rowCount[r] == gameSize) {
                    rowCount[r] = 0;
                    itsBinda();
                }
            }

            if ((gameSize * r) + r == pos) {
                d1Count++;
                if (d1Count == gameSize) {
                    d1Count = 0;
                    itsBinda();
                }
            }

            if ((gameSize * (r + 1)) - (r + 1) == pos) {
                d2Count++;
                if (d2Count == gameSize) {
                    d2Count = 0;
                    itsBinda();
                }
            }
        }
    }

    public void itsBinda() {

        if (bindaCount < 5) {
            binda[bindaCount].startAnimation(bounce);
            binda[bindaCount].setTextColor(getResources().getColor(R.color.accent));
//        binda[bindaCount]
        }
        bindaCount++;

        if (bindaCount == 5) {
            bindaCount = 0;
            dataSingleton.setDidWin(true);
            if (dataSingleton.isHost()) {
                try {
                    winnerPayload.put(getResString(R.string.payload_id), 6);
                    winnerPayload.put(getResString(R.string.user_id), dataSingleton.getMyId());
                    connectionsClient.sendPayload(dataSingleton.getEndPoints(),
                            Payload.fromBytes(winnerPayload.toString().getBytes()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            try {
                winnerPayload.put(getResString(R.string.payload_id), 6);
                winnerPayload.put(getResString(R.string.user_id), dataSingleton.getMyId());
                connectionsClient.sendPayload(dataSingleton.getHostEndPoint(),
                        Payload.fromBytes(winnerPayload.toString().getBytes()));
            } catch (JSONException e) {
                e.printStackTrace();
            }

// TODO: 25-01-2018 open winning page 

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onItemClick(int position) {
        cardClciked(position);

        try {
            numPayload.put(getResString(R.string.payload_id), 2);
            numPayload.put(getResString(R.string.num), dataSingleton.getNumbers().get(position));
            connectionsClient.
                    sendPayload(dataSingleton.getHostEndPoint(),
                            Payload.fromBytes(numPayload.toString().getBytes()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        dataSingleton.setMyTurn(false);
    }

    @Override
    public void onSplitCompleted(GsonModel gsonModel) {
        switch (gsonModel.getPayloadId()) {
            case 2:
                number.setText(dataSingleton.getCurrentNumber());
                pos = Arrays.binarySearch(dataSingleton.getNumbers().toArray(), dataSingleton.getCurrentNumber());
                dataSingleton.getIsTicked().set(pos, true);

                if (dataSingleton.isHost()) {
                    worryAboutTurns();
                }
                break;
            case 3:
                if (gsonModel.getPlayerId().equals(dataSingleton.getMyId())) {
                    dataSingleton.setMyTurn(true);
                    playerName.setText("It's Your Turn");
                } else {
                    playerName.setText(gsonModel.getPlayerName());
                }
                break;
            case 5:
                myRecyclerAdapter.notifyDataSetChanged();
                break;
            case 6:
                break;
        }
    }

    private String getResString(int id) {
        return getResources().getString(id);
    }
}
