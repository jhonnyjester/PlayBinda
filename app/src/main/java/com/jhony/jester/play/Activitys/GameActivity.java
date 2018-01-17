package com.jhony.jester.play.Activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.jhony.jester.play.Adapters.MyRecyclerAdapter;
import com.jhony.jester.play.R;
import com.jhony.jester.play.Utils.Constants;
import com.jhony.jester.play.Utils.DataSingleton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static com.jhony.jester.play.Utils.Constants.GRID;
import static com.jhony.jester.play.Utils.Constants.RECYCLER;
import static com.jhony.jester.play.Utils.DataSingleton.didWin;
import static com.jhony.jester.play.Utils.DataSingleton.gameSize;
import static com.jhony.jester.play.Utils.DataSingleton.isTicked;
import static com.jhony.jester.play.Utils.DataSingleton.numbers;

public class GameActivity extends AppCompatActivity {

    MyRecyclerAdapter myRecyclerAdapter;
    RecyclerView mRecycler;
    TextView[] binda = new TextView[5];
    Animation bounce, spin;
    TextView number;

    int[] rowCount = new int[gameSize], columnCount = new int[gameSize];
    int d1Count = 0, d2Count = 0;
    int column;
    int bindaCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        didWin = false;

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

    }

    public void cardClciked(int pos) {
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
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
