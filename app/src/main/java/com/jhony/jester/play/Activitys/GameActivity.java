package com.jhony.jester.play.Activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.connection.ConnectionsClient;
import com.google.android.gms.nearby.connection.Payload;
import com.jhony.jester.play.Adapters.MyRecyclerAdapter;
import com.jhony.jester.play.Interfaces.BindaItemClickListener;
import com.jhony.jester.play.Interfaces.SplitListener;
import com.jhony.jester.play.R;
import com.jhony.jester.play.Utils.DataSingleton;

import java.util.Arrays;
import java.util.Collections;

import static com.jhony.jester.play.Utils.Constants.GRID;
import static com.jhony.jester.play.Utils.DataSingleton.allPlayer;
import static com.jhony.jester.play.Utils.DataSingleton.currentNumber;
import static com.jhony.jester.play.Utils.DataSingleton.didWin;
import static com.jhony.jester.play.Utils.DataSingleton.endPoints;
import static com.jhony.jester.play.Utils.DataSingleton.gameSize;
import static com.jhony.jester.play.Utils.DataSingleton.hostEndPoint;
import static com.jhony.jester.play.Utils.DataSingleton.isHost;
import static com.jhony.jester.play.Utils.DataSingleton.isTicked;
import static com.jhony.jester.play.Utils.DataSingleton.mUserName;
import static com.jhony.jester.play.Utils.DataSingleton.myId;
import static com.jhony.jester.play.Utils.DataSingleton.myTurn;
import static com.jhony.jester.play.Utils.DataSingleton.numbers;

public class GameActivity extends AppCompatActivity implements BindaItemClickListener, SplitListener {

    String numPayload;
    MyRecyclerAdapter myRecyclerAdapter;
    RecyclerView mRecycler;
    TextView[] binda = new TextView[5];
    Animation bounce, spin;
    TextView number;
    ConnectionsClient connectionsClient;
    int playerSize;
    String winnerPayload;
    int turnCount = -1;
    int pos;
    int[] rowCount = new int[gameSize], columnCount = new int[gameSize];
    int d1Count = 0, d2Count = 0;
    int column;
    int bindaCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        didWin = false;
        playerSize = allPlayer.size();
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


        numbers.clear();
        isTicked.clear();

        for (int i = 0; i < gameSize; i++) {
            rowCount[i] = 0;
            columnCount[i] = 0;
        }

        for (int i = 0; i < (gameSize * gameSize); i++) {
            numbers.add(i + 1);
            isTicked.add(false);
        }

        Collections.shuffle(numbers);

        myRecyclerAdapter = new MyRecyclerAdapter(this, GRID);
        mRecycler.setLayoutManager(new GridLayoutManager(getApplicationContext(), gameSize));
        mRecycler.setItemAnimator(new DefaultItemAnimator());
        mRecycler.setAdapter(myRecyclerAdapter);
        myRecyclerAdapter.setListener(this);

        if (isHost) {
            worryAboutTurns();
        }

    }

    private void worryAboutTurns() {
        turnCount++;
        if (turnCount == playerSize) turnCount = 0;
        if (turnCount == playerSize - 1) myTurn = true;
        else {
            String turnPayload = "3" + "^" +
                    true;
            connectionsClient.sendPayload(allPlayer.get(turnCount).getEndPointId(),
                    Payload.fromBytes(turnPayload.getBytes()));
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

            Log.i("GAMEON", "LOOP: " + r + "\nCOL: " + columnCount[r] + "\nROW: " + rowCount[r] + "\nD1: " + d1Count + "\nD2: " + d2Count + "\nBC: " + bindaCount);
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
            Toast.makeText(this, "YOU WON", Toast.LENGTH_SHORT).show();
            didWin = true;
            if (isHost) {
                winnerPayload = "6" + "^" +
                        true + "^" +
                        myId;
                connectionsClient.sendPayload(endPoints,
                        Payload.fromBytes(winnerPayload.getBytes()));
            }
            winnerPayload = "6" + "^" +
                    true + "^" +
                    myId;
            connectionsClient.sendPayload(hostEndPoint,
                    Payload.fromBytes(winnerPayload.getBytes()));
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
        numPayload = "2" + "^" +
                DataSingleton.numbers.get(position);
        connectionsClient.
                sendPayload(hostEndPoint,
                        Payload.fromBytes(numPayload.getBytes()));
        DataSingleton.myTurn = false;
    }

    @Override
    public void onSplitCompleted(int id) {
        if (id == 2) {
            number.setText(currentNumber);
            pos = Arrays.binarySearch(numbers.toArray(), currentNumber);
            DataSingleton.isTicked.set(pos, true);

            if (isHost) {
                worryAboutTurns();
            }
        }
    }
}
