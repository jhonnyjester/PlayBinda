package com.jhony.jester.play.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.connection.Payload;
import com.jhony.jester.play.Interfaces.SplitListener;
import com.jhony.jester.play.Model.AllPlayers;
import com.jhony.jester.play.R;
import com.jhony.jester.play.Utils.DataSingleton;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.jhony.jester.play.Utils.Constants.JOINED;
import static com.jhony.jester.play.Utils.Constants.WHICH;

public class JoinActivity extends AppCompatActivity implements SplitListener {
    EditText gamePass;
    CircleImageView hostsImage;
    TextView hostsName;
    Button joinGame;
    ImageButton left, right;
    boolean isPass = true;
    Intent joinIntent;
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.joining_layout);

        left = findViewById(R.id.left_arrow);
        right = findViewById(R.id.right_arrow);
        hostsImage = findViewById(R.id.hosts_image);
        hostsName = findViewById(R.id.hosts_name);
        gamePass = findViewById(R.id.game_pass);
        joinGame = findViewById(R.id.join_game);

        joinIntent = new Intent(JoinActivity.this, WaitingActivity.class);
        joinIntent.putExtra(WHICH, JOINED);

        joinGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPass)
                    if (TextUtils.isEmpty(gamePass.getText().toString())) {
                        Toast.makeText(JoinActivity.this, "Enter The  Password", Toast.LENGTH_SHORT).show();
                        return;
                    }
                DataSingleton.allPlayer.add(0,
                        new AllPlayers(DataSingleton.hosts.get(pos).getEndPointId(),
                                DataSingleton.hosts.get(pos).getPlayerInfo()));

                String joinPayload = "4" + "^" +
                        DataSingleton.mUserName + "^" +
                        DataSingleton.mUserDesc + "^" +
                        DataSingleton.mUserLevel + "^"
                        //send image string
                        ;

                Nearby.getConnectionsClient(getApplicationContext())
                        .sendPayload(DataSingleton.hosts.get(pos).getEndPointId(),
                                Payload.fromBytes(joinPayload.getBytes()));

                DataSingleton.hostEndPoint = DataSingleton.hosts.get(pos).getEndPointId();

                startActivity(joinIntent);
                finish();
            }
        });

    }


    @Override
    public void onSplitCompleted(int id) {
        //show the hosts
        if (id == 1) {
            pos = 0;
            hostsName.setText(DataSingleton.hosts.get(pos).toString());
        }
    }
}
